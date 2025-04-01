package com.cadastro.controller;

import com.cadastro.model.Pessoa;
import com.cadastro.model.PessoaDAO;
import java.util.List;

public class PessoaController {

    private final PessoaDAO pessoaDAO;

    public PessoaController() {
        this.pessoaDAO = PessoaDAO.getInstance();
    }

    public void salvar(String nome, String telefone, String endereco) {
        Pessoa pessoa = new Pessoa(nome, telefone, endereco);
        pessoaDAO.salvar(pessoa);
    }

    public void atualizar(Long id, String nome, String telefone, String endereco) {
        Pessoa pessoa = new Pessoa(nome, telefone, endereco);
        pessoa.setId(id);
        pessoaDAO.salvar(pessoa);
    }

    public void excluir(Long id) {
        pessoaDAO.excluir(id);
    }

    public Pessoa buscarPorId(Long id) {
        return pessoaDAO.buscarPorId(id);
    }

    public List<Pessoa> buscarTodos() {
        return pessoaDAO.buscarTodos();
    }

    public void fecharConexao() {
        pessoaDAO.fechar();
    }
}