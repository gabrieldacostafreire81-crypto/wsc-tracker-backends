package com.seuclube.wsctracker.service;

import com.seuclube.wsctracker.dao.CompeticaoDAO;
import com.seuclube.wsctracker.dao.TemporadaCompeticaoDAO;
import com.seuclube.wsctracker.dao.TemporadaDAO;
import com.seuclube.wsctracker.dao.TituloDAO;
import com.seuclube.wsctracker.model.Competicao;
import com.seuclube.wsctracker.model.Temporada;
import com.seuclube.wsctracker.model.TemporadaCompeticao;
import com.seuclube.wsctracker.model.Titulo;

import java.sql.SQLException;
import java.util.List;

public class TemporadaCompeticaoService {

    private final TemporadaCompeticaoDAO temporadaCompeticaoDAO = new TemporadaCompeticaoDAO();
    private final CompeticaoDAO competicaoDAO = new CompeticaoDAO();
    private final TemporadaDAO temporadaDAO = new TemporadaDAO();
    private final TituloDAO tituloDAO = new TituloDAO();

    /** Declara que o clube vai disputar uma competição nesta temporada (sem resultado ainda). */
    public TemporadaCompeticao adicionarCompeticao(int temporadaId, int competicaoId) throws SQLException {
        List<TemporadaCompeticao> existentes = temporadaCompeticaoDAO.listarPorTemporada(temporadaId);
        boolean jaExiste = existentes.stream().anyMatch(tc -> tc.getCompeticaoId() == competicaoId);
        if (jaExiste) {
            throw new IllegalStateException("Esta competição já foi adicionada a esta temporada.");
        }

        TemporadaCompeticao tc = new TemporadaCompeticao();
        tc.setTemporadaId(temporadaId);
        tc.setCompeticaoId(competicaoId);
        return temporadaCompeticaoDAO.salvar(tc);
    }

    /** Registra o resultado (posição de liga ou fase de copa) — e decide automaticamente se virou título. */
    public boolean registrarResultado(int id, Integer resultadoPosicao, String resultadoFase) throws SQLException {
        TemporadaCompeticao tc = temporadaCompeticaoDAO.buscarPorId(id);
        if (tc == null) return false;

        tc.setResultadoPosicao(resultadoPosicao);
        tc.setResultadoFase(resultadoFase);
        boolean ok = temporadaCompeticaoDAO.atualizarResultado(tc);

        aplicarRegraDeTitulo(tc);
        return ok;
    }

    private void aplicarRegraDeTitulo(TemporadaCompeticao tc) throws SQLException {
        Competicao competicao = competicaoDAO.buscarPorId(tc.getCompeticaoId());
        Temporada temporada = temporadaDAO.buscarPorId(tc.getTemporadaId());
        if (competicao == null || temporada == null) return;

        boolean ehTitulo = "liga".equalsIgnoreCase(competicao.getFormato())
                ? Integer.valueOf(1).equals(tc.getResultadoPosicao())
                : "Campeão".equalsIgnoreCase(tc.getResultadoFase());

        Titulo existente = tituloDAO.buscarPorTemporadaECompeticao(tc.getTemporadaId(), tc.getCompeticaoId());

        if (ehTitulo && existente == null) {
            Titulo novo = new Titulo();
            novo.setTimeId(temporada.getTimeId());
            novo.setTemporadaId(tc.getTemporadaId());
            novo.setCompeticaoId(tc.getCompeticaoId());
            tituloDAO.salvar(novo);
        } else if (!ehTitulo && existente != null) {
            // resultado foi corrigido/editado e deixou de ser campeão — remove o título antigo
            tituloDAO.excluirPorTemporadaECompeticao(tc.getTemporadaId(), tc.getCompeticaoId());
        }
    }

    public boolean excluir(int id) throws SQLException {
        TemporadaCompeticao tc = temporadaCompeticaoDAO.buscarPorId(id);
        if (tc != null) {
            tituloDAO.excluirPorTemporadaECompeticao(tc.getTemporadaId(), tc.getCompeticaoId());
        }
        return temporadaCompeticaoDAO.excluir(id);
    }

    public List<TemporadaCompeticao> listarPorTemporada(int temporadaId) throws SQLException {
        return temporadaCompeticaoDAO.listarPorTemporada(temporadaId);
    }
}