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
