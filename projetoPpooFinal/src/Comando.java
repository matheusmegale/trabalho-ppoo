/**
 * A classe Comando representa um comando inserido pelo usuario.
 * Cada comando e composto por uma palavra de comando e, opcionalmente,
 * uma segunda palavra.
 * @author Eduardo Ruan Guimaraes Fonseca, Lucas de Oliveira Pereira, 
 *         Matheus de Paula Megale, Renan Augusto da Silva
 */

public class Comando
{
     /** A palavra de comando inserida pelo usuario. */
    private String palavraDeComando;

    /** A segunda palavra associada ao comando (pode ser nula). */
    private String segundaPalavra;

    /**
     * Cria um novo objeto Comando.
     *
     * @param primeiraPalavra A palavra de comando fornecida pelo usuario.
     * @param segundaPalavra A segunda palavra associada ao comando (pode ser nula).
     */
    public Comando(String primeiraPalavra, String segundaPalavra)
    {
        palavraDeComando = primeiraPalavra;
        this.segundaPalavra = segundaPalavra;
    }

    /**
     * Obtem a palavra de comando.
     *
     * @return A palavra de comando.
     */
    public String getPalavraDeComando()
    {
        return palavraDeComando;
    }

    /**
     * Obtem a segunda palavra associada ao comando.
     *
     * @return A segunda palavra associada ao comando (pode ser nula).
     */
    public String getSegundaPalavra()
    {
        return segundaPalavra;
    }

    /**
     * Verifica se a palavra de comando e desconhecida.
     *
     * @return true se a palavra de comando for desconhecida, false caso contrario.
     */
    public boolean ehDesconhecido()
    {
        return (palavraDeComando == null);
    }

    /**
     * Verifica se o comando possui uma segunda palavra.
     *
     * @return true se houver uma segunda palavra, false caso contrario.
     */
    public boolean temSegundaPalavra()
    {
        return (segundaPalavra != null);
    }
}

