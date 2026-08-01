package com.seuclube.wsctracker.service;

import com.seuclube.wsctracker.dao.TemporadaDAO;
import com.seuclube.wsctracker.model.Temporada;

import java.sql.SQLException;
import java.util.List;

public class TemporadaService {

    private final TemporadaDAO temporadaDAO = new TemporadaDAO();

    public Temporada criar(Temporada temporada) throws SQLException {
        return temporadaDAO.salvar(temporada);
    }

    public List<Temporada> listarTodas() throws SQLException {
        return temporadaDAO.listarTodas();
    }

    public List<Temporada> listarPorTime(int timeId) throws SQLException {
        return temporadaDAO.listarPorTime(timeId);
    }

    public Temporada buscarPorId(int id) throws SQLException {
        return temporadaDAO.buscarPorId(id);
    }
}