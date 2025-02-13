/**
 * A classe Programa contem o metodo main e e responsavel por iniciar o jogo.
 * Cria um objeto da classe Jogo e chama o metodo iniciarJogo para comecar a execucao do jogo.
 * 
 * Ponto de entrada principal do programa.
 * 
 * @author Eduardo Ruan Guimaraes Fonseca, Lucas de Oliveira Pereira, 
 *         Matheus de Paula Megale, Renan Augusto da Silva
 */
public class Programa {

    public static void main(String[] args) {
        Jogo jogo = new Jogo(); // Criamos um objeto jogo
        jogo.iniciarJogo();
    }
}
