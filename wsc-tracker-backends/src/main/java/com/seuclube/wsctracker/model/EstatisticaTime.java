package com.seuclube.wsctracker.model;

public class EstatisticaTime {
    // Persistidos (você preenche)
    private int id;
    private int temporadaId;
    private int vitorias;
    private int empates;
    private int derrotas;
    private int golsFeitos;
    private int golsSofridos;

    // Calculados (o sistema preenche na hora de devolver, nunca são salvos)
    private int totalPartidas;
    private double aproveitamento;
    private Integer artilheiroJogadorId;
    private String artilheiroNome;
    private int artilheiroGols;
    private Integer garcomJogadorId;
    private String garcomNome;
    private int garcomAssistencias;
    private Integer melhorMediaJogadorId;
    private String melhorMediaNome;
    private Double melhorMediaValor;

    public EstatisticaTime() {
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getTemporadaId() { return temporadaId; }
    public void setTemporadaId(int temporadaId) { this.temporadaId = temporadaId; }

    public int getVitorias() { return vitorias; }
    public void setVitorias(int vitorias) { this.vitorias = vitorias; }

    public int getEmpates() { return empates; }
    public void setEmpates(int empates) { this.empates = empates; }

    public int getDerrotas() { return derrotas; }
    public void setDerrotas(int derrotas) { this.derrotas = derrotas; }

    public int getGolsFeitos() { return golsFeitos; }
    public void setGolsFeitos(int golsFeitos) { this.golsFeitos = golsFeitos; }

    public int getGolsSofridos() { return golsSofridos; }
    public void setGolsSofridos(int golsSofridos) { this.golsSofridos = golsSofridos; }

    public int getTotalPartidas() { return totalPartidas; }
    public void setTotalPartidas(int totalPartidas) { this.totalPartidas = totalPartidas; }

    public double getAproveitamento() { return aproveitamento; }
    public void setAproveitamento(double aproveitamento) { this.aproveitamento = aproveitamento; }

    public Integer getArtilheiroJogadorId() { return artilheiroJogadorId; }
    public void setArtilheiroJogadorId(Integer artilheiroJogadorId) { this.artilheiroJogadorId = artilheiroJogadorId; }

    public String getArtilheiroNome() { return artilheiroNome; }
    public void setArtilheiroNome(String artilheiroNome) { this.artilheiroNome = artilheiroNome; }

    public int getArtilheiroGols() { return artilheiroGols; }
    public void setArtilheiroGols(int artilheiroGols) { this.artilheiroGols = artilheiroGols; }

    public Integer getGarcomJogadorId() { return garcomJogadorId; }
    public void setGarcomJogadorId(Integer garcomJogadorId) { this.garcomJogadorId = garcomJogadorId; }

    public String getGarcomNome() { return garcomNome; }
    public void setGarcomNome(String garcomNome) { this.garcomNome = garcomNome; }

    public int getGarcomAssistencias() { return garcomAssistencias; }
    public void setGarcomAssistencias(int garcomAssistencias) { this.garcomAssistencias = garcomAssistencias; }

    public Integer getMelhorMediaJogadorId() { return melhorMediaJogadorId; }
    public void setMelhorMediaJogadorId(Integer melhorMediaJogadorId) { this.melhorMediaJogadorId = melhorMediaJogadorId; }

    public String getMelhorMediaNome() { return melhorMediaNome; }
    public void setMelhorMediaNome(String melhorMediaNome) { this.melhorMediaNome = melhorMediaNome; }

    public Double getMelhorMediaValor() { return melhorMediaValor; }
    public void setMelhorMediaValor(Double melhorMediaValor) { this.melhorMediaValor = melhorMediaValor; }
}