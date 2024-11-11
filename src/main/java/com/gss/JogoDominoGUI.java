package com.gss;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;
import java.awt.image.BufferedImage;

public class JogoDominoGUI extends JFrame {
    private JogoDomino jogo;
    private JPanel mesaPanel;
    private JPanel maoPanel;
    private JButton pegarButton;
    private List<String> nomesJogadores;
    private DatabaseManager dbManager;

    public JogoDominoGUI(List<String> nomesJogadores) {
        this.nomesJogadores = nomesJogadores;
        jogo = new JogoDomino(nomesJogadores.size());
        dbManager = new DatabaseManager();
        configurarInterface();
        atualizarInterface();
    }

    private void configurarInterface() {
        setTitle("Jogo de Dominó - Vez de: " + nomesJogadores.get(jogo.jogadorAtual));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(true); // Permitir redimensionamento
    
        // Ajustar a mesa para preencher uma parte maior da tela
        mesaPanel = new MesaPanel();
        mesaPanel.setBounds(20, 20, getWidth() - 40, getHeight() - 160); // Ajustar altura da mesa
        mesaPanel.setBackground(Color.LIGHT_GRAY);
        add(mesaPanel);
    
        // Painel da mão do jogador
        maoPanel = new JPanel();
        maoPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        maoPanel.setPreferredSize(new Dimension(getWidth() - 40, 80));  // Ajustar largura da mão
        maoPanel.setBorder(BorderFactory.createTitledBorder("Mão do Jogador"));
        maoPanel.setBackground(Color.WHITE);
    
        // Botão para pegar peça
        pegarButton = new JButton("P");
        pegarButton.setFont(new Font("Arial", Font.BOLD, 12));
        pegarButton.setPreferredSize(new Dimension(50, 50));
        pegarButton.addActionListener(e -> {
            if (jogo.temPecas()) {
                Domino peca = jogo.pegarPeca();
                jogo.maos.get(jogo.jogadorAtual).add(peca);
                JOptionPane.showMessageDialog(null, "Você pegou: " + peca);
            } else {
                JOptionPane.showMessageDialog(null, "Não há mais peças para pegar.");
            }
            jogo.proximoJogador();
            atualizarInterface();
        });
    
        // Painel inferior com a mão do jogador e o botão "P"
        JPanel inferiorPanel = new JPanel();
        inferiorPanel.setLayout(new BorderLayout());
        inferiorPanel.setBounds(20, getHeight() - 130, getWidth() - 40, 80);  // Ajustar a posição do painel inferior
        inferiorPanel.add(maoPanel, BorderLayout.CENTER);
        inferiorPanel.add(pegarButton, BorderLayout.EAST);
        add(inferiorPanel);
    
        // Listener para redimensionamento
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Ajusta o tamanho da mesa com o redimensionamento da janela
                mesaPanel.setBounds(20, 20, getWidth() - 40, getHeight() - 160);
    
                // Ajusta a posição do painel inferior
                inferiorPanel.setBounds(20, getHeight() - 130, getWidth() - 40, 80);
            }
        });
    }
    
    private class MesaPanel extends JPanel {
        private final int peçaWidth = 30;   // Largura fixa para as peças
        private final int peçaHeight = 50;  // Altura fixa para as peças
        private final int espaçamentoHorizontal = 10;  // Espaçamento extra para peças horizontais
        private final int espaçamentoVertical = 10;    // Espaçamento extra para peças verticais
        private int x, y;  // Posição da próxima peça
        private int limiteInferior;  // Limite inferior da mesa
        private boolean desenharHorizontal;  // Define se a peça deve ser desenhada horizontalmente
    
        public MesaPanel() {
            // Inicializa a posição para a primeira peça (centralizada no topo)
            this.x = (getWidth() - peçaWidth) / 2;
            this.y = 20;  // Começa no topo da tela
            this.limiteInferior = getHeight() - 40;  // Limite inferior da mesa
            this.desenharHorizontal = false;  // Começa com a primeira peça na orientação vertical
        }
    
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
    
            // Atualiza o limite inferior caso a janela seja redimensionada
            limiteInferior = getHeight() - 40;
    
            for (int i = 0; i < jogo.getMesa().size(); i++) {
                Domino peca = jogo.getMesa().get(i);
                DominoImage dominoImage = new DominoImage(peca.getLado1(), peca.getLado2(), peçaWidth, peçaHeight);
                BufferedImage dominoImg = dominoImage.createDominoImage();
    
                // Atualiza posição x e y para iniciar centralizado na primeira peça
                if (i == 0) {
                    x = (getWidth() - peçaWidth) / 2;
                    y = 20;
                }
    
                // Desenha a peça na orientação atual (horizontal ou vertical)
                Graphics2D g2d = (Graphics2D) g;
                if (desenharHorizontal) {
                    g2d.rotate(Math.toRadians(90), x + peçaWidth / 2, y + peçaHeight / 2);
                }
                g2d.drawImage(dominoImg, x, y, this);
    
                // Se a peça foi girada, desfaz a rotação para os próximos desenhos
                if (desenharHorizontal) {
                    g2d.rotate(Math.toRadians(-90), x + peçaWidth / 2, y + peçaHeight / 2);
                }
    
                // Atualiza a posição para a próxima peça
                if (desenharHorizontal) {
                    x += peçaHeight + espaçamentoHorizontal;  // Avança horizontalmente
                    if (x + peçaHeight > getWidth() - 20) {    // Se atingir o limite direito
                        desenharHorizontal = false;
                        y += peçaWidth + espaçamentoVertical;  // Começa uma nova linha vertical
                        x = (getWidth() - peçaWidth) / 2;  // Reinicia x centralizado
                    }
                } else {
                    y += peçaHeight + espaçamentoVertical;  // Avança verticalmente
                    if (y + peçaHeight > limiteInferior) {   // Se atingir o limite inferior
                        desenharHorizontal = true;
                        x += peçaWidth + espaçamentoHorizontal;  // Começa uma nova linha horizontal
                        y = limiteInferior - peçaHeight;  // Ajusta y para o limite inferior
                    }
                }
            }
        }
    }
    
    
   
    private void atualizarInterface() {
        setTitle("Jogo de Dominó - Vez de: " + nomesJogadores.get(jogo.jogadorAtual));

        // Atualizar mesa
        mesaPanel.repaint();

        // Atualizar mão do jogador
        maoPanel.removeAll();
        for (Domino peca : jogo.getMaoAtual()) {
            // Novo construtor com tamanho definido
            DominoImage dominoImage = new DominoImage(peca.getLado1(), peca.getLado2(), 30, 50);  // Passe o tamanho aqui
            JLabel dominoLabel = new JLabel(new ImageIcon(dominoImage.createDominoImage()));
            dominoLabel.setPreferredSize(new Dimension(30, 50));
            dominoLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
            dominoLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    String[] opcoes = {"Esquerda", "Direita"};
                    int escolha = JOptionPane.showOptionDialog(null,
                            "Deseja jogar a peça à esquerda ou à direita?",
                            "Escolha o lado",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            opcoes,
                            opcoes[0]);
        
                    // Se o jogador escolher jogar à esquerda ou à direita
                    if (escolha == 0 || escolha == 1) {
                        String lado = escolha == 0 ? "esquerda" : "direita";
                        if (jogo.jogarPeca(peca, lado)) {
                            JOptionPane.showMessageDialog(null, "Você jogou à " + lado + ": " + peca);
                            if (jogo.jogadorVenceu()) {
                                String vencedor = nomesJogadores.get(jogo.jogadorAtual);
                                JOptionPane.showMessageDialog(null, "Jogador " + vencedor + " venceu!");
                                dbManager.salvarPartida(nomesJogadores, vencedor);
                                
                                // Voltar para a página inicial ao invés de fechar o aplicativo
                                new HomePage().setVisible(true);
                                dispose();  // Fecha a janela atual de jogo
                            }
                            
                            jogo.proximoJogador();
                            atualizarInterface();
                        } else {
                            JOptionPane.showMessageDialog(null, "Não foi possível jogar a peça à " + lado + ".");
                        }
                    }
                }
            });
            maoPanel.add(dominoLabel);
        }

        maoPanel.revalidate();
        maoPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            int numJogadores;
            List<String> nomesJogadores = new ArrayList<>();

            while (true) {
                try {
                    numJogadores = Integer.parseInt(JOptionPane.showInputDialog("Digite o número de jogadores (2 a 4):"));
                    if (numJogadores >= 2 && numJogadores <= 4) break;
                    else JOptionPane.showMessageDialog(null, "Por favor, insira um número válido de jogadores.");
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Entrada inválida. Por favor, tente novamente.");
                }
            }

            for (int i = 0; i < numJogadores; i++) {
                String nome;
                do {
                    nome = JOptionPane.showInputDialog("Digite o nome do Jogador " + (i + 1) + ":");
                    if (nome == null || nome.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Por favor, insira um nome válido.");
                    }
                } while (nome == null || nome.trim().isEmpty());
                nomesJogadores.add(nome.trim());
            }

            new JogoDominoGUI(nomesJogadores).setVisible(true);
        });
    }
}
