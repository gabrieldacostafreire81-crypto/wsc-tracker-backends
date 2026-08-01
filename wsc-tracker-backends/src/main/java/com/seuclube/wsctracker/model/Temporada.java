package com.seuclube.wsctracker.model;

public class Temporada {
    private int id;
    private int timeId;
    private int numero;
    private String divisao;
    private Integer posicaoFinal;
    private String observacoes;

    public Temporada() {
    }

    public Temporada(int id, int timeId, int numero, String divisao, Integer posicaoFinal, String observacoes) {
        this.id = id;
        this.timeId = timeId;
        this.numero = numero;
        this.divisao = divisao;
        this.posicaoFinal = posicaoFinal;
        this.observacoes = observacoes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTimeId() {
        return timeId;
    }

    public void setTimeId(int timeId) {
        this.timeId = timeId;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getDivisao() {
        return divisao;
    }

    public void setDivisao(String divisao) {
        this.divisao = divisao;
    }

    public Integer getPosicaoFinal() {
        return posicaoFinal;
    }

    public void setPosicaoFinal(Integer posicaoFinal) {
        this.posicaoFinal = posicaoFinal;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}
