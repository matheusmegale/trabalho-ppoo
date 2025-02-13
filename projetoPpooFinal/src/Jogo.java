import java.util.ArrayList;
import java.io.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;

/**
 * A classe Jogo representa o ambiente e as regras do jogo "Estelar: A Grande Fuga".
 * Ela controla a logica do jogo, incluindo a criacao de ambientes, a leitura de perguntas
 * de um arquivo, a inicializacao de temporizadores e a interacao com o jogador.
 * @author Eduardo Ruan Guimaraes Fonseca, Lucas de Oliveira Pereira, 
 *         Matheus de Paula Megale, Renan Augusto da Silva
 */

public class Jogo {

    /**
     * Objeto responsavel por analisar os comandos inseridos pelo jogador.
     */
    private Analisador analisador;
    
    /**
     * Ambiente atual em que o jogador se encontra.
     */
    private Ambiente ambienteAtual;

    /**
     * Lista de questoes que podem ser sorteadas durante o jogo.
     */
    private ArrayList<Questao> listaDeQuestoes;

    /**
     * Lista de salas presentes no jogo.
     */
    private ArrayList<Sala> listaDeSalas;

    /**
     * Lista de corredores presentes no jogo.
     */
    private ArrayList<Corredor> listaDeCorredores;
    
    /**
     * Indica se o jogo foi terminado.
     */
    private boolean terminado;

    /**
     * Objeto que representa o jogador.
     */
    private Jogador jogador;

    /**
     * Objeto Timer para gerenciar o tempo do jogo.
     */
    private Timer timer;

     /**
     * Tempo, em segundos, que o jogador tem para ganhar o jogo.
     */
    private int segundos;

    /**
     * String que contem as tarefas a serem realizadas pelo jogador.
     */
    private String tarefas;

    /**
     * Interface grafica do jogo.
     */
    private TelaJogo telaJogo;


    /**
     * Construtor da classe Jogo.
     * Inicializa as variaveis do jogo, cria ambientes, le perguntas de um arquivo,
     * sorteia salas que terao itens e tarefas, sorteia corredores que terao guardas,
     * inicializa o temporizador e imprime na interface.
     */
    public Jogo() {
        terminado = false; // A variavel "terminado" determina quando o programa encerrara. Como o jogo esta comecando, ela eh inicializa com false
        
        telaJogo = new TelaJogo(); // Criando um objeto TelaJogo
        telaJogo.exibir(); // Chama o metodo exibir(), que exibe a interfa grafica

        tarefas = "\n\nTAREFAS A REALIZAR: \n\n"; // Inicializa a String 'tarefas' com um titulo e depois concatena com as descricoes 

        segundos = 300; // Quantos segundos o jogador tera para ganhar

        timer = new Timer(); // Criando um objeto Timer
        analisador = new Analisador(telaJogo); // Criando um objeto Analisador
        jogador = new Jogador(); // Criando um objeto Jogador
        
        listaDeSalas = new ArrayList<>(); // Criando um ArrayList de Salas
        listaDeCorredores = new ArrayList<>(); // Criando um ArrayList de Corredores
        listaDeQuestoes = new ArrayList<>(); // Criando um ArrayList de Questoes

        criarAmbientes(); // Criando os ambientes do jogo
        lerArquivo("Perguntas.txt"); // Fazendo a leitura do arquivo que contem as questoes
        sortearSalaQueTeraoItens(); // Sorteando as salas que terao tarefas a serem realizadas
        sortearCorredoresQueTeraoGuardas(); // Sorteando os corredores que terao guardas (so pode ser os superiores )
        imprimirNaInterface(); // Metodo que imprime informacoes iniciais na interface grafica.
        

    }
    
    /**
     * Metodo responsavel por imprimir informacoes iniciais na interface grafica.
     */
    private void imprimirNaInterface(){
        telaJogo.imprimirTarefas(tarefas); // Imprime as tarefas a serem realizadas para o usuario na interface grafica
        telaJogo.imprimirSegundos("\n\n\n\n\n        TEMPO RESTANTE: " + formatarTempo(segundos)); // imprime o tempo que o usuario tem para jogar
    }
    
    /**
     * Inicia o jogo, exibindo as boas-vindas na interface grafica.
     * Aguarda que o jogador digite "iniciar" para iniciar o cronômetro e a fase de jogo.
     * Durante a espera, exibe mensagens na interface informando as instrucoes ao jogador.
     * Se o jogador digitar "iniciar", exibe uma mensagem adicional e chama o metodo jogar() para iniciar a fase de jogo.
     * Se o jogador digitar algo diferente de "iniciar", exibe uma mensagem de digitacao invalida.
     */
    public void iniciarJogo(){ 
        imprimirBoasVindas(); // Imprime as boas vindas para o usuario na interce grafica
        String resposta = "";
        telaJogo.imprimir("\n");
        telaJogo.imprimir("Digite 'iniciar' para que o cronometro inicialize" + "\n");
        while(resposta != "iniciar" && segundos > 0){ // Aguarda que o usuario digite "iniciar" para que o jogo comece.
            resposta = telaJogo.getComando();
            if(resposta.equals("iniciar")){
                telaJogo.imprimir("Digite 'ajuda' se voce precisar de ajuda. \n");
                jogar(); // Chama o metodo jogar() para iniciar a fase de jogo.
            }else {
                telaJogo.imprimir("Digitacao invalida" + "\n"); // Caso o usuario digite qualquer coisa que nao seja "iniciar"
            }
        }
    }
    
