package com.seuclube.wsctracker.service;

import com.seuclube.wsctracker.dao.JogadorDAO;
import com.seuclube.wsctracker.dao.TemporadaDAO;
import com.seuclube.wsctracker.dao.TransferenciaDAO;
import com.seuclube.wsctracker.model.Jogador;
import com.seuclube.wsctracker.model.Temporada;
import com.seuclube.wsctracker.model.Transferencia;

import java.sql.SQLException;
import java.util.List;

public class TransferenciaService {

    private final TransferenciaDAO transferenciaDAO = new TransferenciaDAO();
    private final JogadorDAO jogadorDAO = new JogadorDAO();
    private final TemporadaDAO temporadaDAO = new TemporadaDAO();
    private final ElencoService elencoService = new ElencoService();

    public Transferencia criar(Transferencia t) throws SQLException {
        // RF23 — compra sem o jogador existir ainda: cria o jogador primeiro
        if (t.getJogadorId() == 0 && t.getJogadorNomeNovo() != null && !t.getJogadorNomeNovo().isBlank()) {
            Jogador novo = new Jogador();
            novo.setNome(t.getJogadorNomeNovo());
            novo.setPosicao(t.getJogadorPosicaoNovo());
            Jogador criado = jogadorDAO.salvar(novo);
            t.setJogadorId(criado.getId());
        }

        Transferencia salva = transferenciaDAO.salvar(t);
        aplicarEfeitoNoElenco(t);
        return salva;
    }

    /** RF23/RF24 — reflete a transferência no elenco atual e no time do jogador. */
    private void aplicarEfeitoNoElenco(Transferencia t) throws SQLException {
        Temporada temporada = temporadaDAO.buscarPorId(t.getTemporadaId());
        if (temporada == null) return;

        Jogador jogador = jogadorDAO.buscarPorId(t.getJogadorId());
        if (jogador == null) return;

        if ("compra".equalsIgnoreCase(t.getTipo())) {
            elencoService.adicionarAoElencoAtual(t.getTemporadaId(), t.getJogadorId(), "compra");
            jogador.setTimeAtualId(temporada.getTimeId());
            jogadorDAO.atualizar(jogador);

        } else if ("venda".equalsIgnoreCase(t.getTipo())) {
            elencoService.removerDoElencoAtual(t.getTemporadaId(), t.getJogadorId(), "venda");
            jogador.setTimeAtualId(null);
            jogadorDAO.atualizar(jogador);
        }
        // "emprestimo" fica de fora por enquanto — vamos tratar isso com calma numa próxima rodada,
        // já que empréstimo tem uma regra própria (o jogador sai, mas volta depois).
    }

    public boolean atualizar(Transferencia t) throws SQLException {
        boolean ok = transferenciaDAO.atualizar(t);
        if (ok) {
            aplicarEfeitoNoElenco(t);
        }
        return ok;
    }

    public boolean excluir(int id) throws SQLException {
        return transferenciaDAO.excluir(id);
    }

    public List<Transferencia> listarPorJogador(int jogadorId) throws SQLException {
        return transferenciaDAO.listarPorJogador(jogadorId);
    }

    public List<Transferencia> listarPorTemporada(int temporadaId) throws SQLException {
        return transferenciaDAO.listarPorTemporada(temporadaId);
    }

    public Transferencia buscarPorId(int id) throws SQLException {
        return transferenciaDAO.buscarPorId(id);
    }
}
