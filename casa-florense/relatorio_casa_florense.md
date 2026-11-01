# Relatório Técnico — Projeto Integrado Individual

**Tema:** Sistema Web de Gestão de Cardápio e Pedidos — **Casa Florense: Culinária Caseira, Lanches e Sobremesas**

---

## 2. APRESENTAÇÃO DO TEMA (Passo 0)

### 2.1 Definição do Sistema e Justificativa

O sistema escolhido para o desenvolvimento deste Projeto Integrado é um **Sistema Web de Gestão de Cardápio e Pedidos** voltado para o estabelecimento **Casa Florense — Culinária Caseira, Lanches e Sobremesas** (refeições para toda hora). A escolha deste tema justifica-se pela necessidade prática observada em pequenos estabelecimentos gastronômicos locais que oferecem desde pratos executivos e refeições caseiras no almoço até lanches artesanais, porções e sobremesas ao longo do dia. Nesses locais, o registro de pedidos no balcão ou por mensagem ainda é realizado frequentemente em comandas de papel ou planilhas descentralizadas. Esse processo manual gera falhas de comunicação com a cozinha, erros na soma do valor total dos pedidos e dificuldade em consultar o histórico de vendas do dia.

O projeto cumpre integralmente os critérios de aprovação estabelecidos:

* **Funcionalidades distintas:** O sistema permite autenticar operadores (atendentes), cadastrar clientes, gerenciar os produtos do cardápio (pratos principais/refeições caseiras, lanches, sobremesas e bebidas), registrar novos pedidos e atualizar/consultar o status dos pedidos em andamento.
* **Consulta a banco de dados:** Todas as informações de produtos, clientes e pedidos são persistidas e recuperadas em tempo real por meio de um sistema gerenciador de banco de dados relacional MySQL (`casa_florense_db`).
* **Viabilidade de execução:** O escopo foi delimitado estrategicamente nos processos essenciais de atendimento e operação interna, viabilizando a construção de um protótipo funcional sólido em arquitetura Java Web dentro do período letivo.

### 2.2 Levantamento de Informações

Para compreender o funcionamento real do processo de negócio sem depender de um cliente formal único, foram aplicadas três técnicas combinadas de levantamento de requisitos:

1. **Observação direta e experiência como cliente:** Análise do fluxo de atendimento presencial e via *delivery* em restaurantes e lanchonetes locais, mapeando desde a identificação do cliente e escolha dos itens do cardápio até o envio da comanda para o preparo na cozinha e fechamento de caixa.
2. **Análise de sistemas similares (*Benchmarking*):** Observação de plataformas de cardápio digital e sistemas de ponto de venda (PDV) do mercado para identificar os campos essenciais necessários no cadastro de produtos (nome, categoria, descrição e preço) e de pedidos (cliente, item, quantidade, valor total e status).
3. **Conversa informal com profissionais do setor:** Levantamento dos principais gargalos operacionais do atendimento diário, destacando-se a necessidade de uma interface rápida e intuitiva que evite erros de digitação de preços e permita visualizar rapidamente a fila de pedidos pendentes de entrega.

---

## 3. FASE 1 – ANÁLISE DE REQUISITOS

Segundo Sommerville (2019), os requisitos de um sistema estabelecem as descrições do que o software deve fazer, os serviços que oferece e as restrições sobre a sua operação. A seguir, apresentam-se os requisitos funcionais e não funcionais levantados para o sistema **Casa Florense**.

### 3.1 Requisitos Funcionais (RF)

Os Requisitos Funcionais descrevem diretamente as funcionalidades e os serviços que o sistema deve disponibilizar aos usuários (PRESSMAN; MAXIM, 2021):

* **RF01 – Gerenciar Produtos do Cardápio:** O sistema deve permitir ao atendente cadastrar, listar e consultar os itens do cardápio, informando o nome do produto, categoria (Prato Caseiro, Lanche, Sobremesa, Bebida), descrição do item e preço unitário.
* **RF02 – Cadastrar Clientes:** O sistema deve permitir o cadastro e a consulta de clientes, armazenando nome completo, telefone de contato e endereço para entrega.
* **RF03 – Registrar Pedido:** O sistema deve permitir a abertura de um novo pedido, vinculando um cliente cadastrado, o atendente responsável, o produto selecionado do cardápio, a quantidade desejada, o cálculo automático do valor total, a data/hora e o status inicial do pedido (`"Em Preparo"`).
* **RF04 – Consultar Pedidos do Dia:** O sistema deve listar na tela todos os pedidos registrados no banco de dados, exibindo os dados do cliente, o item solicitado, a quantidade, o valor total e o status atual, permitindo acompanhar a fila de produção da cozinha.
* **RF05 – Atualizar Status do Pedido:** O sistema deve permitir ao atendente alterar o status de um pedido existente na fila do dia (ex.: de `"Em Preparo"` para `"Concluído"` ou `"Cancelado"`).
* **RF06 – Autenticar Usuário (Login):** O sistema deve permitir que os funcionários (atendentes) realizem a autenticação mediante e-mail e senha para acessar o painel administrativo.

