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
        ElencoResource elencoResource = new ElencoResource();

        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(it -> it.anyHost());
            });

            config.routes.get("/api/status", ctx -> ctx.result("WSC Tracker backend no ar!"));

                //Time
            config.routes.get("/api/times", timeResource::listarTodos);
            config.routes.post("/api/times", timeResource::criar);
            config.routes.get("/api/times/{id}", timeResource::buscarPorId);
            config.routes.get("/api/times/{id}/total-jogadores", timeResource::totalJogadoresHistorico);
            config.routes.get("/api/times/{id}/estatisticas-coletivas", timeResource::estatisticasColetivas);
            config.routes.put("/api/times/{id}", timeResource::atualizar);
            config.routes.delete("/api/times/{id}", timeResource::excluir);

                //Jogador
            config.routes.get("/api/jogadores", jogadorResource::listarTodos);
            config.routes.post("/api/jogadores", jogadorResource::criar);
            config.routes.put("/api/jogadores/{id}", jogadorResource::atualizar);
            config.routes.delete("/api/jogadores/{id}", jogadorResource::excluir);
            config.routes.get("/api/jogadores/{id}", jogadorResource::buscarPorId);
            config.routes.get("/api/jogadores/{id}/carreira", jogadorResource::carreira);

                //Temporada
            config.routes.get("/api/temporadas", temporadaResource::listar);
            config.routes.post("/api/temporadas", temporadaResource::criar);
            config.routes.put("/api/temporadas/{id}", temporadaResource::atualizar);
            config.routes.delete("/api/temporadas/{id}", temporadaResource::excluir);
            config.routes.get("/api/temporadas/{id}", temporadaResource::buscarPorId);
            config.routes.post("/api/temporadas/{id}/finalizar", temporadaResource::finalizar);

                //Estatistica
            config.routes.get("/api/estatisticas", estatisticaResource::listar);
            config.routes.post("/api/estatisticas", estatisticaResource::registrar);
            config.routes.put("/api/estatisticas/{id}", estatisticaResource::atualizar);
            config.routes.delete("/api/estatisticas/{id}", estatisticaResource::excluir);
            config.routes.get("/api/estatisticas/{id}", estatisticaResource::buscarPorId);

                //Transferencias
            config.routes.get("/api/transferencias", transferenciaResource::listar);
            config.routes.post("/api/transferencias", transferenciaResource::criar);
            config.routes.put("/api/transferencias/{id}", transferenciaResource::atualizar);
            config.routes.delete("/api/transferencias/{id}", transferenciaResource::excluir);
            config.routes.get("/api/transferencias/{id}", transferenciaResource::buscarPorId);
            config.routes.get("/api/times/{id}/financeiro", transferenciaResource::fluxoFinanceiro);

                //Competição
            config.routes.get("/api/competicoes", competicaoResource::listarTodas);
            config.routes.post("/api/competicoes", competicaoResource::criar);
            config.routes.put("/api/competicoes/{id}", competicaoResource::atualizar);
            config.routes.delete("/api/competicoes/{id}", competicaoResource::excluir);
            config.routes.get("/api/competicoes/{id}", competicaoResource::buscarPorId);

                //Titulo
            config.routes.get("/api/titulos", tituloResource::listarPorTime);
            config.routes.post("/api/titulos", tituloResource::criar);
            config.routes.get("/api/titulos/{id}", tituloResource::buscarPorId);
            config.routes.put("/api/titulos/{id}", tituloResource::atualizar);
            config.routes.delete("/api/titulos/{id}", tituloResource::excluir);

               //Elenco
            config.routes.get("/api/temporadas/{temporadaId}/elenco-atual", elencoResource::listarAtual);
            config.routes.get("/api/temporadas/{temporadaId}/elenco-todos", elencoResource::listarTudo);
        }).start(7000);
    }
}
