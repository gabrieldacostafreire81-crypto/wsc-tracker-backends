package com.seuclube.wsctracker.service;

import com.seuclube.wsctracker.dao.TemporadaDAO;
import com.seuclube.wsctracker.model.Elenco;
import com.seuclube.wsctracker.model.Temporada;

import java.sql.SQLException;
import java.util.List;

public class FinalizarTemporadaService {

    private final TemporadaDAO temporadaDAO = new TemporadaDAO();
    private final ElencoService elencoService = new ElencoService();

    public Temporada finalizar(int temporadaId) throws SQLException {
        Temporada atual = temporadaDAO.buscarPorId(temporadaId);
        if (atual == null) {
            throw new IllegalArgumentException("Temporada não encontrada");
        }
        if (atual.isEncerrada()) {
            throw new IllegalStateException("Esta temporada já foi finalizada");
        }

        // 1. Cria a próxima temporada, herdando o nível de treino final
        Temporada proxima = new Temporada();
        proxima.setTimeId(atual.getTimeId());
        proxima.setNumero(atual.getNumero() + 1);
        proxima.setDivisao(atual.getDivisao()); // ponto de partida — o usuário ajusta se subiu/caiu de divisão
        proxima.setNivelTreino(atual.getNivelTreino());
        Temporada proximaCriada = temporadaDAO.salvar(proxima);

        // 2. Herda o elenco atual da temporada encerrada como elenco inicial+atual da nova
        List<Elenco> elencoAtivo = elencoService.listarElencoAtual(temporadaId);
        for (Elenco vinculo : elencoAtivo) {
            elencoService.herdarElenco(proximaCriada.getId(), vinculo.getJogadorId());
        }

        // 3. Trava a temporada encerrada
        temporadaDAO.marcarEncerrada(temporadaId);

        return proximaCriada;
    }
}