package TrabalhoTrem;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static TrabalhoTrem.App.*;

public class UI{
    private JFrame frame;
    private JPanel panel;

    public void createAndShow(){
        JFrame.setDefaultLookAndFeelDecorated(true);
        Font defaultFont = new Font("Arial", Font.PLAIN, 20);
        Font bold = new Font("Arial", Font.BOLD, 22);
        UIManager.put("TextField.font", defaultFont);
        UIManager.put("Button.font", defaultFont);
        UIManager.put("Label.font", defaultFont);
        UIManager.put("List.font", defaultFont);

        panel = new JPanel();
        frame = new JFrame("Trem");


        //Menu
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        Panel cardCriar = new Panel();
        cardCriar.setLayout(new CardLayout(50,10));
        JButton criarTrem = new JButton("Criar Trem");
        cardCriar.add(criarTrem);
        cardCriar.setSize(200,100);
        panel.add(cardCriar);

        Panel cardEditar = new Panel();
        cardEditar.setLayout(new CardLayout(50,10));
        JButton editarTrem = new JButton("Editar Trem");
        cardEditar.add(editarTrem);
        cardEditar.setSize(200,100);
        panel.add(cardEditar);

        Panel cardListar = new Panel();
        cardListar.setLayout(new CardLayout(50,10));
        JButton listarTrem = new JButton("Listar Trem");
        cardListar.add(listarTrem);
        cardListar.setSize(200,100);
        panel.add(cardListar);

        Panel cardDeletar = new Panel();
        cardDeletar.setLayout(new CardLayout(50,10));
        JButton deletarTrem = new JButton("Deletar Trem");
        cardDeletar.add(deletarTrem);
        cardDeletar.setSize(200,100);
        panel.add(cardDeletar);

        Panel cardSalvar = new Panel();
        cardSalvar.setLayout(new CardLayout(50,10));
        JButton salvarTrem = new JButton("Salvar");
        cardSalvar.add(salvarTrem);
        cardSalvar.setSize(200,100);
        panel.add(cardSalvar);


        // Listas de Locomotivas e Vagoes
        DefaultListModel<Locomotiva> listModelL = new DefaultListModel<>();
        for (Locomotiva locomotiva : App.locomotivasToList()) {
            listModelL.addElement(locomotiva);
        }
        JList listaLocomotivas = new JList(listModelL);
        DefaultListModel<Locomotiva> oldListModelL = new DefaultListModel<>();

        DefaultListModel<Vagao> listModelV = new DefaultListModel<>();
        for (Vagao vagao : App.vagoesToList()) {
            listModelV.addElement(vagao);
        }
        JList listaVagoes = new JList(listModelV);
        DefaultListModel<Vagao> oldListModelV = new DefaultListModel<>();

        DefaultListModel<Composicao> listModelC = new DefaultListModel<>();
        for (Composicao composicao : App.composicoesToList()) {
            listModelC.addElement(composicao);
        }

        JList listaComposicoes = new JList(listModelC);
        DefaultListModel<Composicao> defaultListModelC = (DefaultListModel<Composicao>) listaComposicoes.getModel();

        //Criar Trem
        criarTrem.addActionListener(e -> {
            JPanel panelCriar = new JPanel();
            frame.setContentPane(panelCriar);
            panelCriar.setLayout(new BoxLayout(panelCriar, BoxLayout.Y_AXIS));

            panelCriar.add(Box.createVerticalGlue());
            JPanel panelId = new JPanel();
            panelId.setLayout(new BoxLayout(panelId, BoxLayout.X_AXIS));
            JPanel panelId1 = new JPanel();
            panelId1.setLayout(new CardLayout(10,10));
            JTextField id = addTextField(panelId, "ID do Trem");
            id.setFont(bold);
            id.setColumns(20);
            id.setMaximumSize(new Dimension(200, 60));
            panelId1.setSize(100,100);
            panelId1.add(id);
            panelId.add(panelId1);
            panelId.setMaximumSize(new Dimension(700,10000));
            panelCriar.add(panelId);
            panelCriar.add(Box.createVerticalGlue());

            // Cria e adiciona botoes
            JPanel panelBotoesCriar = new JPanel();
            JButton voltar = new JButton("Voltar");
            JButton proximo = new JButton("Próximo");
            panelBotoesCriar.add(voltar);
            panelBotoesCriar.add(proximo);
            panelCriar.add(panelBotoesCriar);

            // Refresh
            frame.revalidate();
            frame.repaint();

            voltar.addActionListener(e1 -> {
                App.composicoesToList();
                for (int i = 0; i < oldListModelL.size(); i++) {
                    listModelL.addElement(oldListModelL.getElementAt(i));
                }
                for (int i = 0; i < oldListModelV.size(); i++) {
                    listModelV.addElement(oldListModelV.getElementAt(i));
                }
                oldListModelL.clear();
                oldListModelV.clear();
                returnToMenu();
                App.reset();
            });

            // Adicionar locomotivas
            proximo.addActionListener(l -> {
                int idTrem;

                JPanel panelCriar2 = new JPanel();
                frame.setContentPane(panelCriar2);
                JLabel listaLocomotivasLabel = new JLabel("Locomotivas");
                panelCriar2.add(listaLocomotivasLabel);

                panelCriar2.add(listaLocomotivas);
                listaLocomotivasLabel.setLabelFor(listaLocomotivas);
                listaLocomotivas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                JScrollPane listScroller = new JScrollPane(listaLocomotivas);
                panelCriar2.add(listScroller);
                panelCriar2.add(voltar);
                JButton adicionar2 = new JButton("Adicionar");
                panelCriar2.add(adicionar2);
                JButton proximo2 = new JButton("Próximo");
                panelCriar2.add(proximo2);

                JPanel panelMontagem = new JPanel();
                JTextField montagem = addTextField(panelMontagem, "Montagem");
                montagem.setEditable(false);
                montagem.setColumns(35);
                panelCriar2.add(panelMontagem);
                panel.setLayout(new BorderLayout());

                try {
                    idTrem = Integer.parseInt(id.getText());
                }
                catch (NumberFormatException e1)
                {
                    idTrem = -1;
                    JOptionPane.showMessageDialog(frame, "FORMATO INVÁLIDO!", "Erro!", JOptionPane.ERROR_MESSAGE);
                    panelBotoesCriar.add(voltar);
                    panelBotoesCriar.add(proximo);
                    frame.setContentPane(panelCriar);
                    frame.revalidate();
                    frame.repaint();
                }


                // Tenta criar o trem com a id solicitada, caso jÃ¡ exista, retorna para o menu anterior
                try {
                    App.id(idTrem);
                } catch (IDJaEmUsoException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
                    panelBotoesCriar.add(voltar);
                    panelBotoesCriar.add(proximo);
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
                    DefaultListModel<Locomotiva> defaultListModel = (DefaultListModel<Locomotiva>) listaLocomotivas.getModel();
                    oldListModelL.addElement(defaultListModel.getElementAt(listaLocomotivas.getSelectedIndex()));
                    defaultListModel.removeElementAt(listaLocomotivas.getSelectedIndex());
                    montagem.setText(App.visualComposition(loc));
                    listaLocomotivas.clearSelection();
                    listaLocomotivas.repaint();
                    frame.revalidate();
                    frame.repaint();
                });



                // Adicionar vagoes
                proximo2.addActionListener(a -> {
                    if (App.check(1))
                    {
                        App.calc();
                        JPanel panelCriar3 = new JPanel();
                        frame.setContentPane(panelCriar3);
                        panelCriar3.setLayout(new BoxLayout(panelCriar3, BoxLayout.Y_AXIS));

                        JPanel panelListaVagoes = new JPanel();
                        JLabel listaVagoesLabel = new JLabel("Vagões");
                        listaVagoesLabel.setLabelFor(listaVagoes);
                        panelListaVagoes.add(listaVagoesLabel);
                        panelListaVagoes.add(listaVagoes);
                        listaVagoes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                        listaVagoes.setMaximumSize(new Dimension(300,100));
                        JScrollPane listScroller2 = new JScrollPane(listaVagoes);
                        panelListaVagoes.add(listScroller2);
                        panelCriar3.add(panelListaVagoes);

                        JPanel panelBotoesAddVagoes = new JPanel();
                        panelBotoesAddVagoes.add(voltar);
                        JButton adicionar3 = new JButton("Adicionar");
                        panelBotoesAddVagoes.add(adicionar3);
                        JButton proximo3 = new JButton("Próximo");
                        panelBotoesAddVagoes.add(proximo3);
                        panelCriar3.add(panelBotoesAddVagoes);

                        panelCriar3.add(panelMontagem);
                        panel.setLayout(new BorderLayout());
                        frame.revalidate();
                        frame.repaint();



                        adicionar3.addActionListener(b ->
                        {
                            if (App.check(((Vagao) listaVagoes.getSelectedValue()).getCapacidadeCarga()))
                            {
                                Vagao vag = (Vagao) listaVagoes.getSelectedValue();
                                App.addVagao(vag);
                                DefaultListModel<Vagao> defaultListModelV = (DefaultListModel<Vagao>) listaVagoes.getModel();
                                oldListModelV.addElement(defaultListModelV.getElementAt(listaVagoes.getSelectedIndex()));
                                defaultListModelV.removeElementAt(listaVagoes.getSelectedIndex());
                                montagem.setText(App.visualComposition(vag));
                                listaVagoes.clearSelection();
                                listaVagoes.repaint();
                            }
                            else
                                JOptionPane.showMessageDialog(frame, "CARGA MÁXIMA DA LOCOMOTIVA EXCEDIDA!", "Erro!", JOptionPane.ERROR_MESSAGE);
                        });

                        // Finalizar trem
                        proximo3.addActionListener(c -> {
                            if (App.check(2))
                            {
                                listModelC.addElement(App.getTrem());
                                App.addTrem();

                                JPanel panelCriar4 = new JPanel();
                                frame.setContentPane(panelCriar4);
                                JLabel fim = new JLabel("Trem criado com sucesso!");
                                panelCriar4.add(fim);
                                oldListModelV.clear();
                                oldListModelL.clear();
                                panelCriar4.add(voltar);
                                panel.setLayout(new BorderLayout());
                                listaComposicoes.revalidate();
                                listaComposicoes.repaint();
                                frame.revalidate();
                                frame.repaint();
                            }
                            else
                                JOptionPane.showMessageDialog(frame, "SELECIONE UM VAGÃO!", "Erro!", JOptionPane.ERROR_MESSAGE);
                        });
                    }
                    else
                        JOptionPane.showMessageDialog(frame, "SELECIONE UMA LOCOMOTIVA!", "Erro!", JOptionPane.ERROR_MESSAGE);
                });

            });
        });

