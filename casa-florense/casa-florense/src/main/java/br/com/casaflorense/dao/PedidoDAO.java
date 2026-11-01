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
