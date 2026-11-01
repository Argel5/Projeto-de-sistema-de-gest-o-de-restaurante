package br.com.casaflorense.dao;

import br.com.casaflorense.model.Cliente;
import br.com.casaflorense.util.ConexaoBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    // Método para cadastrar um novo cliente
    public void cadastrar(Cliente c) throws SQLException {
        if (c.getNome() == null || c.getNome().trim().isEmpty() || c.getTelefone() == null || c.getTelefone().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome e o telefone do cliente são obrigatórios.");
        }
        String sql = "INSERT INTO cliente (nome, telefone, endereco) VALUES (?, ?, ?)";
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, c.getNome());
            stmt.setString(2, c.getTelefone());
            stmt.setString(3, c.getEndereco());
            stmt.executeUpdate();
        }
    }

    // Método para listar os clientes cadastrados
    public List<Cliente> listarClientes() throws SQLException {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM cliente ORDER BY id ASC";
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setTelefone(rs.getString("telefone"));
                c.setEndereco(rs.getString("endereco"));
                lista.add(c);
            }
        }
        return lista;
    }

    // Busca o ID de um cliente pelo nome ou cria automaticamente caso ainda não exista
    public int buscarOuCriarPorNome(String nome) throws SQLException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do cliente é obrigatório.");
        }
        String nomeLimpo = nome.trim();
        String sqlBusca = "SELECT id FROM cliente WHERE LOWER(nome) = LOWER(?) LIMIT 1";
        String sqlInsere = "INSERT INTO cliente (nome, telefone, endereco) VALUES (?, 'Balcão', 'Consumo no Local')";

        try (Connection conn = ConexaoBD.getConexao()) {
            try (PreparedStatement stmtBusca = conn.prepareStatement(sqlBusca)) {
                stmtBusca.setString(1, nomeLimpo);
                try (ResultSet rs = stmtBusca.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("id");
                    }
                }
            }

            try (PreparedStatement stmtInsere = conn.prepareStatement(sqlInsere, Statement.RETURN_GENERATED_KEYS)) {
                stmtInsere.setString(1, nomeLimpo);
                stmtInsere.executeUpdate();
                try (ResultSet rsKeys = stmtInsere.getGeneratedKeys()) {
                    if (rsKeys.next()) {
                        return rsKeys.getInt(1);
                    }
                }
            }
        }
        throw new SQLException("Não foi possível identificar ou cadastrar o cliente.");
    }
}
