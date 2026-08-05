package com.seuclube.wsctracker.model;

public class Temporada {
    private int id;
    private int timeId;
    private int numero;
    private String divisao;
    private Integer posicaoFinal;
    private String observacoes;
    private boolean encerrada;
    private Integer nivelTreino;

    public Temporada() {
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

    public boolean isEncerrada() {
        return encerrada;
    }

    public void setEncerrada(boolean encerrada) {
        this.encerrada = encerrada;
    }

    public Integer getNivelTreino() {
        return nivelTreino;
    }

    public void setNivelTreino(Integer nivelTreino) {
        this.nivelTreino = nivelTreino;
    }
}
