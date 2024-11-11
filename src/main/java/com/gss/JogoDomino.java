package com.gss;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JogoDomino {
    List<Domino> pecas;
    List<Domino> mesa;
    List<List<Domino>> maos;
    int numJogadores;
    int jogadorAtual;

    private int jogadorVencedor = -1; // Inicializa com -1 (sem vencedor)

    public JogoDomino(int numJogadores) {
        this.numJogadores = numJogadores;
        jogadorAtual = 0;
        pecas = new ArrayList<>();
        mesa = new ArrayList<>();
        maos = new ArrayList<>();
        criarPecas();
        Collections.shuffle(pecas);
        distribuirPecas();
    }

    private void criarPecas() {
        for (int i = 0; i <= 6; i++) {
            for (int j = i; j <= 6; j++) {
                pecas.add(new Domino(i, j));
            }
        }
    }

    public List<Domino> getMesa() {
        return new ArrayList<>(mesa); // Retorna uma cópia da lista para evitar modificações externas
    }
    

    private void distribuirPecas() {
        for (int i = 0; i < numJogadores; i++) {
            maos.add(new ArrayList<>());
            for (int j = 0; j < 7; j++) {
                if (!pecas.isEmpty()) {
                    maos.get(i).add(pecas.remove(pecas.size() - 1));
                }
            }
        }
    }

    public List<Domino> getMaoAtual() {
        return maos.get(jogadorAtual);
    }

    public boolean jogarPeca(Domino peca, String lado) {
        // Se a mesa estiver vazia, simplesmente adicione a peça
        if (mesa.isEmpty()) {
            mesa.add(peca);
            maos.get(jogadorAtual).remove(peca);
            return true;
        }
    
        // Obtenha os valores das pontas da mesa
        int inicioMesa = mesa.get(0).getLado1();
        int fimMesa = mesa.get(mesa.size() - 1).getLado2();
    
        // Verifique se a peça encaixa no lado escolhido
        if (lado.equals("esquerda")) {
            // Verifique se a peça encaixa no início da mesa
            if (peca.getLado2() == inicioMesa) {
                mesa.add(0, peca); // Adiciona a peça no início
                maos.get(jogadorAtual).remove(peca);
                return true;
            } else if (peca.getLado1() == inicioMesa) {
                peca.girar(); // Gira a peça para encaixar
                mesa.add(0, peca);
                maos.get(jogadorAtual).remove(peca);
                return true;
            }
        } else if (lado.equals("direita")) {
            // Verifique se a peça encaixa no final da mesa
            if (peca.getLado1() == fimMesa) {
                mesa.add(peca); // Adiciona a peça no final
                maos.get(jogadorAtual).remove(peca);
                return true;
            } else if (peca.getLado2() == fimMesa) {
                peca.girar(); // Gira a peça para encaixar
                mesa.add(peca);
                maos.get(jogadorAtual).remove(peca);
                return true;
            }
        }
    
        // Se a peça não encaixa em nenhuma ponta, a jogada é inválida
        return false;
    }

    public boolean temPecas() {
        return !pecas.isEmpty();
    }

    public Domino pegarPeca() {
        return pecas.remove(pecas.size() - 1);
    }

    public boolean jogadorVenceu() {
        if (maos.get(jogadorAtual).isEmpty()) {
            jogadorVencedor = jogadorAtual; // Define o jogador vencedor
            return true;
        }
        return false;
    }
    
    public int getJogadorVencedor() {
        return jogadorVencedor;
    }

    public void proximoJogador() {
        jogadorAtual = (jogadorAtual + 1) % numJogadores;
    }

    
}