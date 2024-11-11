package com.gss;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class DominoImage {
    private final int lado1;
    private final int lado2;
    private final int width;
    private final int height;

    // Construtor atualizado para aceitar os parâmetros de tamanho
    public DominoImage(int lado1, int lado2, int width, int height) {
        this.lado1 = lado1;
        this.lado2 = lado2;
        this.width = width;
        this.height = height;
    }

    public BufferedImage createDominoImage() {
        // Cria um BufferedImage com a largura e altura passadas
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();

        // Configurações básicas de desenho
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(2, 2, width - 4, height - 4); // Contorno da peça de dominó

        // Desenha a linha divisória no meio da peça (horizontalmente)
        g.drawLine(2, height / 2, width - 2, height / 2); // Linha no meio

        // Desenha os pontos de cada lado do dominó
        drawDots(g, lado1, width / 2, height / 4);
        drawDots(g, lado2, width / 2, 3 * height / 4);

        g.dispose();
        return image;
    }

    private void drawDots(Graphics2D g, int value, int centerX, int centerY) {
        int dotSize = 5; // Tamanho dos pontos
        int halfDot = dotSize / 2;

        switch (value) {
            case 1 -> g.fillOval(centerX - halfDot, centerY - halfDot, dotSize, dotSize); // Centro
            case 2 -> {
                g.fillOval(centerX - 8, centerY - 8, dotSize, dotSize); // Superior esquerdo
                g.fillOval(centerX + 3, centerY + 3, dotSize, dotSize); // Inferior direito
            }
            case 3 -> {
                g.fillOval(centerX - 8, centerY - 8, dotSize, dotSize); // Superior esquerdo
                g.fillOval(centerX - halfDot, centerY - halfDot, dotSize, dotSize); // Centro
                g.fillOval(centerX + 3, centerY + 3, dotSize, dotSize); // Inferior direito
            }
            case 4 -> {
                g.fillOval(centerX - 8, centerY - 8, dotSize, dotSize); // Superior esquerdo
                g.fillOval(centerX + 3, centerY - 8, dotSize, dotSize); // Superior direito
                g.fillOval(centerX - 8, centerY + 3, dotSize, dotSize); // Inferior esquerdo
                g.fillOval(centerX + 3, centerY + 3, dotSize, dotSize); // Inferior direito
            }
            case 5 -> {
                g.fillOval(centerX - halfDot, centerY - halfDot, dotSize, dotSize); // Centro
                g.fillOval(centerX - 8, centerY - 8, dotSize, dotSize); // Superior esquerdo
                g.fillOval(centerX + 3, centerY - 8, dotSize, dotSize); // Superior direito
                g.fillOval(centerX - 8, centerY + 3, dotSize, dotSize); // Inferior esquerdo
                g.fillOval(centerX + 3, centerY + 3, dotSize, dotSize); // Inferior direito
            }
            case 6 -> {
                g.fillOval(centerX - 8, centerY - 8, dotSize, dotSize); // Superior esquerdo
                g.fillOval(centerX + 3, centerY - 8, dotSize, dotSize); // Superior direito
                g.fillOval(centerX - 8, centerY - halfDot, dotSize, dotSize); // Meio esquerdo
                g.fillOval(centerX + 3, centerY - halfDot, dotSize, dotSize); // Meio direito
                g.fillOval(centerX - 8, centerY + 3, dotSize, dotSize); // Inferior esquerdo
                g.fillOval(centerX + 3, centerY + 3, dotSize, dotSize); // Inferior direito
            }
        }
    }

    // Salva a imagem gerada
    public void saveDominoImage(String filename) {
        BufferedImage image = createDominoImage();
        try {
            ImageIO.write(image, "png", new File(filename)); // Salva como PNG
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DominoImage dominoImage = new DominoImage(3, 5, 30, 50);
        dominoImage.saveDominoImage("domino.png");

        // Exibir a imagem
        JFrame frame = new JFrame("Peça de Dominó");
        JLabel label = new JLabel(new ImageIcon(dominoImage.createDominoImage()));
        frame.add(label);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
