package TrabalhoTrem;
import java.util.ArrayList;

/**
 * A classe GaragemLocomotivas armazena objetos da classe Locomotiva
 * @author l.gamarra@edu.pucrs.br
 * @version 30/08/23
 */
public class GaragemLocomotivas
{
    // Armazena as locomotivas
    private ArrayList<Locomotiva> locomotivas = new ArrayList<Locomotiva>();

    /**
     * Construtor Vazio da classe GaragemLocomotivas (Para Javadoc)
     * @author ricardo.rossa@edu.pucrs.br
     */
    public GaragemLocomotivas() {}

    /**
     * Adiciona uma nova locomotiva a garagem
     * @param locomotiva objeto da classe Locomotiva a ser armazenado
     * @author l.gamarra@edu.pucrs.bb
     */
    public void setLocomotiva(Locomotiva locomotiva)
    {
        locomotivas.add(locomotiva);
    }

    /**
     * Retorna um objeto da classe Locomotiva
     * @param n posição do objeto na array
     * @return objeto locomotiva na posição n
     * @author l.gamarra@edu.pucrs.br
     */
    public Locomotiva getLocomotiva(int n)
    {
        return locomotivas.get(n);
    }

    /**
     * Retorna o total de locomotivas na garagem
     * @return O total de locomotivas armazenadas na garagem
     * @author l.gamarra@edu.pucrs.br
     */
    public int totalLocomotivas()
    {
        return locomotivas.size();
    }

    /**
     * Deleta uma locomotiva da array
     * @param locomotiva Objeto a ser removido
     * @author l.gamarra@edu.pucrs.br
     */
    public void removeLocomotiva(Locomotiva locomotiva)
    {
        locomotivas.remove(locomotiva);
    }
}
