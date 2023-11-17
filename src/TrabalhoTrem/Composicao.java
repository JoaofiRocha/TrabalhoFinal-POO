package TrabalhoTrem;
import java.util.ArrayList;

/**
 * Classe Composição
 * @author joao.farah@edu.pucrs.br, l.gamarra@edu.pucrs.br, ricardo.rossa@edu.pucrs.br
 * @version 15/10/2023
 */
public class Composicao implements Comparable<Composicao> {

    //Armazena o código identificador da composição
    private final int id;

    //Armazena o número de vagões
    //private ArrayList<Vagao> vagoes = new ArrayList<Vagao>();

    //Armazena o número de locomotivas
    // private ArrayList<Locomotiva> locomotivas = new ArrayList<Locomotiva>();

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
     *
     * @author joao.farah@edu.pucrs.br
     */
    public Composicao() {
        id = 0;
    }

    /**
     * Construtor da classe Composição
     * Este construtor lança uma exceção IDJaEmUsoException
     *
     * @param id    número de identificação da composição
     * @param patio patio de composições para poder selecionar o patio onde está a composição
     * @throws IDJaEmUsoException se o ID já estiver em uso
     * @author ricardo.rossa@edu.pucrs.br, joao.farah@edu.pucrs.br
     */
    public Composicao(int id, PatioComposicoes patio) throws IDJaEmUsoException {
        if (patio.tremExiste(id)) {
            throw new IDJaEmUsoException("ID EM USO, ESCOLHA OUTRO ID PARA O TREM!");
        }
        // Somente atribui-se o valor do ID a composição caso a exceção não seja lançada
        this.id = id;

    }

    /**
     * Retorna a capacidade máxima de peso real da composição
     *
     * @return A capacidade máxima de peso real da composição
     * @author l.gamarra@edu.pucrs.br
     */
    public double getMaxPesoReal() {
        return maxPesoReal;
    }

    /**
     * Retorna a quantidade máxima de vagões real da composição
     *
     * @return A quantidade máxima de vagões real da composição
     * @author l.gamarra@edu.pucrs.br
     */
    public int getMaxVagoesReal() {
        return maxVagoesReal;
    }

    /**
     * Retorna o peso atual da composição
     *
     * @return O peso atual da composição
     * @author l.gamarra@edu.pucrs.br
     */
    public double getPesoAtual() {
        return pesoAtual;
    }

    /**
     * retorna o identificador da composição
     *
     * @return id da composição
     * @author joao.farah@edu.pucrs.br
     */
    public int getIdentificador() {
        return id;
    }

    /**
     * Retorna o identificador da composição
     * @param n - valor do ID
     * @return id da composição
     * @author joao.farah@edu.pucrs.br
     */
    public int setIdentificador(int n) {
        n = id;
        return n;
    }

    /**
     * Retorna a quantidade de locomotivas
     *
     * @return quantidade de locomotivas
     * @author ricardo.rossa@edu.pucrs.br
     */
    public int getQtdadeLocomotivas() {
        int quant = 0;
        for (Carro c : carros) {
            if (c instanceof Locomotiva) {
                quant++;
            }
        }
        return quant;
    }

    /**
     * Retorna a quantidade de vagões
     *
     * @return quantidade de vagões
     * @author joao.farah@edu.pucrs.br
     */
    public int getQtdadeVagoes() {
        int quant = 0;
        for (Carro c : carros)
            if (c instanceof Vagao) {
                quant++;
            }
        return quant;
    }

