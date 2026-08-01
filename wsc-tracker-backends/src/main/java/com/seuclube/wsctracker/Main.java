package com.seuclube.wsctracker;

import io.javalin.Javalin;
import com.seuclube.wsctracker.dao.ConexaoSQLite;
import com.seuclube.wsctracker.api.*;

public class Main {
    public static void main(String[] args) {
        ConexaoSQLite.testarConexao();
        ConexaoSQLite.executarSchema();

        TimeResource timeResource = new TimeResource();
        JogadorResource jogadorResource = new JogadorResource();
        TemporadaResource temporadaResource = new TemporadaResource();
        EstatisticaResource estatisticaResource = new EstatisticaResource();
        TransferenciaResource transferenciaResource = new TransferenciaResource();
        CompeticaoResource competicaoResource = new CompeticaoResource();
        TituloResource tituloResource = new TituloResource();

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

            config.routes.get("/api/transferencias", transferenciaResource::listar);
            config.routes.post("/api/transferencias", transferenciaResource::criar);
            config.routes.get("/api/transferencias/{id}", transferenciaResource::buscarPorId);

            config.routes.get("/api/competicoes", competicaoResource::listarTodas);
            config.routes.post("/api/competicoes", competicaoResource::criar);
            config.routes.get("/api/competicoes/{id}", competicaoResource::buscarPorId);

            config.routes.get("/api/titulos", tituloResource::listarPorTime);
            config.routes.post("/api/titulos", tituloResource::criar);
            config.routes.get("/api/titulos/{id}", tituloResource::buscarPorId);
        }).start(7000);
    }
}