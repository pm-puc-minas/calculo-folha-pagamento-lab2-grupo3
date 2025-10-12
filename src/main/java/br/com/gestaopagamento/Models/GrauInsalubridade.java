package br.com.gestaopagamento.Models;

public enum GrauInsalubridade {
    BAIXO(1),
    MEDIO(2),
    ALTO(3);

    private final int valor;

    GrauInsalubridade(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    public static GrauInsalubridade fromInt(int grau) {
        switch (grau) {
            case 1: return BAIXO;
            case 2: return MEDIO;
            case 3: return ALTO;
            default: throw new IllegalArgumentException("Grau de insalubridade inválido: " + grau);
        }
    }
}
