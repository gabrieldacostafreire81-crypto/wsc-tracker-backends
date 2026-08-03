package com.seuclube.wsctracker.api;

import com.seuclube.wsctracker.model.Temporada;
import com.seuclube.wsctracker.service.TemporadaService;
import io.javalin.http.Context;

import java.sql.SQLException;

public class TemporadaResource {

    private final TemporadaService temporadaService = new TemporadaService();

    public void listar(Context ctx) throws SQLException {
        String timeIdParam = ctx.queryParam("timeId");

        if (timeIdParam != null) {
            int timeId = Integer.parseInt(timeIdParam);
            ctx.json(temporadaService.listarPorTime(timeId));
        } else {
            ctx.json(temporadaService.listarTodas());
        }
    }

    public void criar(Context ctx) throws SQLException {
        Temporada temporada = ctx.bodyAsClass(Temporada.class);
        Temporada criada = temporadaService.criar(temporada);
        ctx.status(201).json(criada);
    }
    public void atualizar(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Temporada temporada = ctx.bodyAsClass(Temporada.class);
        temporada.setId(id);
        boolean ok = temporadaService.atualizar(temporada);
        if (ok) ctx.json(temporada); else ctx.status(404).result("Não encontrado");
    }

    public void excluir(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean ok = temporadaService.excluir(id);
        if (ok) ctx.status(204); else ctx.status(404).result("Não encontrado");
    }

    public void buscarPorId(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Temporada temporada = temporadaService.buscarPorId(id);
        if (temporada == null) {
            ctx.status(404).result("Temporada não encontrada");
        } else {
            ctx.json(temporada);
        }
    }
}