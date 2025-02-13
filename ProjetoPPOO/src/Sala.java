/**
 * A classe Sala e responsavel por representar ambientes que contem itens e tarefas a serem realizadas.
 * Estende a classe abstrata Ambiente.
 * 
 * Cada sala possui uma descricao, um identificador unico, um item associado e a informacao sobre se ha uma tarefa a ser realizada.
 * 
 * @author Eduardo Ruan Guimaraes Fonsca
 */

public class Sala extends Ambiente{

    /** O item associado a sala. */
    private Item item;

    /** Indica se a sala tem uma tarefa a ser realizada. */
    private boolean temTarefa;

     /**
     * Cria um novo objeto Sala.
     *
     * @param descricao A descricao da sala.
     * @param ID O identificador unico da sala.
     * @param item O item associado a sala.
     */
    public Sala(String descricao, int ID, Item item){
        super(descricao, ID);
        this.item = item;
        this.temTarefa = false;
    }

    /**
     * Define se a sala foi sorteada para conter uma tarefa.
     *
     * @param foiSorteado true se a sala foi sorteada, false caso contrario.
     */
    @Override
    public void setFoiSorteado(boolean foiSorteado){
        this.temTarefa = foiSorteado;
    }

    /**
     * Obtem se a sala foi sorteada para conter uma tarefa.
     *
     * @return true se a sala foi sorteada, false caso contrario.
     */
    @Override
    public boolean getFoiSorteado(){
        return temTarefa;
    }

     /**
     * Obtem o nome do item associado a sala.
     * Útil para reduzir o acoplamento em relacao a classe Jogo.
     *
     * @return O nome do item associado a sala.
     */
    public String getNomeItem(){ 
        return item.getNomeItem();
    }

    /**
     * Obtem o item associado a sala.
     *
     * @return O item associado a sala.
     */
    public Item getItem(){
        return item;
    }

    /**
     * Obtem a descricao da tarefa relacionada ao item na sala.
     *
     * @return A descricao da tarefa relacionada ao item na sala.
     */
    public String getTarefaRelacionadaAoItem(){
        return item.getTarefaRelacionadaAoItem();
    }
}
