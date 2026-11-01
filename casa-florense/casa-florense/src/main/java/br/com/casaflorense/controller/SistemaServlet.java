package br.com.casaflorense.controller;

import br.com.casaflorense.dao.ClienteDAO;
import br.com.casaflorense.dao.PedidoDAO;
import br.com.casaflorense.dao.ProdutoDAO;
import br.com.casaflorense.model.Cliente;
import br.com.casaflorense.model.Produto;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/sistema")
public class SistemaServlet extends HttpServlet {

    private final ProdutoDAO produtoDAO = new ProdutoDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();
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
            } else if ("cadastrarCliente".equals(acao)) {
                Cliente c = new Cliente();
                c.setNome(req.getParameter("nome"));
                c.setTelefone(req.getParameter("telefone"));
                c.setEndereco(req.getParameter("endereco"));
                clienteDAO.cadastrar(c);
                req.setAttribute("mensagemSucesso", "Cliente cadastrado com sucesso!");
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
            req.setAttribute("listaClientes", clienteDAO.listarClientes());
            req.setAttribute("listaPedidos", pedidoDAO.listarPedidosDoDia());
        } catch (Exception e) {
            req.setAttribute("mensagemErro", "Erro ao carregar dados: " + e.getMessage());
        }
        req.getRequestDispatcher("/painel.jsp").forward(req, resp);
    }
}