    /**
     * Inicia um temporizador (timer) que executa uma tarefa (task) em intervalos regulares.
     * Utiliza o metodo scheduleAtFixedRate do objeto Timer para agendar uma tarefa de execucao repetida em intervalos fixos.
     * A tarefa e definida como uma instância anônima de TimerTask, sobrescrevendo o metodo run().
     * O metodo run() sera executado a cada intervalo definido pelo scheduleAtFixedRate.
     * Verifica se ainda ha tempo restante (segundos > 0); caso positivo, decrementa os segundos e imprime o tempo restante na interface grafica.
     * Se o tempo acabar, imprime a mensagem de derrota e encerra a execucao da tarefa agendada, impedindo que seja executada novamente.
     * Executa a cada 1000 milissegundos (1 segundo).
     */
    public void iniciarTimer() { // Este metodo inicia um temporizador (timer) que executa uma tarefa (task) em intervalos regulares.
        timer.scheduleAtFixedRate(new TimerTask() { // O metodo scheduleAtFixedRate do objeto Timer.
                                                    // Agenda uma tarefa para execucao repetida em intervalos fixos.
                                                    // A tarefa e definida como uma instância anônima de TimerTask.
            @Override // Metodo run() esta sobrescrevendo um metodo da classe anônima TimerTask.
            public void run() { // Este metodo sera executado a cada intervalo definido pelo scheduleAtFixedRate.
                if (segundos > 0) { // Verififica se ainda ha tempo restante, caso tenha o decrementa .
                    segundos--;
                    telaJogo.imprimirSegundos("\n\n\n\n\n        TEMPO RESTANTE: " + formatarTempo(segundos)); //formata o tempo em minutos e segundos
                } else {
                    //telaJogo.imprimir("Voce perdeu! O tempo acabou e o alarme foi disparado!");
                    telaJogo.imprimirSegundos("\n\n\n\n\n             O JOGO ACABOU!!");
                    parar(); // Encerra a execucao da tarefa agendada e impede que ela seja executada novamente.
                }
            }
        }, 0, 1000); // Executar a cada 1000 milissegundos (1 segundo).
    }
    
    /**
     * Para o temporizador, encerrando a execucao da tarefa agendada.
     */
    private void parar() {
        timer.cancel(); // Chama o metodo cancel() do objeto Timer, encerrando a execucao da tarefa agendada
    }

    /**
     * Formata o tempo em minutos e segundos.
     * @param segundos Tempo em segundos.
     * @return Tempo formatado (MM:SS).
     */
    public String formatarTempo(int segundos) { // Formata o tempo para ser impresso
        int minutos = segundos / 60;
        int segundosRestantes = segundos % 60;
        return String.format("%02d:%02d", minutos, segundosRestantes);
    }
    
    /**
     * Le as perguntas de um arquivo e armazena em uma lista de questoes.
     * @param nomeArquivo Nome do arquivo que contem as perguntas.
     * @throws FileNotFoundException Se o arquivo com o nome especificado nao for encontrado.
     * @throws IOException Se houver um problema na leitura do arquivo.
     */
    private void lerArquivo(String nomeArquivo) { // Este metodo e responsavel por fazer a leitura do arquivo e
                                                  // armazenar as perguntas e repostas dentro do ArrayList
        try (BufferedReader arq = new BufferedReader(new FileReader(nomeArquivo))) {
            
            String linha = arq.readLine(); // O metodo readLine retorna uma string com a linha lida do arquivo

            while (linha != null) {
                String[] campos = linha.split(","); // Divide a linha usando ',' como delimitador.

                Questao questao = new Questao(campos[0], campos[1]); // Cria um objeto Questao com os campos da linha e adiciona a lista de questoes.

                listaDeQuestoes.add(questao);

                linha = arq.readLine(); // A variavel linha recebera valor null quando o arquivo chegar ao fim
            }

        } catch (FileNotFoundException e) {
            System.out.println("Impossivel abrir o arquivo " + nomeArquivo);
        } catch (IOException e) {
            System.out.println("Problema na leitura do arquivo " + nomeArquivo);
        }
    }
    
