package com.seuclube.wsctracker;

import io.javalin.Javalin;
import com.seuclube.wsctracker.dao.ConexaoSQLite;
import com.seuclube.wsctracker.api.TimeResource;
import com.seuclube.wsctracker.api.JogadorResource;

public class Main {
    public static void main(String[] args) {
        ConexaoSQLite.testarConexao();
        ConexaoSQLite.executarSchema();

        TimeResource timeResource = new TimeResource();
        JogadorResource jogadorResource = new JogadorResource();

        Javalin app = Javalin.create(config -> {
            config.routes.get("/api/status", ctx -> ctx.result("WSC Tracker backend no ar!"));

            config.routes.get("/api/times", timeResource::listarTodos);
            config.routes.post("/api/times", timeResource::criar);
            config.routes.get("/api/times/{id}", timeResource::buscarPorId);

            config.routes.get("/api/jogadores", jogadorResource::listarTodos);
            config.routes.post("/api/jogadores", jogadorResource::criar);
            config.routes.get("/api/jogadores/{id}", jogadorResource::buscarPorId);
        }).start(7000);
    }
}