    /**
     * Engata uma locomotiva na composição
     *
     * @param locomotiva Locomotiva a ser engatada
     * @param garagem    Garagem na qual a locomotiva sera removida
     * @author joao.farah@edu.pucrs.br, l.gamarra@edu.pucrs.br, ricardo.rossa@edu.pucrs.br
     */
    public void engata(Locomotiva locomotiva, GaragemLocomotivas garagem) {
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
     * Engata uma locomotiva individual na composição
     *
     * @param locomotiva Locomotiva a ser engatada
     * @author joao.farah@edu.pucrs.br, l.gamarra@edu.pucrs.br, ricardo.rossa@edu.pucrs.br
     */
    public void engata(Locomotiva locomotiva) {
        // Troca o estado da locomotiva para em uso(1)
        locomotiva.setComposicao(1);

        // Adiciona locomotiva a composição
        carros.add(locomotiva);

        // Incrementa a capacidade de peso do trem
        maxPeso += locomotiva.getPesoMax();

        // Incrementa a capacidade de vagões do trem
        maxVagoes += locomotiva.getQtdadeMaxVagoes();
    }

    /**
     * Calcula a capacidade real da composição dependendo da quantidade de locomotivas engatadas
     *
     * @author l.gamarra@edu.pucrs.br, ricardo.rossa@edu.pucrs.br
     */
    public void calcularCapacidadeReal() {
        maxPesoReal = maxPeso;
        maxVagoesReal = maxVagoes;

        // No caso da composição utilizar mais de uma locomotiva, é necessário calcular suas capacidades reais
        if (getQtdadeLocomotivas() > 1) {
            // Calcula a capacidade de peso máxima a partir da quantidade de locomotivas
            maxPesoReal = maxPeso - (maxPeso / 100 * ((getQtdadeLocomotivas() - 1) * 10));

            // Calcula a quantidade máxima de vagões a partir da quantidade de locomotivas
            maxVagoesReal = (int) Math.floor(maxVagoes - (maxVagoes / 100.0 * ((getQtdadeLocomotivas() - 1) * 10)));
        }
    }

    /**
     * Engata um vagão na composição
     *
     * @param vagao   Vagão a ser engatado
     * @param garagem Garagem na qual o vagao sera removido
     * @author joao.farah@edu.pucrs.br, l.gamarra@edu.pucrs.br, ricardo.rossa@edu.pucrs.br
     */
    public void engata(Vagao vagao, GaragemVagoes garagem) {
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
     * Engata um Vagao individual na composição
     *
     * @param vagao Vagao a ser engatado
     * @author joao.farah@edu.pucrs.br, l.gamarra@edu.pucrs.br, ricardo.rossa@edu.pucrs.br
     */
    public void engata(Vagao vagao) {
        // Troca o estado do vagão para em uso(1)
        vagao.setComposicao(1);

        // Adiciona o peso do vagão a composição
        pesoAtual += vagao.getCapacidadeCarga();

        // Adiciona o vagão a composição
        carros.add(vagao);

    }

    /**
     * Desengata a ultima locomotiva engatada na composicao
     * @param garagem Garagem em que a locomotiva sera armazenada
     * @author ricardo.rossa@edu.pucrs.br, joao.farah@edu.pucrs.br, l.gamarra@edu.pucrs.br
     */
    public void desengata(GaragemLocomotivas garagem)
    {
        // Troca o estado da locomotiva para disponível(0)
        int i = 1;

        Locomotiva l = null;
       for(Carro c: carros)
       {
           c = carros.get(carros.size() - i);
           if(c instanceof Locomotiva)
           {
               c.setComposicao(0);
               l = (Locomotiva) c;
           }
           else
           {
               i++;
           }
       }
        // Adiciona a locomotiva a garagem
        garagem.setLocomotiva(l);

        // Altera a capacidade da composição
        assert l != null;
        maxPeso -= l.getPesoMax();
        maxVagoes -= l.getQtdadeMaxVagoes();

        // Remove a locomotiva da composição
       carros.remove(l);
    }

    /**
     * Desengata o ultimo vagão engatado na composição
     * @param garagem Garagem que o vagao será armazenado
     * @author  ricardo.rossa@edu.pucrs.br, joao.farah@edu.pucrs.br, l.gamarra@edu.pucrs.br
     */
    public void desengata(GaragemVagoes garagem) {

        // Troca o estado do vagão para disponível(0)
        int i = 1;
        Vagao v = null;
        for(Carro c: carros)
        {
            c = carros.get(carros.size() - i);
            if(c instanceof Vagao)
            {
                c.setComposicao(0);
                v = (Vagao) c;
            }
            else
            {
                i++;
            }
        }

        // Adiciona o vagão a garagem
        garagem.setVagao(v);

        // Remove o seu peso da composição
        assert v != null;
        pesoAtual -= v.getCapacidadeCarga();

        // Remove o vagão da composicao (último vagao)
       carros.remove(v);
    }

    /**
     * Retorna o carro na posição N
     * @param n - carro no index N
     * @return carro na N
     * @author joao.farah@edu.pucrs.br
     */
    public Carro getCarro(int n)
    {
        return carros.get(n);
    }

    /**
     * Retorna a quantidade de carros
     * @return quantidade de carros
     * @author joao.farah@edu.pucrs.br
     */
    public int getQtdadeCarros(){return carros.size();}



    /**
     * CompareTo - Compara uma composicao com a outra por ID, assim habilitando o sort.
     * @param outro o objeto a ser comparado.
     * @return Integer.compare(this.getIdentificador(), outro.getIdentificador()) - comparacao por ID
     * @author ricardo.rossa@edu.pucrs.br
     */
    public int compareTo(Composicao outro)
    {
        return Integer.compare(this.getIdentificador(), outro.getIdentificador());
    }

    public String toString()
    {
        return "ID: " + id + " | Locomotivas: " + getQtdadeLocomotivas() + " | Vagões: " + getQtdadeVagoes() + " | Peso do Trem: " + maxPesoReal + " |";
    }
}
