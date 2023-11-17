package TrabalhoTrem;
/**
 * Classe de uma exceção personalizada que verifica se o ID da composição desejada está em uso.
 * @author ricardo.rossa@edu.pucrs.br
 * @version 05/09/2023
 */
public class IDJaEmUsoException extends RuntimeException {
    /**
     * Exceção que lança uma mensagem ao usuário caso o ID já esteja em uso.
     * @param message mensagem que pede para o usuário colocar outro ID.
     * @author ricardo.rossa@edu.pucrs.br
     */
    public IDJaEmUsoException(String message) {
        // Caso a exceção seja lançada, é enviado uma mensagem personalizada ao usuário.
        super(message);
    }
}
