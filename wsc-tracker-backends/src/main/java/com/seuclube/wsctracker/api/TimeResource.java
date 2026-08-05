package com.seuclube.wsctracker.api;

import com.seuclube.wsctracker.model.Time;
import com.seuclube.wsctracker.service.TimeService;
import io.javalin.http.Context;

import java.sql.SQLException;

public class TimeResource {

    private final TimeService timeService = new TimeService();

    public void listarTodos(Context ctx) throws SQLException {
        ctx.json(timeService.listarTodos());
    }

    public void criar(Context ctx) throws SQLException {
        Time time = ctx.bodyAsClass(Time.class);
        Time criado = timeService.criar(time);
        ctx.status(201).json(criado);
    }
    public void atualizar(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Time time = ctx.bodyAsClass(Time.class);
        time.setId(id);
        boolean ok = timeService.atualizar(time);
        if (ok) ctx.json(time); else ctx.status(404).result("Time não encontrado");
    }

    public void excluir(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean ok = timeService.excluir(id);
        if (ok) ctx.status(204); else ctx.status(404).result("Time não encontrado");
    }

    public void buscarPorId(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Time time = timeService.buscarPorId(id);
        if (time == null) {
            ctx.status(404).result("Time não encontrado");
        } else {
            ctx.json(time);
        }
    }
    public void totalJogadoresHistorico(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        ctx.json(java.util.Map.of("timeId", id, "totalJogadoresHistorico", timeService.totalJogadoresHistorico(id)));
    }

    public void estatisticasColetivas(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        ctx.json(timeService.estatisticasColetivas(id));
    }
}
