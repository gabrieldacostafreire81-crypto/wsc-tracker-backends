package com.seuclube.wsctracker.service;

import com.seuclube.wsctracker.dao.JogadorDAO;
import com.seuclube.wsctracker.dao.TransferenciaDAO;
import com.seuclube.wsctracker.model.FluxoFinanceiroTime;
import com.seuclube.wsctracker.model.Jogador;
import com.seuclube.wsctracker.model.Transferencia;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class FinanceiroService {

    private final TransferenciaDAO transferenciaDAO = new TransferenciaDAO();
    private final JogadorDAO jogadorDAO = new JogadorDAO();

    /** RF13 — fluxo financeiro completo do time, somando todas as temporadas. */
    public FluxoFinanceiroTime fluxoPorTime(int timeId) throws SQLException {
        List<Transferencia> transferencias = transferenciaDAO.listarPorTimeCompleto(timeId);

        FluxoFinanceiroTime resultado = new FluxoFinanceiroTime();
        resultado.setTimeId(timeId);

        double somaInvestido = 0, somaArrecadado = 0;
        int quantidadeCompras = 0, quantidadeVendas = 0;

        double somaIdadeComprados = 0, somaIdadeVendidos = 0;
        int quantidadeIdadeComprados = 0, quantidadeIdadeVendidos = 0;

        for (Transferencia t : transferencias) {
            if (t.getValor() == null) continue; // sem valor registrado, não entra na conta

            boolean ehCompra = "compra".equalsIgnoreCase(t.getTipo());
            boolean ehVenda = "venda".equalsIgnoreCase(t.getTipo());

            if (ehCompra) {
                somaInvestido += t.getValor();
                quantidadeCompras++;
            } else if (ehVenda) {
                somaArrecadado += t.getValor();
                quantidadeVendas++;
            }

            // Calcula a idade do jogador NA DATA da transferência (não a idade atual dele)
            Double idadeNaTransferencia = calcularIdade(t.getJogadorId(), t.getData());
            if (idadeNaTransferencia != null) {
                if (ehCompra) {
                    somaIdadeComprados += idadeNaTransferencia;
                    quantidadeIdadeComprados++;
                } else if (ehVenda) {
                    somaIdadeVendidos += idadeNaTransferencia;
                    quantidadeIdadeVendidos++;
                }
            }
        }

        resultado.setTotalInvestido(somaInvestido);
        resultado.setTotalArrecadado(somaArrecadado);
        resultado.setSaldo(somaArrecadado - somaInvestido);
        resultado.setQuantidadeCompras(quantidadeCompras);
        resultado.setQuantidadeVendas(quantidadeVendas);

        if (quantidadeCompras > 0) {
            resultado.setCustoMedioContratacao(somaInvestido / quantidadeCompras);
        }
        if (quantidadeVendas > 0) {
            resultado.setValorMedioVenda(somaArrecadado / quantidadeVendas);
        }
        if (quantidadeIdadeComprados > 0) {
            resultado.setIdadeMediaComprados(somaIdadeComprados / quantidadeIdadeComprados);
        }
        if (quantidadeIdadeVendidos > 0) {
            resultado.setIdadeMediaVendidos(somaIdadeVendidos / quantidadeIdadeVendidos);
        }

        return resultado;
    }

    private Double calcularIdade(int jogadorId, String dataTransferencia) throws SQLException {
        if (dataTransferencia == null) return null;

        Jogador jogador = jogadorDAO.buscarPorId(jogadorId);
        if (jogador == null || jogador.getDataNascimento() == null) return null;

        try {
            LocalDate nascimento = LocalDate.parse(jogador.getDataNascimento());
            LocalDate dataTransf = LocalDate.parse(dataTransferencia);
            return (double) Period.between(nascimento, dataTransf).getYears();
        } catch (Exception e) {
            return null; // datas em formato inválido — ignora esse registro no cálculo de idade
        }
    }
}