package com.seuclube.wsctracker.api;

import com.seuclube.wsctracker.model.Titulo;
import com.seuclube.wsctracker.service.TituloService;
import io.javalin.http.Context;

import java.sql.SQLException;

public class TituloResource {

    private final TituloService tituloService = new TituloService();

    public void listarPorTime(Context ctx) throws SQLException {
        int timeId = Integer.parseInt(ctx.queryParam("timeId"));
        ctx.json(tituloService.listarPorTime(timeId));
    }

    public void criar(Context ctx) throws SQLException {
        Titulo t = ctx.bodyAsClass(Titulo.class);
        Titulo criado = tituloService.criar(t);
        ctx.status(201).json(criado);
    }
    public void atualizar(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Titulo titulo = ctx.bodyAsClass(Titulo.class);
        titulo.setId(id);
        boolean ok = tituloService.atualizar(titulo);
        if (ok) ctx.json(titulo); else ctx.status(404).result("Não encontrado");
    }

    public void excluir(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean ok = tituloService.excluir(id);
        if (ok) ctx.status(204); else ctx.status(404).result("Não encontrado");
    }

    public void buscarPorId(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Titulo t = tituloService.buscarPorId(id);
        if (t == null) {
            ctx.status(404).result("Título não encontrado");
        } else {
            ctx.json(t);
        }
    }
}