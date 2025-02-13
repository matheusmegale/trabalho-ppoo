import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;

/**
 * A classe TelaJogo e responsavel por criar e gerenciar a interface grafica do jogo.
 * Ela utiliza a biblioteca Swing para a criacao de elementos graficos como janelas, paineis e componentes de texto.
 * 
 * Possui elementos como areas de texto para mensagens, campo de entrada para comandos, e labels para exibicao de imagens.
 * 
 * Esta classe facilita a interacao do jogador com o jogo, permitindo a entrada de comandos e exibindo informacoes relevantes.
 * 
 * Para interagir com a classe, e possivel definir comandos usando o metodo setComando(String novoComando),
 * obter comandos com o metodo getComando(), imprimir mensagens na area de saida com o metodo imprimir(String mensagem),
 * imprimir tarefas com o metodo imprimirTarefas(String tarefas) e imprimir segundos com o metodo imprimirSegundos(String segundos).
 * 
 * @author Eduardo Ruan Guimaraes Fonseca
 */

public class TelaJogo{

     /** A janela principal do jogo. */
    private JFrame janelaPrincipal;

    /** A area de saida para mensagens. */
    private JTextArea output;

     /** O campo de entrada para comandos. */
    private JTextField input;

    /** A area para exibicao de tarefas. */
    private JTextArea campoTarefas;

     /** A area para exibicao de segundos. */
    private JTextArea campoTimer;

    /** O label para exibicao do mapa. */
    private JLabel mapa;

     /** O label para exibicao da imagem dos dutos. */
    private JLabel imagemDutos;

    /** O comando digitado pelo jogador. */
    private String comando;


    /**
     * Cria um novo objeto TelaJogo.
     * Inicializa os componentes graficos, como janelas, areas de texto, campos de entrada e labels.
     */
    public TelaJogo(){
        comando = null;
        
        janelaPrincipal = new JFrame("Estelar: A grande fuga!");
        mapa = new JLabel(new ImageIcon("img/mapa.png"));
        imagemDutos = new JLabel(new ImageIcon("img/Dutos.jpeg"));

        campoTarefas = new JTextArea();
        campoTimer = new JTextArea();
        output = new JTextArea();
        input = new JTextField();

        input.addActionListener( // Metodo adiciona um ouvinte de acao ao componente 'input'
            new ActionListener() { // Cria uma instância anônima da interface ActionListener
                @Override
                public void actionPerformed(ActionEvent e) { //Sobrescrevo o metodo actionPerformed da interface ActionListener
                    String novoComando = input.getText(); // Obtem o texto atual do componente 'input', entrada do usuario na interface grafica
                    setComando(novoComando); // Atribui a nosso atributo 'comando', a String digitada pelo usuario
                    imprimir(novoComando + "\n"); // Imprime o que o usuario digitou
                    input.setText(""); // Limpa a a barra de entrada do usuario assim que ele aperta a tecla ENTER
                }
            });

        inicializarJanela();
    }

    /**
     * Inicializa a janela principal e os elementos graficos.
     * Define o layout da janela, cria os paineis, areas de texto, campos de entrada e labels.
     */
    private void inicializarJanela(){
        // Configuracoes da janela
        janelaPrincipal.setSize(1400, 800);
        janelaPrincipal.setLayout(new BorderLayout());

        campoTarefas.setEditable(false);//nao permite digitacao
        campoTimer.setEditable(false);
        output.setEditable(false);

        // Configuracoes do lado esquerdo
        JPanel painelEsquerda  = new JPanel(new GridLayout(2, 1));
        painelEsquerda.add(campoTimer);
        painelEsquerda.add(imagemDutos);
        janelaPrincipal.add(painelEsquerda, BorderLayout.WEST);

        // Configuracoes do lado direito
        JPanel painelDireita = new JPanel();
        painelDireita.setLayout(new BoxLayout(painelDireita, BoxLayout.Y_AXIS));
        painelDireita.add(campoTarefas);
        janelaPrincipal.add(painelDireita, BorderLayout.EAST);

        // Configuracoes do centro
        JPanel painelCentro = new JPanel();
        painelCentro.setLayout(new BoxLayout(painelCentro, BoxLayout.Y_AXIS));
        painelCentro.add(mapa);
        janelaPrincipal.add(painelCentro, BorderLayout.CENTER);

        // Configuracoes do inferior
        JPanel painelInferior = new JPanel();
        painelInferior.setLayout(new BoxLayout(painelInferior, BoxLayout.Y_AXIS));
        JScrollPane outputScrollPane = new JScrollPane(output);// Permite rolar o conteudo de um componente, e neste caso, e o 'output'
        outputScrollPane.setPreferredSize(new Dimension(1380,140));// Define as dimensoes preferenciais do JScrollPane
        outputScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);// Independentemente do conteudo do componente ser ou nao maior que a area visivel, a barra de rolagem vertical sera sempre exibida.
        input.setPreferredSize(new Dimension(20, 50));// Define as dimensoes preferenciais do componente 'input'
        painelInferior.add(outputScrollPane);
        painelInferior.add(input);
        janelaPrincipal.add(painelInferior, BorderLayout.SOUTH);

