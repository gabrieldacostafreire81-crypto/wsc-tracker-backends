package com.seuclube.wsctracker.model;

public class Transferencia {
    private int id;
    private int jogadorId;
    private int temporadaId;
    private String timeOrigem;
    private String timeDestino;
    private Double valor;
    private String tipo;
    private String data;

    public Transferencia() {
    }

    public Transferencia(int id, int jogadorId, int temporadaId, String timeOrigem, String timeDestino,
                         Double valor, String tipo, String data) {
        this.id = id;
        this.jogadorId = jogadorId;
        this.temporadaId = temporadaId;
        this.timeOrigem = timeOrigem;
        this.timeDestino = timeDestino;
        this.valor = valor;
        this.tipo = tipo;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJogadorId() {
        return jogadorId;
    }

    public void setJogadorId(int jogadorId) {
        this.jogadorId = jogadorId;
    }

    public int getTemporadaId() {
        return temporadaId;
    }

    public void setTemporadaId(int temporadaId) {
        this.temporadaId = temporadaId;
    }

    public String getTimeOrigem() {
        return timeOrigem;
    }

    public void setTimeOrigem(String timeOrigem) {
        this.timeOrigem = timeOrigem;
    }

    public String getTimeDestino() {
        return timeDestino;
    }

    public void setTimeDestino(String timeDestino) {
        this.timeDestino = timeDestino;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    private String jogadorNomeNovo;
    private String jogadorPosicaoNovo;

    public String getJogadorNomeNovo() { return jogadorNomeNovo; }
    public void setJogadorNomeNovo(String jogadorNomeNovo) { this.jogadorNomeNovo = jogadorNomeNovo; }
    public String getJogadorPosicaoNovo() { return jogadorPosicaoNovo; }
    public void setJogadorPosicaoNovo(String jogadorPosicaoNovo) { this.jogadorPosicaoNovo = jogadorPosicaoNovo; }
}
