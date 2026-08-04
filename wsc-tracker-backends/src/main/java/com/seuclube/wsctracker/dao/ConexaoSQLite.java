package com.seuclube.wsctracker.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexaoSQLite {

    private static final String URL = "jdbc:sqlite:wsc_tracker.db";
    private static Connection conexao;

    public static Connection getConnection() throws SQLException {
        if (conexao == null || conexao.isClosed()) {
            conexao = DriverManager.getConnection(URL);
            try (Statement stmt = conexao.createStatement()) {
                stmt.execute("PRAGMA foreign_keys = ON;");
                stmt.execute("PRAGMA journal_mode = WAL;");
            }
        }
        return conexao;
    }

    public static void testarConexao() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("SELECT 1");
            System.out.println("Conexão com o SQLite OK!");
        } catch (SQLException e) {
            System.err.println("Erro ao conectar no banco: " + e.getMessage());
        }
    }

    public static void executarSchema() {
        try {
            Connection conn = getConnection();
            String sql = new String(Files.readAllBytes(Paths.get("schema.sql")));

            for (String comando : sql.split(";")) {
                String limpo = comando.trim();
                if (limpo.isEmpty()) continue;

                try (Statement stmt = conn.createStatement()) {
                    stmt.execute(limpo);
                } catch (SQLException e) {
                    // Comandos como "ALTER TABLE ADD COLUMN" falham na 2ª execução em diante
                    // (a coluna já existe) — isso é esperado e não deve travar o restante do schema.
                    if (!e.getMessage().contains("duplicate column name")) {
                        System.err.println("Aviso ao aplicar comando do schema: " + e.getMessage());
                    }
                }
            }
            System.out.println("Schema aplicado com sucesso! Tabelas criadas/atualizadas.");
        } catch (IOException e) {
            System.err.println("Não encontrei o arquivo schema.sql: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Erro ao conectar para aplicar schema: " + e.getMessage());
        }
    }
}
