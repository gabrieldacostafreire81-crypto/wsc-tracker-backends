package com.seuclube.wsctracker.model;

public class Time {
    private int id;
    private String nome;
    private int temporadaFundacao;
    private String observacoes;

    // Construtor vazio (necessário para o Jackson, usado pelo Javalin, montar o objeto a partir do JSON)
    public Time() {
    }

    public Time(int id, String nome, int temporadaFundacao, String observacoes) {
        this.id = id;
        this.nome = nome;
        this.temporadaFundacao = temporadaFundacao;
        this.observacoes = observacoes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getTemporadaFundacao() {
        return temporadaFundacao;
    }

    public void setTemporadaFundacao(int temporadaFundacao) {
        this.temporadaFundacao = temporadaFundacao;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}