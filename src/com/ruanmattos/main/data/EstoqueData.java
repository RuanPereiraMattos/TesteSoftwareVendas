package com.ruanmattos.main.data;

/**
 *
 * @author Ruan Pereira Mattos
 */
public class EstoqueData {

    int id;
    int id_produto;
    int qtd;

    public EstoqueData(int id, int id_produto, int qtd) {
        this.id = id;
        this.id_produto = id_produto;
        this.qtd = qtd;
    }

    /**
     * 
     * @return id, id_produto, qtd
     */
    public Object[] toObject() {
        return new Object[]{id, id_produto, qtd};
    }

    public int getId() {
        return id;
    }

    public int getId_produto() {
        return id_produto;
    }

    public int getQtd() {
        return qtd;
    }

}
