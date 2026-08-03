package com.seuclube.wsctracker.dao;

import com.seuclube.wsctracker.model.Temporada;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TemporadaDAO {

    public Temporada salvar(Temporada temporada) throws SQLException {
        String sql = "INSERT INTO temporada (time_id, numero, divisao, posicao_final, observacoes) VALUES (?, ?, ?, ?, ?)";
        Connection conn = ConexaoSQLite.getConnection();        try (
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, temporada.getTimeId());
            stmt.setInt(2, temporada.getNumero());
            stmt.setString(3, temporada.getDivisao());

            if (temporada.getPosicaoFinal() != null) {
                stmt.setInt(4, temporada.getPosicaoFinal());
            } else {
                stmt.setNull(4, Types.INTEGER);
            }

            stmt.setString(5, temporada.getObservacoes());
            stmt.executeUpdate();

            try (ResultSet chaves = stmt.getGeneratedKeys()) {
                if (chaves.next()) {
                    temporada.setId(chaves.getInt(1));
                }
            }
        }
        return temporada;
    }

    public List<Temporada> listarTodas() throws SQLException {
        String sql = "SELECT * FROM temporada";
        List<Temporada> temporadas = new ArrayList<>();

        Connection conn = ConexaoSQLite.getConnection();        try (
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                temporadas.add(mapear(rs));
            }
        }
        return temporadas;
    }

    public List<Temporada> listarPorTime(int timeId) throws SQLException {
        String sql = "SELECT * FROM temporada WHERE time_id = ? ORDER BY numero";
        List<Temporada> temporadas = new ArrayList<>();

        Connection conn = ConexaoSQLite.getConnection();        try (
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, timeId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    temporadas.add(mapear(rs));
                }
            }
        }
        return temporadas;
    }

    public Temporada buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM temporada WHERE id = ?";
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

    private Temporada mapear(ResultSet rs) throws SQLException {
        int posicaoFinal = rs.getInt("posicao_final");
        Integer posicaoFinalObj = rs.wasNull() ? null : posicaoFinal;

        return new Temporada(
                rs.getInt("id"),
                rs.getInt("time_id"),
                rs.getInt("numero"),
                rs.getString("divisao"),
                posicaoFinalObj,
                rs.getString("observacoes")
        );
    }
    public boolean atualizar(Temporada temporada) throws SQLException {
        String sql = "UPDATE temporada SET time_id = ?, numero = ?, divisao = ?, posicao_final = ?, observacoes = ? WHERE id = ?";
        Connection conn = ConexaoSQLite.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, temporada.getTimeId());
            stmt.setInt(2, temporada.getNumero());
            stmt.setString(3, temporada.getDivisao());
            if (temporada.getPosicaoFinal() != null) {
                stmt.setInt(4, temporada.getPosicaoFinal());
            } else {
                stmt.setNull(4, Types.INTEGER);
            }
            stmt.setString(5, temporada.getObservacoes());
            stmt.setInt(6, temporada.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean excluir(int id) throws SQLException {
        String sql = "DELETE FROM temporada WHERE id = ?";
        Connection conn = ConexaoSQLite.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
}
