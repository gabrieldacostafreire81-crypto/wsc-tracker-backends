package com.seuclube.wsctracker.model;

public class Jogador {
    private int id;
    private String nome;
    private String posicao;
    private String nacionalidade;
    private String dataNascimento;
    private Integer timeAtualId;

    public Jogador() {
    }

    public Jogador(int id, String nome, String posicao, String nacionalidade, String dataNascimento, Integer timeAtualId) {
        this.id = id;
        this.nome = nome;
        this.posicao = posicao;
        this.nacionalidade = nacionalidade;
        this.dataNascimento = dataNascimento;
        this.timeAtualId = timeAtualId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPosicao() {
        return posicao;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Integer getTimeAtualId() {
        return timeAtualId;
    }

    public void setTimeAtualId(Integer timeAtualId) {
        this.timeAtualId = timeAtualId;
    }
}
