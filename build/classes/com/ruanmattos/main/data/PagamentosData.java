package com.ruanmattos.main.data;

/**
 *
 * @author Ruan Pereira Mattos
 */
public class PagamentosData {

    int tipo;
    int vezes;
    float valor;
    float troco;

    /**
     *
     * @param tipo significa: 0 - Dinheiro, 1 - Débito e 2 - Crédito
     * @param vezes quando pagamento for 2 - Crédito insira a quantidade de vezes aqui
     * @param valor valor que o cliente está pagando
     * @param troco caso o penúltimo método de pagamento ainda faltar valor para terminar o pagamento e o último método de pagamento for dinheiro
     */
    public PagamentosData(int tipo, int vezes, float valor, float troco) {
        this.tipo = tipo;
        this.vezes = vezes;
        this.valor = valor;
        this.troco = troco;
    }

    public int getTipo() {
        return tipo;
    }

    public int getVezes() {
        return vezes;
    }

    public float getValor() {
        return valor;
    }

    public float getTroco() {
        return troco;
    }

}
