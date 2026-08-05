package com.seuclube.wsctracker.service;

import com.seuclube.wsctracker.dao.CompeticaoDAO;
import com.seuclube.wsctracker.model.Competicao;

import java.sql.SQLException;
import java.util.List;

public class CompeticaoService {

    private final CompeticaoDAO competicaoDAO = new CompeticaoDAO();

    public Competicao criar(Competicao c) throws SQLException {
        return competicaoDAO.salvar(c);
    }
    public boolean atualizar(Competicao c) throws SQLException {
        return competicaoDAO.atualizar(c);
    }

    public boolean excluir(int id) throws SQLException {
        return competicaoDAO.excluir(id);
    }

    public List<Competicao> listarTodas() throws SQLException {
        return competicaoDAO.listarTodas();
    }

    public Competicao buscarPorId(int id) throws SQLException {
        return competicaoDAO.buscarPorId(id);
    }
}