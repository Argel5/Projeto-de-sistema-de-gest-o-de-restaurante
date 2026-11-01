# Casa Florense — Culinária Caseira, Lanches e Sobremesas
*Refeições para toda hora*

Sistema Web de Gestão de Cardápio, Clientes e Pedidos desenvolvido como protótipo funcional para o **Projeto Integrado Individual**.

## Arquitetura e Tecnologias
- **Linguagem:** Java 11+ (Servlets e JSP - Padrão MVC e DAO)
- **Banco de Dados:** MySQL (`casa_florense_db`) via API JDBC
- **Front-end:** HTML5, CSS3 e Bootstrap 5

## Estrutura do Projeto
- `sql/schema.sql`: Script de criação e povoamento do banco de dados MySQL (`casa_florense_db`).
- `src/main/java/br/com/casaflorense/model/`: Classes de domínio (`Atendente`, `Cliente`, `Produto`, `Pedido`).
- `src/main/java/br/com/casaflorense/util/ConexaoBD.java`: Conexão JDBC com o MySQL.
- `src/main/java/br/com/casaflorense/dao/`: Camada de persistência (`ProdutoDAO`, `ClienteDAO`, `PedidoDAO`).
- `src/main/java/br/com/casaflorense/controller/SistemaServlet.java`: Controlador HTTP (`GET` e `POST`).
- `src/main/webapp/painel.jsp`: Interface web responsiva do sistema.
