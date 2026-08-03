package com.seuclube.wsctracker.service;

import com.seuclube.wsctracker.dao.TransferenciaDAO;
import com.seuclube.wsctracker.model.Transferencia;

import java.sql.SQLException;
import java.util.List;

public class TransferenciaService {

    private final TransferenciaDAO transferenciaDAO = new TransferenciaDAO();

    public Transferencia criar(Transferencia t) throws SQLException {
        return transferenciaDAO.salvar(t);
    }
    public boolean atualizar(Transferencia t) throws SQLException {
        return transferenciaDAO.atualizar(t);
    }

    public boolean excluir(int id) throws SQLException {
        return transferenciaDAO.excluir(id);
    }

    public List<Transferencia> listarPorJogador(int jogadorId) throws SQLException {
        return transferenciaDAO.listarPorJogador(jogadorId);
    }

    public List<Transferencia> listarPorTemporada(int temporadaId) throws SQLException {
        return transferenciaDAO.listarPorTemporada(temporadaId);
    }

    public Transferencia buscarPorId(int id) throws SQLException {
        return transferenciaDAO.buscarPorId(id);
    }
}