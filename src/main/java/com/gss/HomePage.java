package com.gss;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame {

    public HomePage() {
        // Configurações básicas do frame
        setTitle("Domino Game");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Centraliza a janela na tela

        // Criação do painel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());  // Usa GridBagLayout para centralizar os botões
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Margens entre os componentes

        // Botão para iniciar o jogo de dominó
        JButton startGameButton = new JButton("Iniciar Jogo");
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Inicia o método main de JogoDominoGUI
                JogoDominoGUI.main(new String[0]);
                dispose();  // Fecha a janela principal (opcional)
            }
        });
        

        // Botão para exibir o histórico de partidas
        JButton historicoButton = new JButton("Exibir Histórico");
        historicoButton.addActionListener(e -> {
            HistoricoGUI historicoGUI = new HistoricoGUI();
            historicoGUI.setVisible(true);
        });
        

        // Adiciona os botões ao painel
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(startGameButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(historicoButton, gbc);

        // Adiciona o painel ao frame
        add(panel);

        // Torna o frame visível
        setVisible(true);
    }

    public static void main(String[] args) {
        // Inicializa a página principal (home)
        new HomePage();
    }
}
