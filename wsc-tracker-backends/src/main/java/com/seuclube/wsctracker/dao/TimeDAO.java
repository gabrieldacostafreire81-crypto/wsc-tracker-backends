package com.seuclube.wsctracker.dao;

import com.seuclube.wsctracker.model.Time;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TimeDAO {

    public Time salvar(Time time) throws SQLException {
        String sql = "INSERT INTO time (nome, temporada_fundacao, observacoes) VALUES (?, ?, ?)";
        try (Connection conn = ConexaoSQLite.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, time.getNome());
            stmt.setInt(2, time.getTemporadaFundacao());
            stmt.setString(3, time.getObservacoes());
            stmt.executeUpdate();

            try (ResultSet chaves = stmt.getGeneratedKeys()) {
                if (chaves.next()) {
                    time.setId(chaves.getInt(1));
                }
            }
        }
        return time;
    }

    public List<Time> listarTodos() throws SQLException {
        String sql = "SELECT * FROM time";
        List<Time> times = new ArrayList<>();

        try (Connection conn = ConexaoSQLite.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                times.add(mapear(rs));
            }
        }
        return times;
    }

    public Time buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM time WHERE id = ?";
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

    private Time mapear(ResultSet rs) throws SQLException {
        return new Time(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getInt("temporada_fundacao"),
                rs.getString("observacoes")
        );
    }
}
