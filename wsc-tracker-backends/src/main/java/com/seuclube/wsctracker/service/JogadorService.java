package com.seuclube.wsctracker.service;

import com.seuclube.wsctracker.dao.JogadorDAO;
import com.seuclube.wsctracker.model.Jogador;

import java.sql.SQLException;
import java.util.List;

public class JogadorService {

    private final JogadorDAO jogadorDAO = new JogadorDAO();

    public Jogador criar(Jogador jogador) throws SQLException {
        return jogadorDAO.salvar(jogador);
    }

    public List<Jogador> listarTodos() throws SQLException {
        return jogadorDAO.listarTodos();
    }

    public Jogador buscarPorId(int id) throws SQLException {
        return jogadorDAO.buscarPorId(id);
    }
}
