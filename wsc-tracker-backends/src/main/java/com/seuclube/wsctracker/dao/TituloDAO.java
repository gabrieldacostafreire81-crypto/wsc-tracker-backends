package com.seuclube.wsctracker.dao;

import com.seuclube.wsctracker.model.Titulo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TituloDAO {

    public Titulo salvar(Titulo t) throws SQLException {
        String sql = "INSERT INTO titulo (time_id, temporada_id, competicao_id) VALUES (?, ?, ?)";
        Connection conn = ConexaoSQLite.getConnection();        try (
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, t.getTimeId());
            stmt.setInt(2, t.getTemporadaId());
            stmt.setInt(3, t.getCompeticaoId());
            stmt.executeUpdate();

            try (ResultSet chaves = stmt.getGeneratedKeys()) {
                if (chaves.next()) {
                    t.setId(chaves.getInt(1));
                }
            }
        }
        return t;
    }

    public List<Titulo> listarPorTime(int timeId) throws SQLException {
        String sql = "SELECT * FROM titulo WHERE time_id = ?";
        List<Titulo> lista = new ArrayList<>();

        Connection conn = ConexaoSQLite.getConnection();        try (
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, timeId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        }
        return lista;
    }

    public Titulo buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM titulo WHERE id = ?";
        Connection conn = ConexaoSQLite.getConnection();        try (
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        }
        return null;
    }

    private Titulo mapear(ResultSet rs) throws SQLException {
        return new Titulo(
                rs.getInt("id"),
                rs.getInt("time_id"),
                rs.getInt("temporada_id"),
                rs.getInt("competicao_id")
        );
    }
    public boolean atualizar(Titulo t) throws SQLException {
        String sql = "UPDATE titulo SET time_id = ?, temporada_id = ?, competicao_id = ? WHERE id = ?";
        Connection conn = ConexaoSQLite.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, t.getTimeId());
            stmt.setInt(2, t.getTemporadaId());
            stmt.setInt(3, t.getCompeticaoId());
            stmt.setInt(4, t.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean excluir(int id) throws SQLException {
        String sql = "DELETE FROM titulo WHERE id = ?";
        Connection conn = ConexaoSQLite.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
    public Titulo buscarPorTemporadaECompeticao(int temporadaId, int competicaoId) throws SQLException {
        String sql = "SELECT * FROM titulo WHERE temporada_id = ? AND competicao_id = ?";
        Connection conn = ConexaoSQLite.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, temporadaId);
            stmt.setInt(2, competicaoId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public boolean excluirPorTemporadaECompeticao(int temporadaId, int competicaoId) throws SQLException {
        String sql = "DELETE FROM titulo WHERE temporada_id = ? AND competicao_id = ?";
        Connection conn = ConexaoSQLite.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, temporadaId);
            stmt.setInt(2, competicaoId);
            return stmt.executeUpdate() > 0;
        }
    }
}
