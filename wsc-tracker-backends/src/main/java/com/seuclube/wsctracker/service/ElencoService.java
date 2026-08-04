package com.seuclube.wsctracker.service;

import com.seuclube.wsctracker.dao.ElencoDAO;
import com.seuclube.wsctracker.model.Elenco;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ElencoService {

    private final ElencoDAO elencoDAO = new ElencoDAO();

    /** Adiciona um jogador ao elenco ATUAL de uma temporada (compra, base, ou herança). */
    public Elenco adicionarAoElencoAtual(int temporadaId, int jogadorId, String origemEntrada) throws SQLException {
        Elenco e = new Elenco();
        e.setTemporadaId(temporadaId);
        e.setJogadorId(jogadorId);
        e.setStatus("atual");
        e.setOrigemEntrada(origemEntrada);
        e.setDataEntrada(LocalDate.now().toString());
        return elencoDAO.salvar(e);
    }

    /** Marca a saída de um jogador do elenco atual (venda, empréstimo, dispensa). */
    public boolean removerDoElencoAtual(int temporadaId, int jogadorId, String motivoSaida) throws SQLException {
        Elenco existente = elencoDAO.buscarAtivoPorJogadorETemporada(jogadorId, temporadaId);
        if (existente == null) return false; // jogador não estava no elenco atual desta temporada
        existente.setMotivoSaida(motivoSaida);
        existente.setDataSaida(LocalDate.now().toString());
        return elencoDAO.atualizar(existente);
    }

    public List<Elenco> listarElencoAtual(int temporadaId) throws SQLException {
        return elencoDAO.listarAtivoPorTemporada(temporadaId);
    }

    public List<Elenco> listarTudo(int temporadaId) throws SQLException {
        return elencoDAO.listarPorTemporada(temporadaId);
    }
    /** Usado só no Finalizar Temporada: registra o elenco herdado como Elenco Inicial E Elenco Atual da nova temporada. */
    public void herdarElenco(int novaTemporadaId, int jogadorId) throws SQLException {
        Elenco inicial = new Elenco();
        inicial.setTemporadaId(novaTemporadaId);
        inicial.setJogadorId(jogadorId);
        inicial.setStatus("inicial");
        inicial.setOrigemEntrada("herdado");
        inicial.setDataEntrada(LocalDate.now().toString());
        elencoDAO.salvar(inicial);

        Elenco atual = new Elenco();
        atual.setTemporadaId(novaTemporadaId);
        atual.setJogadorId(jogadorId);
        atual.setStatus("atual");
        atual.setOrigemEntrada("herdado");
        atual.setDataEntrada(LocalDate.now().toString());
        elencoDAO.salvar(atual);
    }
}