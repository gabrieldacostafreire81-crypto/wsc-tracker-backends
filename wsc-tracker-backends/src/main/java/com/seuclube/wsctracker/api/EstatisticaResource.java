package com.seuclube.wsctracker.api;

import com.seuclube.wsctracker.model.EstatisticaJogadorTemporada;
import com.seuclube.wsctracker.service.EstatisticaService;
import io.javalin.http.Context;

import java.sql.SQLException;

public class EstatisticaResource {

    private final EstatisticaService estatisticaService = new EstatisticaService();

    public void listar(Context ctx) throws SQLException {
        String temporadaIdParam = ctx.queryParam("temporadaId");
        String jogadorIdParam = ctx.queryParam("jogadorId");

        if (temporadaIdParam != null) {
            ctx.json(estatisticaService.listarPorTemporada(Integer.parseInt(temporadaIdParam)));
        } else if (jogadorIdParam != null) {
            ctx.json(estatisticaService.listarPorJogador(Integer.parseInt(jogadorIdParam)));
        } else {
            ctx.status(400).result("Informe temporadaId ou jogadorId como query parameter");
        }
    }

    public void registrar(Context ctx) throws SQLException {
        EstatisticaJogadorTemporada estatistica = ctx.bodyAsClass(EstatisticaJogadorTemporada.class);
        EstatisticaJogadorTemporada criada = estatisticaService.registrar(estatistica);
        ctx.status(201).json(criada);
    }

    public void buscarPorId(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        EstatisticaJogadorTemporada estatistica = estatisticaService.buscarPorId(id);
        if (estatistica == null) {
            ctx.status(404).result("Estatística não encontrada");
        } else {
            ctx.json(estatistica);
        }
    }
}