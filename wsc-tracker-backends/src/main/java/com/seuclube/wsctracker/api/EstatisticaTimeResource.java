package com.seuclube.wsctracker.api;

import com.seuclube.wsctracker.model.EstatisticaTime;
import com.seuclube.wsctracker.service.EstatisticaTimeService;
import io.javalin.http.Context;

import java.sql.SQLException;

public class EstatisticaTimeResource {

    private final EstatisticaTimeService service = new EstatisticaTimeService();

    public void buscar(Context ctx) throws SQLException {
        int temporadaId = Integer.parseInt(ctx.pathParam("temporadaId"));
        ctx.json(service.buscarComCalculos(temporadaId));
    }

    public void salvar(Context ctx) throws SQLException {
        int temporadaId = Integer.parseInt(ctx.pathParam("temporadaId"));
        EstatisticaTime dados = ctx.bodyAsClass(EstatisticaTime.class);
        EstatisticaTime salvo = service.salvarOuAtualizar(temporadaId, dados);
        ctx.json(salvo);
    }
}