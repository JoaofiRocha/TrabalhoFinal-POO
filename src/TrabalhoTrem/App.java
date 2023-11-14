package TrabalhoTrem;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

/**
 *  Principal classe do programa
 * @author l.gamarra@edu.pucrs.br, joao.farah@edu.pucrs.br, @ricardo.rossa@edu.pucrs.br
 * @version 15/10/23
 */
public class App{
    /**
     * Construtor Vazio da classe App (Para Javadoc)
     *
     * @author ricardo.rossa@edu.pucrs.br
     */
    public App(){
    }

    /**
     * Classe Main que executa as instruções que compõem o programa
     *
     * @param args não utilizado
     */
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            UI ui = new UI();
            ui.createAndShow();
        });

        Scanner in = new Scanner(System.in);

        // Inicializa a garagem para locomotivas
        GaragemLocomotivas garagemLocomotivas = new GaragemLocomotivas();

        // Cria locomotivas para utilização do App
        // generateLocomotivas(garagemLocomotivas);

        // Inicializa a garagem para vagões
        GaragemVagoes garagemVagoes = new GaragemVagoes();

        // Cria vagões para utilização do App
        // generateVagoes(garagemVagoes);

        // Inicializa o patio para os trems
        PatioComposicoes patioComposicoes = new PatioComposicoes();

        // Define o Path de onde será carregado as locomotivas e os vagões
        String diretorioAtual = Paths.get("").toAbsolutePath().toString();
        String separadorArquivos = File.separator;
        Path vagoesCSV = Paths.get(diretorioAtual + separadorArquivos + "src" + separadorArquivos + "TrabalhoTrem" + separadorArquivos + "vag.csv");
        Path locomotivasCSV = Paths.get(diretorioAtual + separadorArquivos + "src" + separadorArquivos + "TrabalhoTrem" + separadorArquivos + "loc.csv");
        Path composicoesCSV = Paths.get(diretorioAtual + separadorArquivos + "src" + separadorArquivos + "TrabalhoTrem" + separadorArquivos + "comp.csv");

        // Realiza a leitura dos arquivos CSV Vagões e Locomotiva e armazena os armazena em sua respectiva garagem
        lerVagoes(vagoesCSV, garagemVagoes);
        lerLocomotivas(locomotivasCSV, garagemLocomotivas);
        lerComposicoes(composicoesCSV, patioComposicoes);


        // Recebe inputs do usuário
        int userIn;
        do {
            System.out.println("Criar um Trem(1), Editar um Trem(2), Listar Todos os Trens(3), Desfazer um Trem(4), Salvar e Sair(5)");
            userIn = in.nextInt();

            switch (userIn) {

                case 1:
                    System.out.print("Insira o ID do Trem a ser criado: ");

                    // Recebe o ID do trem
                    int idTrem = in.nextInt();

                    // Inicializa um trem como null e um boolean idValido para checar a validade do ID
                    Composicao trem = null;
                    boolean idValido = false;

                    // Enquanto o ID for invalido, o loop continua executando
                    while (!idValido) {
                        // Aqui se tenta criar um trem, que ja foi inicializado fora deste bloco como null
                        // Caso obtenha-se um idValido, o trem é criado e saímos da exceção
                        try {
                            trem = new Composicao(idTrem, patioComposicoes);
                            idValido = true;
                            System.out.println("Trem criado com sucesso!");
                            System.out.println();
                            // Caso o ID seja inválido, é lançada a exceção que pede para o usuário colocar um novo ID!
                        } catch (IDJaEmUsoException e) {
                            System.out.println(e.getMessage());
                            idTrem = in.nextInt();
                        }
                    }
                    // Permite escolher locomotivas

                    int n;

                    do {
                        System.out.println("LOCOMOTIVAS DISPONÍVEIS:");
                        imprimirInfo(garagemLocomotivas);
                        System.out.print("Insira o ID da locomotiva desejada: ");
                        int id = in.nextInt();
                        int posicao = findLocomotiva(id, garagemLocomotivas);
                        if (posicao == 100) {
                            System.out.println("O ID fornecido não existe!");
                            n = 1;
                        } else {
                            trem.engata(garagemLocomotivas.getLocomotiva(posicao), garagemLocomotivas);
                            System.out.println("Locomotiva acoplada com sucesso!");
                            System.out.print("Gostaria de selecionar outra locomotiva? Sim(1) / Não(2)");
                            n = in.nextInt();
                        }

                    }
                    while (n == 1);

                    // Calcula as capacidades do conjunto de locomotivas escolhido
                    trem.calcularCapacidadeReal();

                    // Permite engatar vagões

                    do {
                        System.out.println("VAGÕES DISPONÍVEIS:");
                        imprimirInfo(garagemVagoes);
                        System.out.println("Peso Disponível: " + (trem.getMaxPesoReal() - trem.getPesoAtual()) + " | Vagões Disponíveis: " + (trem.getMaxVagoesReal() - trem.getQtdadeVagoes()));
                        System.out.print("Insira o ID do vagão desejado: ");
                        int id = in.nextInt();
                        int posicao = findVagao(id, garagemVagoes);
                        if (posicao == 100) {
                            System.out.println("O ID fornecido não existe!");
                            n = 1;
                        } else {
                            if (trem.getMaxPesoReal() - (trem.getPesoAtual() + garagemVagoes.getVagao(posicao).getCapacidadeCarga()) <= 0 || trem.getMaxVagoesReal() - trem.getQtdadeVagoes() == 0) {
                                System.out.println("Não é possível adicionar mais vagões!");
                                n = 2;
                            } else {
                                trem.engata(garagemVagoes.getVagao(posicao), garagemVagoes);
                                System.out.println("Vagão acoplado com sucesso!");
                                System.out.print("Gostaria de selecionar outro vagão? Sim(1) / Não(2)");
                                n = in.nextInt();
                            }
                        }
                    } while (n == 1);

                    patioComposicoes.setComposicao(trem);
                    System.out.println("O trem foi adicionado ao pátio!");

                    break;

                case 2:

                    // Permite ao usuário escolher o trem a ser editado

                    if (patioComposicoes.totalComposicoes() == 0) {
                        System.out.println("Não existem composições para editar!");
                        break;
                    }
                    System.out.print("Insira o ID do trem a ser editado: ");
                    boolean tremExiste;
                    do {
                        idTrem = in.nextInt();
                        tremExiste = patioComposicoes.tremExiste(idTrem);
                        if (!tremExiste)
                            System.out.println("Não existe trem com a ID fornecida!");
                    } while (!tremExiste);

                    // Carrega a composição no objeto "trem"
                    trem = patioComposicoes.getComposicao(patioComposicoes.acharTrem(idTrem));

                    do {
                        // Apresenta diversas possibilidades de edição
                        System.out.println("Inserir uma locomotiva(1), Inserir um vagão(2), Remover o último elemento do trem(3), Listar locomotivas livres(4), Listar vagões livres(5), Encerrar edição(6)");
                        n = in.nextInt();
                        switch (n) {
                            case 1 -> {
                                // Caso existam vagões na composição, não permite que locomotivas sejam adicionadas
                                if (trem.getQtdadeVagoes() > 0)
                                    System.out.println("Para adicionar mais locomotivas é preciso remover todos os vagões!");
                                    // Caso contrario, permite adicionar novas locomotivas
                                else {
                                    System.out.print("Insira o ID da locomotiva desejada: ");
                                    int id = in.nextInt();
                                    int posicao = findLocomotiva(id, garagemLocomotivas);
                                    if (posicao == 100)
                                        System.out.println("O ID fornecido não existe!");
                                    else {
                                        trem.engata(garagemLocomotivas.getLocomotiva(posicao), garagemLocomotivas);
                                        System.out.println("Locomotiva acoplada com sucesso!");

                                        // Calcula novamente as capacidades reais da composição
                                        trem.calcularCapacidadeReal();
                                    }
                                }
                            }

                            case 2 -> {

                                // Permite o usuário escolher um vagão para adicionar a composição
                                System.out.println("Peso Disponível: " + (trem.getMaxPesoReal() - trem.getPesoAtual()) + " | Vagões Disponíveis: " + (trem.getMaxVagoesReal() - trem.getQtdadeVagoes()));
                                System.out.print("Insira o ID do vagão desejado: ");
                                int id = in.nextInt();
                                int posicao = findVagao(id, garagemVagoes);

                                // Caso não exista nenhuma composição com o ID informado, volta ao menu de edição
                                if (posicao == 100)
                                    System.out.println("O ID fornecido não existe!");

                                    // Existindo uma composição com o ID informado, é necessário conferir se o vagão pode ser acoplado
                                else {
                                    // Verifica a possibilidade de acoplar o vagão de acordo com as capacidades das locomotivas
                                    if (trem.getMaxPesoReal() - (trem.getPesoAtual() + garagemVagoes.getVagao(posicao).getCapacidadeCarga()) <= 0 || trem.getMaxVagoesReal() - trem.getQtdadeVagoes() == 0) {
                                        System.out.println("Não é possível adicionar mais vagões!");
                                    }

                                    // Acopla o vagão selecionado
                                    else {
                                        trem.engata(garagemVagoes.getVagao(posicao), garagemVagoes);
                                        System.out.println("Vagão acoplado com sucesso!");
                                    }
                                }
                            }

                            case 3 -> {

                                // Verifica a existencia de vagões, caso existam, o último será removido e devolvido a garagem
                                if (trem.getQtdadeVagoes() != 0)
                                    trem.desengata(garagemVagoes);

                                    // Verifica a existencia de locomotivas, caso existam, o último será removido e devolvido a garagem
                                else if (trem.getQtdadeLocomotivas() != 1) {
                                    trem.desengata(garagemLocomotivas);

                                    // A capacidade real da composição é recalculada após a remoção de uma locomotiva
                                    trem.calcularCapacidadeReal();
                                }

                                // Caso exista somente uma locomotiva na composição, não permite que seja removida
                                else
                                    System.out.println("Não é possível deixar um trem sem nenhuma locomotiva! Para excluir um trem, acesse a opção no menu principal.");
                            }

                            case 4 -> {

                                // Imprime todas locomotivas disponíveis
                                System.out.println("LOCOMOTIVAS DISPONÍVEIS:");
                                imprimirInfo(garagemLocomotivas);
                            }

                            case 5 -> {

                                // Imprime todos os vagões disponíveis
                                System.out.println("VAGÕES DISPONÍVEIS:");
                                imprimirInfo(garagemVagoes);
                            }
                        }
                    } while (n != 6);

                    break;

                case 3:

                    // Imprime todas composições no pátio
                    if (patioComposicoes.totalComposicoes() == 0) {
                        System.out.println("Não existem composições para exibir!");
                        break;
                    }
                    imprimirInfo(patioComposicoes);
                    break;

                case 4:

                    // Permite a exclusão de uma composição

                    if (patioComposicoes.totalComposicoes() == 0) {
                        System.out.println("Não existem composições para excluir!");
                        break;
                    }

                    System.out.print("Insira o ID do trem a ser excluído: ");

                    do {
                        idTrem = in.nextInt();
                        tremExiste = patioComposicoes.tremExiste(idTrem);
                        if (!tremExiste)
                            System.out.println("Não existe trem com a ID fornecida!");
                    } while (!tremExiste);

                    // Carrega o objeto composicao para a variável "trem"
                    trem = patioComposicoes.getComposicao(patioComposicoes.acharTrem(idTrem));

                    // Devolve todos os vagões a garagem
                    while (trem.getQtdadeVagoes() != 0)
                        trem.desengata(garagemVagoes);

                    // Devolve todas as locomotivas a garagem
                    while (trem.getQtdadeLocomotivas() != 0)
                        trem.desengata(garagemLocomotivas);

                    // Exclui a composição do pátio
                    patioComposicoes.deletarComposicao(trem);

                    System.out.println("A composição foi excluída! Locomotivas e vagões retornaram a suas respectivas garagens.");

                case 05:
                    salvar("loc.csv", salvar(garagemLocomotivas));
                    salvar("vag.csv", salvar(garagemVagoes));
                    salvar("comp.csv", salvar(patioComposicoes));
            }
        } while (userIn != 5);
    }

    /**
     * Este método é utilizado para gerar diferentes configurações de locomotivas aleatóriamente
     *
     * @param garagem Local onde devem ser armazenados as locomotivas
     * @author l.gamarra@edu.pucrs.br
     */
    public static void generateLocomotivas(GaragemLocomotivas garagem){
        for (int i = 0; i < 100; i++) {
            int id = i;
            int peso = (int) (30 + Math.random() * 100) * 5;
            int vagoes = peso / 15;
            addLocomotiva(id, peso, vagoes, garagem);
        }
    }


    /**
     * Cria um novo objeto Locomotiva, e coloca-o na garagem respectiva
     *
     * @param id      Identificador da locomotiva
     * @param peso    Peso máximo suportado
     * @param vagoes  Quantidade máxima de vagões suportado
     * @param garagem Local onde a locomotiva deve ser armazenada
     * @author l.gamarra@edu.pucrs.br
     */
    public static void addLocomotiva(int id, int peso, int vagoes, GaragemLocomotivas garagem){
        Locomotiva locomotiva = new Locomotiva(id, peso, vagoes);
        garagem.setLocomotiva(locomotiva);
    }


    /**
     * Este método é utilizado para imprimir todas as locomotivas armazenadas na garagem.
     *
     * @param garagemLocomotivas Local onde as locomotivas estão armazenadas
     * @author l.gamarra@edu.pucrs.br
     */
    public static void imprimirInfo(GaragemLocomotivas garagemLocomotivas){
        garagemLocomotivas.sortLocomotivas();
        System.out.println("          Locomotivas Disponíveis");
        System.out.println("   ID   |   Peso Máx.   |   Vagões Máx.  |");
        for (int i = 0; i < garagemLocomotivas.totalLocomotivas(); i++) {
            System.out.print(garagemLocomotivas.getLocomotiva(i).getIdentificador());
            imprimirEspaços(calcularEspaços(8, String.valueOf(garagemLocomotivas.getLocomotiva(i).getIdentificador()).length()));
            System.out.print("|");

            System.out.print(garagemLocomotivas.getLocomotiva(i).getPesoMax());
            imprimirEspaços(calcularEspaços(15, String.valueOf(garagemLocomotivas.getLocomotiva(i).getPesoMax()).length()));
            System.out.print("|");

            System.out.print(garagemLocomotivas.getLocomotiva(i).getQtdadeMaxVagoes());
            imprimirEspaços(calcularEspaços(16, String.valueOf(garagemLocomotivas.getLocomotiva(i).getQtdadeMaxVagoes()).length()));
            System.out.println("|");

        }
    }

    /**
     * Imprime espaços, de acordo com o valor inserido
     *
     * @param n Quantidade de espaços para serem impressos
     * @author l.gamarra@edu.pucrs.br
     */
    public static void imprimirEspaços(int n){
        for (int i = 0; i < n; i++) {
            System.out.print(" ");
        }
    }

    /**
     * Calcula quantos espaços são necessários para imprimir uma palavra de forma formatada
     *
     * @param total   O tamanho do espaço a ser preenchido
     * @param palavra A palavra que será impressa
     * @return A quantidade de espaços necessários para preencher o espaço total
     * @author l.gamarra@edu.pucrs.br
     */
    public static int calcularEspaços(int total, int palavra){
        return (total - palavra);
    }

    /**
     * Cria um novo objeto Vagao, e coloca-o na garagem respectiva
     *
     * @param id      Identificador do vagão
     * @param peso    Peso máximo suportado
     * @param garagem Local onde o vagão deve ser armazenado
     * @author l.gamarra@edu.pucrs.br
     */
    public static void addVagao(int id, int peso, GaragemVagoes garagem){
        Vagao vagao = new Vagao(id, peso, 0);
        garagem.setVagao(vagao);
    }


    /**
     * Este método é utilizado para imprimir todas os vagões armazenados na garagem.
     *
     * @param garagemVagoes Local de armazenamento dos vagões
     * @author ricardo.rossa@edu.pucrs.br
     */
    public static void imprimirInfo(GaragemVagoes garagemVagoes){
        garagemVagoes.sortVagoes();
        System.out.println("       Vagões Disponíveis");
        System.out.println("   ID   |   Carga Máx.   |");
        for (int i = 0; i < garagemVagoes.totalVagoes(); i++) {
            System.out.print(garagemVagoes.getVagao(i).getIdentificador());
            imprimirEspaços(calcularEspaços(8, String.valueOf(garagemVagoes.getVagao(i).getIdentificador()).length()));
            System.out.print("|");

            System.out.print(garagemVagoes.getVagao(i).getCapacidadeCarga());
            imprimirEspaços(calcularEspaços(16, String.valueOf(garagemVagoes.getVagao(i).getCapacidadeCarga()).length()));
            System.out.println("|");
        }

    }

    /**
     * Este método é utilizado para imprimir todas os trens criados que foram armazenados no patio.
     *
     * @param trens Local onde os trens estão armazenadas
     * @author joao.farah@edu.pucrs.br, l.gamarra@edu.pucrs.br
     */
    public static void imprimirInfo(PatioComposicoes trens){
        trens.sortComposicoes();
        System.out.println("          Composições Disponíveis (\u001B[35mLocomotivas\u001B[0m e \u001B[36mVagões\u001B[0m)");
        System.out.println("   ID   |   Quantidade de Locomotivas   |   Quantidade de Vagões   |   Peso do Trem   |   Visualização da Composição");
        for (int i = 0; i < trens.totalComposicoes(); i++) {
            System.out.print(trens.getComposicao(i).getIdentificador());
            imprimirEspaços(calcularEspaços(8, String.valueOf(trens.getComposicao(i).getIdentificador()).length()));
            System.out.print("|");

            System.out.print(trens.getComposicao(i).getQtdadeLocomotivas());
            imprimirEspaços(calcularEspaços(31, String.valueOf(trens.getComposicao(i).getQtdadeLocomotivas()).length()));
            System.out.print("|");

            System.out.print(trens.getComposicao(i).getQtdadeVagoes());
            imprimirEspaços(calcularEspaços(26, String.valueOf(trens.getComposicao(i).getQtdadeVagoes()).length()));
            System.out.print("|");

            System.out.print(trens.getComposicao(i).getPesoAtual());
            imprimirEspaços(calcularEspaços(18, String.valueOf(trens.getComposicao(i).getPesoAtual()).length()));
            System.out.print("|");

            // Inicializa strings com valores de cores
            String reset = "\u001B[0m";
            String purple = "\u001B[35m";
            String cyan = "\u001B[36m";

            // Inicializa uma string vazia
            String print = "";

            // Identifica cada carro da composição
            for (int k = 0; k < trens.getComposicao(i).getQtdadeCarros(); k++) {

                // Caso o carro seja uma locomotiva
                if (trens.getComposicao(i).getCarro(k) instanceof Locomotiva)
                    print += purple + "L" + trens.getComposicao(i).getCarro(k).getIdentificador() + reset;

                    // Caso o carro seja um vagão
                else
                    print += cyan + "V" + trens.getComposicao(i).getCarro(k).getIdentificador() + reset;

                // Caso não seja o último elemento é preciso concatenar a ligação
                if (k < trens.getComposicao(i).getQtdadeCarros() - 1)
                    print += "---";
            }
            // Imprime a visualização
            System.out.println(print);

        }

    }

    /**
     * Este método é utilizado para gerar diferentes configurações de vagões aleatóriamente
     *
     * @param garagem Local onde devem ser armazenados os vagões
     * @author l.gamarra@edu.pucrs.br
     */
    public static void generateVagoes(GaragemVagoes garagem){
        for (int i = 0; i < 100; i++) {
            int id = i;
            int peso = (int) (4 + Math.random() * 8) * 5;
            addVagao(id, peso, garagem);
        }
    }

    /**
     * Este método é utilizado para encontrar a locomotiva especificada pelo usuário
     * Caso a locomotiva não seja encontrada (inválida) é retornado 100.
     *
     * @param id      Identificação da locomotiva que deseja-se encontrar
     * @param garagem Local onde a locomotiva deve ser localizada
     * @return Retorna a posição na array da locomotiva com o ID fornecido
     * @author l.gamarra@edu.pucrs.br
     */
    public static int findLocomotiva(int id, GaragemLocomotivas garagem){
        // Procura a locomotiva com o ID fornecido
        for (int i = 0; i < garagem.totalLocomotivas(); i++) {
            if (id == garagem.getLocomotiva(i).getIdentificador())
                return i;
        }

        // No caso do ID não existir, o valor 100 é retornado
        return 100;
    }

    /**
     * Este método é utilizado para encontrar o vagão especificado pelo usuário
     * Caso o vagão não seja encontrado (inválida) é retornado 100.
     *
     * @param id      Identificação do vagão que deseja-se encontrar
     * @param garagem Local onde o vagão deve ser localizado
     * @return Retorna a posição na array do vagão com o ID fornecido
     * @author l.gamarra@edu.pucrs.br
     */
    public static int findVagao(int id, GaragemVagoes garagem){
        // Procura a locomotiva com o ID fornecido
        for (int i = 0; i < garagem.totalVagoes(); i++) {
            if (id == garagem.getVagao(i).getIdentificador())
                return i;
        }

        // No caso do ID não existir, o valor 100 é retornado
        return 100;
    }

    /**
     * Método que realiza a leitura de um arquivo contendo Vagoes.
     *
     * @param vagoesCSV     - path do arquivo.
     * @param garagemVagoes - onde estao aramzenados os vagoes
     * @author ricardo.rossa@edu.pucrs.br
     */
    public static void lerVagoes(Path vagoesCSV, GaragemVagoes garagemVagoes){
        // Configura o arquivo a ser lido
        try (BufferedReader br = Files.newBufferedReader(vagoesCSV)) {
            File file = vagoesCSV.toFile();

            // Caso a File esteja vazia, automaticamente é gerado conteúdo.
            if (file.length() == 0) {
                generateVagoes(garagemVagoes);
            }

            // Inicializa uma string vazia
            String line = "";

            // Executa um loop até o final do arquivo
            while ((line = br.readLine()) != null) {
                // Separa o conteúdo lido em uma array de string separado por ","
                String[] partes = line.split(",", 3);

                // Confere se o procedimento foi concluído com êxito
                if (partes.length == 3) {
                    // Carrega o código
                    int codigo = Integer.parseInt(partes[0].trim());

                    // Carrega a carga máx.
                    double maxCarga = Double.parseDouble(partes[1].trim());

                    // Inicializa um vagão com os dados informados
                    Vagao vag = new Vagao(codigo, maxCarga, 0);

                    // Salva o vagão na garagem
                    garagemVagoes.setVagao(vag);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Método que realiza a leitura de um arquivo contendo Locomotivas.
     *
     * @param locomotivasCSV     - path do arquivo.
     * @param garagemLocomotivas - onde estao armazenadas as locomotivas
     * @author ricardo.rossa@edu.pucrs.br
     */
    public static void lerLocomotivas(Path locomotivasCSV, GaragemLocomotivas garagemLocomotivas){
        // Configura o arquivo a ser lido
        try (BufferedReader br = Files.newBufferedReader(locomotivasCSV)) {
            File file = locomotivasCSV.toFile();

            // Caso a File esteja vazia, automaticamente é gerado conteúdo.
            if (file.length() == 0) {
                generateLocomotivas(garagemLocomotivas);
            }

            // Inicializa uma string vazia
            String line = "";

            // Executa um loop até o final do arquivo
            while ((line = br.readLine()) != null) {
                // Separa o conteúdo lido em uma array de string separado por ","
                String[] partes = line.split(",", 4);

                // Confere se o procedimento foi concluído com êxito
                if (partes.length == 4) {
                    // Carrega o código
                    int id = Integer.parseInt(partes[0].trim());

                    // Carrega o peso máx.
                    double pesoMax = Double.parseDouble(partes[1].trim());

                    // Carrega a quantidade máx. de vagões
                    int quantMaxVagoes = Integer.parseInt(partes[2].trim());

                    // Inicializa uma locomotiva com os dados informados
                    Locomotiva locom = new Locomotiva(id, pesoMax, quantMaxVagoes);

                    // Salva a locomotiva na garagem
                    garagemLocomotivas.setLocomotiva(locom);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Método que realiza a leitura de um arquivo contendo Composicoes.
     *
     * @param path             - path do arquivo.
     * @param patioComposicoes - onde estao armazenadas as composicoes
     * @author l.gamarra@edu.pucrs.br, ricardo.rossa@edu.pucrs.br, joao.farah@edu.pucrs.br
     */
    public static void lerComposicoes(Path path, PatioComposicoes patioComposicoes){
        // Configura o arquivo a ser lido
        try (BufferedReader br = Files.newBufferedReader(path)) {

            // Inicializa uma string vazia
            String line = "";

            // Enquanto a linha lida for diferente de "null", inicializa uma nova composição
            while ((line = br.readLine()) != null) {
                // Caso a linha tenha tamanho == 0, cancela o loop
                if (line.length() == 0)
                    break;

                // Declara uma nova composição
                Composicao c;

                try {
                    // Inicializa a composição utilizando o "id" lido do arquivo, e a armazena no pátio
                    c = new Composicao(Integer.parseInt(line.substring(0, line.length() - 1)), patioComposicoes);
                } catch (IDJaEmUsoException e) {
                    throw new RuntimeException(e);
                }

                // Enquanto a linha não for vazia(""), inicializa uma nova locomotiva
                while (!(line = br.readLine()).equals("")) {
                    // Separa o conteúdo lido em uma array de string separado por ","
                    String[] partes = line.split(",", 4);

                    // Confere se o procedimento foi concluído com êxito
                    if (partes.length == 4) {
                        // Carrega o código
                        int id = Integer.parseInt(partes[0].trim());

                        // Carrega o peso máx.
                        double pesoMax = Double.parseDouble(partes[1].trim());

                        // Carrega a quantidade máx. de vagões
                        int quantMaxVagoes = Integer.parseInt(partes[2].trim());

                        // Inicializa a locomotiva com os dados lidos
                        Locomotiva locom = new Locomotiva(id, pesoMax, quantMaxVagoes);

                        // Engata a locomotiva na composição
                        c.engata(locom);
                    }
                }

                // Enquanto a linha não for vazia(""), inicializa um novo vagão
                while (!(line = br.readLine()).equals("")) {
                    // Separa o conteúdo lido em uma array de string separado por ","
                    String[] partes = line.split(",", 3);

                    // Confere se o procedimento foi concluído com êxito
                    if (partes.length == 3) {
                        // Carrega o código
                        int codigo = Integer.parseInt(partes[0].trim());

                        // Carrega a carga máx.
                        double maxCarga = Double.parseDouble(partes[1].trim());

                        // Inicializa o vagão através dos dados lidos
                        Vagao vag = new Vagao(codigo, maxCarga, 0);

                        // Engata o vagão na composição
                        c.engata(vag);
                    }
                }
                // Calcula a capacidade real da composição
                c.calcularCapacidadeReal();

                // Armazena a composição no pátio
                patioComposicoes.setComposicao(c);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Método que salva um arquivo recebido como input.
     *
     * @param fileName - nome do arquivo
     * @param print    - conteúdo a ser escrito no arquivo.
     * @author l.gamarra@edu.pucrs.br, joao.farah@edu.pucrs.br, ricardo.rossa@edu.pucrs.br
     */
    public static void salvar(String fileName, String print){
        // Obtem o separador específico do sistema do usuário
        String separador = File.separator;

        // Obtem o caminho do diretório atual
        String currDir = Paths.get("").toAbsolutePath().toString();

        // Configura o diretório específico para os arquivos CSV dentro do projeto
        String fileComplete = currDir + separador + "src" + separador + "TrabalhoTrem" + separador;

        // Cria o arquivo onde o estado do programa será salvado com o nome correto
        File save = new File(fileComplete, fileName);

        try {
            // Inicializa o BufferedWriter
            BufferedWriter bw = new BufferedWriter(new FileWriter(save));

            // Salva o conteúdo do print
            bw.write(print);

            // Salva imediatamente
            bw.flush();

            // Encerra o BufferedWriter
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Método utilizado dentro do método salvar para arquivos. Este método salva uma string formatada, contendo todas as locomotivas.
     *
     * @param garagemLocomotivas - onde estão aramzenadas as locomotivas
     * @return result - String formatada de locomotivas.
     * @author l.gamarra@edu.pucrs.br, joao.farah@edu.pucrs.br, ricardo.rossa@edu.pucrs.br
     */
    public static String salvar(GaragemLocomotivas garagemLocomotivas){
        // Organiza as locomotivas na garagem
        garagemLocomotivas.sortLocomotivas();

        // Inicializa uma string vazia
        String result = "";

        // Concatena dados de cada umas das locomotivas armazenadas
        for (int i = 0; i < garagemLocomotivas.totalLocomotivas(); i++) {
            result += salvar(garagemLocomotivas.getLocomotiva(i));
        }

        // Retorna result
        return result;
    }

    /**
     * Método utilizado dentro do método salvar para arquivos. Este método salva uma string formatada, contendo uma locomotiva.
     *
     * @param locomotiva - locomotiva individual a ser salva
     * @return result - String formatada da locomotiva.
     * @author l.gamarra@edu.pucrs.br, joao.farah@edu.pucrs.br, ricardo.rossa@edu.pucrs.br
     */
    public static String salvar(Locomotiva locomotiva){
        // Inicializa uma string vazia
        String result = "";

        // Concatena o identificador da locomotiva seguido de ","
        result += locomotiva.getIdentificador();
        result += ", ";

        // Concatena o peso máx. da locomotiva seguido de ","
        result += locomotiva.getPesoMax();
        result += ", ";

        // Concatena a quantidade máx. de vagões da locomotiva seguido de ","
        result += locomotiva.getQtdadeMaxVagoes();
        result += ", ";
        result += "\n";

        // Retorna result
        return result;
    }

    /**
     * Método utilizado dentro do método salvar para arquivos. Este método salva uma string formatada, contendo todos os vagoes.
     *
     * @param garagemVagoes- onde estão aramzenados os Vagoes.
     * @return result - String formatada de vagoes.
     * @author l.gamarra@edu.pucrs.br, joao.farah@edu.pucrs.br, ricardo.rossa@edu.pucrs.br
     */
    public static String salvar(GaragemVagoes garagemVagoes){
        // Organiza os vagões na garagem
        garagemVagoes.sortVagoes();

        // Inicializa uma string vazia
        String res = "";

        // Concatena dados de cada um dos vagões armazenados
        for (int i = 0; i < garagemVagoes.totalVagoes(); i++) {
            res += salvar(garagemVagoes.getVagao(i));
        }

        // Retorna result
        return res;
    }

    /**
     * Método utilizado dentro do método salvar para arquivos. Este método salva uma string formatada, contendo um vagao.
     *
     * @param vagao - vagao individual a ser salvo
     * @return result - String formatada do vagao
     * @author l.gamarra@edu.pucrs.br, joao.farah@edu.pucrs.br, ricardo.rossa@edu.pucrs.br
     */
    public static String salvar(Vagao vagao){
        // Inicializa uma string vazia
        String res = "";

        // Concatena o identificador do vagão seguido de ","
        res += vagao.getIdentificador();
        res += ", ";

        // Concatena a capacidade de carga do vagão seguido de ","
        res += vagao.getCapacidadeCarga();
        res += ", ";
        res += "\n";

        // Retorna result
        return res;
    }

    /**
     * Método utilizado dentro do método salvar para arquivos. Este método salva uma string formatada, contendo todas as composicoes.
     *
     * @param trens - onde estão aramzenadas as composicoes
     * @return result - String formatada de composicoes
     * @author l.gamarra@edu.pucrs.br, joao.farah@edu.pucrs.br, ricardo.rossa@edu.pucrs.br
     */
    public static String salvar(PatioComposicoes trens){
        // Organiza as composições no pátio
        trens.sortComposicoes();

        // Inicializa uma string vazia
        String result = "";

        // Concatena dados de cada umas das composições armazenadas
        for (int i = 0; i < trens.totalComposicoes(); i++) {
            // Concatena o identificador da composição
            result += trens.getComposicao(i).getIdentificador();

            // Concatena uma "," e quebra a linha
            result += ",\n";

            // Concatena dados de cada uma das locomotivas da composição
            for (int k = 0; k < trens.getComposicao(i).getQtdadeLocomotivas(); k++) {
                // Concatena dados das locomotivas
                result += salvar((Locomotiva) trens.getComposicao(i).getCarro(k));
            }
            // Quebra a linha
            result += "\n";

            // Concatena dados de todos os vagões da composição
            for (int k = trens.getComposicao(i).getQtdadeLocomotivas(); k < trens.getComposicao(i).getQtdadeCarros(); k++) {
                // Concatena dados de vagões
                result += salvar((Vagao) trens.getComposicao(i).getCarro(k));
            }
            // Quebra a linha
            result += "\n";
        }
        // Retorna result
        return result;
    }

    public static String addEspaços(int n){
        String res ="";
        for (int i = 0; i < n; i++) {
            res += " ";
        }
        return res;
    }

    public static String[] retornaInfo(){
        getGaragemLocomotivas();
        String[] res = new String[garagemLocomotivas.totalLocomotivas()];

        for (int i = 0; i < garagemVagoes.totalVagoes(); i++) {
            res[i] = "";

            res[i] += garagemVagoes.getVagao(i).getIdentificador();
            res[i] += addEspaços(8);

            res[i] += "|";

            res[i] += garagemVagoes.getVagao(i).getCapacidadeCarga();
            res[i] += addEspaços(16);

            res[i] += "|";
        }
        return res;
    }
}