    /**
     * Sorteia 5 salas que terao Itens e Tarefas a serem realizadas.
     * Utiliza o metodo de sorteio para obter IDs de salas no intervalo [2, 26].
     * Os IDs sorteados correspondem as salas que terao Itens e Tarefas.
     * Apenas salas com IDs pares no intervalo [2, 26] podem ser sorteadas.
     * Os IDs sorteados tem seus atributos "foiSorteado" definidos como true, indicando que terao Itens e Tarefas.
     * O metodo tambem chama o metodo tarefasRealizar() para verificar as tarefas a serem realizadas em cada sala sorteada.
     */
    private void sortearSalaQueTeraoItens() { // Sorteia 5 ambientes que terao Itens e Tarefas a serem realizadas
        Random random = new Random();
        ArrayList<Integer> numerosSorteados = new ArrayList<>(); // ArrayList para guardar os nimeros sorteados
        while (numerosSorteados.size() < 5) { // Sorteia nimeros pares dentro do intervalo [2, 26]
            int numeroAleatorio = 2 + 2 * random.nextInt(12); // Multiplica por 2 para ter certeza que serao nimeros pares, somo 2 para ter certeza que o menor nimero possivel seja 2
            if (!numerosSorteados.contains(numeroAleatorio)) { //Impede que tenha nimeros repetidos no ArrayList
                numerosSorteados.add(numeroAleatorio); // Adiciona o nimero sorteado no ArrayList
            }
        }
        for (int i = 0; i < numerosSorteados.size(); i++) { // Percorro o ArrayList de nimeros dos sorteados
            int num = numerosSorteados.get(i); // Pego um nimero do ArrayList de nimeros dos sorteados
            for (int j = 0; j < listaDeSalas.size(); j++) { //  o ArrayList de salas existentes
                Ambiente ambiente = listaDeSalas.get(j); // Pego uma sala do ArrayList de salas existentes
                if (ambiente.getID() == (num)) { // Verifico se o ID da minha sala e igual ao um nimero sorteado
                    ambiente.setFoiSorteado(true); // Caso ele seja igual, chamo um metodo que "seta" como true aquela sala, dizendo que ela foi sorteada
                    tarefasRealizar(ambiente); // Chamo metodo tarefasRealizar() para verificar qual tarefa eu tenho naquela sala
                }
            }
        }
    }
    
    /**
     * Metodo responsavel por concatenar as tarefas que serao realizadas pelo usuario.
     * @param ambiente Ambiente que contem a tarefa a ser realizada.
     */
    private void tarefasRealizar(Ambiente ambiente) {// Metodo responsavel por concatenar as tarefas que serao realizadas pelo usuario.
    
        // Converte o ambiente para o tipo Sala, assumindo que o ambiente seja realmente do tipo Sala.
        Sala sala = (Sala) ambiente;
    
        // Concatena a tarefa relacionada ao item presente na sala ao conjunto de tarefas.
        tarefas += "\n" + sala.getTarefaRelacionadaAoItem() + "\n";
    }
    

    /**
     * Sorteia dois corredores (superiores e/ou inferiores) que terao guardas.
     * Utiliza o metodo de sorteio para obter IDs de corredores no intervalo [3, 9].
     * Os IDs sorteados correspondem aos corredores que podem ter guardas.
     * Apenas corredores com IDs impares no intervalo [3, 9] podem ser sorteados.
     * Os corredores sorteados tem seus atributos "foiSorteado" definidos como true, indicando que terao guardas.
     */
    private void sortearCorredoresQueTeraoGuardas() { // Apenas dois corredores (superiores e/ou inferiores) terao
                                                      // guardas
        ArrayList<Integer> sorteados = new ArrayList<>(); // ArrayList para guardar os valores dos ID's sorteados
        Random random = new Random();
        while (sorteados.size() < 2) { // Este while ira sortear dois valores impares no intervalo [3, 9] 
                                       // que correspondem aos ID's dos corredores que podem ter guardas
            int numeroAleatorio = (2 * random.nextInt(3)) + 3; // Neste trecho, apenas os valores impares no intervalo [3, 9] podem ser sorteados
            if (!sorteados.contains(numeroAleatorio)) { // Verificacao para saber se o nimero sorteado ja foi sorteado antes (para nao adicionarmos valores iguais no Array de sorteados)
                sorteados.add(numeroAleatorio); // Adiciona o valor sorteado no ArrayList de sorteados
            }
        }

        for (int i = 0; i < sorteados.size(); i++) { // Percorre o ArrayList de sorteados 
            int num = sorteados.get(i); // Criamos uma variavel para guardar o valor da posicao "i" do ArrayList de sorteados
            for (int j = 0; j < listaDeCorredores.size(); j++) { // Percorre o ArrayList de Corredores
                Ambiente ambiente = listaDeCorredores.get(j); // Criamos uma variavel do tipo estatico Ambiente para guardar o objeto da posicao "j" do ArrayList de Corredores
                if (ambiente.getID() == (num)) { // Se o ID da variavel Ambiente for igual ao valor do numero sorteado, entao esse ambiente, que e um corredor, tera um guarda
                    ambiente.setFoiSorteado(true); // Define o atributo booleano "temGuarda" do objeto como true
                }
            }
        }
    }

