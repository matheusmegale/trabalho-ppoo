import java.util.*;

/**
 * A classe abstrata Ambiente representa um ambiente generico em um sistema.
 * Cada ambiente possui uma descricao, um identificador unico (ID) e possiveis saidas
 * para outros ambientes em diferentes direcoes.
 * @author Eduardo Ruan Guimaraes Fonseca, Lucas de Oliveira Pereira, 
 *         Matheus de Paula Megale, Renan Augusto da Silva
 */


public abstract class Ambiente {
    
    /** Descricao do ambiente. */
    private String descricao;
    
    /** Identificador unico do ambiente. */
    private int ID;
    
    /** HashMap das saidas para outros ambientes. */
    private HashMap<String, Ambiente> saidas;

    /**
     * Construtor para criar um novo ambiente.
     *
     * @param descricao A descricao do ambiente.
     * @param ID O identificador unico do ambiente.
     */
    public Ambiente(String descricao, int ID) {
        this.descricao = descricao;
        this.ID = ID;
        saidas = new HashMap<String, Ambiente>();
    }

    /**
     * Ajusta a saida para um ambiente em uma direcao especifica.
     *
     * @param direcao A direcao para a qual a saida esta sendo ajustada.
     * @param ambiente O ambiente para o qual a saida esta sendo ajustada.
     */
    public void ajustarSaida(String direcao, Ambiente ambiente) {
       saidas.put(direcao, ambiente);
    }

    /**
     * Obtem o ambiente na direcao especificada.
     *
     * @param direcao A direcao desejada.
     * @return O ambiente na direcao especificada, ou null se nao houver ambiente nessa direcao.
     */
    public Ambiente getAmbiente(String direcao) {
        return saidas.get(direcao);
    }

    /**
     * Obtem o identificador unico do ambiente.
     *
     * @return O identificador unico do ambiente.
     */
    public int getIDAmbiente(){
        return ID;
    }

    /**
     * Obtem uma representacao textual das saidas disponiveis neste ambiente.
     *
     * @return Uma string contendo as direcoes das saidas disponiveis.
     */
    public String getSaida(){
        String saidasAmbiente = "";
        for (String direcao : saidas.keySet()) {
            saidasAmbiente = saidasAmbiente + direcao + " ";
        }
        return saidasAmbiente; //contaneta as possiveis saidas com espacoe entre elas
    }

    /**
     * Verifica se ha uma saida na direcao especificada.
     *
     * @param saida A direcao a ser verificada.
     * @return true se houver uma saida nessa direcao, false caso contrario.
     */
    public boolean verificaSaida(String saida) {
        if(saidas.containsKey(saida)){
            return true;
        }
        return false;
    }
    
    /**
     * Obtem a descricao do ambiente.
     *
     * @return A descricao do ambiente.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Obtem o ID do ambiente.
     *
     * @return O ID do ambiente.
     */
    public int getID(){
        return ID;
    }

    /**
     * Define se o ambiente foi sorteado. Se ele for uma instância de Sala, o sorteio
     * sera para saber se ele tera alguma tarefa. Se ele for uma intância de Corredor,
     * o sorteio sera para saber se ele tera algum guarda. 
     * @param foiSorteado true se o ambiente foi sorteado, false caso contrario.
     */
    public abstract void setFoiSorteado(boolean foiSorteado);

    /**
     * Obtem se o ambiente foi sorteado. Se for uma Sala e tiver tarefa, retornara true,
     * caso contrario, retornara false. Se for um Corredor e tiver guarda, retornara true,
     * caso contrario, retornara false.
     *
     * @return true se o ambiente foi sorteado, false caso contrario.
     */
    public abstract boolean getFoiSorteado();

}