### 3.2 Requisitos Não Funcionais (RNF)

Os Requisitos Não Funcionais definem propriedades de qualidade, desempenho, segurança e restrições tecnológicas do sistema:

* **RNF01 – Desempenho e Tempo de Resposta:** As operações de consulta de cardápio, listagem de pedidos e gravação no banco de dados devem ser processadas e exibidas no navegador em até 3 segundos em condições normais de rede local.
* **RNF02 – Usabilidade e Responsividade:** A interface do sistema deve ser construída para ambiente Web utilizando HTML5, CSS3 e Bootstrap, garantindo layout limpo, responsivo e de fácil operação tanto em computadores de balcão quanto em dispositivos móveis.
* **RNF03 – Segurança e Controle de Acesso:** O acesso às funcionalidades de cadastro de produtos, clientes e pedidos deve ser restrito a usuários autenticados no sistema por meio de sessão ativa.
* **RNF04 – Persistência e Padrão Tecnológico:** O sistema deve ser desenvolvido na linguagem Java para ambiente Web (utilizando padrão MVC e API JDBC) e armazenar todas as informações de forma persistente e íntegra no banco de dados relacional MySQL.

### 3.3 Diagrama de Casos de Uso

A Figura 1 apresenta o Diagrama de Casos de Uso do sistema **Casa Florense**, ilustrando a interação do ator **Atendente** com os cinco casos de uso principais do sistema.

*(Inserir aqui a Figura 1 – Diagrama de Casos de Uso)*

**Figura 1 – Diagrama de Casos de Uso do Sistema Casa Florense.** Fonte: Elaborado pelo autor (2026).

### 3.4 Descrição Simplificada dos Casos de Uso

Conforme exigido nas orientações da Fase 1 (mínimo de 3 casos de uso descritos no formato simplificado com ator, objetivo e passos principais), seguem as especificações detalhadas dos casos de uso do sistema:

* **Caso de Uso 01 (UC01): Gerenciar Produtos do Cardápio**
  * **Ator:** Atendente.
  * **Objetivo:** Cadastrar um novo item gastronômico (prato caseiro, lanche, sobremesa ou bebida) no cardápio da Casa Florense e visualizar os produtos disponíveis no banco de dados.
  * **Passos principais:**
    1. O atendente acessa o menu "Cardápio / Produtos" no sistema.
    2. O sistema exibe o formulário de cadastro e a tabela de produtos já cadastrados.
    3. O atendente preenche o nome do produto, seleciona a categoria, informa a descrição e digita o preço unitário.
    4. O atendente clica no botão "Salvar no Cardápio".
    5. O sistema valida os campos obrigatórios, grava o registro na tabela `produto` do MySQL e atualiza a listagem na tela.

* **Caso de Uso 02 (UC02): Cadastrar Cliente**
  * **Ator:** Atendente.
  * **Objetivo:** Registrar os dados de um cliente no sistema para vinculá-lo a pedidos de balcão ou entrega (*delivery*).
  * **Passos principais:**
    1. O atendente seleciona a opção "Clientes" no menu principal.
    2. O atendente informa o nome completo, o telefone/WhatsApp e o endereço do cliente.
    3. O atendente confirma a operação clicando em "Cadastrar Cliente".
    4. O sistema valida os dados obrigatórios, armazena o registro na tabela `cliente` do MySQL e exibe o cliente na listagem.

