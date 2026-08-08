package com.seuclube.wsctracker.service;

import com.seuclube.wsctracker.dao.EstatisticaDAO;
import com.seuclube.wsctracker.dao.EstatisticaTimeDAO;
import com.seuclube.wsctracker.dao.JogadorDAO;
import com.seuclube.wsctracker.model.EstatisticaJogadorTemporada;
import com.seuclube.wsctracker.model.EstatisticaTime;
import com.seuclube.wsctracker.model.Jogador;

import java.sql.SQLException;
import java.util.List;

public class EstatisticaTimeService {

    private final EstatisticaTimeDAO estatisticaTimeDAO = new EstatisticaTimeDAO();
    private final EstatisticaDAO estatisticaDAO = new EstatisticaDAO();
    private final JogadorDAO jogadorDAO = new JogadorDAO();

    /** Cria ou atualiza os dados manuais (V/E/D, gols) — upsert, já que só existe 1 registro por temporada. */
    public EstatisticaTime salvarOuAtualizar(int temporadaId, EstatisticaTime dados) throws SQLException {
        EstatisticaTime existente = estatisticaTimeDAO.buscarPorTemporada(temporadaId);

        if (existente == null) {
            dados.setTemporadaId(temporadaId);
            return estatisticaTimeDAO.salvar(dados);
        } else {
            dados.setId(existente.getId());
            dados.setTemporadaId(temporadaId);
            estatisticaTimeDAO.atualizar(dados);
            return dados;
        }
    }

    /** Busca os dados manuais + calcula tudo o resto (aproveitamento e destaques da temporada). */
    public EstatisticaTime buscarComCalculos(int temporadaId) throws SQLException {
        EstatisticaTime e = estatisticaTimeDAO.buscarPorTemporada(temporadaId);
        if (e == null) {
            e = new EstatisticaTime();
            e.setTemporadaId(temporadaId);
        }

        int totalPartidas = e.getVitorias() + e.getEmpates() + e.getDerrotas();
        e.setTotalPartidas(totalPartidas);

        if (totalPartidas > 0) {
            double pontos = e.getVitorias() * 3 + e.getEmpates();
            e.setAproveitamento(Math.round((pontos / (totalPartidas * 3)) * 1000.0) / 10.0); // 1 casa decimal
        }

        calcularDestaques(temporadaId, e);
        return e;
    }

    private void calcularDestaques(int temporadaId, EstatisticaTime e) throws SQLException {
        List<EstatisticaJogadorTemporada> registros = estatisticaDAO.listarPorTemporada(temporadaId);
        if (registros.isEmpty()) return;

        int maiorGols = -1, maiorAssist = -1;
        Double maiorNota = null;
        Integer artilheiroId = null, garcomId = null, melhorMediaId = null;

        for (EstatisticaJogadorTemporada r : registros) {
            if (r.getGols() > maiorGols) {
                maiorGols = r.getGols();
                artilheiroId = r.getJogadorId();
            }
            if (r.getAssistencias() > maiorAssist) {
                maiorAssist = r.getAssistencias();
                garcomId = r.getJogadorId();
            }
            if (r.getNotaMedia() != null && (maiorNota == null || r.getNotaMedia() > maiorNota)) {
                maiorNota = r.getNotaMedia();
                melhorMediaId = r.getJogadorId();
            }
        }

        if (artilheiroId != null) {
            e.setArtilheiroJogadorId(artilheiroId);
            e.setArtilheiroGols(maiorGols);
            Jogador j = jogadorDAO.buscarPorId(artilheiroId);
            if (j != null) e.setArtilheiroNome(j.getNome());
        }
        if (garcomId != null) {
            e.setGarcomJogadorId(garcomId);
            e.setGarcomAssistencias(maiorAssist);
            Jogador j = jogadorDAO.buscarPorId(garcomId);
            if (j != null) e.setGarcomNome(j.getNome());
        }
        if (melhorMediaId != null) {
            e.setMelhorMediaJogadorId(melhorMediaId);
            e.setMelhorMediaValor(maiorNota);
            Jogador j = jogadorDAO.buscarPorId(melhorMediaId);
            if (j != null) e.setMelhorMediaNome(j.getNome());
        }
    }
}