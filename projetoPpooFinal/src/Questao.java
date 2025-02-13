/**
 * A classe Questao e responsavel por representar as questoes a serem respondidas pelo jogador.
 * Estas questoes sao sorteadas quando o jogador chega a um ambiente que contem uma missao.
 * 
 * Cada questao possui uma pergunta e uma resposta associada.
 * 
 * @author Matheus de Paula Megale
 */

public class Questao {
    /** A pergunta associada a questao. */
    private String pergunta;

     /** A resposta correta a pergunta. */
    private String resposta;

    /**
     * Cria um novo objeto Questao.
     *
     * @param pergunta A pergunta a ser associada a questao.
     * @param resposta A resposta correta a pergunta.
     */
    public Questao(String pergunta, String resposta){
        this.pergunta = pergunta;
        this.resposta = resposta;
    }

    /**
     * Obtem a pergunta associada a questao.
     *
     * @return A pergunta associada a questao.
     */
    public String getPergunta(){
        return pergunta;
    }

    /**
     * Obtem a resposta correta a pergunta.
     *
     * @return A resposta correta a pergunta.
     */
    public String getResposta(){
        return resposta;
    }

    /**
     * Verifica se o jogador acertou a questao comparando a resposta fornecida com a resposta correta.
     *
     * @param resposta A resposta fornecida pelo jogador.
     * @return true se o jogador acertou, false se errou.
     */
    public boolean acertou(String resposta){
        if(this.resposta.equals(resposta)){ //se a resposta do jogador for igual a resposta da pergunta, entao ele acertou
            return true;
        }
        return false;
    }
}
