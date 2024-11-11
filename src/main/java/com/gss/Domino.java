package com.gss;

public class Domino {
    private int lado1;
    private int lado2;

    public Domino(int lado1, int lado2) {
        this.lado1 = lado1;
        this.lado2 = lado2;
    }
    
    public void girar() {
            int temp = lado1;
            lado1 = lado2;
            lado2 = temp;
    }


    public int getLado1() {
        return lado1;
    }

    public int getLado2() {
        return lado2;
    }

    @Override
    public String toString() {
        return "[" + lado1 + "|" + lado2 + "]";
    }
}