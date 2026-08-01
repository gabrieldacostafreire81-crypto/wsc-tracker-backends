package com.seuclube.wsctracker.model;

public class EstatisticasColetivasTime {
    private int timeId;
    private int totalGolsClube;
    private int totalJogosClube;
    private int totalJogadoresDistintos;

    private Integer artilheiroJogadorId;
    private String artilheiroNome;
    private int artilheiroGols;

    private Integer maisJogosJogadorId;
    private String maisJogosNome;
    private int maisJogosQuantidade;

    private Integer maiorNotaMediaJogadorId;
    private String maiorNotaMediaNome;
    private Double maiorNotaMediaValor;

    public int getTimeId() {
        return timeId;
    }

    public void setTimeId(int timeId) {
        this.timeId = timeId;
    }

    public int getTotalGolsClube() {
        return totalGolsClube;
    }

    public void setTotalGolsClube(int totalGolsClube) {
        this.totalGolsClube = totalGolsClube;
    }

    public int getTotalJogosClube() {
        return totalJogosClube;
    }

    public void setTotalJogosClube(int totalJogosClube) {
        this.totalJogosClube = totalJogosClube;
    }

    public int getTotalJogadoresDistintos() {
        return totalJogadoresDistintos;
    }

    public void setTotalJogadoresDistintos(int totalJogadoresDistintos) {
        this.totalJogadoresDistintos = totalJogadoresDistintos;
    }

    public Integer getArtilheiroJogadorId() {
        return artilheiroJogadorId;
    }

    public void setArtilheiroJogadorId(Integer artilheiroJogadorId) {
        this.artilheiroJogadorId = artilheiroJogadorId;
    }

    public String getArtilheiroNome() {
        return artilheiroNome;
    }

    public void setArtilheiroNome(String artilheiroNome) {
        this.artilheiroNome = artilheiroNome;
    }

    public int getArtilheiroGols() {
        return artilheiroGols;
    }

    public void setArtilheiroGols(int artilheiroGols) {
        this.artilheiroGols = artilheiroGols;
    }

    public Integer getMaisJogosJogadorId() {
        return maisJogosJogadorId;
    }

    public void setMaisJogosJogadorId(Integer maisJogosJogadorId) {
        this.maisJogosJogadorId = maisJogosJogadorId;
    }

    public String getMaisJogosNome() {
        return maisJogosNome;
    }

    public void setMaisJogosNome(String maisJogosNome) {
        this.maisJogosNome = maisJogosNome;
    }

    public int getMaisJogosQuantidade() {
        return maisJogosQuantidade;
    }

    public void setMaisJogosQuantidade(int maisJogosQuantidade) {
        this.maisJogosQuantidade = maisJogosQuantidade;
    }

    public Integer getMaiorNotaMediaJogadorId() {
        return maiorNotaMediaJogadorId;
    }

    public void setMaiorNotaMediaJogadorId(Integer maiorNotaMediaJogadorId) {
        this.maiorNotaMediaJogadorId = maiorNotaMediaJogadorId;
    }

    public String getMaiorNotaMediaNome() {
        return maiorNotaMediaNome;
    }

    public void setMaiorNotaMediaNome(String maiorNotaMediaNome) {
        this.maiorNotaMediaNome = maiorNotaMediaNome;
    }

    public Double getMaiorNotaMediaValor() {
        return maiorNotaMediaValor;
    }

    public void setMaiorNotaMediaValor(Double maiorNotaMediaValor) {
        this.maiorNotaMediaValor = maiorNotaMediaValor;
    }
}