    /**
     * Cria os ambientes do jogo, incluindo salas e corredores, ajustando suas conexoes e itens.
     * As salas ja serao instanciadas com seus respectivos itens e tarefas relacionadas com esses itens
     * Mas tanto os itens quanto as tarefas so serao disponiveis para os ambientes que forem sorteados
     * As salas e corredores sao instanciados e adicionados as listas correspondentes.
     * As saidas entre ambientes sao ajustadas para conectar os diferentes espacos no jogo.
     * O ambiente inicial e definido como a sala de prisao.
     */
    private void criarAmbientes() {
        Sala prisao, refeitorio, escudos, navegacao, armas, o2, eletrica, administracao, garagem;
        Sala comunicacoes, seguranca, motorSuperior, motorInferior, reator;
        Corredor corredorSD, corredorSE, corredorID, corredorIE, corredorPrinc, corredorAdm, corredorReator;

        // Instanciando e adicionando as salas
        prisao = new Sala("na prisao", 1, null);
        listaDeSalas.add(prisao);
        refeitorio = new Sala("no refeitorio", 2, new Item("Mantimentos", "Pegue mantimentos no refeitorio"));
        listaDeSalas.add(refeitorio);
        escudos = new Sala("na sala de escudos", 4, new Item("Colete", "Pegue o colete nos escudos"));
        listaDeSalas.add(escudos);
        navegacao = new Sala("na navegacao", 6, new Item("Mapa", "Pegue o mapa na navegacao"));
        listaDeSalas.add(navegacao);
        armas = new Sala("na sala de armas", 8, new Item("Arma", "Pegue uma arma na sala de armas"));
        listaDeSalas.add(armas);
        o2 = new Sala("na sala de oxigenio", 10, new Item("Galao de oxigenio", "Pegue o galao de oxigenio na o2"));
        listaDeSalas.add(o2);
        eletrica = new Sala("na sala de eletrica", 12, new Item("Bateria", "Pegue a bateria na eletrica"));
        listaDeSalas.add(eletrica);
        administracao = new Sala("na sala de administracao", 14, new Item("Codigo de seguranca", "Pegue o codigo de seguranca na administracao"));
        listaDeSalas.add(administracao);
        garagem = new Sala("na garagem", 16, new Item("Chave da nave", "Pegue a chave da nave na garagem"));
        listaDeSalas.add(garagem);
        comunicacoes = new Sala("na sala de comunicacoes", 18, new Item("Radio", "Pegue o radio nas comunicacoes"));
        listaDeSalas.add(comunicacoes);
        seguranca = new Sala("na sala de seguranca", 20, new Item("Lanterna", "Pegue a lanterna na sala de seguranca"));
        listaDeSalas.add(seguranca);
        motorSuperior = new Sala("no motor superior", 22, new Item("Gasolina", "Pegue a gasolina no motor superior"));
        listaDeSalas.add(motorSuperior);
        motorInferior = new Sala("no motor inferior", 24, new Item("Chave de fenda", "Pegue uma chave de fenda no motor inferior"));
        listaDeSalas.add(motorInferior);
        reator = new Sala("no reator", 26, new Item("Motor", "Pegue o motor no reator"));
        listaDeSalas.add(reator);

        // Instanciando e adicionando os corredores
        corredorSD = new Corredor("no corredor superior direito", 3);
        listaDeCorredores.add(corredorSD);
        corredorSE = new Corredor("no corredor superior esquerdo", 5);
        listaDeCorredores.add(corredorSE);
        corredorID = new Corredor("no corredor inferior direito", 7);
        listaDeCorredores.add(corredorID);
        corredorIE = new Corredor("no corredor inferior esquerdo", 9);
        listaDeCorredores.add(corredorIE);
        corredorPrinc = new Corredor("no corredor principal", 11);
        listaDeCorredores.add(corredorPrinc);
        corredorAdm = new Corredor("no corredor da administracao", 13);
        listaDeCorredores.add(corredorAdm);
        corredorReator = new Corredor("no corredor do reator", 15);
        listaDeCorredores.add(corredorReator);

        // Ajustando as saidas entre os ambientes
        prisao.ajustarSaida("refeitorio", refeitorio);

        refeitorio.ajustarSaida("esquerda", corredorSE);
        refeitorio.ajustarSaida("direita", corredorSD);
        refeitorio.ajustarSaida("baixo", corredorAdm);
        refeitorio.ajustarSaida("prisao", prisao);

        escudos.ajustarSaida("direita", corredorSE);
        escudos.ajustarSaida("baixo", corredorPrinc);

        navegacao.ajustarSaida("direita", corredorPrinc);
        navegacao.ajustarSaida("reator", reator);

        armas.ajustarSaida("cima", corredorPrinc);
        armas.ajustarSaida("direita", corredorIE);

        eletrica.ajustarSaida("baixo", corredorIE);
        eletrica.ajustarSaida("administracao", administracao);

        garagem.ajustarSaida("esquerda", corredorIE);
        garagem.ajustarSaida("direita", corredorID);
        garagem.ajustarSaida("cima", corredorAdm);
        garagem.ajustarSaida("comunicacoes", comunicacoes);

        motorSuperior.ajustarSaida("esquerda", corredorSD);
        motorSuperior.ajustarSaida("baixo", corredorReator);

        motorInferior.ajustarSaida("cima", corredorReator);
        motorInferior.ajustarSaida("esquerda", corredorID);

        reator.ajustarSaida("esquerda", corredorReator);
        reator.ajustarSaida("navegacao", navegacao);

        seguranca.ajustarSaida("direita", corredorReator);

        comunicacoes.ajustarSaida("baixo", corredorID);
        comunicacoes.ajustarSaida("garagem", garagem);

        o2.ajustarSaida("esquerda", corredorPrinc);

        administracao.ajustarSaida("direita", corredorAdm);
        administracao.ajustarSaida("eletrica", eletrica);

        corredorSD.ajustarSaida("esquerda", refeitorio);
        corredorSD.ajustarSaida("direita", motorSuperior);

        corredorID.ajustarSaida("direita", motorInferior);
        corredorID.ajustarSaida("cima", comunicacoes);
        corredorID.ajustarSaida("esquerda", garagem);

        corredorSE.ajustarSaida("esquerda", escudos);
        corredorSE.ajustarSaida("direita", refeitorio);

        corredorIE.ajustarSaida("direita", garagem);
        corredorIE.ajustarSaida("esquerda", armas);
        corredorIE.ajustarSaida("cima", eletrica);

        corredorReator.ajustarSaida("esquerda", seguranca);
        corredorReator.ajustarSaida("direita", reator);
        corredorReator.ajustarSaida("cima", motorSuperior);
        corredorReator.ajustarSaida("baixo", motorInferior);

        corredorPrinc.ajustarSaida("cima", escudos);
        corredorPrinc.ajustarSaida("baixo", armas);
        corredorPrinc.ajustarSaida("esquerda", navegacao);
        corredorPrinc.ajustarSaida("direita", o2);

        corredorAdm.ajustarSaida("baixo", garagem);
        corredorAdm.ajustarSaida("cima", refeitorio);
        corredorAdm.ajustarSaida("esquerda", administracao);

        ambienteAtual = prisao; // Define a sala de prisao como o ambiente inicial do jogo
    }

