import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A classe Jogador representa o jogador no jogo, incluindo o inventario de itens.
 * O jogador pode adicionar itens ao inventario e obter uma lista nao modificavel dos itens presentes.
 * 
 * @author Lucas de Oliveira Pereira
 */

public class Jogador{

    /** O inventario que armazena os itens do jogador. */
    private ArrayList<Item> inventario;

    /**
     * Cria um novo objeto Jogador com um inventario vazio.
     */
    public Jogador() {
        inventario = new ArrayList<Item>();
    }

    /**
     * Adiciona um item ao inventario do jogador.
     *
     * @param item O item a ser adicionado ao inventario.
     */
    public void adicionarItemInventario(Item item) {
        inventario.add(item);
    }

    /**
     * Obtem uma lista nao modificavel dos itens no inventario do jogador.
     *
     * @return Uma lista nao modificavel dos itens no inventario do jogador.
     */
    public List<Item> getArrayListInventario() {
        return Collections.unmodifiableList(inventario);
    }

}