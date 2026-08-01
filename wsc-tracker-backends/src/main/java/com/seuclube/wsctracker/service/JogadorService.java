package com.seuclube.wsctracker.service;

import com.seuclube.wsctracker.dao.EstatisticaDAO;
import com.seuclube.wsctracker.dao.JogadorDAO;
import com.seuclube.wsctracker.model.EstatisticaJogadorTemporada;
import com.seuclube.wsctracker.model.EstatisticasCarreiraJogador;
import com.seuclube.wsctracker.model.Jogador;

import java.sql.SQLException;
import java.util.List;

public class JogadorService {

    private final JogadorDAO jogadorDAO = new JogadorDAO();
    private final EstatisticaDAO estatisticaDAO = new EstatisticaDAO();

    public Jogador criar(Jogador jogador) throws SQLException {
        return jogadorDAO.salvar(jogador);
    }

    public List<Jogador> listarTodos() throws SQLException {
        return jogadorDAO.listarTodos();
    }

    public Jogador buscarPorId(int id) throws SQLException {
        return jogadorDAO.buscarPorId(id);
    }

    /** RF14 — estatísticas agregadas da carreira do jogador no clube, somando todas as temporadas. */
    public EstatisticasCarreiraJogador estatisticasCarreira(int jogadorId) throws SQLException {
        List<EstatisticaJogadorTemporada> registros = estatisticaDAO.listarPorJogador(jogadorId);

        EstatisticasCarreiraJogador resultado = new EstatisticasCarreiraJogador();
        resultado.setJogadorId(jogadorId);
        resultado.setTotalTemporadas(registros.size());

        if (registros.isEmpty()) {
            return resultado; // jogador sem estatísticas ainda — devolve tudo zerado
        }

        int somaJogos = 0, somaGols = 0, somaAssistencias = 0;
        int somaAmarelos = 0, somaVermelhos = 0;
        double somaNotas = 0;
        int quantidadeComNota = 0;

        Integer melhorTemporadaId = null;
        int maiorGolsTemporada = -1;

        for (EstatisticaJogadorTemporada r : registros) {
            somaJogos += r.getJogos();
            somaGols += r.getGols();
            somaAssistencias += r.getAssistencias();
            somaAmarelos += r.getCartoesAmarelos();
            somaVermelhos += r.getCartoesVermelhos();

            if (r.getNotaMedia() != null) {
                somaNotas += r.getNotaMedia();
                quantidadeComNota++;
            }

            if (r.getGols() > maiorGolsTemporada) {
                maiorGolsTemporada = r.getGols();
                melhorTemporadaId = r.getTemporadaId();
            }
        }

        resultado.setTotalJogos(somaJogos);
        resultado.setTotalGols(somaGols);
        resultado.setTotalAssistencias(somaAssistencias);
        resultado.setTotalCartoesAmarelos(somaAmarelos);
        resultado.setTotalCartoesVermelhos(somaVermelhos);
        resultado.setMelhorTemporadaId(melhorTemporadaId);
        resultado.setGolsNaMelhorTemporada(maiorGolsTemporada);

        if (quantidadeComNota > 0) {
            resultado.setNotaMediaGeral(somaNotas / quantidadeComNota);
        }

        return resultado;
    }
}