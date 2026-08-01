package com.seuclube.wsctracker.model;

public class FluxoFinanceiroTime {
    private int timeId;
    private double totalInvestido;
    private double totalArrecadado;
    private double saldo;
    private int quantidadeCompras;
    private int quantidadeVendas;
    private Double custoMedioContratacao;
    private Double valorMedioVenda;
    private Double idadeMediaComprados;
    private Double idadeMediaVendidos;

    public int getTimeId() {
        return timeId;
    }

    public void setTimeId(int timeId) {
        this.timeId = timeId;
    }

    public double getTotalInvestido() {
        return totalInvestido;
    }

    public void setTotalInvestido(double totalInvestido) {
        this.totalInvestido = totalInvestido;
    }

    public double getTotalArrecadado() {
        return totalArrecadado;
    }

    public void setTotalArrecadado(double totalArrecadado) {
        this.totalArrecadado = totalArrecadado;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public int getQuantidadeCompras() {
        return quantidadeCompras;
    }

    public void setQuantidadeCompras(int quantidadeCompras) {
        this.quantidadeCompras = quantidadeCompras;
    }

    public int getQuantidadeVendas() {
        return quantidadeVendas;
    }

    public void setQuantidadeVendas(int quantidadeVendas) {
        this.quantidadeVendas = quantidadeVendas;
    }

    public Double getCustoMedioContratacao() {
        return custoMedioContratacao;
    }

    public void setCustoMedioContratacao(Double custoMedioContratacao) {
        this.custoMedioContratacao = custoMedioContratacao;
    }

    public Double getValorMedioVenda() {
        return valorMedioVenda;
    }

    public void setValorMedioVenda(Double valorMedioVenda) {
        this.valorMedioVenda = valorMedioVenda;
    }

    public Double getIdadeMediaComprados() {
        return idadeMediaComprados;
    }

    public void setIdadeMediaComprados(Double idadeMediaComprados) {
        this.idadeMediaComprados = idadeMediaComprados;
    }

    public Double getIdadeMediaVendidos() {
        return idadeMediaVendidos;
    }

    public void setIdadeMediaVendidos(Double idadeMediaVendidos) {
        this.idadeMediaVendidos = idadeMediaVendidos;
    }
}