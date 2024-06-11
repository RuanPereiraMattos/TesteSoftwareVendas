package com.ruanmattos.main.data;

/**
 *
 * @author Ruan Pereira Mattos
 */
public class VendaProdutoData {

    int id;
    int id_produto;
    public String nome;
    float valor_und;
    int qtd;
    public float valor_total;

    /**
     * Id do produto que está sendo vendido
     * Nome do produto que está sendo vendido
     * Quantidade do produto que está sendo vendido
     * Valor Unitário do produto que está sendo vendido
     * Valor Total do produto que está sendo vendido
     * @param id_produto Id do produto que está sendo vendido
     * @param nome Nome do produto que está sendo vendido
     * @param qtd Quantidade do produto que está sendo vendido
     * @param valor_und Valor Unitário do produto que está sendo vendido
     * @param valor_total Valor Total do produto que está sendo vendido
     */
    public VendaProdutoData(int id_produto, String nome, int qtd, float valor_und, float valor_total) {
        this.id_produto = id_produto;
        this.nome = nome;
        this.qtd = qtd;
        this.valor_und = valor_und;
        this.valor_total = valor_total;
    }

    /*public VendaProdutoData(int id, String nome, float valor_und, int qtd, float valor_total) {
    this.id = id;
    this.nome = nome;
    this.valor_und = valor_und;
    this.qtd = qtd;
    this.valor_total = valor_total;
    }*/
    /**
     * Classe usada para obter o nome, quantidade, valor unitário e valor total do produto que está sendo vendido
     * @return retorna um array de Object para ser mais fácil colocar os dados dentro de cada linha de uma DefaultTableModel
     */
    public Object[] toObject() {
        return new Object[]{nome, qtd, valor_und, valor_total};
    }

    /**
     * Classe usada para obter a id, nome, quantidade, valor unitário e valor total do produto que está sendo vendido
     * @return retorna um array de Object para ser mais fácil colocar os dados dentro de cada linha de uma DefaultTableModel
     */
    public Object[] toObjectId() {
        return new Object[]{id, nome, qtd, valor_und, valor_total};
    }

    /**
     * Classe usada para obter o id do produto, nome, quantidade, valor unitário e valor total do produto que está sendo vendido
     * @return retorna um array de Object para ser mais fácil colocar os dados dentro de cada linha de uma DefaultTableModel
     */
    public Object[] toObjectIdProduto() {
        return new Object[]{id_produto, nome, qtd, valor_und, valor_total};
    }
    
    public int getId() {
        return id;
    }

    public int getId_produto() {
        return id_produto;
    }

    public String getNome() {
        return nome;
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