* **Caso de Uso 03 (UC03): Registrar Pedido**
  * **Ator:** Atendente.
  * **Objetivo:** Lançar um novo pedido para um cliente, calculando automaticamente o valor total e enviando para a fila de preparo da cozinha.
  * **Passos principais:**
    1. O atendente acessa a tela "Pedidos".
    2. O sistema carrega do banco de dados os clientes cadastrados e os produtos disponíveis no cardápio.
    3. O atendente seleciona o cliente, escolhe o item do cardápio desejado e informa a quantidade.
    4. O sistema calcula o valor total do pedido (`quantidade * preco`) e o atendente clica em "Confirmar Pedido".
    5. O sistema grava o pedido na tabela `pedido` do MySQL com o status `"Em Preparo"` e atualiza a fila de pedidos do dia.

* **Caso de Uso 04 (UC04): Consultar Pedidos do Dia**
  * **Ator:** Atendente.
  * **Objetivo:** Visualizar em tempo real todos os pedidos registrados no dia para acompanhamento da produção e entrega.
  * **Passos principais:**
    1. O atendente acessa o painel "Fila de Pedidos do Dia".
    2. O sistema realiza uma consulta integrada (`INNER JOIN`) no banco de dados relacionando as tabelas de pedidos, clientes e produtos.
    3. O sistema exibe a tabela ordenada com código do pedido, nome do cliente, item solicitado, quantidade, valor total e status atual.

* **Caso de Uso 05 (UC05): Atualizar Status do Pedido**
  * **Ator:** Atendente.
  * **Objetivo:** Alterar a situação de um pedido na fila do dia conforme o andamento na cozinha.
  * **Passos principais:**
    1. O atendente localiza o pedido desejado na listagem da "Fila de Pedidos do Dia".
    2. O atendente aciona a opção de atualização de status para `"Concluído"` (ou `"Cancelado"`).
    3. O sistema atualiza o registro no banco de dados MySQL e reflete a nova situação visualmente na tabela.

---

## 4. FASE 2 – MODELAGEM DO SISTEMA

A modelagem de sistemas permite construir representações abstratas da estrutura de dados e do comportamento dinâmico do software antes de sua codificação (PRESSMAN; MAXIM, 2021). A seguir, apresentam-se os artefatos de modelagem orientada a objetos e relacional do sistema **Casa Florense**.

### 4.1 Diagrama de Classes

O Diagrama de Classes (Figura 2) representa a estrutura estática do sistema orientada a objetos, contemplando as 4 classes de domínio: `Atendente`, `Cliente`, `Produto` (que representa os itens do cardápio) e `Pedido`, juntamente com seus atributos, métodos e multiplicidades de associação.

*(Inserir aqui a Figura 2 – Diagrama de Classes)*

**Figura 2 – Diagrama de Classes do Sistema Casa Florense.** Fonte: Elaborado pelo autor (2026).

### 4.2 Modelo Entidade-Relacionamento (MER)

Para a persistência das informações no sistema gerenciador de banco de dados MySQL (`casa_florense_db`), as classes de domínio foram mapeadas para o modelo relacional apresentado na Figura 3, destacando as chaves primárias (*Primary Keys* – PK), as chaves estrangeiras (*Foreign Keys* – FK) e os relacionamentos `1:N` que garantem a integridade referencial dos pedidos realizados.

*(Inserir aqui a Figura 3 – Modelo Entidade-Relacionamento / MER)*

**Figura 3 – Modelo Entidade-Relacionamento (MER) do Sistema Casa Florense.** Fonte: Elaborado pelo autor (2026).

### 4.3 Dicionário de Dados

De acordo com Elmasri e Navathe (2019), o dicionário de dados cataloga os metadados das tabelas do banco de dados, especificando nomes de campos, tipos de dados, restrições de chave e obrigatoriedade de preenchimento. Os Quadros 1 a 4 detalham a estrutura das entidades do esquema `casa_florense_db`.

**Quadro 1 – Dicionário de Dados da Entidade `produto` (Cardápio)**

| Campo | Tipo de Dado | Obrigatório | Chave | Descrição |
| :--- | :--- | :---: | :---: | :--- |
| `id` | `INT AUTO_INCREMENT` | Sim | PK | Identificador único do item do cardápio. |
| `nome` | `VARCHAR(100)` | Sim | - | Nome do prato caseiro, lanche, sobremesa ou bebida. |
| `categoria` | `VARCHAR(50)` | Sim | - | Categoria no cardápio (Prato Caseiro, Lanche, Sobremesa, Bebida). |
| `descricao` | `VARCHAR(255)` | Não | - | Ingredientes, acompanhamentos ou detalhes do item. |
| `preco` | `DECIMAL(10,2)` | Sim | - | Preço unitário de venda do produto em reais (R\$). |

