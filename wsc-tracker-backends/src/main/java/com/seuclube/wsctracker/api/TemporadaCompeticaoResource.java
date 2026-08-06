package com.seuclube.wsctracker.api;

import com.seuclube.wsctracker.model.Competicao;
import com.seuclube.wsctracker.service.TemporadaCompeticaoService;
import io.javalin.http.Context;

import java.sql.SQLException;
import java.util.Map;

public class TemporadaCompeticaoResource {

    private final TemporadaCompeticaoService service = new TemporadaCompeticaoService();

    public void listarPorTemporada(Context ctx) throws SQLException {
        int temporadaId = Integer.parseInt(ctx.pathParam("temporadaId"));
        ctx.json(service.listarPorTemporada(temporadaId));
    }

    public void adicionar(Context ctx) throws SQLException {
        Map<String, Object> body = ctx.bodyAsClass(Map.class);
        int temporadaId = (int) (double) (Double) body.get("temporadaId");
        int competicaoId = (int) (double) (Double) body.get("competicaoId");
        ctx.status(201).json(service.adicionarCompeticao(temporadaId, competicaoId));
    }

    public void registrarResultado(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Map<String, Object> body = ctx.bodyAsClass(Map.class);

        Integer resultadoPosicao = body.get("resultadoPosicao") != null
                ? (int) (double) (Double) body.get("resultadoPosicao") : null;
        String resultadoFase = (String) body.get("resultadoFase");

        boolean ok = service.registrarResultado(id, resultadoPosicao, resultadoFase);
        if (ok) ctx.status(204); else ctx.status(404).result("Não encontrado");
    }

    public void excluir(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean ok = service.excluir(id);
        if (ok) ctx.status(204); else ctx.status(404).result("Não encontrado");
    }
}