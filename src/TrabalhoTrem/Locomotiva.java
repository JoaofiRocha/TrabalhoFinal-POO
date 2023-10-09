package TrabalhoTrem;
/**
 * Classe Locomotiva armazena dados das locomotivas
 * @author l.gamarra@edu.pucrs.br
 * @version 30/08/23
 */

public class Locomotiva extends Carro
{

    // Armazena a capacidade máxima de peso permitida
    private double maxPeso;

    // Armazena a capacidade máxima de vagões engatados
    private int maxVagao;

    /**
     * Construtor Vazio da classe Locomotiva (Para Javadoc)
     * @author ricardo.rossa@edu.pucrs.br
     */
    public Locomotiva() {}

    /**
     * Construtor da classe Locomotiva
     * @param id número de identificação
     * @param maxPeso peso máximo permitido
     * @param maxVagao número máximo de vagões utilizados
     * @author l.gamarra@edu.pucrs.br
     */
    public Locomotiva(int id, double maxPeso, int maxVagao)
    {
        super(id, 0);
        this.maxPeso = maxPeso;
        this.maxVagao = maxVagao;
    }


    /**
     * Retorna o peso máximo permitido
     * @return peso máximo permitido
     * @author l.gamarra@edu.pucrs.br
     */
    public double getPesoMax()
    {
        return maxPeso;
    }

    /**
     * Retorna a quantidade máxima de vagões permitida
     * @return a quantidade máxima de vagões permitida
     * @author l.gamarra@edu.pucrs.br
     */
    public int getQtdadeMaxVagoes()
    {
        return maxVagao;
    }
}