        // Editar Trem
        editarTrem.addActionListener(e -> {
            JPanel panelEditar = new JPanel();
            JButton selecionar = new JButton("Selecionar");
            JButton voltar = new JButton("Voltar");
            //JList listaComposicoes = new JList(App.composicoesToList());

            JPanel panelListaEditar = new JPanel();
            JLabel listaComposicaoLabel = new JLabel("Composições");
            listaComposicaoLabel.setLabelFor(listaComposicoes);
            panelListaEditar.add(listaComposicaoLabel);
            panelListaEditar.add(listaComposicoes);
            listaComposicoes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            listaVagoes.setMaximumSize(new Dimension(300,100));
            JScrollPane listScroller1 = new JScrollPane(listaComposicoes);
            panelListaEditar.add(listScroller1);
            panelEditar.add(panelListaEditar);

            panelEditar.add(voltar);
            panelEditar.add(selecionar);

            frame.setContentPane(panelEditar);
            frame.revalidate();
            frame.repaint();

            voltar.addActionListener(e1 -> {
                returnToMenu();
            });

            selecionar.addActionListener(s -> {
                Composicao editar = (Composicao) listaComposicoes.getSelectedValue();
                if(editar == null)
                {
                    JOptionPane.showMessageDialog(frame, "SELECIONE UM TREM PARA EDITAR!", "Editar Trem!", JOptionPane.ERROR_MESSAGE);

                }
                else
                {
                    App.loadComposition(editar);
                    JPanel panelEdicao = new JPanel();
                    panelEdicao.setLayout(new BoxLayout(panelEdicao, BoxLayout.Y_AXIS));

                    JPanel cardInserirLocomotiva = new JPanel();
                    cardInserirLocomotiva.setLayout(new CardLayout(50, 10));
                    JButton inserirLocomotiva = new JButton("Inserir Locomotiva");
                    cardInserirLocomotiva.add(inserirLocomotiva);
                    cardInserirLocomotiva.setSize(200, 100);
                    panelEdicao.add(cardInserirLocomotiva);

                    JPanel cardInserirVagao = new JPanel();
                    cardInserirVagao.setLayout(new CardLayout(50, 10));
                    JButton inserirVagao = new JButton("Inserir Vagão");
                    cardInserirVagao.add(inserirVagao);
                    cardInserirVagao.setSize(200, 100);
                    panelEdicao.add(cardInserirVagao);

                    JPanel cardRemoverUltimo = new JPanel();
                    cardRemoverUltimo.setLayout(new CardLayout(50, 10));
                    JButton removerUltimo = new JButton("Remover Último Elemento");
                    cardRemoverUltimo.add(removerUltimo);
                    cardRemoverUltimo.setSize(200, 100);
                    panelEdicao.add(cardRemoverUltimo);

                    JPanel cardListarLocomotivas = new JPanel();
                    cardListarLocomotivas.setLayout(new CardLayout(50, 10));
                    JButton listarLocomotivas = new JButton("Locomotivas Livres");
                    cardListarLocomotivas.add(listarLocomotivas);
                    cardListarLocomotivas.setSize(200, 100);
                    panelEdicao.add(cardListarLocomotivas);

                    JPanel cardListarVagoes = new JPanel();
                    cardListarVagoes.setLayout(new CardLayout(50, 10));
                    JButton listarVagoes = new JButton("Vagões Livres");
                    cardListarVagoes.add(listarVagoes);
                    cardListarVagoes.setSize(200, 100);
                    panelEdicao.add(cardListarVagoes);

                    JPanel cardVoltar = new JPanel();
                    cardVoltar.setLayout(new CardLayout(50, 10));
                    cardVoltar.add(voltar);
                    cardVoltar.setSize(200, 100);
                    panelEdicao.add(cardVoltar);

                    frame.setContentPane(panelEdicao);
                    frame.revalidate();
                    frame.repaint();

                    inserirLocomotiva.addActionListener(e1 -> {
                        if (App.check(3)) {
                            JPanel panelLoc = new JPanel();
                            frame.setContentPane(panelLoc);
                            JLabel listaLocomotivasLabel = new JLabel("Locomotivas");
                            panelLoc.add(listaLocomotivasLabel);
                            panelLoc.add(listaLocomotivas);
                            listaLocomotivasLabel.setLabelFor(listaLocomotivas);
                            listaLocomotivas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                            JScrollPane listScroller = new JScrollPane(listaLocomotivas);
                            panelLoc.add(listScroller);
                            JButton adicionar2 = new JButton("Adicionar");
                            panelLoc.add(voltar);
                            panelLoc.add(adicionar2);
                            panel.setLayout(new BorderLayout());
                            frame.revalidate();
                            frame.repaint();

                            adicionar2.addActionListener(a -> {
                                Locomotiva loc = (Locomotiva) listaLocomotivas.getSelectedValue();
                                App.addLocomotiva(loc);
                                DefaultListModel<Locomotiva> defaultListModel = (DefaultListModel<Locomotiva>) listaLocomotivas.getModel();
                                oldListModelL.addElement(defaultListModel.getElementAt(listaLocomotivas.getSelectedIndex()));
                                defaultListModel.removeElementAt(listaLocomotivas.getSelectedIndex());
                                listaLocomotivas.clearSelection();
                                listaLocomotivas.repaint();
                            });
                        }
                        else
                            JOptionPane.showMessageDialog(frame, "NÃO É POSSÍVEL INSERIR LOCOMOTIVA!", "Erro!", JOptionPane.ERROR_MESSAGE);
                    });

                    inserirVagao.addActionListener(e1 -> {
                        if (App.check(1)) {
                            App.calc();
                            JPanel panelVag = new JPanel();
                            frame.setContentPane(panelVag);
                            panelVag.setLayout(new BoxLayout(panelVag, BoxLayout.Y_AXIS));

                            JPanel panelListaVag = new JPanel();
                            JLabel listaVagoesLabel = new JLabel("Vagões");
                            panelListaVag.add(listaVagoesLabel);
                            panelListaVag.add(listaVagoes);
                            listaVagoesLabel.setLabelFor(listaVagoes);
                            listaVagoes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                            JScrollPane listScroller2 = new JScrollPane(listaVagoes);
                            panelListaVag.add(listScroller2);
                            panelVag.add(panelListaVag);

                            JPanel panelBotoesAddVagoes = new JPanel();
                            panelBotoesAddVagoes.add(voltar);
                            JButton adicionar3 = new JButton("Adicionar");
                            panelBotoesAddVagoes.add(adicionar3);
                            panelVag.add(panelBotoesAddVagoes);

                            panel.setLayout(new BorderLayout());
                            frame.revalidate();
                            frame.repaint();

                            adicionar3.addActionListener(a -> {
                                if (App.check(editar, (Vagao) listaVagoes.getSelectedValue())) {
                                    Vagao vag = (Vagao) listaVagoes.getSelectedValue();
                                    App.addVagao(vag);
                                    DefaultListModel<Vagao> defaultListModelV = (DefaultListModel<Vagao>) listaVagoes.getModel();
                                    oldListModelV.addElement(defaultListModelV.getElementAt(listaVagoes.getSelectedIndex()));
                                    defaultListModelV.removeElementAt(listaVagoes.getSelectedIndex());
                                    listaVagoes.clearSelection();
                                    listaVagoes.repaint();
                                }
                                else
                                    JOptionPane.showMessageDialog(frame, "CARGA MÁXIMA DA LOCOMOTIVA EXCEDIDA!", "Erro!", JOptionPane.ERROR_MESSAGE);
                            });
                        }
                        else
                            JOptionPane.showMessageDialog(frame, "CARGA MÁXIMA DA LOCOMOTIVA ARROZ!", "Erro!", JOptionPane.ERROR_MESSAGE);
                    });

                    removerUltimo.addActionListener(e1 -> {
                        if (editar.getQtdadeCarros() != 1)
                        {
                            JOptionPane.showMessageDialog(frame, "Removido Com Sucesso!", "Deu Certo!", JOptionPane.INFORMATION_MESSAGE);
                            if (editar.getLast() instanceof Locomotiva && editar.getQtdadeLocomotivas() >= 2) {
                                listModelL.addElement((Locomotiva) editar.getLast());
                            } else if (editar.getLast() instanceof Vagao) {
                                listModelV.addElement((Vagao) editar.getLast());
                            }
                            App.removeLast(editar);
                        } else
                            JOptionPane.showMessageDialog(frame, "NÃO É POSSÍVEL REMOVER!", "Último Elemento!", JOptionPane.ERROR_MESSAGE);
                    });

                    listarLocomotivas.addActionListener(e1 -> {
                        JPanel panelLista = new JPanel();
                        frame.setContentPane(panelLista);
                        panelLista.setLayout(new BoxLayout(panelLista, BoxLayout.Y_AXIS));

                        JButton voltarB = new JButton("Voltar");

                        JPanel panelListaLoc = new JPanel();
                        JLabel listaLocLabel = new JLabel("Locomotivas");
                        listaLocLabel.setLabelFor(listaLocomotivas);
                        panelListaLoc.add(listaLocLabel);
                        panelListaLoc.add(listaLocomotivas);
                        listaLocomotivas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                        listaLocomotivas.setMaximumSize(new Dimension(300, 100));
                        JScrollPane listScroller = new JScrollPane(listaLocomotivas);
                        panelListaLoc.add(listScroller);
                        listaLocomotivas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                        panelLista.add(panelListaLoc);

                        panelLista.add(voltarB);
                        panelLista.add(Box.createVerticalGlue());
                        frame.revalidate();
                        frame.repaint();

                        voltarB.addActionListener(e2 -> {
                            returnToMenu();
                        });
                    });

                    listarVagoes.addActionListener(e1 -> {
                        JPanel panelLista = new JPanel();
                        frame.setContentPane(panelLista);
                        panelLista.setLayout(new BoxLayout(panelLista, BoxLayout.Y_AXIS));

                        JButton voltarB = new JButton("Voltar");

                        JPanel panelListaVag = new JPanel();
                        JLabel listaVagLabel = new JLabel("Vagões");
                        listaVagLabel.setLabelFor(listaVagoes);
                        panelListaVag.add(listaVagLabel);
                        panelListaVag.add(listaVagoes);
                        listaVagoes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                        listaVagoes.setMaximumSize(new Dimension(300, 100));
                        JScrollPane listScroller = new JScrollPane(listaVagoes);
                        panelListaVag.add(listScroller);
                        listaVagoes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                        panelLista.add(panelListaVag);

                        panelLista.add(voltarB);
                        panelLista.add(Box.createVerticalGlue());
                        frame.revalidate();
                        frame.repaint();

                        voltarB.addActionListener(e2 -> {
                            returnToMenu();
                        });
                    });

                    voltar.addActionListener(e1 -> {
                        returnToMenu();
                    });
                }
            });
        });

