package TrabalhoTrem;
import javax.swing.*;
import java.awt.*;

public class UI{
    private JFrame frame;
    private JPanel panel;
    public void createAndShow(){
        panel = new JPanel();
        frame = new JFrame("Trem");

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


        criarTrem.addActionListener(e -> {
            JPanel panelCriar = new JPanel();
            frame.setContentPane(panelCriar);
            JButton voltar = new JButton("Voltar");
            panelCriar.add(voltar);
            frame.revalidate();
            frame.repaint();
            voltar.addActionListener(e1 -> {
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
}
