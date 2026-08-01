package com.seuclube.wsctracker.dao;

import com.seuclube.wsctracker.model.Transferencia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransferenciaDAO {

    public Transferencia salvar(Transferencia t) throws SQLException {
        String sql = "INSERT INTO transferencia (jogador_id, temporada_id, time_origem, time_destino, valor, tipo, data) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoSQLite.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, t.getJogadorId());
            stmt.setInt(2, t.getTemporadaId());
            stmt.setString(3, t.getTimeOrigem());
            stmt.setString(4, t.getTimeDestino());

            if (t.getValor() != null) {
                stmt.setDouble(5, t.getValor());
            } else {
                stmt.setNull(5, Types.REAL);
            }

            stmt.setString(6, t.getTipo());
            stmt.setString(7, t.getData());
            stmt.executeUpdate();

            try (ResultSet chaves = stmt.getGeneratedKeys()) {
                if (chaves.next()) {
                    t.setId(chaves.getInt(1));
                }
            }
        }
        return t;
    }

    public List<Transferencia> listarPorJogador(int jogadorId) throws SQLException {
        String sql = "SELECT * FROM transferencia WHERE jogador_id = ?";
        return listarComFiltro(sql, jogadorId);
    }

    public List<Transferencia> listarPorTemporada(int temporadaId) throws SQLException {
        String sql = "SELECT * FROM transferencia WHERE temporada_id = ?";
        return listarComFiltro(sql, temporadaId);
    }

    private List<Transferencia> listarComFiltro(String sql, int filtroId) throws SQLException {
        List<Transferencia> lista = new ArrayList<>();
        try (Connection conn = ConexaoSQLite.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, filtroId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        }
        return lista;
    }

    public Transferencia buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM transferencia WHERE id = ?";
        try (Connection conn = ConexaoSQLite.getConnection();
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

    private Transferencia mapear(ResultSet rs) throws SQLException {
        double valor = rs.getDouble("valor");
        Double valorObj = rs.wasNull() ? null : valor;

        return new Transferencia(
                rs.getInt("id"),
                rs.getInt("jogador_id"),
                rs.getInt("temporada_id"),
                rs.getString("time_origem"),
                rs.getString("time_destino"),
                valorObj,
                rs.getString("tipo"),
                rs.getString("data")
        );
    }
}
