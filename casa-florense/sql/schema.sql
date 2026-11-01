-- =========================================================================
-- Script de Criação e Povoamento do Banco de Dados MySQL
-- Sistema: Casa Florense — Culinária Caseira, Lanches e Sobremesas
-- =========================================================================

CREATE DATABASE IF NOT EXISTS casa_florense_db
CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE casa_florense_db;

CREATE TABLE IF NOT EXISTS atendente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    telefone VARCHAR(20) NOT NULL,
    endereco VARCHAR(200) NOT NULL
);

CREATE TABLE IF NOT EXISTS produto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    descricao VARCHAR(255),
    preco DECIMAL(10,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT NOT NULL,
    produto_id INT NOT NULL,
    atendente_id INT DEFAULT 1,
    quantidade INT NOT NULL,
    valor_total DECIMAL(10,2) NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'Em Preparo',
    data_hora DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cliente_id) REFERENCES cliente(id),
    FOREIGN KEY (produto_id) REFERENCES produto(id),
    FOREIGN KEY (atendente_id) REFERENCES atendente(id)
);

-- Inserção de dados iniciais para testes
INSERT INTO atendente (nome, email, senha) VALUES
('Operador Caixa', 'atendimento@casaflorense.com.br', '123456');

INSERT INTO cliente (nome, telefone, endereco) VALUES
('Carlos Eduardo Silva', '(54) 99812-3456', 'Rua Borges de Medeiros, 450 - Centro'),
('Mariana Costa', '(54) 99123-7890', 'Av. 25 de Julho, 112 - Centro');

INSERT INTO produto (nome, categoria, descricao, preco) VALUES
('Bife à Parmegiana Caseiro', 'Prato Caseiro', 'Filé empanado ao molho artesanal, queijo gratinado, arroz e fritas', 44.90),
('X-Florense Artesanal', 'Lanche', 'Pão brioche, hambúrguer 180g, queijo colonial, bacon e maionese da casa', 29.00),
('Torta Holandesa com Calda', 'Sobremesa', 'Fatia de torta artesanal com ganache de chocolate meio amargo', 16.50),
('Suco Natural de Uva 500ml', 'Bebida', 'Suco integral de uva da Serra Gaúcha sem adição de açúcar', 12.00);