**Quadro 2 – Dicionário de Dados da Entidade `cliente`**

| Campo | Tipo de Dado | Obrigatório | Chave | Descrição |
| :--- | :--- | :---: | :---: | :--- |
| `id` | `INT AUTO_INCREMENT` | Sim | PK | Identificador único do cliente. |
| `nome` | `VARCHAR(100)` | Sim | - | Nome completo do cliente. |
| `telefone` | `VARCHAR(20)` | Sim | - | Número de telefone/WhatsApp para contato. |
| `endereco` | `VARCHAR(200)` | Sim | - | Endereço completo para entrega ou referência. |

**Quadro 3 – Dicionário de Dados da Entidade `pedido`**

| Campo | Tipo de Dado | Obrigatório | Chave | Descrição |
| :--- | :--- | :---: | :---: | :--- |
| `id` | `INT AUTO_INCREMENT` | Sim | PK | Número identificador único do pedido (comanda). |
| `cliente_id` | `INT` | Sim | FK | Chave estrangeira que referencia a tabela `cliente`. |
| `produto_id` | `INT` | Sim | FK | Chave estrangeira que referencia o item na tabela `produto`. |
| `atendente_id` | `INT` | Não | FK | Chave estrangeira que referencia o operador na tabela `atendente`. |
| `quantidade` | `INT` | Sim | - | Quantidade de unidades solicitadas do produto. |
| `valor_total` | `DECIMAL(10,2)` | Sim | - | Valor total calculado do pedido (`quantidade * preco`). |
| `status` | `VARCHAR(30)` | Sim | - | Status atual do pedido (`Em Preparo`, `Concluído`, `Cancelado`). |
| `data_hora` | `DATETIME` | Sim | - | Data e hora exatas do registro do pedido. |

**Quadro 4 – Dicionário de Dados da Entidade `atendente`**

| Campo | Tipo de Dado | Obrigatório | Chave | Descrição |
| :--- | :--- | :---: | :---: | :--- |
| `id` | `INT AUTO_INCREMENT` | Sim | PK | Identificador único do funcionário/atendente. |
| `nome` | `VARCHAR(100)` | Sim | - | Nome do funcionário operador do sistema. |
| `email` | `VARCHAR(100)` | Sim | Unique | E-mail de login para acesso ao sistema. |
| `senha` | `VARCHAR(100)` | Sim | - | Senha para autenticação no painel. |

### 4.4 Diagrama de Sequência

A Figura 4 ilustra o Diagrama de Sequência referente ao caso de uso principal do sistema (**UC03 – Registrar Pedido**), demonstrando a ordem cronológica da troca de mensagens entre o ator `Atendente`, a camada de interface (`:TelaPedido`), a camada de controle (`:PedidoController`) e a camada de persistência (`:PedidoDAO`).

*(Inserir aqui a Figura 4 – Diagrama de Sequência)*

**Figura 4 – Diagrama de Sequência do Caso de Uso "Registrar Pedido".** Fonte: Elaborado pelo autor (2026).

---

## 5. FASE 3 – PROTOTIPAGEM DE TELAS

De acordo com Pressman e Maxim (2021), a prototipagem de interfaces permite validar a usabilidade e a disposição visual dos elementos de entrada e saída antes da codificação definitiva. Para o sistema **Casa Florense**, foram elaborados os protótipos de 4 telas principais, utilizando design responsivo baseado em HTML5, CSS3 e Bootstrap 5.

### 5.1 Tela 1 – Autenticação de Operador (Login)

*(Inserir aqui a Figura 5 – Protótipo da Tela 1)*

**Figura 5 – Protótipo da Tela de Login do Sistema Casa Florense.** Fonte: Elaborado pelo autor (2026).

**Descrição da função:** Esta tela serve para controlar o acesso restrito ao painel administrativo do estabelecimento (atendendo aos requisitos RF06 e RNF03). O atendente insere seu e-mail cadastrado e sua senha, clicando em "Entrar no Sistema" para iniciar a sessão de trabalho.

### 5.2 Tela 2 – Gestão do Cardápio (Cadastro e Listagem de Produtos)

*(Inserir aqui a Figura 6 – Protótipo da Tela 2)*

**Figura 6 – Protótipo da Tela de Gestão do Cardápio (Produtos).** Fonte: Elaborado pelo autor (2026).

