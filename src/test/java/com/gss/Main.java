package com.gss;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        DatabaseManager dbManager = new DatabaseManager();

        // Teste: salvar uma partida no banco de dados
        dbManager.salvarPartida(Arrays.asList("Jogador1", "Jogador2"), "Jogador1");

        // Teste: buscar o histórico de partidas
        System.out.println("Histórico de partidas:");
        for (String[] partida : dbManager.getHistorico()) {
            System.out.println("Jogadores: " + partida[0] + ", Vencedor: " + partida[1] + ", Data: " + partida[2]);
        }
    }
}
