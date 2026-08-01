package com.seuclube.wsctracker.service;

import com.seuclube.wsctracker.dao.TimeDAO;
import com.seuclube.wsctracker.model.Time;

import java.sql.SQLException;
import java.util.List;

public class TimeService {

    private final TimeDAO timeDAO = new TimeDAO();

    public Time criar(Time time) throws SQLException {
        return timeDAO.salvar(time);
    }

    public List<Time> listarTodos() throws SQLException {
        return timeDAO.listarTodos();
    }

    public Time buscarPorId(int id) throws SQLException {
        return timeDAO.buscarPorId(id);
    }
}