**Descrição da função:** Esta tela permite ao atendente cadastrar novos itens gastronômicos da Casa Florense (pratos caseiros, lanches artesanais, sobremesas e bebidas), informando nome, categoria, descrição e preço unitário (RF01). Logo abaixo do formulário, o sistema exibe uma tabela dinâmica com todos os itens ativos no cardápio recuperados do banco de dados MySQL.

### 5.3 Tela 3 – Cadastro e Consulta de Clientes

*(Inserir aqui a Figura 7 – Protótipo da Tela 3)*

**Figura 7 – Protótipo da Tela de Cadastro e Consulta de Clientes.** Fonte: Elaborado pelo autor (2026).

**Descrição da função:** Destina-se ao cadastro rápido de clientes para pedidos presenciais ou entregas (*delivery*), solicitando nome completo, telefone/WhatsApp e endereço (RF02). Na parte inferior, apresenta a listagem de clientes já cadastrados para consulta imediata pelo atendente.

### 5.4 Tela 4 – Registro de Novo Pedido e Fila de Pedidos do Dia

*(Inserir aqui a Figura 8 – Protótipo da Tela 4)*

**Figura 8 – Protótipo da Tela de Registro e Fila de Pedidos do Dia.** Fonte: Elaborado pelo autor (2026).

**Descrição da função:** É a tela operacional principal do sistema, onde o atendente seleciona o cliente, escolhe o item do cardápio (refeição caseira, lanche, sobremesa ou bebida), define a quantidade e confirma a abertura do pedido com cálculo automático do valor total (RF03). Abaixo, exibe em tempo real a fila de pedidos do dia com os respectivos status (`Em Preparo`, `Concluído` ou `Cancelado`), permitindo o acompanhamento e atualização pela equipe (RF04 e RF05).

---

## 6. FASE 4 – DESENVOLVIMENTO (PROGRAMAÇÃO)

Para a construção do protótipo funcional, foi adotada a linguagem **Java** (Java EE / Servlets e JSP) estruturada no padrão arquitetural **MVC (Model-View-Controller)** e **DAO (Data Access Object)**, utilizando persistência relacional no banco de dados **MySQL** por meio da API **JDBC**.

Conforme solicitado nas diretrizes do projeto, foram implementadas e conectadas ao banco de dados duas funcionalidades principais completas:

1. **Funcionalidade 1 – Gestão do Cardápio (Cadastro e Listagem de Produtos):** Permite gravar novos pratos caseiros, lanches, sobremesas e bebidas na tabela `produto` e listar o cardápio atualizado diretamente do banco de dados.
2. **Funcionalidade 2 – Registro, Cálculo e Listagem de Pedidos:** Realiza a leitura dos clientes e dos produtos do cardápio no MySQL, calcula automaticamente o valor total do pedido (`quantidade * preco`), grava o registro na tabela `pedido` e consulta a fila de pedidos do dia com junção de tabelas (`INNER JOIN`), além de permitir a atualização do status do pedido.

### 6.1 Script de Criação e Povoamento do Banco de Dados MySQL

Abaixo apresenta-se o script SQL executado no MySQL para criar o esquema `casa_florense_db`, as tabelas relacionais e os registros iniciais para teste:

```sql
CREATE DATABASE IF NOT EXISTS casa_florense_db
CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE casa_florense_db;

CREATE TABLE atendente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(100) NOT NULL
);

CREATE TABLE cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    telefone VARCHAR(20) NOT NULL,
    endereco VARCHAR(200) NOT NULL
);

CREATE TABLE produto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    descricao VARCHAR(255),
    preco DECIMAL(10,2) NOT NULL
);

CREATE TABLE pedido (
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
```

### 6.2 Classe de Conexão com o Banco de Dados (`ConexaoBD.java`)

Classe responsável por estabelecer a comunicação via driver JDBC com o servidor MySQL local:

```java
package br.com.casaflorense.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {
    private static final String URL = "jdbc:mysql://localhost:3306/casa_florense_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConexao() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC do MySQL não encontrado: " + e.getMessage());
        }
    }
}
```

### 6.3 Código da Funcionalidade 1: Gestão do Cardápio (`ProdutoDAO.java`)

O trecho abaixo demonstra a gravação de um novo item gastronômico no cardápio (`INSERT`) com validação de regras de negócio e a consulta de todos os itens cadastrados (`SELECT`) na tabela `produto`:

