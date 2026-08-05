package com.seuclube.wsctracker.dao;

import com.seuclube.wsctracker.model.Competicao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompeticaoDAO {

    public Competicao salvar(Competicao c) throws SQLException {
        String sql = "INSERT INTO competicao (nome, tipo) VALUES (?, ?)";
        Connection conn = ConexaoSQLite.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, c.getNome());
            stmt.setString(2, c.getTipo());
            stmt.executeUpdate();

            try (ResultSet chaves = stmt.getGeneratedKeys()) {
                if (chaves.next()) {
                    c.setId(chaves.getInt(1));
                }
            }
        }
        return c;
    }

    public List<Competicao> listarTodas() throws SQLException {
        String sql = "SELECT * FROM competicao";
        List<Competicao> lista = new ArrayList<>();

        Connection conn = ConexaoSQLite.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }
        return lista;
    }

    public Competicao buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM competicao WHERE id = ?";
        Connection conn = ConexaoSQLite.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        }
        return null;
    }

    private Competicao mapear(ResultSet rs) throws SQLException {
        return new Competicao(rs.getInt("id"), rs.getString("nome"), rs.getString("tipo"));
    }
    public boolean atualizar(Competicao c) throws SQLException {
        String sql = "UPDATE competicao SET nome = ?, tipo = ? WHERE id = ?";
        Connection conn = ConexaoSQLite.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, c.getNome());
            stmt.setString(2, c.getTipo());
            stmt.setInt(3, c.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean excluir(int id) throws SQLException {
        String sql = "DELETE FROM competicao WHERE id = ?";
        Connection conn = ConexaoSQLite.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
}
