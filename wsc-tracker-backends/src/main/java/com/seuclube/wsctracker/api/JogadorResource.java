package com.seuclube.wsctracker.api;

import com.seuclube.wsctracker.model.Jogador;
import com.seuclube.wsctracker.service.JogadorService;
import io.javalin.http.Context;

import java.sql.SQLException;

public class JogadorResource {

    private final JogadorService jogadorService = new JogadorService();

    public void listarTodos(Context ctx) throws SQLException {
        ctx.json(jogadorService.listarTodos());
    }

    public void criar(Context ctx) throws SQLException {
        Jogador jogador = ctx.bodyAsClass(Jogador.class);
        Jogador criado = jogadorService.criar(jogador);
        ctx.status(201).json(criado);
    }

    public void atualizar(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Jogador jogador = ctx.bodyAsClass(Jogador.class);
        jogador.setId(id);
        boolean ok = jogadorService.atualizar(jogador);
        if (ok) ctx.json(jogador); else ctx.status(404).result("Jogador não encontrado");
    }

    public void excluir(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean ok = jogadorService.excluir(id);
        if (ok) ctx.status(204); else ctx.status(404).result("Jogador não encontrado");
    }

    public void buscarPorId(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Jogador jogador = jogadorService.buscarPorId(id);
        if (jogador == null) {
            ctx.status(404).result("Jogador não encontrado");
        } else {
            ctx.json(jogador);
        }
    }

    public void carreira(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        ctx.json(jogadorService.estatisticasCarreira(id));
    }
}