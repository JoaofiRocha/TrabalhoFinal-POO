package TrabalhoTrem;
import javax.swing.*;
import java.awt.*;

import static TrabalhoTrem.App.*;

public class UI{
    private JFrame frame;
    private JPanel panel;
    public void createAndShow(){
        panel = new JPanel();
        frame = new JFrame("Trem");

        //Menu
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JButton criarTrem = new JButton("Criar Trem");
        JButton editarTrem = new JButton("Editar Trem");
        JButton listarTrem = new JButton("Listar Trem");
        JButton deletarTrem = new JButton("Deletar Trem");
        JButton salvarTrem = new JButton("Salvar Trem");
        panel.add(criarTrem);
        panel.add(editarTrem);
        panel.add(listarTrem);
        panel.add(deletarTrem);
        panel.add(salvarTrem);

        //Criar Trem
        criarTrem.addActionListener(e -> {
            JPanel panelCriar = new JPanel();
            frame.setContentPane(panelCriar);
            JTextField id = addTextField(panelCriar, "ID do Trem");

            // Cria e adiciona botoes
            JButton voltar = new JButton("Voltar");
            JButton proximo = new JButton("Proximo");
            panelCriar.add(proximo);
            panelCriar.add(voltar);

            // Refresh
            frame.revalidate();
            frame.repaint();
            voltar.addActionListener(e1 -> {
                returnToMenu();
                App.reset();
            });

            // Adicionar locomotivas
            proximo.addActionListener(l -> {
                int idTrem = Integer.parseInt(id.getText());

                JPanel panelCriar2 = new JPanel();
                frame.setContentPane(panelCriar2);
                JLabel listaLocomotivasLabel = new JLabel("Locomotivas");
                panelCriar2.add(listaLocomotivasLabel);
                JList listaLocomotivas = new JList(App.locomotivasToList());
                panelCriar2.add(listaLocomotivas);
                listaLocomotivasLabel.setLabelFor(listaLocomotivas);
                listaLocomotivas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                JScrollPane listScroller = new JScrollPane(listaLocomotivas);
                panelCriar2.add(listScroller);
                panelCriar2.add(voltar);
                JButton adicionar2 = new JButton("Adicionar");
                panelCriar2.add(adicionar2);
                JButton proximo2 = new JButton("Proximo");
                panelCriar2.add(proximo2);
                panel.setLayout(new BorderLayout());
                Locomotiva d = null;

                // Tenta criar o trem com a id solicitada, caso já exista, retorna para o menu anterior
                try {
                    App.id(idTrem);
                } catch (RuntimeException ex) {
                    frame.setContentPane(panelCriar);
                    frame.revalidate();
                    frame.repaint();
                }

                // Refresh
                frame.revalidate();
                frame.repaint();

                adicionar2.addActionListener(a ->
                {
                    Locomotiva loc = (Locomotiva) listaLocomotivas.getSelectedValue();
                    App.addLocomotiva(loc);
                    listaLocomotivas.clearSelection();
                });

                // Adicionar vagoes
                proximo2.addActionListener(a -> {
                JPanel panelCriar3 = new JPanel();
                frame.setContentPane(panelCriar3);
                JLabel listaVagoesLabel = new JLabel("Vagoes");
                panelCriar3.add(listaVagoesLabel);
                JList listaVagoes = new JList(App.vagoesToList());
                panelCriar3.add(listaVagoes);
                listaVagoesLabel.setLabelFor(listaVagoes);
                listaVagoes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                JScrollPane listScroller2 = new JScrollPane(listaVagoes);
                panelCriar3.add(listScroller2);
                panelCriar3.add(voltar);
                JButton adicionar3 = new JButton("Adicionar");
                panelCriar3.add(adicionar3);
                JButton proximo3 = new JButton("Proximo");
                panelCriar3.add(proximo3);
                panel.setLayout(new BorderLayout());


                frame.revalidate();
                frame.repaint();


                adicionar3.addActionListener(b ->
                {
                    Vagao vag = (Vagao) listaVagoes.getSelectedValue();
                });

                // Finalizar trem
                proximo3.addActionListener(c -> {
                    JPanel panelCriar4 = new JPanel();
                    frame.setContentPane(panelCriar4);
                    JLabel fim = new JLabel("Trem criado com sucesso!");
                    panelCriar4.add(fim);
                    panelCriar4.add(voltar);
                    panel.setLayout(new BorderLayout());

                    frame.revalidate();
                    frame.repaint();

                });

                });

            });

            id.addActionListener(e1 -> {

            });

        });


        // Listar Trens
        listarTrem.addActionListener(e -> {
            JPanel panelLista = new JPanel();
            frame.setContentPane(panelLista);

            JButton voltarB = new JButton("Voltar");
            JList listaLocomotivas = new JList(App.composicoesToList());
            JScrollPane scrollTrem = new JScrollPane();

            scrollTrem.setViewportView(listaLocomotivas);
            scrollTrem.setPreferredSize(new Dimension(280,200));
            scrollTrem.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            scrollTrem.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

            listaLocomotivas.setLayoutOrientation(JList.VERTICAL);
            listaLocomotivas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            panel.setLayout(new BorderLayout());
            panelLista.add(scrollTrem, BorderLayout.CENTER);
            panelLista.add(voltarB, BorderLayout.SOUTH);
            frame.revalidate();
            frame.repaint();

            voltarB.addActionListener(e1 -> {
                returnToMenu();
            });
        });

        // Salvar Trens
        salvarTrem.addActionListener(a -> {
            App.salvar("loc.csv", App.salvar(garagemLocomotivas));
            App.salvar("vag.csv", App.salvar(garagemVagoes));
            App.salvar("comp.csv", App.salvar(patioComposicoes));
        });


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,300);
        frame.getContentPane().add(panel);
        frame.setVisible(true);

    }

    private void returnToMenu(){
        frame.setContentPane(panel);
        frame.revalidate();
        frame.repaint();
    }

    private JTextField addTextField(JPanel panel, String label) {
        JLabel l = new JLabel(label, JLabel.TRAILING);
        panel.add(l);
        JTextField textField = new JTextField(10);
        l.setLabelFor(textField);
        textField.setEditable(true);
        panel.add(textField);
        return textField;
    }
}
