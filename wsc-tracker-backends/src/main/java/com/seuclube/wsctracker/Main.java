package com.seuclube.wsctracker;

import io.javalin.Javalin;
import com.seuclube.wsctracker.dao.ConexaoSQLite;

public class Main {
    public static void main(String[] args) {
        ConexaoSQLite.testarConexao();
        ConexaoSQLite.executarSchema();   // <- novo

        Javalin app = Javalin.create(config -> {
            config.routes.get("/api/status", ctx -> ctx.result("WSC Tracker backend no ar!"));
        }).start(7000);
    }
}