```java
package br.com.casaflorense.dao;

import br.com.casaflorense.model.Produto;
import br.com.casaflorense.util.ConexaoBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    // Método para gravar um novo item no Cardápio
    public void cadastrar(Produto p) throws SQLException {
        if (p.getNome() == null || p.getNome().trim().isEmpty() || p.getPreco() <= 0) {
            throw new IllegalArgumentException("O nome do produto é obrigatório e o preço deve ser maior que zero.");
        }
        String sql = "INSERT INTO produto (nome, categoria, descricao, preco) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getCategoria());
            stmt.setString(3, p.getDescricao());
            stmt.setDouble(4, p.getPreco());
            stmt.executeUpdate();
        }
    }

    // Método para consultar todos os itens do Cardápio
    public List<Produto> listarCardapio() throws SQLException {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT * FROM produto ORDER BY id ASC";
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setCategoria(rs.getString("categoria"));
                p.setDescricao(rs.getString("descricao"));
                p.setPreco(rs.getDouble("preco"));
                lista.add(p);
            }
        }
        return lista;
    }
}
```

### 6.4 Código da Funcionalidade 2: Registro e Consulta de Pedidos (`PedidoDAO.java`)

O trecho a seguir implementa o registro de um novo pedido buscando o preço unitário do item selecionado no banco para calcular o valor total, a listagem dos pedidos do dia utilizando `INNER JOIN` entre as tabelas `pedido`, `cliente` e `produto`, e a atualização de status do pedido:

```java
package br.com.casaflorense.dao;

import br.com.casaflorense.model.Pedido;
import br.com.casaflorense.util.ConexaoBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    // Registra um novo pedido calculando o valor total com base no item do cardápio
    public void registrarPedido(int clienteId, int produtoId, int quantidade) throws SQLException {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade mínima para o pedido é 1 unidade.");
        }
        String sqlPreco = "SELECT preco FROM produto WHERE id = ?";
        String sqlInsert = "INSERT INTO pedido (cliente_id, produto_id, quantidade, valor_total, status) VALUES (?, ?, ?, ?, 'Em Preparo')";

        try (Connection conn = ConexaoBD.getConexao()) {
            double precoUnitario = 0;
            try (PreparedStatement stmtPreco = conn.prepareStatement(sqlPreco)) {
                stmtPreco.setInt(1, produtoId);
                try (ResultSet rs = stmtPreco.executeQuery()) {
                    if (rs.next()) {
                        precoUnitario = rs.getDouble("preco");
                    } else {
                        throw new SQLException("Produto não encontrado no cardápio.");
                    }
                }
            }

            double valorTotal = precoUnitario * quantidade;

            try (PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert)) {
                stmtInsert.setInt(1, clienteId);
                stmtInsert.setInt(2, produtoId);
                stmtInsert.setInt(3, quantidade);
                stmtInsert.setDouble(4, valorTotal);
                stmtInsert.executeUpdate();
            }
        }
    }

    // Lista os pedidos do dia com os nomes do Cliente e do Produto do Cardápio
    public List<Pedido> listarPedidosDoDia() throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT ped.id, cli.nome AS nome_cliente, prod.nome AS nome_produto, " +
                     "ped.quantidade, ped.valor_total, ped.status, ped.data_hora " +
                     "FROM pedido ped " +
                     "INNER JOIN cliente cli ON ped.cliente_id = cli.id " +
                     "INNER JOIN produto prod ON ped.produto_id = prod.id " +
                     "ORDER BY ped.id DESC";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Pedido ped = new Pedido();
                ped.setId(rs.getInt("id"));
                ped.setNomeCliente(rs.getString("nome_cliente"));
                ped.setNomeProduto(rs.getString("nome_produto"));
                ped.setQuantidade(rs.getInt("quantidade"));
                ped.setValorTotal(rs.getDouble("valor_total"));
                ped.setStatus(rs.getString("status"));
                ped.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
                pedidos.add(ped);
            }
        }
        return pedidos;
    }

    // Atualiza o status de um pedido na fila (ex: Em Preparo -> Concluído)
    public void atualizarStatus(int pedidoId, String novoStatus) throws SQLException {
        String sql = "UPDATE pedido SET status = ? WHERE id = ?";
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, novoStatus);
            stmt.setInt(2, pedidoId);
            stmt.executeUpdate();
        }
    }
}
```

### 6.5 Controlador Web (`SistemaServlet.java`)

Para conectar os formulários HTML/JSP às classes DAO acima, o Servlet processa as requisições HTTP `POST` (cadastro de item no cardápio, lançamento de pedido e atualização de status) e `GET` (listagem dos dados na tela):

