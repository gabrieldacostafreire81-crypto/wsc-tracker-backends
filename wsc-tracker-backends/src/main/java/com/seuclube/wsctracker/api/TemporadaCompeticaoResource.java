package com.seuclube.wsctracker.api;

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
        int temporadaId = paraInt(body.get("temporadaId"));
        int competicaoId = paraInt(body.get("competicaoId"));
        try {
            ctx.status(201).json(service.adicionarCompeticao(temporadaId, competicaoId));
        } catch (IllegalStateException e) {
            ctx.status(409).result(e.getMessage());
        }
    }

    public void registrarResultado(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Map<String, Object> body = ctx.bodyAsClass(Map.class);

        Integer resultadoPosicao = paraIntOuNull(body.get("resultadoPosicao"));
        String resultadoFase = (String) body.get("resultadoFase");

        boolean ok = service.registrarResultado(id, resultadoPosicao, resultadoFase);
        if (ok) ctx.status(204); else ctx.status(404).result("Não encontrado");
    }

    public void excluir(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean ok = service.excluir(id);
        if (ok) ctx.status(204); else ctx.status(404).result("Não encontrado");
    }

    /** Converte com segurança, aceitando Integer, Long ou Double — o que o Jackson mandar. */
    private int paraInt(Object valor) {
        if (valor instanceof Number) return ((Number) valor).intValue();
        throw new IllegalArgumentException("Esperava um número, recebi: " + valor);
    }

    private Integer paraIntOuNull(Object valor) {
        if (valor == null) return null;
        if (valor instanceof Number) return ((Number) valor).intValue();
        return null;
    }
}