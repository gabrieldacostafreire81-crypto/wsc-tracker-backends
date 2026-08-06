package com.seuclube.wsctracker.model;

public class Competicao {
    private int id;
    private String nome;
    private String formato;       // "liga" | "copa"
    private String abrangencia;   // "nacional" | "continental"

    public Competicao() {
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getFormato() { return formato; }
    public void setFormato(String formato) { this.formato = formato; }

    public String getAbrangencia() { return abrangencia; }
    public void setAbrangencia(String abrangencia) { this.abrangencia = abrangencia; }
}