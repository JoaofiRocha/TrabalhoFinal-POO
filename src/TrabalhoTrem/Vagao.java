package TrabalhoTrem;
/**
 * Classe Vagão
 * @author ricardo.rossa@edu.pucrs.br
 * @version 15/10/23
 */
public class Vagao extends Carro {
    // Armazena o valor de carga máxima suportado pelo vagão
    private double cargaMaxima;

    /**
     * Construtor Vazio da Classe Vagão
     *
     * @author ricardo.rossa@edu.pucrs.br
     */
    public Vagao() {
        super();
        cargaMaxima = 0.0;
    }

    /**
     * Construtor da Classe Vagao
     *
     * @param id Número de identificação
     * @author ricardo.rossa@edu.pucrs.br
     */
    public Vagao(int id) {
        super(id, 0);
        cargaMaxima = 0.0;
    }

    /**
     * Construtor da Classe Vagão - Super Carro
     *
     * @param codigo   Número de identificação
     * @param maxCarga Carga máxima suportada pelo vagão
     * @param estado   Atributo que identifica se o vagão está em uso ou livre
     * @author ricardo.rossa@edu.pucrs.br
     */
    public Vagao(int codigo, double maxCarga, int estado) {

        super(codigo, estado);
        cargaMaxima = maxCarga;
    }

    /**
     * Método para obter a Capacidade de Carga do Vagão
     *
     * @return cargaMaxima
     * @author ricardo.rossa@edu.pucrs.br
     */
    public double getCapacidadeCarga() {
        return cargaMaxima;
    }


    /**
     * CompareTo que faz um Override na implementação da Classe Carro
     * Compara um vagao com a outra por ID, assim habilitando o sort.
     * @param outro o objeto a ser comparado.
     * @return super.compareTo(outro) - Caso o objeto não seja uma Vagao, é chamada o construtor pai.
     * @author ricardo.rossa@edu.pucrs.br
     */
    @Override
    public int compareTo(Carro outro)
    {
        if(outro instanceof Vagao)
        {
            return Integer.compare(this.getIdentificador(), outro.getIdentificador());
        }
        return super.compareTo(outro);
    }

    public String toString()
    {
        return "ID: " + getIdentificador() + " | Carga Máxima: " + getCapacidadeCarga() + " | ";
    }

}


