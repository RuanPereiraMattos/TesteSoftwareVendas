package com.ruanmattos.main.data;

//<editor-fold defaultstate="collapsed" desc="Imports">
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
//</editor-fold>

/**
 * Class VendasData
 *
 * @author Ruan Pereira Mattos
 */
public class VendasData {

    int id, id_cliente, hora, minuto, dia, mes, ano;
    public Timestamp data_venda;
    String nome;
    //String hora;
    ArrayList<ProdutosData> produtos = new ArrayList<>();
    public List<PagamentosData> pagamentos = new ArrayList<>();

    /*public VendasData(int id, int id_cliente, String hora) {
    this.id = id;
    this.id_cliente = id_cliente;
    this.hora = hora;
    }*/

    /**
     * 
     * @param id id_venda
     * @param id_cliente
     * @param nome
     * @param hora
     * @param minuto
     * @param dia
     * @param mes
     * @param ano 
     */
    public VendasData(int id, int id_cliente, String nome, int hora, int minuto, int dia, int mes, int ano) {
        this.id = id;
        this.id_cliente = id_cliente;
        this.nome = nome;
        this.hora = hora;
        this.minuto = minuto;
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
    }

    public Object[] toObject() {
        String produtos_str = "";
        for (ProdutosData p : produtos) {
            produtos_str += "Nome: " + p.getNome() + " Descricao: " + p.getDescricao() + " Valor: " + p.getValor();
        }
        return new Object[]{id, nome, hora, minuto, dia, mes, ano};
    }

    public int getId() {
        return id;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public int getHora() {
        return hora;
    }

    public int getMinuto() {
        return minuto;
    }

    public int getDia() {
        return dia;
    }

    public int getMes() {
        return mes;
    }

    public int getAno() {
        return ano;
    }

    public String getNome() {
        return nome;
    }

    /*public String getHora() {
    return hora;
    }*/

    public ArrayList<ProdutosData> getProdutos() {
        return produtos;
    }

}
