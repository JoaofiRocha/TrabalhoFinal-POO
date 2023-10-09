package TrabalhoTrem;
import java.util.ArrayList;
/**
 * classe Composição
 * @author joao.farah@edu.pucrs.br, l.gamarra@edu.pucrs.br
 * @version 02/09/2023
 */
public class Composicao {

    //Armazena o código identificador da composição
    private final int id;

    //Armazena o número de vagões
    private ArrayList<Vagao> vagoes = new ArrayList<Vagao>();

    //Armazena o número de locomotivas
    private ArrayList<Locomotiva> locomotivas = new ArrayList<Locomotiva>();

    // Armazena o número de carros
    private ArrayList<Carro> carros = new ArrayList<Carro>();

    //Peso máximo permitido no trem
    private double maxPeso;

    //Máximo de vagoes que cabem no trem
    private int maxVagoes;

    //Peso atual do trem
    private double pesoAtual;

    // Cria variáveis para armazenar as capacidades reais da composição
    private double maxPesoReal = maxPeso;
    private int maxVagoesReal = maxVagoes;

    /**
     * Construtor vazio da classe Composição
     * @author joao.farah@edu.pucrs.br
     */
    public Composicao()
    {
        id = 0;
    }

    /**
     * Construtor da classe Composição
     * Este construtor lança uma exceção IDJaEmUsoException
     * @param id número de identificação da composição
     * @param patio patio de composições para poder selecionar o patio onde está a composição
     * @throws IDJaEmUsoException se o ID já estiver em uso
     * @author ricardo.rossa@edu.pucrs.br, joao.farah@edu.pucrs.br
     */
    public Composicao(int id, PatioComposicoes patio) throws IDJaEmUsoException
    {
        if(patio.tremExiste(id)) {
            throw new IDJaEmUsoException("ID EM USO, ESCOLHA OUTRO ID PARA O TREM!");
        }
        // Somente atribui-se o valor do ID a composição caso a exceção não seja lançada
        this.id = id;

    }

    /**
     * Retorna a capacidade máxima de peso real da composição
     * @return A capacidade máxima de peso real da composição
     * @author l.gamarra@edu.pucrs.br
     */
    public double getMaxPesoReal()
    {
        return maxPesoReal;
    }

    /**
     * Retorna a quantidade máxima de vagões real da composição
     * @return A quantidade máxima de vagões real da composição
     * @author l.gamarra@edu.pucrs.br
     */
    public int getMaxVagoesReal()
    {
        return maxVagoesReal;
    }

    /**
     * Retorna o peso atual da composição
     * @return O peso atual da composição
     * @author l.gamarra@edu.pucrs.br
     */
    public double getPesoAtual()
    {
        return pesoAtual;
    }

    /**
     * retorna o identificador da composição
     * @return id da composição
     * @author joao.farah@edu.pucrs.br
     */
    public int getIdentificador()
    {
        return id;
    }

    /**
     * Retorna a quantidade de locomotivas
     * @return quantidade de locomotivas
     * @author ricardo.rossa@edu.pucrs.br
     */
    public int getQtdadeLocomotivas()
    {
        int quant = 0;
        for(Carro c : carros)
        {
            if(c instanceof Locomotiva)
            {
                quant++;
            }
        }
        return quant;
    }

    /**
     * Retorna a quantidade de vagões
     * @return quantidade de vagões
     * @author joao.farah@edu.pucrs.br
     */
    public int getQtdadeVagoes()
    {
        int quant = 0;
        for(Carro c : carros)
            if(c instanceof Vagao)
            {
                quant++;
            }
        return quant;
    }

    /**
     * Engata uma locomotiva na composição
     * @param locomotiva Locomotiva a ser engatada
     * @param garagem Garagem na qual a locomotiva sera removida
     * author joao.farah@edu.pucrs.br, l.gamarra@edu.pucrs.br
     */
    public void engataLocomotiva(Locomotiva locomotiva, GaragemLocomotivas garagem)
    {
        // Troca o estado da locomotiva para em uso(1)
        locomotiva.setComposicao(1);

        // Adiciona locomotiva a composição
        carros.add(locomotiva);

        // Incrementa a capacidade de peso do trem
        maxPeso += locomotiva.getPesoMax();

        // Incrementa a capacidade de vagões do trem
        maxVagoes += locomotiva.getQtdadeMaxVagoes();

        // Remove a locomotiva da garagem
        garagem.removeLocomotiva(locomotiva);
    }

    /**
     * Calcula a capacidade real da composição dependendo da quantidade de locomotivas engatadas
     * @author l.gamarra@edu.pucrs.br
     */
    public void calcularCapacidadeReal()
    {
        maxPesoReal = maxPeso;
        maxVagoesReal = maxVagoes;

        // No caso da composição utilizar mais de uma locomotiva, é necessário calcular suas capacidades reais
        if (locomotivas.size() > 1)
        {
            // Calcula a capacidade de peso máxima a partir da quantidade de locomotivas
            maxPesoReal = maxPeso - (maxPeso / 100 * ((locomotivas.size() - 1) * 10));

            // Calcula a quantidade máxima de vagões a partir da quantidade de locomotivas
            maxVagoesReal = (int) Math.floor(maxVagoes - (maxVagoes / 100.0 * ((locomotivas.size() - 1) * 10)));
        }
    }

    /**
     * engata um vagão na composição
     * @param vagao Vagão a ser engatado
     * @param garagem Garagem na qual o vagao sera removido
     * author joao.farah@edu.pucrs.br, l.gamarra@edu.pucrs.br
     */
    public void engataVagao(Vagao vagao, GaragemVagoes garagem)
    {
        // Troca o estado do vagão para em uso(1)
        vagao.setComposicao(1);

        // Adiciona o peso do vagão a composição
        pesoAtual += vagao.getCapacidadeCarga();

        // Adiciona o vagão a composição
        carros.add(vagao);

        // Remove o vagão da garagem
        garagem.removeVagao(vagao);
    }

    /**
     * Desengata a ultima locomotiva engatada na composicao
     * @param garagem Garagem em que a locomotiva sera armazenada
     * @author joao.farah@edu.pucrs.br, l.gamarra@edu.pucrs.br
     */
    public void desengataLocomotiva(GaragemLocomotivas garagem)
    {
        // Troca o estado da locomotiva para disponível(0)
        locomotivas.get(locomotivas.size() - 1).setComposicao(0);

        // Adiciona a locomotiva a garagem
        garagem.setLocomotiva(locomotivas.get(locomotivas.size() - 1));

        // Altera a capacidade da composição
        maxPeso -= locomotivas.get(locomotivas.size() - 1).getPesoMax();
        maxVagoes -= locomotivas.get(locomotivas.size() - 1).getQtdadeMaxVagoes();

        // Remove a locomotiva da composição
        locomotivas.remove(locomotivas.size() - 1);
    }

    /**
     * desengata o ultimo vagão engatado na composição
     * @param garagem Garagem que o vagao será armazenado
     * @author joao.farah@edu.pucrs.br, ricardo.rossa@edu.pucrs.br, l.gamarra@edu.pucrs.br
     */
    public void desengataVagao(GaragemVagoes garagem) {
        // Troca o estado do vagão para disponível(0)
        vagoes.get(vagoes.size() - 1).setComposicao(0);

        // Adiciona o vagão a garagem
        garagem.setVagao(vagoes.get(vagoes.size() - 1));

        // Remove o seu peso da composição
        pesoAtual -= vagoes.get(vagoes.size() - 1).getCapacidadeCarga();

        // Remove o vagão da composicao (último vagao)
        vagoes.remove(vagoes.size() - 1);
    }
}
