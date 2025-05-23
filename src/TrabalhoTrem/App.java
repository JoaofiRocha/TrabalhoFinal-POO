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

    // Garagem onde as locomotivas ficam armazenadas
    static GaragemLocomotivas garagemLocomotivas;

    // Garagem onde os vagões ficam armazenados
    static GaragemVagoes garagemVagoes;

    // Pátio onde as composições estão armazenadas
    static PatioComposicoes patioComposicoes;

    // Um objeto da classe composição, utilizado para criação e edição de composições
    static Composicao trem;

    // As variáveis abaixo são utilizadas de forma temporária para armazenar informações da composição
    // Quantidade máxima de vagões
    static int maxVagoes = 0;

    // Peso máximo
    static double maxPeso = 0;

    // Quantidade máxima de vagões anterior
    static int oldMaxVagoes = 0;

    // Peso máximo anterior
    static double oldMaxPeso = 0;

    // String para a visualização da composição
    static String montagemString = "";

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
        garagemLocomotivas = new GaragemLocomotivas();

        // Cria locomotivas para utilização do App
        // generateLocomotivas(garagemLocomotivas);

        // Inicializa a garagem para vagões
        garagemVagoes = new GaragemVagoes();

        // Cria vagões para utilização do App
        // generateVagoes(garagemVagoes);

        // Inicializa o patio para os trems
        patioComposicoes = new PatioComposicoes();

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

    }

    /**
     * Este método é utilizado para criar um novo trem
     * @param n o id usado para a nova composição
     * @author l.gamarra@edu.pucrs.br, joao.farah@edu.pucrs.br, ricardo.rossa@edu.pucrs.br
     */
    public static void id (int n)
    {
        try {
            trem = new Composicao(n, patioComposicoes);
        } catch (IDJaEmUsoException e) {
            throw new IDJaEmUsoException(e.getMessage());
        }
    }

    /**
     * Este método é utilizado para adicionar uma locomotiva a composição
     * @param loc Locomotiva a ser engatada na composição
     * @author l.gamarra@edu.pucrs.br, joao.farah@edu.pucrs.br, ricardo.rossa@edu.pucrs.br
     */
    public static void addLocomotiva (Locomotiva loc)
    {
        trem.engata(loc, garagemLocomotivas);
    }

    /**
     * Este método é utilizado para adicionar um vagão a composição
     * @param vag Vagão a ser engatado na composição
     * @author l.gamarra@edu.pucrs.br, joao.farah@edu.pucrs.br, ricardo.rossa@edu.pucrs.br
     */
    public static void addVagao (Vagao vag)
    {
        trem.engata(vag, garagemVagoes);
    }

    /**
     * Este método calcula as reais capacidades da composição
     * @author l.gamarra@edu.pucrs.br, joao.farah@edu.pucrs.br, ricardo.rossa@edu.pucrs.br
     */
    public static void calc()
    {
        trem.calcularCapacidadeReal();
        maxVagoes = trem.getMaxVagoesReal();
        maxPeso = trem.getMaxPesoReal();
    }


    /**
     * Este método calcula a possibilidade de engatar um vagão a uma composição
     * @param c Composição na qual o vagão vai ser engatado
     * @param v Vagão a ser engatado na composição
     * @return True, caso seja possível adicionar o vagão a composição, caso contrário, false
     * @author l.gamarra@edu.pucrs.br, joao.farah@edu.pucrs.br, ricardo.rossa@edu.pucrs.br
     */
    public static boolean check(Composicao c, Vagao v)
    {
        if (c.getPesoAtual() + v.getCapacidadeCarga() <= c.getMaxPesoReal() && c.getQtdadeVagoes() + 1 <= c.getMaxVagoesReal())
            return true;
        else
            return false;
    }

    /**
     * Verifica a possibilidade de adicionar um vagão a composição
     * @param peso Capacidade de peso do vagão a ser adicionado
     * @return True, caso seja possível adicionar o vagão a composição, caso contrário, false
     * @author l.gamarra@edu.pucrs.br, joao.farah@edu.pucrs.br, ricardo.rossa@edu.pucrs.br
     */
    public static boolean check(double peso)
    {
        oldMaxPeso = maxPeso;
        oldMaxVagoes = maxVagoes;
        maxPeso = maxPeso - peso;
        maxVagoes = maxVagoes - 1;
        if (maxVagoes >= 0 && maxPeso >= 0)
        {
            return true;
        }
        else
        {
            maxPeso = oldMaxPeso;
            maxVagoes = oldMaxVagoes;
            return false;
        }
    }


    /**
     * Este método confere a possibilidade de realizar alguma ação
     * @param n Caso 1: A composição não contém locomotivas, retorna false | Caso 2: A composição não contém vagões, retorna false | Caso 3: A composição contém algum vagão, retorna false
     * @return True, caso a ação seja possível, caso contrário, retorna false
     * @author l.gamarra@edu.pucrs.br, joao.farah@edu.pucrs.br, ricardo.rossa@edu.pucrs.br
     */
    public static boolean check(int n)
    {
        if (n == 1 && trem.getQtdadeLocomotivas() == 0)
            return false;
        else if (n == 2 && trem.getQtdadeVagoes() == 0)
            return false;
        else if (n == 3 && trem.getQtdadeVagoes() > 0)
            return false;
        else
            return true;
    }

    /**
     * Este método é utilizado para adicionar uma composição ao pátio
     * @author l.gamarra@edu.pucrs.br, joao.farah@edu.pucrs.br, ricardo.rossa@edu.pucrs.br
     */
    public static void addTrem()
    {
        patioComposicoes.setComposicao(trem);
        reset();
    }

    /**
     * Este método é utilizado para resetar as variáveis utilizadas na criação de uma composição
     * @author l.gamarra@edu.pucrs.br, joao.farah@edu.pucrs.br, ricardo.rossa@edu.pucrs.br
     */
    public static void reset()
    {
        trem = null;
        maxPeso = 0;
        maxVagoes = 0;
        oldMaxPeso = 0;
        oldMaxVagoes = 0;
        montagemString = "";
    }

    /**
     * Este método é responsável por adicionar a uma String de visualização uma locomotiva
     * @param loc Locomotiva a ser adicionada a visualização
     * @return a String de visualização atualizada
     * @author l.gamarra@edu.pucrs.br, joao.farah@edu.pucrs.br, ricardo.rossa@edu.pucrs.br
     */
    public static String visualComposition(Locomotiva loc){
        if (montagemString.equals(""))
            montagemString = "L" + loc.getIdentificador();
        else
            montagemString += "---L" + loc.getIdentificador();
        return montagemString;
    }

    /**
     * Este método é responsável por adicionar a uma String de visualização um vagão
     * @param vag Vagão a ser adicionado a visualização
     * @return a String de visualização atualizada
     * @author l.gamarra@edu.pucrs.br, joao.farah@edu.pucrs.br, ricardo.rossa@edu.pucrs.br
     */
    public static String visualComposition(Vagao vag){
        if (montagemString.equals(""))
            montagemString = "V" + vag.getIdentificador();
        else
            montagemString += "---V" + vag.getIdentificador();
        return montagemString;
    }

    /**
     * Este método é utilizado para remover o último elemento de uma composição
     * @param comp Composição de onde o elemento será removido
     * @author l.gamarra@edu.pucrs.br, joao.farah@edu.pucrs.br, ricardo.rossa@edu.pucrs.br
     */
    public static void removeLast(Composicao comp) {
        if (comp.getLast() instanceof Locomotiva && comp.getQtdadeLocomotivas() >= 2)
            comp.desengata(garagemLocomotivas);
        else if (comp.getLast() instanceof Vagao)
            comp.desengata(garagemVagoes);
    }

    /**
     * Este método é utilizado para carregar uma composição
     * @param comp Composição para ser carregada
     * @author l.gamarra@edu.pucrs.br, joao.farah@edu.pucrs.br, ricardo.rossa@edu.pucrs.br
     */
    public static void loadComposition(Composicao comp) {
        trem = comp;
    }

    /**
     * Este método retorna a composição armazenada na classe App
     * @return Composição armazenada na classe App
     * @author l.gamarra@edu.pucrs.br, joao.farah@edu.pucrs.br, ricardo.rossa@edu.pucrs.br
     */
    public static Composicao getTrem()
    {
        return trem;
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

            String print = (vizualizaComposicoes(trens, i));
            // Imprime a visualização
            System.out.println(print);

        }

    }

    /**
     * Este método cria a String da montagem de uma composição
     * @param trens Local onde as composições estão armazenados
     * @param i Posição da composição na lista
     * @return String da montagem de uma composição
     * @author l.gamarra@edu.pucrs.br
     */
    public static String vizualizaComposicoes(PatioComposicoes trens, int i){
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
        return print;
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

    /**
     * Este método cria uma Array com todas as composições
     * @return Array com todas as composições
     * @author l.gamarra@edu.pucrs.br, joao.farah@edu.pucrs.br, ricardo.rossa@edu.pucrs.br
     */
    public static Composicao[] composicoesToList()
    {
        Composicao[] res = new Composicao[patioComposicoes.totalComposicoes()];

        for (int i = 0; i < patioComposicoes.totalComposicoes(); i++)
        {
            res[i] = patioComposicoes.getComposicao(i);
        }

        return res;
    }

    /**
     * Este método cria uma Array com todas as locomotivas
     * @return Array com todas as locomotivas
     * @author l.gamarra@edu.pucrs.br, joao.farah@edu.pucrs.br, ricardo.rossa@edu.pucrs.br
     */
    public static Locomotiva[] locomotivasToList()
    {
        Locomotiva[] res = new Locomotiva[garagemLocomotivas.totalLocomotivas()];

        for (int i = 0; i < garagemLocomotivas.totalLocomotivas(); i++)
        {
            res[i] = garagemLocomotivas.getLocomotiva(i);
        }

        return res;
    }

    /**
     * Este método cria uma Array com todos os vagões
     * @return Array com todos os vagões
     * @author l.gamarra@edu.pucrs.br, joao.farah@edu.pucrs.br, ricardo.rossa@edu.pucrs.br
     */
    public static Vagao[] vagoesToList()
    {
        Vagao[] res = new Vagao[garagemVagoes.totalVagoes()];

        for (int i = 0; i < garagemVagoes.totalVagoes(); i++)
        {
            res[i] = garagemVagoes.getVagao(i);
        }

        return res;
    }

    /**
     * Este método cria uma Array com todas as locomotivas
     * @return Array com todas as locomotivas
     * @author l.gamarra@edu.pucrs.br, joao.farah@edu.pucrs.br, ricardo.rossa@edu.pucrs.br
     */
    public static void deleteComposition(Composicao comp) {
        do
        {
            if (comp.getLast() instanceof Locomotiva)
            {
                comp.desengata(garagemLocomotivas);
            }
            else
            {
                comp.desengata(garagemVagoes);
            }
        } while (comp.getQtdadeCarros() > 0);
        patioComposicoes.deletarComposicao(comp);
    }
}


