package com.seuclube.wsctracker.model;

public class Titulo {
    private int id;
    private int timeId;
    private int temporadaId;
    private int competicaoId;

    public Titulo() {
    }

    public Titulo(int id, int timeId, int temporadaId, int competicaoId) {
        this.id = id;
        this.timeId = timeId;
        this.temporadaId = temporadaId;
        this.competicaoId = competicaoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTimeId() {
        return timeId;
    }

    public void setTimeId(int timeId) {
        this.timeId = timeId;
    }

    public int getTemporadaId() {
        return temporadaId;
    }

    public void setTemporadaId(int temporadaId) {
        this.temporadaId = temporadaId;
    }

    public int getCompeticaoId() {
        return competicaoId;
    }

    public void setCompeticaoId(int competicaoId) {
        this.competicaoId = competicaoId;
    }
}