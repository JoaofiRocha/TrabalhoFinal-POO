package TrabalhoTrem;

/**
 * Código da classe Carro, responsável por armazenar métodos e variáveis comuns das classes Locomotiva e Vagao
 * @author l.gamarra@edu.pucrs.br, ricardo.rossa@edu.pucrs.br, joao.farah@edu.pucrs.br
 */

public abstract class Carro {

    // Armazena a identificação da locomotiva
    private int id;

    // Armazena a composição (estado) do vagão  -> em uso == 1 | livre == 0
    private int composicao;

    public Carro()
    {

    }

    public Carro (int id, int composicao)
    {
        this.id = id;
        this.composicao = composicao;
    }

    /**
     * Retorna a identificação do carro
     * @return a identificação do carro
     * @author l.gamarra@edu.pucrs.br, ricardo.rossa@edu.pucrs.br
     */
    public int getIdentificador()
    {
        return id;
    }

    /**
     * Retorna o status da composição da locomotiva
     * @return o status da composição da locomotiva
     * @author l.gamarra@edu.pucrs.br
     */
    public int getComposicao()
    {
        return composicao;
    }

    /**
     * Troca o status da composição da locomotiva
     * @param n Status: (0) Disponível // (1) Em uso
     * @author l.gamarra@edu.pucrs.br
     */
    public void setComposicao(int n)
    {
        composicao = n;
    }

}
