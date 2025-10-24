package br.com.gestaopagamento.Models;

public enum GrauInsalubridade {
    // Adicione o NENHUM(0) para casos onde não se aplica
    NENHUM(0), 
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

    // ESTE É O MÉTODO QUE ESTÁ FALTANDO
    public static GrauInsalubridade fromInt(int grau) {
        switch (grau) {
            case 0: return NENHUM; // Adicionamos o caso 0
            case 1: return BAIXO;
            case 2: return MEDIO;
            case 3: return ALTO;
            default: 
                // Lança um erro se o número for inválido
                throw new IllegalArgumentException("Grau de insalubridade inválido: " + grau);
        }
    }
}