```java
package br.com.casaflorense.controller;

import br.com.casaflorense.dao.PedidoDAO;
import br.com.casaflorense.dao.ProdutoDAO;
import br.com.casaflorense.model.Produto;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/sistema")
public class SistemaServlet extends HttpServlet {

    private final ProdutoDAO produtoDAO = new ProdutoDAO();
    private final PedidoDAO pedidoDAO = new PedidoDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String acao = req.getParameter("acao");

        try {
            if ("cadastrarProduto".equals(acao)) {
                Produto p = new Produto();
                p.setNome(req.getParameter("nome"));
                p.setCategoria(req.getParameter("categoria"));
                p.setDescricao(req.getParameter("descricao"));
                p.setPreco(Double.parseDouble(req.getParameter("preco")));
                produtoDAO.cadastrar(p);
                req.setAttribute("mensagemSucesso", "Item adicionado ao cardápio da Casa Florense com sucesso!");
            } else if ("registrarPedido".equals(acao)) {
                int clienteId = Integer.parseInt(req.getParameter("clienteId"));
                int produtoId = Integer.parseInt(req.getParameter("produtoId"));
                int quantidade = Integer.parseInt(req.getParameter("quantidade"));
                pedidoDAO.registrarPedido(clienteId, produtoId, quantidade);
                req.setAttribute("mensagemSucesso", "Pedido registrado e enviado para a cozinha!");
            } else if ("atualizarStatus".equals(acao)) {
                int pedidoId = Integer.parseInt(req.getParameter("pedidoId"));
                String novoStatus = req.getParameter("novoStatus");
                pedidoDAO.atualizarStatus(pedidoId, novoStatus);
                req.setAttribute("mensagemSucesso", "Status do pedido atualizado com sucesso!");
            }
        } catch (Exception e) {
            req.setAttribute("mensagemErro", "Erro na operação: " + e.getMessage());
        }
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("listaCardapio", produtoDAO.listarCardapio());
            req.setAttribute("listaPedidos", pedidoDAO.listarPedidosDoDia());
        } catch (Exception e) {
            req.setAttribute("mensagemErro", "Erro ao carregar dados: " + e.getMessage());
        }
        req.getRequestDispatcher("/painel.jsp").forward(req, resp);
    }
}
```

### 6.6 Registro de Execução das Funcionalidades

As Figuras 9 e 10 demonstram as duas funcionalidades implementadas em execução e integradas ao banco de dados `casa_florense_db`:

*(Inserir aqui a Figura 9 – Execução da Funcionalidade 1: Gestão do Cardápio)*

**Figura 9 – Funcionalidade de Gestão do Cardápio em execução conectada ao MySQL.** Fonte: Elaborado pelo autor (2026).

*(Inserir aqui a Figura 10 – Execução da Funcionalidade 2: Registro e Fila de Pedidos)*

**Figura 10 – Funcionalidade de Registro e Consulta de Pedidos em execução.** Fonte: Elaborado pelo autor (2026).

---

## 7. FASE 5 – TESTES DE SOFTWARE

Segundo Sommerville (2019), a fase de testes destina-se a demonstrar que o programa executa o que foi especificado nos requisitos e a identificar eventuais defeitos antes da entrada em operação. Para validar as funcionalidades implementadas no sistema **Casa Florense**, foi elaborado e executado um Plano de Testes contemplando 5 casos de teste funcionais (`CT01` a `CT05`), abrangendo cenários de sucesso (entradas válidas) e de restrição/validação (entradas inválidas).

### 7.1 Plano e Registro de Execução dos Casos de Teste

O Quadro 5 apresenta o plano de testes executado no protótipo, seguindo o modelo oficial da disciplina:

**Quadro 5 – Plano e Execução de Casos de Teste do Sistema Casa Florense**

