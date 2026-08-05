package com.seuclube.wsctracker.api;

import com.seuclube.wsctracker.service.ElencoService;
import io.javalin.http.Context;

import java.sql.SQLException;

public class ElencoResource {

    private final ElencoService elencoService = new ElencoService();

    public void listarAtual(Context ctx) throws SQLException {
        int temporadaId = Integer.parseInt(ctx.pathParam("temporadaId"));
        ctx.json(elencoService.listarElencoAtual(temporadaId));
    }

    public void listarTudo(Context ctx) throws SQLException {
        int temporadaId = Integer.parseInt(ctx.pathParam("temporadaId"));
        ctx.json(elencoService.listarTudo(temporadaId));
    }
}