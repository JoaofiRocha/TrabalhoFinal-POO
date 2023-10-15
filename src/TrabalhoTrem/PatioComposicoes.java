package TrabalhoTrem;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Armazena objetos da classe Composicao
 * @author joao.farah@edu.pucrs.br, l.gamarra@edu.pucrs.br, ricardo.rossa@edu.pucrs.br
 * @version 15/10/23
 */
public class PatioComposicoes
{
    //Armazena as composições
    private ArrayList<Composicao> composicoes = new ArrayList<Composicao>();

    /**
     * Construtor Vazio da classe PatioComposicoes (Para Javadoc)
     * @author ricardo.rossa@edu.pucrs.br
     */
    public PatioComposicoes() {}

    /**
     * Adiciona uma composição a array
     * @param composicao Objeto a ser adicionado a array
     * @author l.gamarra@edu.pucrs.br
     */
    public void setComposicao(Composicao composicao)
    {
        composicoes.add(composicao);
    }

    /**
     * Retorna o tamanho da array
     * @return O tamanho da array "composicoes"
     * @author l.gamarra@edu.pucrs.br
     */
    public int totalComposicoes()
    {
        return composicoes.size();
    }

    /**
     * Método responsável por retornar a composição na array
     * @param n Posição onde a composição está armazenada na array
     * @return Uma composição
     * @author l.gamarra@edu.pucrs.br
     */
    public Composicao getComposicao(int n){return composicoes.get(n);}

    /**
     * Encontra a posição de uma composição na array através da sua ID
     * @param n ID da composição
     * @return A posição da composição na array
     * @author l.gamarra@edu.pucrs.br
     */
    public int acharTrem(int n)
    {
        for (int i = 0; i < composicoes.size(); i++)
        {
            if (composicoes.get(i).getIdentificador() == n)
                return i;
        }
        return 0;
    }

    /**
     * Indica se uma ID é correspondente a alguma composição
     * @param n ID da composição
     * @return true, caso a ID esteja em uso por alguma composição // false, caso nenhuma composição use a ID fornecida
     * @author l.gamarra@edu.pucrs.br
     */
    public boolean tremExiste(int n)
    {
        for (int i = 0; i < composicoes.size(); i++)
        {
            if (composicoes.get(i).getIdentificador() == n)
                return true;
        }
        return false;
    }

    /**
     * Deleta uma composição
     * @param composicao Composição a ser deletada
     * @author l.gamarra@edu.pucrs.br
     */
    public void deletarComposicao (Composicao composicao)
    {
        composicoes.remove(composicao);
    }


    /**
     * Ordena as composicoes utilizando o compareTo implementado na classe do objeto (Composicao)
     * @author ricardo.rossa@edu.pucrs.br
     */
    public void sortComposicoes()
    {
        Collections.sort(composicoes);
    }


}