        // Empacota a janela
        janelaPrincipal.pack();

    }

    /**
     * Obtem o comando digitado pelo jogador.
     * Aguarda ate que um comando seja digitado.
     *
     * @return O comando digitado pelo jogador.
     * @throws InterruptedException Se ocorrer uma interrupcao enquanto a thread estiver dormindo.
     */
    public String getComando() {
        while (comando == null) {// continua executando enquanto o valor da variavel 'comando' for 'null'
            try {// tentativa de pausar a execucao do codigo por 250 milissegundos usando Thread.sleep(250).
                Thread.sleep(250);//Thread.sleep e um metodo estatico da classe Thread
                // Que pausa a execucao da thread atual por um determinado numero de milissegundos.
            } catch (InterruptedException e) {
                e.printStackTrace();
                // Se durante a pausa ocorrer uma interrupcao, a excecao InterruptedException sera lancada.
                // printStackTrace() e um metodo da classe Throwable
                // Que imprime a pilha de chamadas da excecao no console.
            }
        }

        String comandoEnviado = comando;// Apos sair do loop
                                        // O valor atual da variavel comando e atribuido a variavel local comandoEnviado.
                                        // Isso captura o comando que foi digitado durante o periodo de espera.
        comando = null;// O valor da variavel comando e reinicializado para null
                       // Isso prepara a variavel para a proxima entrada do usuario.
        return comandoEnviado;
    } 

    /**
     * Define o comando digitado pelo jogador.
     *
     * @param novoComando O novo comando digitado.
     */
    public void setComando(String novoComando) {
        comando = novoComando;
    }

    /**
     * Imprime as tarefas na area de tarefas.
     *
     * @param tarefas As tarefas a serem impressas.
     */
    public void imprimirTarefas(String tarefas){
        campoTarefas.setText(tarefas);
    }

    /**
     * Imprime os segundos na area de timer.
     *
     * @param segundos Os segundos a serem impressos.
     */
    public void imprimirSegundos(String segundos){
        campoTimer.setText(segundos);
    }

    /**
     * Adiciona uma mensagem a area de saida (`output`) da interface grafica.
     * A mensagem e anexada ao conteudo existente na area de saida.
     * Alem disso, a posicao do cursor e ajustada para o final do texto, garantindo que a mensagem recem-adicionada seja visivel.
     *
     * @param mensagem A mensagem a ser adicionada a area de saida.
     */
    public void imprimir(String mensagem){
        output.append(mensagem);
        output.setCaretPosition(output.getDocument().getLength());// e utilizada para ajustar a posicao do cursor na area de texto.
        //output.getDocument() - Obtem o modelo de documento (responsavel por armazenar o conteudo textual da area).
        //.getLength() - Retorna o comprimento (numero de caracteres) do conteudo atual no modelo de documento.
        //.setCaretPosition(...): Define a posicao do cursor na area de texto.
        //Ira definir a posicao do cursor para o final da area de texto, garantindo que,
        //ao adicionar uma nova mensagem usando output.append(mensagem), 
        //o cursor seja movido para a ultima posicao do texto. 
    }

    /**
     * Torna a janela principal visivel.
     * Este metodo e usado para exibir a interface grafica do jogo.
     * A visibilidade da janela e alterada para verdadeira, tornando-a visivel para o usuario.
     */
    public void exibir(){
        janelaPrincipal.setVisible(true);
    }

    /**
    * Retorna a instância da JFrame que representa a janela principal.
    *
    * @return A instância da JFrame que representa a janela principal.
    */
    public JFrame getJanelaPrincipal(){
        return janelaPrincipal;
    }
}