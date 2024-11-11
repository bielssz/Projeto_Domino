package com.gss;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class HistoricoGUI extends JFrame {
    private DatabaseManager dbManager;
    private JTable table;

    public HistoricoGUI() {
        dbManager = new DatabaseManager();
        setTitle("Histórico de Partidas");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        configurarTabela();
    }

    private void configurarTabela() {
        String[] colunas = {"Jogadores", "Vencedor", "Data"};
        DefaultTableModel tableModel = new DefaultTableModel(colunas, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        carregarDados();
    }

    private void carregarDados() {
        List<String[]> historico = dbManager.getHistorico();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);  // Limpa a tabela

        for (String[] partida : historico) {
            model.addRow(partida);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new HistoricoGUI().setVisible(true);
        });
    }
}
