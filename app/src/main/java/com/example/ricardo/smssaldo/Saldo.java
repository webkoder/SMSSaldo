package com.example.ricardo.smssaldo;

/**
 * Created by Ricardo on 08/06/2015.
 */
public class Saldo {
    public static final int SALDO_ATUAL = 1;
    public static final int CREDITO_INSERIDO = 2;

    private int id;
    private String saldo;
    private String validade;
    private long recebimento;
    private int originalid;
    private int tipo;

    public Saldo(){}
    public Saldo(int id, String saldo, String validade, long recebimento, int originalid, int tipo){
        if(id != 0) this.setId(id);
        this.setSaldo(saldo);
        this.setValidade(validade);
        this.setRecebimento(recebimento);
        this.setOriginalid(originalid);
        this.setTipo(tipo);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }

    public long getRecebimento() {
        return recebimento;
    }

    public void setRecebimento(long recebimento) {
        this.recebimento = recebimento;
    }

    public int getOriginalid() {
        return originalid;
    }

    public void setOriginalid(int originalid) {
        this.originalid = originalid;
    }


    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}