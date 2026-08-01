package com.seuclube.wsctracker.api;

import com.seuclube.wsctracker.model.Transferencia;
import com.seuclube.wsctracker.service.TransferenciaService;
import io.javalin.http.Context;

import java.sql.SQLException;

public class TransferenciaResource {

    private final TransferenciaService transferenciaService = new TransferenciaService();

    public void listar(Context ctx) throws SQLException {
        String jogadorIdParam = ctx.queryParam("jogadorId");
        String temporadaIdParam = ctx.queryParam("temporadaId");

        if (jogadorIdParam != null) {
            ctx.json(transferenciaService.listarPorJogador(Integer.parseInt(jogadorIdParam)));
        } else if (temporadaIdParam != null) {
            ctx.json(transferenciaService.listarPorTemporada(Integer.parseInt(temporadaIdParam)));
        } else {
            ctx.status(400).result("Informe jogadorId ou temporadaId como query parameter");
        }
    }

    public void criar(Context ctx) throws SQLException {
        Transferencia t = ctx.bodyAsClass(Transferencia.class);
        Transferencia criada = transferenciaService.criar(t);
        ctx.status(201).json(criada);
    }

    public void buscarPorId(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Transferencia t = transferenciaService.buscarPorId(id);
        if (t == null) {
            ctx.status(404).result("Transferência não encontrada");
        } else {
            ctx.json(t);
        }
    }
    private final com.seuclube.wsctracker.service.FinanceiroService financeiroService =
            new com.seuclube.wsctracker.service.FinanceiroService();

    public void fluxoFinanceiro(Context ctx) throws SQLException {
        int timeId = Integer.parseInt(ctx.pathParam("id"));
        ctx.json(financeiroService.fluxoPorTime(timeId));
    }
}