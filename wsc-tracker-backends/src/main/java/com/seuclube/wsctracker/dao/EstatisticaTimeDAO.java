package com.seuclube.wsctracker.dao;

import com.seuclube.wsctracker.model.EstatisticaTime;

import java.sql.*;

public class EstatisticaTimeDAO {

    public EstatisticaTime salvar(EstatisticaTime e) throws SQLException {
        String sql = "INSERT INTO estatistica_time (temporada_id, vitorias, empates, derrotas, gols_feitos, gols_sofridos) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = ConexaoSQLite.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, e.getTemporadaId());
            stmt.setInt(2, e.getVitorias());
            stmt.setInt(3, e.getEmpates());
            stmt.setInt(4, e.getDerrotas());
            stmt.setInt(5, e.getGolsFeitos());
            stmt.setInt(6, e.getGolsSofridos());
            stmt.executeUpdate();
            try (ResultSet chaves = stmt.getGeneratedKeys()) {
                if (chaves.next()) e.setId(chaves.getInt(1));
            }
        }
        return e;
    }

    public boolean atualizar(EstatisticaTime e) throws SQLException {
        String sql = "UPDATE estatistica_time SET vitorias = ?, empates = ?, derrotas = ?, gols_feitos = ?, gols_sofridos = ? WHERE id = ?";
        Connection conn = ConexaoSQLite.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, e.getVitorias());
            stmt.setInt(2, e.getEmpates());
            stmt.setInt(3, e.getDerrotas());
            stmt.setInt(4, e.getGolsFeitos());
            stmt.setInt(5, e.getGolsSofridos());
            stmt.setInt(6, e.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    public EstatisticaTime buscarPorTemporada(int temporadaId) throws SQLException {
        String sql = "SELECT * FROM estatistica_time WHERE temporada_id = ?";
        Connection conn = ConexaoSQLite.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, temporadaId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    private EstatisticaTime mapear(ResultSet rs) throws SQLException {
        EstatisticaTime e = new EstatisticaTime();
        e.setId(rs.getInt("id"));
        e.setTemporadaId(rs.getInt("temporada_id"));
        e.setVitorias(rs.getInt("vitorias"));
        e.setEmpates(rs.getInt("empates"));
        e.setDerrotas(rs.getInt("derrotas"));
        e.setGolsFeitos(rs.getInt("gols_feitos"));
        e.setGolsSofridos(rs.getInt("gols_sofridos"));
        return e;
    }
}