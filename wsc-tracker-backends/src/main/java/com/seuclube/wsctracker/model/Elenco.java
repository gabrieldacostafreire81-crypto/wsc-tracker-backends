package com.seuclube.wsctracker.model;

public class Elenco {
    private int id;
    private int temporadaId;
    private int jogadorId;
    private String status;         // "inicial" ou "atual"
    private String origemEntrada;  // "herdado" | "compra" | "base" | "emprestimo"
    private String dataEntrada;
    private String motivoSaida;    // "venda" | "emprestimo" | "dispensa" | null
    private String dataSaida;

    public Elenco() {}

    public Elenco(int id, int temporadaId, int jogadorId, String status, String origemEntrada,
                  String dataEntrada, String motivoSaida, String dataSaida) {
        this.id = id;
        this.temporadaId = temporadaId;
        this.jogadorId = jogadorId;
        this.status = status;
        this.origemEntrada = origemEntrada;
        this.dataEntrada = dataEntrada;
        this.motivoSaida = motivoSaida;
        this.dataSaida = dataSaida;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getTemporadaId() { return temporadaId; }
    public void setTemporadaId(int temporadaId) { this.temporadaId = temporadaId; }
    public int getJogadorId() { return jogadorId; }
    public void setJogadorId(int jogadorId) { this.jogadorId = jogadorId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getOrigemEntrada() { return origemEntrada; }
    public void setOrigemEntrada(String origemEntrada) { this.origemEntrada = origemEntrada; }
    public String getDataEntrada() { return dataEntrada; }
    public void setDataEntrada(String dataEntrada) { this.dataEntrada = dataEntrada; }
    public String getMotivoSaida() { return motivoSaida; }
    public void setMotivoSaida(String motivoSaida) { this.motivoSaida = motivoSaida; }
    public String getDataSaida() { return dataSaida; }
    public void setDataSaida(String dataSaida) { this.dataSaida = dataSaida; }
}
