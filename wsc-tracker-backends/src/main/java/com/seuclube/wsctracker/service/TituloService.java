package com.seuclube.wsctracker.service;

import com.seuclube.wsctracker.dao.TituloDAO;
import com.seuclube.wsctracker.model.Titulo;

import java.sql.SQLException;
import java.util.List;

public class TituloService {

    private final TituloDAO tituloDAO = new TituloDAO();

    public Titulo criar(Titulo t) throws SQLException {
        return tituloDAO.salvar(t);
    }
    public boolean atualizar(Titulo t) throws SQLException {
        return tituloDAO.atualizar(t);
    }

    public boolean excluir(int id) throws SQLException {
        return tituloDAO.excluir(id);
    }

    public List<Titulo> listarPorTime(int timeId) throws SQLException {
        return tituloDAO.listarPorTime(timeId);
    }

    public Titulo buscarPorId(int id) throws SQLException {
        return tituloDAO.buscarPorId(id);
    }
}