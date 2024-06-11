package com.ruanmattos.main.data;

/**
 *
 * @author Ruan Pereira Mattos
 */
public class ClienteData {

    int id;
    String nome;
    String telefone;
    String cpf;

    public ClienteData(int id, String nome, String telefone, String cpf) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.cpf = cpf;
    }

    /**
     *
     * @return nome, telefone, cpf
     */
    public Object[] toObjectNomeTelefoneCPF() {
        return new Object[]{nome, telefone, cpf};
    }

    /**
     *
     * @return id, nome
     */
    public Object[] toObject() {
        return new Object[]{id, nome};
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCpf() {
        return cpf;
    }

}
