package com.seuclube.wsctracker.model;

public class Jogador {
    private int id;
    private String nome;
    private String posicao;
    private String nacionalidade;
    private String dataNascimento;
    private Integer timeAtualId;
    private boolean origemBase;
    private String dataChegadaBase;
    private Integer overallBase;

    public Jogador() {
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getPosicao() { return posicao; }
    public void setPosicao(String posicao) { this.posicao = posicao; }

    public String getNacionalidade() { return nacionalidade; }
    public void setNacionalidade(String nacionalidade) { this.nacionalidade = nacionalidade; }

    public String getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(String dataNascimento) { this.dataNascimento = dataNascimento; }

    public Integer getTimeAtualId() { return timeAtualId; }
    public void setTimeAtualId(Integer timeAtualId) { this.timeAtualId = timeAtualId; }

    public boolean isOrigemBase() { return origemBase; }
    public void setOrigemBase(boolean origemBase) { this.origemBase = origemBase; }

    public String getDataChegadaBase() { return dataChegadaBase; }
    public void setDataChegadaBase(String dataChegadaBase) { this.dataChegadaBase = dataChegadaBase; }

    public Integer getOverallBase() { return overallBase; }
    public void setOverallBase(Integer overallBase) { this.overallBase = overallBase; }
}
