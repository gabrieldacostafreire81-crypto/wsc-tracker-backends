package com.seuclube.wsctracker.dao;

import com.seuclube.wsctracker.model.Elenco;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ElencoDAO {

    public Elenco salvar(Elenco e) throws SQLException {
        String sql = "INSERT INTO elenco (temporada_id, jogador_id, status, origem_entrada, data_entrada, motivo_saida, data_saida) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection conn = ConexaoSQLite.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, e.getTemporadaId());
            stmt.setInt(2, e.getJogadorId());
            stmt.setString(3, e.getStatus());
            stmt.setString(4, e.getOrigemEntrada());
            stmt.setString(5, e.getDataEntrada());
            stmt.setString(6, e.getMotivoSaida());
            stmt.setString(7, e.getDataSaida());
            stmt.executeUpdate();
            try (ResultSet chaves = stmt.getGeneratedKeys()) {
                if (chaves.next()) e.setId(chaves.getInt(1));
            }
        }
        return e;
    }

    public boolean atualizar(Elenco e) throws SQLException {
        String sql = "UPDATE elenco SET status = ?, motivo_saida = ?, data_saida = ? WHERE id = ?";
        Connection conn = ConexaoSQLite.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, e.getStatus());
            stmt.setString(2, e.getMotivoSaida());
            stmt.setString(3, e.getDataSaida());
            stmt.setInt(4, e.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    /** Elenco atual de uma temporada — só quem está ativo (sem motivo de saída registrado). */
    public List<Elenco> listarAtivoPorTemporada(int temporadaId) throws SQLException {
        String sql = "SELECT * FROM elenco WHERE temporada_id = ? AND status = 'atual' AND motivo_saida IS NULL";
        return listarComFiltro(sql, temporadaId);
    }

    public List<Elenco> listarPorTemporada(int temporadaId) throws SQLException {
        String sql = "SELECT * FROM elenco WHERE temporada_id = ?";
        return listarComFiltro(sql, temporadaId);
    }

    private List<Elenco> listarComFiltro(String sql, int temporadaId) throws SQLException {
        List<Elenco> lista = new ArrayList<>();
        Connection conn = ConexaoSQLite.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, temporadaId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }

    /** Encontra o registro ativo de um jogador específico numa temporada (usado na venda). */
    public Elenco buscarAtivoPorJogadorETemporada(int jogadorId, int temporadaId) throws SQLException {
        String sql = "SELECT * FROM elenco WHERE jogador_id = ? AND temporada_id = ? AND motivo_saida IS NULL";
        Connection conn = ConexaoSQLite.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, jogadorId);
            stmt.setInt(2, temporadaId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    private Elenco mapear(ResultSet rs) throws SQLException {
        return new Elenco(
                rs.getInt("id"), rs.getInt("temporada_id"), rs.getInt("jogador_id"),
                rs.getString("status"), rs.getString("origem_entrada"),
                rs.getString("data_entrada"), rs.getString("motivo_saida"), rs.getString("data_saida")
        );
    }
}