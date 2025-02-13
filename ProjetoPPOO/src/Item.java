/**
 * A classe Item representa um objeto que pode ser encontrado ou utilizado no jogo.
 * Cada item possui um nome e uma descricao da tarefa relacionada a ele.
 * Exemplos de itens podem incluir chaves, mapas, ou qualquer outro objeto relevante
 * para a jogabilidade.
 * 
 * @author Renan Augusto da Silva
 */
public class Item {
    
    /** O nome do item. */
    private String nomeItem;

    /** A descricao da tarefa relacionada ao item. */
    private String tarefaRelacionadaAoItem;

    /**
     * Cria um novo objeto Item.
     *
     * @param nomeItem O nome do item.
     * @param tarefaRelacionadaAoItem A descricao da tarefa relacionada ao item.
     */
    public Item(String nomeItem, String tarefaRelacionadaAoItem){
        this.nomeItem = nomeItem;
        this.tarefaRelacionadaAoItem = tarefaRelacionadaAoItem;
    }

    /**
     * Obtem o nome do item.
     *
     * @return O nome do item.
     */
    public String getNomeItem(){
        return nomeItem;
    }
    
    /**
     * Obtem a descricao da tarefa relacionada ao item.
     *
     * @return A descricao da tarefa relacionada ao item.
     */
    public String getTarefaRelacionadaAoItem(){
        return tarefaRelacionadaAoItem;
    }
}
