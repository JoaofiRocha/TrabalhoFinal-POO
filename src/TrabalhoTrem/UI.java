package TrabalhoTrem;
import javax.swing.*;
import java.awt.*;

import static TrabalhoTrem.App.retornaInfo;

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
            JTextField id = addTextField(panelCriar, "ID");
            JButton voltar = new JButton("Voltar");
            panelCriar.add(voltar);
            frame.revalidate();
            frame.repaint();
            voltar.addActionListener(e1 -> {
                returnToMenu();
            });

            id.addActionListener(e1 -> {
                JList listaLocomotivas = new JList(retornaInfo());
                panelCriar.add(listaLocomotivas);
                listaLocomotivas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

                JScrollPane listScroller = new JScrollPane(listaLocomotivas);

                panelCriar.setLayout(new BorderLayout());

                panelCriar.add(listScroller);
                frame.revalidate();
                frame.repaint();
            });

        });


        // Listar Trens
        listarTrem.addActionListener(e -> {
            JPanel panelLista = new JPanel();
            frame.setContentPane(panelLista);

            JButton voltarB = new JButton("Voltar");
            JList listaLocomotivas = new JList(new String[] {"Trem1", "Trem2" ,"Trem3", "Trem4" ,"Trem5", "Trem6", "Trem7" ,"Trem8", "Trem9" ,"Trem10","Trem11" ,"Trem12", "Trem13 - texto longo muito longo enorme gigantesco gigante grande grandioso texto longo", });
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
