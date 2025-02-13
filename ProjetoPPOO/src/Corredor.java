/**
 * A classe Corredor representa um tipo especifico de ambiente que contem guardas.
 * Estende a classe abstrata Ambiente.
 * Cada corredor pode ou nao conter um guarda, dependendo do sorteio.
 *
 * @author Matheus de Paula Megale
 */

public class Corredor extends Ambiente{
    
    /** Indica se o corredor contem um guarda. */
    private boolean temGuarda;

    /**
     * Cria um novo objeto Corredor.
     *
     * @param descricao A descricao do corredor.
     * @param ID O identificador unico do corredor.
     */
    public Corredor(String descricao, int ID){
        super(descricao, ID);
        temGuarda = false;
    }

    /**
     * Define se o corredor foi sorteado para conter um guarda.
     *
     * @param foiSorteado true se o corredor foi sorteado, false caso contrario.
     */
    @Override
    public void setFoiSorteado(boolean foiSorteado){
        temGuarda = foiSorteado;
    }

    /**
     * Obtem se o corredor foi sorteado para conter um guarda.
     *
     * @return true se o corredor foi sorteado, false caso contrario.
     */
    @Override
    public boolean getFoiSorteado(){
        return temGuarda;
    }
}
