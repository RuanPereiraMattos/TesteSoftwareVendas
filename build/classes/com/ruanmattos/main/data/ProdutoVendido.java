package com.ruanmattos.main.data;

/**
 *
 * @author Ruan Pereira Mattos
 */
public class ProdutoVendido {

    int id, id_venda;
    String nome;
    String descricao;
    float valor_und;
    int qtd;
    float valor_total;

    public ProdutoVendido(String nome, String descricao, float valor_und, int qtd, float valor_total) {
        this.nome = nome;
        this.descricao = descricao;
        this.valor_und = valor_und;
        this.qtd = qtd;
        this.valor_total = valor_total;
    }

    public ProdutoVendido(int id, int id_venda, String nome, String descricao, float valor_und, int qtd, float valor_total) {
        this.id = id;
        this.id_venda = id_venda;
        this.nome = nome;
        this.descricao = descricao;
        this.valor_und = valor_und;
        this.qtd = qtd;
        this.valor_total = valor_total;
    }

    public Object[] toObject() {
        return new Object[]{nome, descricao, valor_und, qtd, valor_total};
    }

    public int getId() {
        return id;
    }

    public int getId_venda() {
        return id_venda;
    }
    
    

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public float getValor_und() {
        return valor_und;
    }

    public int getQtd() {
        return qtd;
    }

    public float getValor_total() {
        return valor_total;
    }

}
