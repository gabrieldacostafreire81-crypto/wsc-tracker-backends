package com.seuclube.wsctracker.service;

import com.seuclube.wsctracker.dao.TemporadaDAO;
import com.seuclube.wsctracker.model.Elenco;
import com.seuclube.wsctracker.model.Temporada;
import com.seuclube.wsctracker.model.TemporadaCompeticao;

import java.sql.SQLException;
import java.util.List;

public class FinalizarTemporadaService {

    private final TemporadaDAO temporadaDAO = new TemporadaDAO();
    private final ElencoService elencoService = new ElencoService();
    private final TemporadaCompeticaoService temporadaCompeticaoService = new TemporadaCompeticaoService();

    public Temporada finalizar(int temporadaId) throws SQLException {
        Temporada atual = temporadaDAO.buscarPorId(temporadaId);
        if (atual == null) {
            throw new IllegalArgumentException("Temporada não encontrada");
        }
        if (atual.isEncerrada()) {
            throw new IllegalStateException("Esta temporada já foi finalizada");
        }

        Temporada proxima = new Temporada();
        proxima.setTimeId(atual.getTimeId());
        proxima.setNumero(atual.getNumero() + 1);
        proxima.setNivelTreino(atual.getNivelTreino());
        Temporada proximaCriada = temporadaDAO.salvar(proxima);

        // Herda o elenco atual
        List<Elenco> elencoAtivo = elencoService.listarElencoAtual(temporadaId);
        for (Elenco vinculo : elencoAtivo) {
            elencoService.herdarElenco(proximaCriada.getId(), vinculo.getJogadorId());
        }

        // Herda as competições disputadas (sem resultado — a nova temporada começa em branco)
        List<TemporadaCompeticao> competicoesAtivas = temporadaCompeticaoService.listarPorTemporada(temporadaId);
        for (TemporadaCompeticao tc : competicoesAtivas) {
            temporadaCompeticaoService.adicionarCompeticao(proximaCriada.getId(), tc.getCompeticaoId());
        }

        temporadaDAO.marcarEncerrada(temporadaId);

        return proximaCriada;
    }
}