    /**
     * Inicia a fase de jogo, iniciando o temporizador e executando um loop ate que o jogo seja concluido.
     * Dentro do loop, obtem o comando do jogador atraves do Analisador, processa o comando e verifica se o jogo deve ser encerrado.
     * O jogo e encerrado se o jogador realizar todas as tarefas, se o tempo se esgotar ou se for explicitamente terminado.
     * Apos a conclusao do jogo, exibe uma mensagem de agradecimento ao jogador.
     */
    public void jogar() {
        iniciarTimer(); // Inicia o temporizador

        while (!terminado) { // Loop principal do jogo
            Comando comando = analisador.pegarComando(); // Obtem o comando do jogador
            terminado = processarComando(comando); // Processa o comando e verifica se o jogo deve ser encerrado

            if(segundos == 0){ // Verifica se o tempo esgotou
                terminado = true;
            }
        }
         telaJogo.imprimir("Obrigado por jogar. Ate mais!" + "\n"); // Exibe mensagem de agradecimento ao jogador
    }

    /**
     * Imprime uma mensagem de boas-vindas ao jogador, fornecendo informacoes sobre o jogo e sua premissa.
     * Chama o metodo para imprimir a localizacao atual do jogador.
     */
    public void imprimirBoasVindas() {
        telaJogo.imprimir("\n" + "Ola, jogador" + "\n");
        telaJogo.imprimir("\n");
        telaJogo.imprimir("Bem-vindo ao Estelar: A Grande Fuga!" + "\n");
        telaJogo.imprimir("Estelar: A Grande Fuga eh um novo jogo de aventura, incrivelmente legal. Fuja enquanto ha tempo!");
        telaJogo.imprimir("\n");

        imprimirLocalizacaoAtual(); // Chama o metodo para imprimir a localizacao atual do jogador.
    }


