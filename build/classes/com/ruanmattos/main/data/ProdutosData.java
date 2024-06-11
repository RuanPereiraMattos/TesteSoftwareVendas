package com.ruanmattos.main.data;

/**
 *
 * @author Ruan Pereira Mattos
 */
public class ProdutosData {

    int id;
    String nome;
    String descricao;
    int quantidade;
    float valor;

    public ProdutosData(int id, String nome, int quantidade, float valor) {
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    public ProdutosData(int id, String nome, String descricao, float valor) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
    }

    public Object[] toObject() {
        Object[] obj = new Object[]{nome, descricao, valor};
        return obj;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public float getValor() {
        return valor;
    }

}
