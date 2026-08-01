package com.seuclube.wsctracker.model;

public class EstatisticasCarreiraJogador {
    private int jogadorId;
    private int totalTemporadas;
    private int totalJogos;
    private int totalGols;
    private int totalAssistencias;
    private int totalCartoesAmarelos;
    private int totalCartoesVermelhos;
    private Double notaMediaGeral;
    private Integer melhorTemporadaId;
    private int golsNaMelhorTemporada;

    public EstatisticasCarreiraJogador() {
    }

    public int getJogadorId() {
        return jogadorId;
    }

    public void setJogadorId(int jogadorId) {
        this.jogadorId = jogadorId;
    }

    public int getTotalTemporadas() {
        return totalTemporadas;
    }

    public void setTotalTemporadas(int totalTemporadas) {
        this.totalTemporadas = totalTemporadas;
    }

    public int getTotalJogos() {
        return totalJogos;
    }

    public void setTotalJogos(int totalJogos) {
        this.totalJogos = totalJogos;
    }

    public int getTotalGols() {
        return totalGols;
    }

    public void setTotalGols(int totalGols) {
        this.totalGols = totalGols;
    }

    public int getTotalAssistencias() {
        return totalAssistencias;
    }

    public void setTotalAssistencias(int totalAssistencias) {
        this.totalAssistencias = totalAssistencias;
    }

    public int getTotalCartoesAmarelos() {
        return totalCartoesAmarelos;
    }

    public void setTotalCartoesAmarelos(int totalCartoesAmarelos) {
        this.totalCartoesAmarelos = totalCartoesAmarelos;
    }

    public int getTotalCartoesVermelhos() {
        return totalCartoesVermelhos;
    }

    public void setTotalCartoesVermelhos(int totalCartoesVermelhos) {
        this.totalCartoesVermelhos = totalCartoesVermelhos;
    }

    public Double getNotaMediaGeral() {
        return notaMediaGeral;
    }

    public void setNotaMediaGeral(Double notaMediaGeral) {
        this.notaMediaGeral = notaMediaGeral;
    }

    public Integer getMelhorTemporadaId() {
        return melhorTemporadaId;
    }

    public void setMelhorTemporadaId(Integer melhorTemporadaId) {
        this.melhorTemporadaId = melhorTemporadaId;
    }

    public int getGolsNaMelhorTemporada() {
        return golsNaMelhorTemporada;
    }

    public void setGolsNaMelhorTemporada(int golsNaMelhorTemporada) {
        this.golsNaMelhorTemporada = golsNaMelhorTemporada;
    }
}