    /**
     * Este metodo e responsavel por processar o comando recebido e executar a acao correspondente,
     * baseando-se na palavra-chave do comando. Ele lida com diferentes comandos, como ajuda, ir para um ambiente,
     * sair do jogo, observar a localizacao atual, realizar uma tarefa, espiar, verificar o inventario e pegar a mininave.
     *
     * @param comando O comando a ser processado.
     * @return Retorna true se o jogador deseja sair do jogo; caso contrario, retorna false.
     */
    private boolean processarComando(Comando comando) {
        boolean querSair = false;

        if (comando.ehDesconhecido()) { // Verifica se o comando e desconhecido
            telaJogo.imprimir("Eu nao entendi o que voce disse..." + "\n");
            return false;
        }
        String palavraDeComando = comando.getPalavraDeComando();
        switch (palavraDeComando) { // Estrutura de selecao switch para lidar com diferentes comandos
            case "ajuda":
                imprimirAjuda();
                break;
            case "ir":
                irParaAmbiente(comando);
                querSair = terminado;
                break;
            case "sair":
                querSair = sair(comando);
                break;
            case "observar":
                imprimirLocalizacaoAtual();
                break;
            case "realizar":
                realizarTarefa();
                querSair = terminado;
                break;
            case "espiar":
                espiar(comando);
                break;
            case "inventario":
                olharInventario();
            break;
            case "mininave":
                querSair = pegarMininave();
            break;
            default:
                break;
        }

        return querSair;
    }

    /**
     * Realiza a acao de pegar a mininave, verificando se o jogador esta na sala correta
     * e se possui todos os itens necessarios para concluir o jogo. Caso o jogador tenha
     * todos os itens, imprime uma mensagem de vitoria e encerra o jogo. Se o jogador nao
     * estiver na sala correta ou nao tiver todos os itens, imprime mensagens indicando
     * a situacao e continua o jogo.
     *
    * @return Verdadeiro se o jogador concluiu o jogo (pegou a mininave), falso caso contrario.
    */
    private boolean pegarMininave(){
        if(ambienteAtual.getID() == 16){// Verifica se o jogador esta na garagem (ID 16)
            if(jogador.getArrayListInventario().size() < 5){// Verifica se o jogador possui todos os itens necessarios no inventario
                telaJogo.imprimir("Voce ainda nao tem todos os itens necessarios para fugir!" + "\n");
                return false;
            }else{
                telaJogo.imprimir("Parabens, VOCE GANHOU!!!" + "\n"); // Mensagem para indicar que o jogador ganhou 
                JOptionPane.showMessageDialog(telaJogo.getJanelaPrincipal(), "Parabens, VOCÊ GANHOU!!!" + "\n" + "Rumo ao planeta Terra!"); // Mostra uma caixa de mensagem para o jogador, avisando-o que ele ganhou e o jogo acabou
                segundos = 0; // Define segundos como 0 para encerrar o jogo
                return true;
            }
        }else{
            telaJogo.imprimir("A mininave esta na garagem!" + "\n");
            return false;
        }
    }

    /**
     * Exibe o conteido do inventario do jogador.
     * Se o inventario estiver vazio, imprime uma mensagem indicando isso.
     * Caso contrario, mostra a quantidade de itens no inventario e lista os itens.
     */
    private void olharInventario(){
        if(jogador.getArrayListInventario().size() == 0){ // Verifica se o inventario do jogador esta vazio
           telaJogo.imprimir("Inventario vazio" + "\n");
        } else{
            // Imprime a quantidade de itens no inventario e lista os itens
            telaJogo.imprimir("Voce possui " + jogador.getArrayListInventario().size() +" itens no inventario, aqui esta:" + "\n");
            for(Item i : jogador.getArrayListInventario()){
                telaJogo.imprimir(i.getNomeItem() + "  ");
            }
            telaJogo.imprimir("\n");
        }
    }

    /**
     * Espia em uma direcao especifica a partir do ambiente atual.
     * Se a direcao fornecida existir como saida no ambiente atual,
     * verifica se ha alienigenas naquele ambiente e imprime a informacao correspondente.
     * Caso contrario, imprime uma mensagem indicando que a direcao e inexistente.
     *
 * @param comando O comando que contem a direcao para espiar.
 */
    private void espiar(Comando comando){
        // Verifica se o comando possui uma segunda palavra (direcao)
        if (comando.temSegundaPalavra()) {
            String direcao = comando.getSegundaPalavra();
            if(ambienteAtual.verificaSaida(direcao)){ // Verifica se a direcao fornecida existe como saida no ambiente atual
                verificarAlienigenasAmbiente(direcao);
            } else{
                telaJogo.imprimir("Direcao inexistente" + "\n");
            }
        } else {
            telaJogo.imprimir("Espiar para onde?" + "\n"); // Caso nao haja segunda palavra (direcao) no comando
        }
    }

