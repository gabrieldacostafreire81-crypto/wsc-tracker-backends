-- schema.sql — WSC Tracker (v3.1 consolidado)
-- Execute este script uma vez para criar a estrutura do banco (idempotente).

CREATE TABLE IF NOT EXISTS time (
                                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                                    nome TEXT NOT NULL,
                                    temporada_fundacao INTEGER,
                                    observacoes TEXT
);

CREATE TABLE IF NOT EXISTS jogador (
                                       id INTEGER PRIMARY KEY AUTOINCREMENT,
                                       nome TEXT NOT NULL,
                                       posicao TEXT,
                                       nacionalidade TEXT,
                                       data_nascimento TEXT,
                                       time_atual_id INTEGER,
                                       origem_base INTEGER DEFAULT 0,
                                       data_chegada_base TEXT,
                                       overall_base INTEGER,
                                       FOREIGN KEY (time_atual_id) REFERENCES time(id) ON DELETE SET NULL
    );

CREATE TABLE IF NOT EXISTS temporada (
                                         id INTEGER PRIMARY KEY AUTOINCREMENT,
                                         time_id INTEGER NOT NULL,
                                         numero INTEGER NOT NULL,
                                         observacoes TEXT,
                                         encerrada INTEGER DEFAULT 0,
                                         nivel_treino INTEGER DEFAULT 0,
                                         FOREIGN KEY (time_id) REFERENCES time(id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS estatistica_jogador_temporada (
                                                             id INTEGER PRIMARY KEY AUTOINCREMENT,
                                                             jogador_id INTEGER NOT NULL,
                                                             temporada_id INTEGER NOT NULL,
                                                             jogos INTEGER DEFAULT 0,
                                                             gols INTEGER DEFAULT 0,
                                                             assistencias INTEGER DEFAULT 0,
                                                             nota_media REAL,
                                                             cartoes_amarelos INTEGER DEFAULT 0,
                                                             cartoes_vermelhos INTEGER DEFAULT 0,
                                                             valor_mercado REAL,
                                                             status TEXT,
                                                             overall INTEGER,
                                                             FOREIGN KEY (jogador_id) REFERENCES jogador(id) ON DELETE CASCADE,
    FOREIGN KEY (temporada_id) REFERENCES temporada(id) ON DELETE CASCADE,
    UNIQUE (jogador_id, temporada_id)
    );

CREATE TABLE IF NOT EXISTS transferencia (
                                             id INTEGER PRIMARY KEY AUTOINCREMENT,
                                             jogador_id INTEGER NOT NULL,
                                             temporada_id INTEGER NOT NULL,
                                             time_origem TEXT,
                                             time_destino TEXT,
                                             valor REAL,
                                             tipo TEXT NOT NULL,
                                             data TEXT,
                                             FOREIGN KEY (jogador_id) REFERENCES jogador(id) ON DELETE CASCADE,
    FOREIGN KEY (temporada_id) REFERENCES temporada(id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS competicao (
                                          id INTEGER PRIMARY KEY AUTOINCREMENT,
                                          nome TEXT NOT NULL,
                                          formato TEXT NOT NULL,        -- 'liga' | 'copa'
                                          abrangencia TEXT              -- 'nacional' | 'continental'
);

CREATE TABLE IF NOT EXISTS temporada_competicao (
                                                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                                                    temporada_id INTEGER NOT NULL,
                                                    competicao_id INTEGER NOT NULL,
                                                    resultado_posicao INTEGER,    -- preenchido quando a competição é 'liga'
                                                    resultado_fase TEXT,          -- preenchido quando a competição é 'copa'
                                                    FOREIGN KEY (temporada_id) REFERENCES temporada(id) ON DELETE CASCADE,
    FOREIGN KEY (competicao_id) REFERENCES competicao(id) ON DELETE CASCADE,
    UNIQUE (temporada_id, competicao_id)
    );

CREATE TABLE IF NOT EXISTS titulo (
                                      id INTEGER PRIMARY KEY AUTOINCREMENT,
                                      time_id INTEGER NOT NULL,
                                      temporada_id INTEGER NOT NULL,
                                      competicao_id INTEGER NOT NULL,
                                      FOREIGN KEY (time_id) REFERENCES time(id) ON DELETE CASCADE,
    FOREIGN KEY (temporada_id) REFERENCES temporada(id) ON DELETE CASCADE,
    FOREIGN KEY (competicao_id) REFERENCES competicao(id) ON DELETE CASCADE,
    UNIQUE (temporada_id, competicao_id)
    );

CREATE TABLE IF NOT EXISTS elenco (
                                      id INTEGER PRIMARY KEY AUTOINCREMENT,
                                      temporada_id INTEGER NOT NULL,
                                      jogador_id INTEGER NOT NULL,
                                      status TEXT NOT NULL,
                                      origem_entrada TEXT,
                                      data_entrada TEXT,
                                      motivo_saida TEXT,
                                      data_saida TEXT,
                                      FOREIGN KEY (temporada_id) REFERENCES temporada(id) ON DELETE CASCADE,
    FOREIGN KEY (jogador_id) REFERENCES jogador(id) ON DELETE CASCADE
    );

CREATE INDEX IF NOT EXISTS idx_estatistica_jogador ON estatistica_jogador_temporada(jogador_id);
CREATE INDEX IF NOT EXISTS idx_estatistica_temporada ON estatistica_jogador_temporada(temporada_id);
CREATE INDEX IF NOT EXISTS idx_transferencia_jogador ON transferencia(jogador_id);
CREATE INDEX IF NOT EXISTS idx_transferencia_temporada ON transferencia(temporada_id);
CREATE INDEX IF NOT EXISTS idx_temporada_time ON temporada(time_id);
CREATE INDEX IF NOT EXISTS idx_titulo_time ON titulo(time_id);
CREATE INDEX IF NOT EXISTS idx_elenco_temporada ON elenco(temporada_id);
CREATE INDEX IF NOT EXISTS idx_elenco_jogador ON elenco(jogador_id);
CREATE INDEX IF NOT EXISTS idx_temporada_competicao_temporada ON temporada_competicao(temporada_id);
ALTER TABLE jogador ADD COLUMN time_base_id INTEGER;

CREATE TABLE IF NOT EXISTS estatistica_time (
                                                id INTEGER PRIMARY KEY AUTOINCREMENT,
                                                temporada_id INTEGER NOT NULL UNIQUE,
                                                vitorias INTEGER DEFAULT 0,
                                                empates INTEGER DEFAULT 0,
                                                derrotas INTEGER DEFAULT 0,
                                                gols_feitos INTEGER DEFAULT 0,
                                                gols_sofridos INTEGER DEFAULT 0,
                                                FOREIGN KEY (temporada_id) REFERENCES temporada(id) ON DELETE CASCADE
    );