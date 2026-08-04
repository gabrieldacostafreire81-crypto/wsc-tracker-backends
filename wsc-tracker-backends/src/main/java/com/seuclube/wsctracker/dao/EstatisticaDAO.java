package com.seuclube.wsctracker.dao;

import com.seuclube.wsctracker.model.EstatisticaJogadorTemporada;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstatisticaDAO {

    public EstatisticaJogadorTemporada salvar(EstatisticaJogadorTemporada e) throws SQLException {
        String sql = "INSERT INTO estatistica_jogador_temporada " +
                "(jogador_id, temporada_id, jogos, gols, assistencias, nota_media, " +
                "cartoes_amarelos, cartoes_vermelhos, valor_mercado, status, overall) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = ConexaoSQLite.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, e.getJogadorId());
            stmt.setInt(2, e.getTemporadaId());
            stmt.setInt(3, e.getJogos());
            stmt.setInt(4, e.getGols());
            stmt.setInt(5, e.getAssistencias());
            setDoubleOuNull(stmt, 6, e.getNotaMedia());
            stmt.setInt(7, e.getCartoesAmarelos());
            stmt.setInt(8, e.getCartoesVermelhos());
            setDoubleOuNull(stmt, 9, e.getValorMercado());
            stmt.setString(10, e.getStatus());
            setIntOuNull(stmt, 11, e.getOverall());

            stmt.executeUpdate();

            try (ResultSet chaves = stmt.getGeneratedKeys()) {
                if (chaves.next()) {
                    e.setId(chaves.getInt(1));
                }
            }
        }
        return e;
    }

    public boolean atualizar(EstatisticaJogadorTemporada e) throws SQLException {
        String sql = "UPDATE estatistica_jogador_temporada SET jogador_id = ?, temporada_id = ?, jogos = ?, gols = ?, " +
                "assistencias = ?, nota_media = ?, cartoes_amarelos = ?, cartoes_vermelhos = ?, valor_mercado = ?, " +
                "status = ?, overall = ? WHERE id = ?";
        Connection conn = ConexaoSQLite.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, e.getJogadorId());
            stmt.setInt(2, e.getTemporadaId());
            stmt.setInt(3, e.getJogos());
            stmt.setInt(4, e.getGols());
            stmt.setInt(5, e.getAssistencias());
            setDoubleOuNull(stmt, 6, e.getNotaMedia());
            stmt.setInt(7, e.getCartoesAmarelos());
            stmt.setInt(8, e.getCartoesVermelhos());
            setDoubleOuNull(stmt, 9, e.getValorMercado());
            stmt.setString(10, e.getStatus());
            setIntOuNull(stmt, 11, e.getOverall());
            stmt.setInt(12, e.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean excluir(int id) throws SQLException {
        String sql = "DELETE FROM estatistica_jogador_temporada WHERE id = ?";
        Connection conn = ConexaoSQLite.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    /** Todas as estatísticas de UM jogador em UMA temporada específica. */
    public List<EstatisticaJogadorTemporada> listarPorTemporada(int temporadaId) throws SQLException {
        String sql = "SELECT * FROM estatistica_jogador_temporada WHERE temporada_id = ?";
        return listarComFiltro(sql, temporadaId);
    }

    /** Todas as estatísticas de UM jogador, em TODAS as temporadas (carreira dele no clube — base do RF14). */
    public List<EstatisticaJogadorTemporada> listarPorJogador(int jogadorId) throws SQLException {
        String sql = "SELECT * FROM estatistica_jogador_temporada WHERE jogador_id = ?";
        return listarComFiltro(sql, jogadorId);
    }

    /** Todas as estatísticas de TODOS os jogadores, em TODAS as temporadas de UM time — base do RF15. */
    public List<EstatisticaJogadorTemporada> listarPorTime(int timeId) throws SQLException {
        String sql = "SELECT e.* FROM estatistica_jogador_temporada e " +
                "JOIN temporada t ON e.temporada_id = t.id " +
                "WHERE t.time_id = ?";

        List<EstatisticaJogadorTemporada> lista = new ArrayList<>();
        Connection conn = ConexaoSQLite.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, timeId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        }
        return lista;
    }

    private List<EstatisticaJogadorTemporada> listarComFiltro(String sql, int filtroId) throws SQLException {
        List<EstatisticaJogadorTemporada> lista = new ArrayList<>();
        Connection conn = ConexaoSQLite.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, filtroId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        }
        return lista;
    }

    public EstatisticaJogadorTemporada buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM estatistica_jogador_temporada WHERE id = ?";
        Connection conn = ConexaoSQLite.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        }
        return null;
    }

    private void setDoubleOuNull(PreparedStatement stmt, int index, Double valor) throws SQLException {
        if (valor != null) {
            stmt.setDouble(index, valor);
        } else {
            stmt.setNull(index, Types.REAL);
        }
    }

    private void setIntOuNull(PreparedStatement stmt, int index, Integer valor) throws SQLException {
        if (valor != null) {
            stmt.setInt(index, valor);
        } else {
            stmt.setNull(index, Types.INTEGER);
        }
    }

    private EstatisticaJogadorTemporada mapear(ResultSet rs) throws SQLException {
        EstatisticaJogadorTemporada e = new EstatisticaJogadorTemporada();
        e.setId(rs.getInt("id"));
        e.setJogadorId(rs.getInt("jogador_id"));
        e.setTemporadaId(rs.getInt("temporada_id"));
        e.setJogos(rs.getInt("jogos"));
        e.setGols(rs.getInt("gols"));
        e.setAssistencias(rs.getInt("assistencias"));

        double notaMedia = rs.getDouble("nota_media");
        e.setNotaMedia(rs.wasNull() ? null : notaMedia);

        e.setCartoesAmarelos(rs.getInt("cartoes_amarelos"));
        e.setCartoesVermelhos(rs.getInt("cartoes_vermelhos"));

        double valorMercado = rs.getDouble("valor_mercado");
        e.setValorMercado(rs.wasNull() ? null : valorMercado);

        e.setStatus(rs.getString("status"));

        int overall = rs.getInt("overall");
        e.setOverall(rs.wasNull() ? null : overall);

        return e;
    }
}