package com.seuclube.wsctracker.dao;

import com.seuclube.wsctracker.model.TemporadaCompeticao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TemporadaCompeticaoDAO {

    public TemporadaCompeticao salvar(TemporadaCompeticao tc) throws SQLException {
        String sql = "INSERT INTO temporada_competicao (temporada_id, competicao_id, resultado_posicao, resultado_fase) " +
                "VALUES (?, ?, ?, ?)";
        Connection conn = ConexaoSQLite.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, tc.getTemporadaId());
            stmt.setInt(2, tc.getCompeticaoId());
            setIntOuNull(stmt, 3, tc.getResultadoPosicao());
            stmt.setString(4, tc.getResultadoFase());
            stmt.executeUpdate();
            try (ResultSet chaves = stmt.getGeneratedKeys()) {
                if (chaves.next()) tc.setId(chaves.getInt(1));
            }
        }
        return tc;
    }

    public boolean atualizarResultado(TemporadaCompeticao tc) throws SQLException {
        String sql = "UPDATE temporada_competicao SET resultado_posicao = ?, resultado_fase = ? WHERE id = ?";
        Connection conn = ConexaoSQLite.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            setIntOuNull(stmt, 1, tc.getResultadoPosicao());
            stmt.setString(2, tc.getResultadoFase());
            stmt.setInt(3, tc.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean excluir(int id) throws SQLException {
        String sql = "DELETE FROM temporada_competicao WHERE id = ?";
        Connection conn = ConexaoSQLite.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<TemporadaCompeticao> listarPorTemporada(int temporadaId) throws SQLException {
        String sql = "SELECT * FROM temporada_competicao WHERE temporada_id = ?";
        List<TemporadaCompeticao> lista = new ArrayList<>();
        Connection conn = ConexaoSQLite.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, temporadaId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }

    public TemporadaCompeticao buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM temporada_competicao WHERE id = ?";
        Connection conn = ConexaoSQLite.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    private void setIntOuNull(PreparedStatement stmt, int index, Integer valor) throws SQLException {
        if (valor != null) stmt.setInt(index, valor);
        else stmt.setNull(index, Types.INTEGER);
    }

    private TemporadaCompeticao mapear(ResultSet rs) throws SQLException {
        TemporadaCompeticao tc = new TemporadaCompeticao();
        tc.setId(rs.getInt("id"));
        tc.setTemporadaId(rs.getInt("temporada_id"));
        tc.setCompeticaoId(rs.getInt("competicao_id"));

        int posicao = rs.getInt("resultado_posicao");
        tc.setResultadoPosicao(rs.wasNull() ? null : posicao);

        tc.setResultadoFase(rs.getString("resultado_fase"));
        return tc;
    }
}