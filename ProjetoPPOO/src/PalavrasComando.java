/**
 * A classe PalavrasComando e responsavel por armazenar e verificar palavras de comando validas.
 * Contem um vetor constante de palavras de comando e metodos para obter a lista de comandos
 * e verificar se uma dada string e uma palavra de comando valida.
 * 
 * Exemplos de comandos: "ir", "sair", "ajuda", "observar", "realizar", "espiar", "inventario", "mininave".
 * 
 * @author Eduardo Ruan Guimaraes Fonseca, Lucas de Oliveira Pereira, 
 *         Matheus de Paula Megale, Renan Augusto da Silva
 */

public class PalavrasComando
{
    /** Um vetor constante que guarda todas as palavras de comandos validas. */
    private static final String[] comandosValidos = {
        "ir", "sair", "ajuda", "observar", "realizar", "espiar", "inventario", "mininave"
    };

    /**
     * Construtor padrao da classe PalavrasComando.
     * Inicializa as palavras de comando (nao ha acoes especificas neste momento).
     */
    public PalavrasComando()
    {
        // nada a fazer no momento...
    }

    /**
     * Obtem uma string contendo todos os comandos validos separados por espacos.
     *
     * @return Uma string contendo todos os comandos validos.
     */
    public String getComandos(){
        String comandos = "";
        for(String comando : comandosValidos){
            comandos = comandos + comando + " ";
        }
        return comandos;
    }

    /**
     * Verifica se uma dada string e uma palavra de comando valida.
     *
     * @param umaString A string a ser verificada como comando valido.
     * @return true se a string fornecida e um comando valido, false se nao e.
     */
    public boolean ehComando(String umaString)
    {
        for(int i = 0; i < comandosValidos.length; i++) {
            if(comandosValidos[i].equals(umaString))
                return true;
        }
        // se chegamos aqui, a string nao foi encontrada nos comandos.
        return false;
    }
}
