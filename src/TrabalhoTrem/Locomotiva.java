package TrabalhoTrem;
/**
 * Classe Locomotiva armazena dados das locomotivas
 * @author l.gamarra@edu.pucrs.br, ricardo.rossa@edu.pucrs.br
 * @version 15/10/23
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


    /**
     * CompareTo que faz um Override na implementação da Classe Carro
     * Compara uma locomotiva com a outra por ID, assim habilitando o sort.
     * @param outro o objeto a ser comparado.
     * @return super.compareTo(outro) - Caso o objeto não seja uma Locomotiva, é chamada o construtor pai.
     * @author ricardo.rossa@edu.pucrs.br
     */
    @Override
    public int compareTo(Carro outro)
    {
        if(outro instanceof Locomotiva)
        {
            return Integer.compare(this.getIdentificador(), outro.getIdentificador());
        }
        return super.compareTo(outro);
    }

    public String toString()
    {
        return "ID: " + getIdentificador() + " | Peso Máximo: " + getPesoMax() + " | Quantidade Máxima de Vagões: " + getQtdadeMaxVagoes() + " | ";
    }



}

