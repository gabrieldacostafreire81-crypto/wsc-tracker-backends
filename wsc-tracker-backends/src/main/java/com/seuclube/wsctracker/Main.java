package com.seuclube.wsctracker;

import io.javalin.Javalin;
import com.seuclube.wsctracker.dao.ConexaoSQLite;
import com.seuclube.wsctracker.api.TimeResource;
import com.seuclube.wsctracker.api.JogadorResource;
import com.seuclube.wsctracker.api.TemporadaResource;
import com.seuclube.wsctracker.api.EstatisticaResource;

public class Main {
    public static void main(String[] args) {
        ConexaoSQLite.testarConexao();
        ConexaoSQLite.executarSchema();

        TimeResource timeResource = new TimeResource();
        JogadorResource jogadorResource = new JogadorResource();
        TemporadaResource temporadaResource = new TemporadaResource();
        EstatisticaResource estatisticaResource = new EstatisticaResource();

        Javalin app = Javalin.create(config -> {
            config.routes.get("/api/status", ctx -> ctx.result("WSC Tracker backend no ar!"));

            config.routes.get("/api/times", timeResource::listarTodos);
            config.routes.post("/api/times", timeResource::criar);
            config.routes.get("/api/times/{id}", timeResource::buscarPorId);

            config.routes.get("/api/jogadores", jogadorResource::listarTodos);
            config.routes.post("/api/jogadores", jogadorResource::criar);
            config.routes.get("/api/jogadores/{id}", jogadorResource::buscarPorId);

            config.routes.get("/api/temporadas", temporadaResource::listar);
            config.routes.post("/api/temporadas", temporadaResource::criar);
            config.routes.get("/api/temporadas/{id}", temporadaResource::buscarPorId);

            config.routes.get("/api/estatisticas", estatisticaResource::listar);
            config.routes.post("/api/estatisticas", estatisticaResource::registrar);
            config.routes.get("/api/estatisticas/{id}", estatisticaResource::buscarPorId);
        }).start(7000);
    }
}