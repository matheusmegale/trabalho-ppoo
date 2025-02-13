import java.util.Scanner;

/**
 * A classe Analisador e responsavel por analisar os comandos fornecidos pelo usuario
 * durante a execucao do jogo.
 * @author Eduardo Ruan Guimaraes Fonseca, Lucas de Oliveira Pereira, 
 *         Matheus de Paula Megale, Renan Augusto da Silva
 */

public class Analisador {
    /** Guarda todas as palavras de comando validas. */
    private PalavrasComando palavrasDeComando; 

    /** Origem da entrada de comandos. */
    private TelaJogo telaJogo;

    /**
     * Cria um analisador para ler comandos da tela do jogo.
     *
     * @param telaJogo A tela do jogo da qual os comandos serao lidos.
     */
    public Analisador(TelaJogo telaJogo) {
        palavrasDeComando = new PalavrasComando();
        this.telaJogo = telaJogo;   
    }

    /**
     * Obtem o comando do usuario.
     *
     * @return O comando do usuario.
     */
    public Comando pegarComando() {
        String linha; // guardar uma linha inteira
        String palavra1 = null;
        String palavra2 = null;

        linha = telaJogo.getComando(); // linha recebe toda a String que o usuario digitar na interface grafica

        // Tenta encontrar ate duas palavras na linha
        Scanner tokenizer = new Scanner(linha);
        if (tokenizer.hasNext()) {// O metodo hasNext() retorna true se houver mais tokens (palavra ou parte de uma linha) disponiveis e false se nao houver.
            palavra1 = tokenizer.next(); // pega a primeira palavra. O metodo next() do Scanner le e retorna o proximo token como uma string.
            if (tokenizer.hasNext()) {
                palavra2 = tokenizer.next(); // pega a segunda palavra
                // obs: nos simplesmente ignoramos o resto da linha.
            }
        }

        tokenizer.close();
        
        if (palavrasDeComando.ehComando(palavra1)) {// Verifica se a primeira palavra e um comando valido
            return new Comando(palavra1, palavra2);
        } else {// Caso nao seja um comando valido, retorna um Comando com a primeira palavra como nula
            return new Comando(null, palavra2);
        }
    }

    /**
     * Obtem uma representacao dos comandos disponiveis.
     *
     * @return Uma string contendo os comandos disponiveis.
     */
    public String getComandos() {
        return palavrasDeComando.getComandos();
    }
}
