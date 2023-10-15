package TrabalhoTrem;
import java.util.ArrayList;
import java.util.Collections;
/**
 * Classe da Garagem de Vagões
 * @author ricardo.rossa@edu.pucrs.br
 * @version 15/10/2023;
 */

public class GaragemVagoes
{
    private ArrayList<Vagao> vagoes = new ArrayList<Vagao>();

    /**
     * Construtor Vazio da classe App (Para Javadoc)
     * @author ricardo.rossa@edu.pucrs.br
     */
    public GaragemVagoes() {}

    /**
     * Adiciona um novo vagão a garagem
     * @param vagao   um objeto da classe Vagao que será armazenado
     * @author ricardo.rosssa@edu.pucrs.br
     */
    public void setVagao(Vagao vagao)
    {
        vagoes.add(vagao);
    }


    /**
     * Obtem um vagão que está armazenado na garagem
     * @param n um vagão desejado identificado pelo ID
     * @return o vagão especificado (n)
     * @author ricardo.rosssa@edu.pucrs.br
     */
    public Vagao getVagao(int n)
    {
        return vagoes.get(n);
    }
    /**
     * Informa o total de vagões armazenados na garagem
     * @return o total de vagões disponíveis
     * @author l.gamarra@edu.pucrs.br
     */
    public int totalVagoes()
    {
        return vagoes.size();
    }

    /**
     * Remove um vagão da garagem
     * @param vagao Um objeto da classe Vagão que será removido da garagem
     * @author ricardo.rossa@edu.pucrs.br
     */
    public void removeVagao(Vagao vagao) { vagoes.remove(vagao);}

    /**
     * Ordena os Vagoes utilizando o compareTo implementado na classe do objeto (Vagao)
     * @author ricardo.rossa@edu.pucrs.br
     */
    public void sortVagoes()
    {
        Collections.sort(vagoes);
    }
}