        // Listar Trens
        listarTrem.addActionListener(e -> {
            JPanel panelLista = new JPanel();
            frame.setContentPane(panelLista);

            JButton voltarB = new JButton("Voltar");
            //JList listaComposicoes = new JList(App.composicoesToList());
            JScrollPane scrollTrem = new JScrollPane();

            JPanel panelListarComposicoes = new JPanel();
            JLabel listaComposicaoLabel = new JLabel("Composições");
            listaComposicaoLabel.setLabelFor(listaComposicoes);
            panelListarComposicoes.add(listaComposicaoLabel);
            panelListarComposicoes.add(listaComposicoes);
            listaComposicoes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            listaVagoes.setMaximumSize(new Dimension(300,100));
            JScrollPane listScroller = new JScrollPane(listaComposicoes);
            panelListarComposicoes.add(listScroller);
            listaComposicoes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            panelLista.add(panelListarComposicoes);

            panelLista.add(voltarB);
            frame.revalidate();
            frame.repaint();

            voltarB.addActionListener(e1 -> {

                returnToMenu();
            });
        });

        deletarTrem.addActionListener(b -> {
            JPanel panelDeletar = new JPanel();
            JButton selecionar = new JButton("Excluir");
            JButton voltar = new JButton("Voltar");
            //JList listaComposicoes = new JList(App.composicoesToList());


            JPanel panelListarDeletar = new JPanel();
            JLabel listaComposicaoLabel = new JLabel("Composições");
            listaComposicaoLabel.setLabelFor(listaComposicoes);
            panelListarDeletar.add(listaComposicaoLabel);
            panelListarDeletar.add(listaComposicoes);
            listaComposicoes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            listaVagoes.setMaximumSize(new Dimension(300,100));
            JScrollPane listScroller = new JScrollPane(listaComposicoes);
            panelListarDeletar.add(listScroller);
            listaComposicoes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            listaComposicoes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            panelDeletar.add(panelListarDeletar);

            panelDeletar.add(voltar);
            panelDeletar.add(selecionar);

            frame.setContentPane(panelDeletar);
            frame.revalidate();
            frame.repaint();

            voltar.addActionListener(e1 -> {
                returnToMenu();
            });

            selecionar.addActionListener(s -> {
                Composicao deletar = (Composicao) listaComposicoes.getSelectedValue();
                if(deletar == null)
                {
                    JOptionPane.showMessageDialog(frame, "SELECIONE UM TREM PARA EXCLUIR!", "Erro!", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    for (int i = 0; i < deletar.getQtdadeCarros(); i++) {
                        if (deletar.getCarro(i) instanceof Locomotiva) {
                            listModelL.addElement((Locomotiva) deletar.getCarro(i));
                        } else {
                            listModelV.addElement((Vagao) deletar.getCarro(i));
                        }
                    }
                    defaultListModelC.removeElementAt(listaComposicoes.getSelectedIndex());
                    App.deleteComposition(deletar);
                }
            });
        });

        // Salvar Trens
        salvarTrem.addActionListener(a -> {
            App.salvar("loc.csv", App.salvar(garagemLocomotivas));
            App.salvar("vag.csv", App.salvar(garagemVagoes));
            App.salvar("comp.csv", App.salvar(patioComposicoes));
            JOptionPane.showMessageDialog(frame, "Composições Salvas com Sucesso", "Deu Certo!", JOptionPane.INFORMATION_MESSAGE);
        });


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(750,350);
        frame.getContentPane().add(panel);
        frame.setResizable(false);
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