| ID | O que foi testado | Resultado esperado | Resultado obtido |
| :---: | :--- | :--- | :--- |
| **CT01** | Cadastro de novo item no cardápio com dados válidos (`"Torta Holandesa com Calda"`, categoria `Sobremesa`, `R$ 16,50`). | Produto gravado na tabela `produto` do MySQL e exibido imediatamente na listagem do cardápio com mensagem de confirmação. | **Conforme esperado.** Registro salvo no banco e listado na tela (Figura 11). |
| **CT02** | Tentativa de cadastro de item no cardápio sem preencher o campo obrigatório "Nome do Produto" ou informando preço inválido (`R$ 0,00`). | O sistema deve bloquear a gravação, exibir mensagem de alerta de validação e não inserir registro inconsistente no banco. | **Conforme esperado.** Submissão bloqueada e alerta de validação exibido (Figura 11). |
| **CT03** | Registro de novo pedido com dados válidos (Cliente: `Carlos Eduardo Silva`, Item: `2x Bife à Parmegiana Caseiro` a `R$ 44,90`). | O sistema deve calcular o valor total automaticamente (`R$ 89,80`), gravar na tabela `pedido` com status `"Em Preparo"` e exibir na fila do dia. | **Conforme esperado.** Cálculo exato (`R$ 89,80`) e pedido listado na fila do dia (Figura 12). |
| **CT04** | Tentativa de registrar pedido informando quantidade inválida (zero ou número negativo). | O sistema deve impedir o registro do pedido e apresentar alerta informando que a quantidade mínima é 1 unidade. | **Conforme esperado.** Operação recusada pela validação de regra de negócio (Figura 12). |
| **CT05** | Consulta e atualização do status de um pedido existente na fila do dia (alteração de `"Em Preparo"` para `"Concluído"`). | O sistema deve executar o `UPDATE` na tabela `pedido` do MySQL e atualizar o indicador visual de status na fila do dia. | **Conforme esperado.** Status atualizado para `"Concluído"` no banco e na interface (Figura 12). |

### 7.2 Evidências dos Testes Executados

As Figuras 11 e 12 apresentam as evidências de execução dos casos de teste `CT01` a `CT05` no protótipo do sistema **Casa Florense**:

*(Inserir aqui a Figura 11 – Evidências dos Casos de Teste CT01 e CT02)*

**Figura 11 – Evidência de execução dos Casos de Teste CT01 e CT02 (Cadastro válido e validação de campos obrigatórios no Cardápio).** Fonte: Elaborado pelo autor (2026).

*(Inserir aqui a Figura 12 – Evidências dos Casos de Teste CT03, CT04 e CT05)*

**Figura 12 – Evidência de execução dos Casos de Teste CT03, CT04 e CT05 (Registro de pedido com cálculo automático, validação de quantidade e atualização de status).** Fonte: Elaborado pelo autor (2026).

---

## 8. CONSIDERAÇÕES FINAIS

O desenvolvimento deste Projeto Integrado Interdisciplinar permitiu vivenciar, de forma prática e estruturada, todas as etapas do ciclo de vida da engenharia de software aplicadas a uma necessidade real do cotidiano por meio do sistema **Casa Florense — Culinária Caseira, Lanches e Sobremesas**. A integração entre o levantamento de requisitos, a modelagem UML e relacional (MER), a prototipagem de interfaces, a codificação em Java Web com persistência em MySQL e a execução de testes documentados evidenciou a importância do planejamento prévio antes da escrita do código-fonte.

Entre os principais aprendizados adquiridos, destaca-se a compreensão prática de como os requisitos funcionais se transformam diretamente em classes de domínio, tabelas relacionais no banco de dados e métodos na camada de persistência (DAO). As principais dificuldades enfrentadas concentraram-se no alinhamento inicial entre as chaves estrangeiras do Modelo Entidade-Relacionamento (MER) e as consultas com junção de tabelas (`INNER JOIN`) via JDBC no Java para exibir os nomes dos clientes e dos itens do cardápio na fila de pedidos — desafio que foi superado com o apoio do dicionário de dados e dos testes incrementais realizados.

Conclui-se que o protótipo desenvolvido atingiu integralmente os objetivos propostos, resultando em uma solução funcional, organizada e coerente com as boas práticas de Análise e Desenvolvimento de Sistemas.

---

## 9. REFERÊNCIAS

BEZERRA, Eduardo. **Princípios de Análise e Projeto de Sistemas com UML**. 3. ed. Rio de Janeiro: Elsevier, 2015.

ELMASRI, Ramez; NAVATHE, Shamkant B. **Sistemas de Banco de Dados**. 7. ed. São Paulo: Pearson Education do Brasil, 2019.

PRESSMAN, Roger S.; MAXIM, Bruce R. **Engenharia de Software: uma abordagem profissional**. 9. ed. Porto Alegre: AMGH, 2021.

SOMMERVILLE, Ian. **Engenharia de Software**. 10. ed. São Paulo: Pearson Education do Brasil, 2019.
