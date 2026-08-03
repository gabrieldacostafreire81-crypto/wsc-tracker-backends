package com.seuclube.wsctracker.dao;

import com.seuclube.wsctracker.model.Transferencia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransferenciaDAO {

    public Transferencia salvar(Transferencia t) throws SQLException {
        String sql = "INSERT INTO transferencia (jogador_id, temporada_id, time_origem, time_destino, valor, tipo, data) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection conn = ConexaoSQLite.getConnection();        try (
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
        Connection conn = ConexaoSQLite.getConnection();        try (
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
    /** Todas as transferências de todas as temporadas de UM time — base do RF13. */
    public List<Transferencia> listarPorTimeCompleto(int timeId) throws SQLException {
        String sql = "SELECT tr.* FROM transferencia tr " +
                "JOIN temporada t ON tr.temporada_id = t.id " +
                "WHERE t.time_id = ?";

        List<Transferencia> lista = new ArrayList<>();
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
    public boolean atualizar(Transferencia t) throws SQLException {
        String sql = "UPDATE transferencia SET jogador_id = ?, temporada_id = ?, time_origem = ?, time_destino = ?, " +
                "valor = ?, tipo = ?, data = ? WHERE id = ?";
        Connection conn = ConexaoSQLite.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
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
            stmt.setInt(8, t.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean excluir(int id) throws SQLException {
        String sql = "DELETE FROM transferencia WHERE id = ?";
        Connection conn = ConexaoSQLite.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
}
