package com.seuclube.wsctracker.service;

import com.seuclube.wsctracker.dao.EstatisticaDAO;
import com.seuclube.wsctracker.dao.JogadorDAO;
import com.seuclube.wsctracker.dao.TemporadaDAO;
import com.seuclube.wsctracker.dao.TimeDAO;
import com.seuclube.wsctracker.model.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeService {

    private final TimeDAO timeDAO = new TimeDAO();
    private final TemporadaDAO temporadaDAO = new TemporadaDAO();
    private final EstatisticaDAO estatisticaDAO = new EstatisticaDAO();
    private final JogadorDAO jogadorDAO = new JogadorDAO();

    public Time criar(Time time) throws SQLException {
        return timeDAO.salvar(time);
    }

    public List<Time> listarTodos() throws SQLException {
        return timeDAO.listarTodos();
    }

    public Time buscarPorId(int id) throws SQLException {
        return timeDAO.buscarPorId(id);
    }
    public boolean atualizar(Time time) throws SQLException {
        return timeDAO.atualizar(time);
    }

    public boolean excluir(int id) throws SQLException {
        return timeDAO.excluir(id);
    }

    /** RF12 — total de jogadores distintos que já passaram pelo clube. */
    public int totalJogadoresHistorico(int timeId) throws SQLException {
        List<EstatisticaJogadorTemporada> registros = estatisticaDAO.listarPorTime(timeId);
        return (int) registros.stream()
                .map(EstatisticaJogadorTemporada::getJogadorId)
                .distinct()
                .count();
    }

    /** RF15 — estatísticas coletivas do time inteiro, somando todas as temporadas. */
    public EstatisticasColetivasTime estatisticasColetivas(int timeId) throws SQLException {
        List<EstatisticaJogadorTemporada> registros = estatisticaDAO.listarPorTime(timeId);

        EstatisticasColetivasTime resultado = new EstatisticasColetivasTime();
        resultado.setTimeId(timeId);

        if (registros.isEmpty()) {
            return resultado; // time sem nenhuma estatística registrada ainda
        }

        // Passo 1: agrupar os registros por jogador, somando gols/jogos/notas de cada um
        Map<Integer, int[]> totaisPorJogador = new HashMap<>(); // [gols, jogos]
        Map<Integer, double[]> notasPorJogador = new HashMap<>(); // [somaNotas, quantidade]

        int somaGolsClube = 0;
        int somaJogosClube = 0;

        for (EstatisticaJogadorTemporada r : registros) {
            somaGolsClube += r.getGols();
            somaJogosClube += r.getJogos();

            int[] totais = totaisPorJogador.computeIfAbsent(r.getJogadorId(), k -> new int[2]);
            totais[0] += r.getGols();
            totais[1] += r.getJogos();

            if (r.getNotaMedia() != null) {
                double[] notas = notasPorJogador.computeIfAbsent(r.getJogadorId(), k -> new double[2]);
                notas[0] += r.getNotaMedia();
                notas[1] += 1;
            }
        }

        resultado.setTotalGolsClube(somaGolsClube);
        resultado.setTotalJogosClube(somaJogosClube);
        resultado.setTotalJogadoresDistintos(totaisPorJogador.size());

        // Passo 2: descobrir o artilheiro (mais gols somados) e quem mais jogou
        int melhorGols = -1, melhorJogos = -1;
        Integer artilheiroId = null, maisJogosId = null;

        for (Map.Entry<Integer, int[]> entry : totaisPorJogador.entrySet()) {
            int gols = entry.getValue()[0];
            int jogos = entry.getValue()[1];

            if (gols > melhorGols) {
                melhorGols = gols;
                artilheiroId = entry.getKey();
            }
            if (jogos > melhorJogos) {
                melhorJogos = jogos;
                maisJogosId = entry.getKey();
            }
        }

        // Passo 3: descobrir quem tem a maior nota média histórica
        Double melhorNota = null;
        Integer melhorNotaId = null;

        for (Map.Entry<Integer, double[]> entry : notasPorJogador.entrySet()) {
            double media = entry.getValue()[0] / entry.getValue()[1];
            if (melhorNota == null || media > melhorNota) {
                melhorNota = media;
                melhorNotaId = entry.getKey();
            }
        }

        // Passo 4: buscar os nomes desses jogadores (só agora, pra não consultar o banco à toa)
        if (artilheiroId != null) {
            resultado.setArtilheiroJogadorId(artilheiroId);
            resultado.setArtilheiroGols(melhorGols);
            Jogador j = jogadorDAO.buscarPorId(artilheiroId);
            if (j != null) resultado.setArtilheiroNome(j.getNome());
        }

        if (maisJogosId != null) {
            resultado.setMaisJogosJogadorId(maisJogosId);
            resultado.setMaisJogosQuantidade(melhorJogos);
            Jogador j = jogadorDAO.buscarPorId(maisJogosId);
            if (j != null) resultado.setMaisJogosNome(j.getNome());
        }

        if (melhorNotaId != null) {
            resultado.setMaiorNotaMediaJogadorId(melhorNotaId);
            resultado.setMaiorNotaMediaValor(melhorNota);
            Jogador j = jogadorDAO.buscarPorId(melhorNotaId);
            if (j != null) resultado.setMaiorNotaMediaNome(j.getNome());
        }

        return resultado;
    }
}