    /**
     * Realiza a tarefa associada ao ambiente atual, se houver.
     * Se o ambiente atual for um corredor, exibe uma mensagem informando que nao ha tarefas nos corredores.
     * Se o ambiente atual nao tiver sido sorteado, informa que nao ha tarefa nesta sala.
     * Caso contrario, exibe informacoes sobre a tarefa, incluindo a pergunta associada e solicita a resposta do jogador.
     * Se o jogador responder corretamente, recebe o item associado a tarefa.
     * Caso contrario, o jogo e encerrado.
     */
    private void realizarTarefa() {
        if (ambienteAtual instanceof Corredor) { // Verifica se o ambiente atual e um corredor
            telaJogo.imprimir("Nao ha tarefas nos corredores!" + "\n");
        } else if (!ambienteAtual.getFoiSorteado()) { // Verifica se o ambiente atual nao foi sorteado
            telaJogo.imprimir("Nao ha tarefa nesta sala!" + "\n");
        } else {
            Sala salaAtual = (Sala) ambienteAtual;

            telaJogo.imprimir("Voce possui uma tarefa neste ambiente!" + "\n");
            telaJogo.imprimir("Ao realizar essa tarefa voce coletara o item: " + salaAtual.getNomeItem() + "\n");
            telaJogo.imprimir("A tarefa eh o seguinte, voce tera que responder corretamente a seguinte questao: " + "\n");

            Questao tarefa = sortearQuestao(); // Sorteia uma questao

            telaJogo.imprimir(tarefa.getPergunta() + "\n"); // Exibe a pergunta ao jogador

            String respostaTarefa = telaJogo.getComando(); // Obtem a resposta do jogador

            if (tarefa.acertou(respostaTarefa)) { // Se o jogador acertar a resposta
                telaJogo.imprimir("Parabens voce acertou! Receba o item " + salaAtual.getNomeItem() + "\n"); 
                ambienteAtual.setFoiSorteado(false); // A tarefa nao estara mais disponivel quando o jogador retornar a este ambiente
                jogador.adicionarItemInventario(salaAtual.getItem()); // Adiciona o item ao inventario do jogador
            } else {// Se o jogador errar a resposta
                telaJogo.imprimir("Voce perdeu! A resposta esta incorreta e o alarme foi disparado..." + "\n");
                JOptionPane.showMessageDialog(telaJogo.getJanelaPrincipal(), "Voce perdeu! A resposta esta incorreta e o alarme foi disparado..." + "\n" + "Mais sorte na proxima vez!"); // Mostra uma caixa de mensagem para o jogador, avisando-o que ele perdeu e o jogo acabou
                acabarJogo(true); // Encerra o jogo
            }
        }
    }

    /**
     * Verifica a presenca de alienigenas no ambiente para a direcao especifica indicada.
     * Se o ambiente para o qual o usuario esta prestes a se mover for um corredor, verifica se esse corredor
     * possui alienigenas sorteado. Exibe mensagens indicando a presenca ou ausencia de alienigenas no ambiente.
     *
     * @param direcao A direcao para a qual o usuario esta prestes a se mover.
     */
    private void verificarAlienigenasAmbiente(String direcao) {
        // Verifica se o ambiente em que o usuario esta prestes a entrar e um corredor.
        if (ambienteAtual.getAmbiente(direcao) instanceof Corredor) {
            // Verifica se o corredor em que o usuario esta prestes a entrar possui alienigena.
            if (((Corredor) ambienteAtual.getAmbiente(direcao)).getFoiSorteado()) {
                telaJogo.imprimir("Possui alienigena no ambiente, cuidado!" + "\n");
            } else {
                telaJogo.imprimir("Tudo tranquilo, nao possui alienigena no ambiente, pode seguir em frente!" + "\n");
            }
        } else {
            telaJogo.imprimir("Zero perigos por aqui..." + "\n");
        }
    }

   /**
     * Finaliza o jogo conforme a condicao especificada.
     *
     * @param finalizar Indica se o jogo deve ser finalizado (true) ou nao (false).
     *
     * Este metodo e chamado no metodo realizarTarefa() e no irParaAmbiente(Comando comando). Sua funcao e definir o valor do
     * atributo booleano 'terminado' como true, encerrando o jogo. Quando o metodo realizarTarefa() e concluido, o controle
     * retorna ao metodo processarComando(). Se o jogador errar a resposta da pergunta, 'terminado' e definido como true,
     * e no metodo processarComando(), uma variavel local recebe o valor de 'terminado', que neste caso sera true, retornando
     * ao metodo jogar() encerrando o jogo. Ja no metodo irParaAmbiente(Comando comando), caso o usuario entre em corredor que
     * possui guarda ele perde, uma variavel local de processarComando() recebe o valor de 'terminado', que neste caso sera
     * true, voltando ao metodo jogar(), encerrando o jogo.
     */
    private void acabarJogo(boolean finalizar) { 
        this.terminado = finalizar;
    }

