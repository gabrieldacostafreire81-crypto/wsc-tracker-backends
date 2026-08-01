package com.seuclube.wsctracker.api;

import com.seuclube.wsctracker.model.Competicao;
import com.seuclube.wsctracker.service.CompeticaoService;
import io.javalin.http.Context;

import java.sql.SQLException;

public class CompeticaoResource {

    private final CompeticaoService competicaoService = new CompeticaoService();

    public void listarTodas(Context ctx) throws SQLException {
        ctx.json(competicaoService.listarTodas());
    }

    public void criar(Context ctx) throws SQLException {
        Competicao c = ctx.bodyAsClass(Competicao.class);
        Competicao criada = competicaoService.criar(c);
        ctx.status(201).json(criada);
    }

    public void buscarPorId(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Competicao c = competicaoService.buscarPorId(id);
        if (c == null) {
            ctx.status(404).result("Competição não encontrada");
        } else {
            ctx.json(c);
        }
    }
}