package com.seuclube.wsctracker.model;

public class TemporadaCompeticao {
    private int id;
    private int temporadaId;
    private int competicaoId;
    private Integer resultadoPosicao; // liga
    private String resultadoFase;     // copa

    public TemporadaCompeticao() {
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getTemporadaId() { return temporadaId; }
    public void setTemporadaId(int temporadaId) { this.temporadaId = temporadaId; }

    public int getCompeticaoId() { return competicaoId; }
    public void setCompeticaoId(int competicaoId) { this.competicaoId = competicaoId; }

    public Integer getResultadoPosicao() { return resultadoPosicao; }
    public void setResultadoPosicao(Integer resultadoPosicao) { this.resultadoPosicao = resultadoPosicao; }

    public String getResultadoFase() { return resultadoFase; }
    public void setResultadoFase(String resultadoFase) { this.resultadoFase = resultadoFase; }
}