    /**
     * Sorteia uma questao aleatoria da lista de questoes.
     *
     * @return Uma questao aleatoria.
     *
     * Este metodo utiliza um gerador de nimeros aleatorios para obter um indice aleatorio na lista de questoes.
     * A questao correspondente a esse indice e recuperada, removida da lista para evitar repeticoes durante a execucao do jogo
     * e retornada como resultado do metodo.
     */
    private Questao sortearQuestao() {
        Random questaoAleatoria = new Random();
        int numeroAleatorio = questaoAleatoria.nextInt(listaDeQuestoes.size() - 1);
        Questao questao = listaDeQuestoes.get(numeroAleatorio); // Variavel temporaria do tipo Questao
        removerQuestao(numeroAleatorio); // Garante que nao teremos questoes repetidas durante a execucao do jogo
        return questao;
    }

    /**
     * Remove a questao da lista de questoes com base no indice fornecido.
     *
     * @param numeroAleatorio O indice da questao a ser removida.
     *
     * Este metodo remove a questao da lista de questoes com base no indice fornecido.
     * Ele e usado para garantir que nao haja repeticoes de questoes durante a execucao do jogo.
     */
    private void removerQuestao(int numeroAleatorio) {
        listaDeQuestoes.remove(numeroAleatorio);
    }

    /**
     * Imprime mensagens de ajuda para o jogador.
     * Informa sobre o objetivo do jogo, fornece dicas e lista os comandos disponiveis.
     */
    private void imprimirAjuda() {
        // Informa ao jogador sobre o objetivo do jogo
        telaJogo.imprimir("Voce esta tentando fugir desses terriveis alienigenas!" + "\n");
        telaJogo.imprimir("Fuja enquanto ha tempo!" + "\n");
        telaJogo.imprimir("\n");

        // Fornece informacoes sobre os comandos disponiveis
        telaJogo.imprimir("Suas palavras de comando sao:" + "\n");
        telaJogo.imprimir(analisador.getComandos() + "\n");
    }

    /**
     * Move o jogador para o proximo ambiente com base no comando fornecido.
     *
     * @param comando O comando inserido pelo jogador para indicar a direcao desejada.
     */
    private void irParaAmbiente(Comando comando) {
        if (!comando.temSegundaPalavra()) { // Verifica se o comando possui uma segunda palavra (direcao)
            // Se nao ha segunda palavra, o jogador nao especificou a direcao
            telaJogo.imprimir("Ir pra onde?" + "\n");
            return;
        }
        // Obtem a direcao do comando
        String direcao = comando.getSegundaPalavra();

        // Obtem o proximo ambiente com base na direcao especificada
        Ambiente proximoAmbiente = null;
        if (direcao != "") { // Verifica se ha passagem para o proximo ambiente
            proximoAmbiente = ambienteAtual.getAmbiente(direcao);
        }

        if (proximoAmbiente == null) {
            telaJogo.imprimir("Nao ha passagem!" + "\n");
        }else if (proximoAmbiente instanceof Corredor) { // Se o proximo ambiente for um corredor, verifica se o jogador teve sorte
            if (((Corredor) proximoAmbiente).getFoiSorteado()) {
                telaJogo.imprimir("Voce perdeu! Um alienigena te matou!" + "\n");
                JOptionPane.showMessageDialog(telaJogo.getJanelaPrincipal(), "Voce perdeu! Um alienigena te matou!" + "\n" + "Preste mais atencao na proxima vez..."); // Mostra uma caixa de mensagem para o jogador, avisando-o que ele perdeu e o jogo acabou
                acabarJogo(true);
                segundos = 0;
            }else{
                ambienteAtual = proximoAmbiente;
                imprimirLocalizacaoAtual();
            }
        }else{ // Move o jogador para o proximo ambiente
            ambienteAtual = proximoAmbiente;
            imprimirLocalizacaoAtual(); // Imprime a localizacao atual
        }
    }

    /**
    * Metodo responsavel por imprimir a localizacao atual do jogador.
    * Exibe a descricao do ambiente atual e as saidas disponiveis.
    */
    private void imprimirLocalizacaoAtual() {
        telaJogo.imprimir("Voce esta " + ambienteAtual.getDescricao() + "\n");
        telaJogo.imprimir("Saidas: " + "\n");
        telaJogo.imprimir(ambienteAtual.getSaida() + "\n");
        telaJogo.imprimir("\n");
    }

    /**
    * Metodo responsavel por processar o comando "sair" no jogo.
    *
    * @param comando O comando fornecido pelo jogador.
    * @return true se o comando foi processado com sucesso e o jogo deve ser encerrado, false caso contrario.
    */
    private boolean sair(Comando comando) {
        if (comando.temSegundaPalavra()) {
            telaJogo.imprimir("Sair o que?" + "\n");
            return false;
        } else {
            //System.exit(0);
            segundos = 0;
            return true;
        }
    }
    
}