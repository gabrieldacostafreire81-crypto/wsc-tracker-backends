"Este projeto é o [backend/frontend] do WSC Tracker — veja o repositório irmão em [https://github.com/gabrieldacostafreire81-crypto/wsc-tracker-frontend/tree/main]".

WSC Tracker — Backend

Sistema de registro e análise histórica de clube no jogo World Soccer Champs: elenco, temporadas, estatísticas, transferências e títulos.

Construído com:

Java 17 — linguagem, sem framework pesado
Javalin 7 — microframework HTTP para a API REST
SQLite (via JDBC, driver org.xerial:sqlite-jdbc) — banco de dados embutido, arquivo único
Jackson — conversão automática de objetos Java ↔ JSON
Maven — build e gerenciamento de dependências

Arquitetura: em camadas (api → service → dao → banco), separando regras de negócio de acesso a dados e de rotas HTTP. Documentação completa de arquitetura, modelo de dados e decisões de design disponível à parte.

Frontend: projeto separado (wsc-tracker-frontend), em HTML/CSS/JS puro, consumindo esta API via REST.
