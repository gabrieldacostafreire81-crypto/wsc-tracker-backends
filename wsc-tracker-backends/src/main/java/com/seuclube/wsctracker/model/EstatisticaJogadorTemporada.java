package com.seuclube.wsctracker.model;

public class EstatisticaJogadorTemporada {
    private int id;
    private int jogadorId;
    private int temporadaId;
    private int jogos;
    private int gols;
    private int assistencias;
    private Double notaMedia;
    private int cartoesAmarelos;
    private int cartoesVermelhos;
    private Double valorMercado;
    private String status;

    public EstatisticaJogadorTemporada() {
    }

    public EstatisticaJogadorTemporada(int id, int jogadorId, int temporadaId, int jogos, int gols,
                                       int assistencias, Double notaMedia, int cartoesAmarelos,
                                       int cartoesVermelhos, Double valorMercado, String status) {
        this.id = id;
        this.jogadorId = jogadorId;
        this.temporadaId = temporadaId;
        this.jogos = jogos;
        this.gols = gols;
        this.assistencias = assistencias;
        this.notaMedia = notaMedia;
        this.cartoesAmarelos = cartoesAmarelos;
        this.cartoesVermelhos = cartoesVermelhos;
        this.valorMercado = valorMercado;
        this.status = status;
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

    public int getJogos() {
        return jogos;
    }

    public void setJogos(int jogos) {
        this.jogos = jogos;
    }

    public int getGols() {
        return gols;
    }

    public void setGols(int gols) {
        this.gols = gols;
    }

    public int getAssistencias() {
        return assistencias;
    }

    public void setAssistencias(int assistencias) {
        this.assistencias = assistencias;
    }

    public Double getNotaMedia() {
        return notaMedia;
    }

    public void setNotaMedia(Double notaMedia) {
        this.notaMedia = notaMedia;
    }

    public int getCartoesAmarelos() {
        return cartoesAmarelos;
    }

    public void setCartoesAmarelos(int cartoesAmarelos) {
        this.cartoesAmarelos = cartoesAmarelos;
    }

    public int getCartoesVermelhos() {
        return cartoesVermelhos;
    }

    public void setCartoesVermelhos(int cartoesVermelhos) {
        this.cartoesVermelhos = cartoesVermelhos;
    }

    public Double getValorMercado() {
        return valorMercado;
    }

    public void setValorMercado(Double valorMercado) {
        this.valorMercado = valorMercado;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}