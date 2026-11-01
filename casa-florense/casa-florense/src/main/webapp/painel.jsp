<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="br.com.casaflorense.model.Produto" %>
<%@ page import="br.com.casaflorense.model.Cliente" %>
<%@ page import="br.com.casaflorense.model.Pedido" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Casa Florense — Culinária Caseira, Lanches e Sobremesas</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<nav class="navbar navbar-dark bg-dark mb-4">
    <div class="container">
        <span class="navbar-brand fw-bold text-warning">CASA FLORENSE</span>
        <span class="text-light small">Culinária Caseira, Lanches e Sobremesas — Refeições para toda hora</span>
    </div>
</nav>

<div class="container pb-5">

    <% if (request.getAttribute("mensagemSucesso") != null) { %>
        <div class="alert alert-success"><%= request.getAttribute("mensagemSucesso") %></div>
    <% } %>
    <% if (request.getAttribute("mensagemErro") != null) { %>
        <div class="alert alert-danger"><%= request.getAttribute("mensagemErro") %></div>
    <% } %>

    <div class="row g-4">
        <!-- GESTÃO DO CARDÁPIO (RF01) -->
        <div class="col-md-6">
            <div class="card shadow-sm">
                <div class="card-header bg-dark text-white fw-semibold">Gestão do Cardápio (RF01)</div>
                <div class="card-body">
                    <form action="sistema" method="post" class="mb-3">
                        <input type="hidden" name="acao" value="cadastrarProduto">
                        <div class="row g-2 mb-2">
                            <div class="col-8">
                                <input type="text" name="nome" class="form-control form-control-sm" placeholder="Nome do Item" required>
                            </div>
                            <div class="col-4">
                                <select name="categoria" class="form-select form-select-sm">
                                    <option value="Prato Caseiro">Prato Caseiro</option>
                                    <option value="Lanche">Lanche</option>
                                    <option value="Sobremesa">Sobremesa</option>
                                    <option value="Bebida">Bebida</option>
                                </select>
                            </div>
                        </div>
                        <div class="row g-2 mb-2">
                            <div class="col-8">
                                <input type="text" name="descricao" class="form-control form-control-sm" placeholder="Descrição / Ingredientes">
                            </div>
                            <div class="col-4">
                                <input type="number" step="0.01" name="preco" class="form-control form-control-sm" placeholder="Preço (R$)" required>
                            </div>
                        </div>
                        <button type="submit" class="btn btn-success btn-sm">Salvar no Cardápio</button>
                    </form>

                    <h6 class="fw-bold text-secondary">Itens Ativos no Cardápio</h6>
                    <table class="table table-sm table-striped">
                        <thead>
                        <tr><th>ID</th><th>Item</th><th>Categoria</th><th>Preço</th></tr>
                        </thead>
                        <tbody>
                        <%
                            List<Produto> cardapio = (List<Produto>) request.getAttribute("listaCardapio");
                            if (cardapio != null) {
                                for (Produto p : cardapio) {
                        %>
                        <tr>
                            <td>#<%= p.getId() %></td>
                            <td><%= p.getNome() %></td>
                            <td><%= p.getCategoria() %></td>
                            <td>R$ <%= String.format("%.2f", p.getPreco()) %></td>
                        </tr>
                        <%      }
                            }
                        %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <!-- REGISTRO E FILA DE PEDIDOS DO DIA (RF03, RF04, RF05) -->
        <div class="col-md-6">
            <div class="card shadow-sm">
                <div class="card-header bg-dark text-white fw-semibold">Registro e Fila de Pedidos do Dia (RF03 / RF04 / RF05)</div>
                <div class="card-body">
                    <form action="sistema" method="post" class="mb-3">
                        <input type="hidden" name="acao" value="registrarPedido">
                        <div class="row g-2 mb-2">
                            <div class="col-6">
                                <input type="number" name="clienteId" class="form-control form-control-sm" placeholder="ID do Cliente" required>
                            </div>
                            <div class="col-6">
                                <input type="number" name="produtoId" class="form-control form-control-sm" placeholder="ID do Produto" required>
                            </div>
                        </div>
                        <div class="d-flex gap-2">
                            <input type="number" name="quantidade" class="form-control form-control-sm w-25" placeholder="Qtd" value="1" required>
                            <button type="submit" class="btn btn-warning btn-sm fw-bold">Confirmar Pedido</button>
                        </div>
                    </form>

                    <h6 class="fw-bold text-secondary">Fila de Pedidos do Dia</h6>
                    <table class="table table-sm table-striped">
                        <thead>
                        <tr><th>#</th><th>Cliente</th><th>Item</th><th>Qtd</th><th>Total</th><th>Status</th></tr>
                        </thead>
                        <tbody>
                        <%
                            List<Pedido> pedidos = (List<Pedido>) request.getAttribute("listaPedidos");
                            if (pedidos != null) {
                                for (Pedido ped : pedidos) {
                        %>
                        <tr>
                            <td>#<%= ped.getId() %></td>
                            <td><%= ped.getNomeCliente() %></td>
                            <td><%= ped.getNomeProduto() %></td>
                            <td><%= ped.getQuantidade() %></td>
                            <td>R$ <%= String.format("%.2f", ped.getValorTotal()) %></td>
                            <td><span class="badge bg-info text-dark"><%= ped.getStatus() %></span></td>
                        </tr>
                        <%      }
                            }
                        %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
