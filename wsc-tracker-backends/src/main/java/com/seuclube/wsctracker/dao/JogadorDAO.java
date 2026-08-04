package com.seuclube.wsctracker.dao;

import com.seuclube.wsctracker.model.Jogador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JogadorDAO {

    public Jogador salvar(Jogador jogador) throws SQLException {
        String sql = "INSERT INTO jogador (nome, posicao, nacionalidade, data_nascimento, time_atual_id, " +
                "origem_base, data_chegada_base, overall_base) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = ConexaoSQLite.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, jogador.getNome());
            stmt.setString(2, jogador.getPosicao());
            stmt.setString(3, jogador.getNacionalidade());
            stmt.setString(4, jogador.getDataNascimento());

            if (jogador.getTimeAtualId() != null) {
                stmt.setInt(5, jogador.getTimeAtualId());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }

            stmt.setInt(6, jogador.isOrigemBase() ? 1 : 0);
            stmt.setString(7, jogador.getDataChegadaBase());

            if (jogador.getOverallBase() != null) {
                stmt.setInt(8, jogador.getOverallBase());
            } else {
                stmt.setNull(8, Types.INTEGER);
            }

            stmt.executeUpdate();
            try (ResultSet chaves = stmt.getGeneratedKeys()) {
                if (chaves.next()) jogador.setId(chaves.getInt(1));
            }
        }
        return jogador;
    }

    public boolean atualizar(Jogador jogador) throws SQLException {
        String sql = "UPDATE jogador SET nome = ?, posicao = ?, nacionalidade = ?, data_nascimento = ?, " +
                "time_atual_id = ?, origem_base = ?, data_chegada_base = ?, overall_base = ? WHERE id = ?";
        Connection conn = ConexaoSQLite.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, jogador.getNome());
            stmt.setString(2, jogador.getPosicao());
            stmt.setString(3, jogador.getNacionalidade());
            stmt.setString(4, jogador.getDataNascimento());

            if (jogador.getTimeAtualId() != null) {
                stmt.setInt(5, jogador.getTimeAtualId());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }

            stmt.setInt(6, jogador.isOrigemBase() ? 1 : 0);
            stmt.setString(7, jogador.getDataChegadaBase());

            if (jogador.getOverallBase() != null) {
                stmt.setInt(8, jogador.getOverallBase());
            } else {
                stmt.setNull(8, Types.INTEGER);
            }

            stmt.setInt(9, jogador.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean excluir(int id) throws SQLException {
        String sql = "DELETE FROM jogador WHERE id = ?";
        Connection conn = ConexaoSQLite.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Jogador> listarTodos() throws SQLException {
        String sql = "SELECT * FROM jogador";
        List<Jogador> jogadores = new ArrayList<>();
        Connection conn = ConexaoSQLite.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) jogadores.add(mapear(rs));
        }
        return jogadores;
    }

    public Jogador buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM jogador WHERE id = ?";
        Connection conn = ConexaoSQLite.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    private Jogador mapear(ResultSet rs) throws SQLException {
        Jogador j = new Jogador();
        j.setId(rs.getInt("id"));
        j.setNome(rs.getString("nome"));
        j.setPosicao(rs.getString("posicao"));
        j.setNacionalidade(rs.getString("nacionalidade"));
        j.setDataNascimento(rs.getString("data_nascimento"));

        int timeAtualId = rs.getInt("time_atual_id");
        j.setTimeAtualId(rs.wasNull() ? null : timeAtualId);

        j.setOrigemBase(rs.getInt("origem_base") == 1);
        j.setDataChegadaBase(rs.getString("data_chegada_base"));

        int overallBase = rs.getInt("overall_base");
        j.setOverallBase(rs.wasNull() ? null : overallBase);

        return j;
    }
}