package com.seuclube.wsctracker.dao;

import com.seuclube.wsctracker.model.Jogador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JogadorDAO {

    public Jogador salvar(Jogador jogador) throws SQLException {
        String sql = "INSERT INTO jogador (nome, posicao, nacionalidade, data_nascimento, time_atual_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoSQLite.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, jogador.getNome());
            stmt.setString(2, jogador.getPosicao());
            stmt.setString(3, jogador.getNacionalidade());
            stmt.setString(4, jogador.getDataNascimento());

            if (jogador.getTimeAtualId() != null) {
                stmt.setInt(5, jogador.getTimeAtualId());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }

            stmt.executeUpdate();

            try (ResultSet chaves = stmt.getGeneratedKeys()) {
                if (chaves.next()) {
                    jogador.setId(chaves.getInt(1));
                }
            }
        }
        return jogador;
    }

    public List<Jogador> listarTodos() throws SQLException {
        String sql = "SELECT * FROM jogador";
        List<Jogador> jogadores = new ArrayList<>();

        try (Connection conn = ConexaoSQLite.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                jogadores.add(mapear(rs));
            }
        }
        return jogadores;
    }

    public Jogador buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM jogador WHERE id = ?";
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

    private Jogador mapear(ResultSet rs) throws SQLException {
        int timeAtualId = rs.getInt("time_atual_id");
        Integer timeAtualIdObj = rs.wasNull() ? null : timeAtualId;

        return new Jogador(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("posicao"),
                rs.getString("nacionalidade"),
                rs.getString("data_nascimento"),
                timeAtualIdObj
        );
    }
}
