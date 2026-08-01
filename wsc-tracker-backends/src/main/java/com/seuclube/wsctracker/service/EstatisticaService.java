package com.seuclube.wsctracker.service;

import com.seuclube.wsctracker.dao.EstatisticaDAO;
import com.seuclube.wsctracker.model.EstatisticaJogadorTemporada;

import java.sql.SQLException;
import java.util.List;

public class EstatisticaService {

    private final EstatisticaDAO estatisticaDAO = new EstatisticaDAO();

    public EstatisticaJogadorTemporada registrar(EstatisticaJogadorTemporada estatistica) throws SQLException {
        return estatisticaDAO.salvar(estatistica);
    }

    public List<EstatisticaJogadorTemporada> listarPorTemporada(int temporadaId) throws SQLException {
        return estatisticaDAO.listarPorTemporada(temporadaId);
    }

    public List<EstatisticaJogadorTemporada> listarPorJogador(int jogadorId) throws SQLException {
        return estatisticaDAO.listarPorJogador(jogadorId);
    }

    public EstatisticaJogadorTemporada buscarPorId(int id) throws SQLException {
        return estatisticaDAO.buscarPorId(id);
    }
}