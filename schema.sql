-- schema.sql — WSC Tracker
-- Execute este script uma vez para criar a estrutura do banco.

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
                                       FOREIGN KEY (time_atual_id) REFERENCES time(id) ON DELETE SET NULL
    );

CREATE TABLE IF NOT EXISTS temporada (
                                         id INTEGER PRIMARY KEY AUTOINCREMENT,
                                         time_id INTEGER NOT NULL,
                                         numero INTEGER NOT NULL,
                                         divisao TEXT,
                                         posicao_final INTEGER,
                                         observacoes TEXT,
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
                                          tipo TEXT
);

CREATE TABLE IF NOT EXISTS titulo (
                                      id INTEGER PRIMARY KEY AUTOINCREMENT,
                                      time_id INTEGER NOT NULL,
                                      temporada_id INTEGER NOT NULL,
                                      competicao_id INTEGER NOT NULL,
                                      FOREIGN KEY (time_id) REFERENCES time(id) ON DELETE CASCADE,
    FOREIGN KEY (temporada_id) REFERENCES temporada(id) ON DELETE CASCADE,
    FOREIGN KEY (competicao_id) REFERENCES competicao(id) ON DELETE CASCADE
    );

-- Índices (seção 9 do documento de arquitetura)
CREATE INDEX IF NOT EXISTS idx_estatistica_jogador ON estatistica_jogador_temporada(jogador_id);
CREATE INDEX IF NOT EXISTS idx_estatistica_temporada ON estatistica_jogador_temporada(temporada_id);
CREATE INDEX IF NOT EXISTS idx_transferencia_jogador ON transferencia(jogador_id);
CREATE INDEX IF NOT EXISTS idx_transferencia_temporada ON transferencia(temporada_id);
CREATE INDEX IF NOT EXISTS idx_temporada_time ON temporada(time_id);
CREATE INDEX IF NOT EXISTS idx_titulo_time ON titulo(time_id);

-- ================= v3: Fase 8 =================

-- Jogador: origem da base e overall de chegada
ALTER TABLE jogador ADD COLUMN origem_base INTEGER DEFAULT 0;
ALTER TABLE jogador ADD COLUMN data_chegada_base TEXT;
ALTER TABLE jogador ADD COLUMN overall_base INTEGER;

-- Temporada: nível de treino/academia
ALTER TABLE temporada ADD COLUMN nivel_treino INTEGER DEFAULT 0;

-- Estatística por temporada: overall daquela temporada específica
ALTER TABLE estatistica_jogador_temporada ADD COLUMN overall INTEGER;

-- Elenco: quem está no time em cada temporada, e como chegou/saiu
CREATE TABLE IF NOT EXISTS elenco (
                                      id INTEGER PRIMARY KEY AUTOINCREMENT,
                                      temporada_id INTEGER NOT NULL,
                                      jogador_id INTEGER NOT NULL,
                                      status TEXT NOT NULL,           -- 'inicial' ou 'atual'
                                      origem_entrada TEXT,            -- 'herdado' | 'compra' | 'base' | 'emprestimo'
                                      data_entrada TEXT,
                                      motivo_saida TEXT,              -- 'venda' | 'emprestimo' | 'dispensa' | NULL
                                      data_saida TEXT,
                                      FOREIGN KEY (temporada_id) REFERENCES temporada(id) ON DELETE CASCADE,
    FOREIGN KEY (jogador_id) REFERENCES jogador(id) ON DELETE CASCADE
    );

CREATE INDEX IF NOT EXISTS idx_elenco_temporada ON elenco(temporada_id);
CREATE INDEX IF NOT EXISTS idx_elenco_jogador ON elenco(jogador_id);
-- Temporada: trava de edição após finalizada
ALTER TABLE temporada ADD COLUMN encerrada INTEGER DEFAULT 0;