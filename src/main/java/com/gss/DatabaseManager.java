package com.gss;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;

class DatabaseManager {
    private Connection connection;

    public DatabaseManager() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:jogo_dominio.db");
            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS historico (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                     "jogadores TEXT," +
                     "vencedor TEXT," +
                     "data TEXT)";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void salvarPartida(List<String> jogadores, String vencedor) {
        String sql = "INSERT INTO historico (jogadores, vencedor, data) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, String.join(", ", jogadores));
            pstmt.setString(2, vencedor);
            pstmt.setString(3, new java.util.Date().toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String[]> getHistorico() {
        List<String[]> historico = new ArrayList<>();
        String sql = "SELECT jogadores, vencedor, data FROM historico";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String jogadores = rs.getString("jogadores");
                String vencedor = rs.getString("vencedor");
                String data = rs.getString("data");
                historico.add(new String[]{jogadores, vencedor, data});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historico;
    }
}