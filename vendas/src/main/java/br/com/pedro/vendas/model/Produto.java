package br.com.pedro.vendas.model;

import java.util.List;

public class Produto {

    private int id;
    private String nome;
    private double preco;   
    private double precoMedio;
    private int qtdeEstoque;
    private double valorUltimaCompra;
    private double valorUltimaVenda;
    private Categoria categoria;
    private List<Fornecedor> fornecedores;
    private List<Venda> vendas;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public double getPrecoMedio() {
        return precoMedio;
    }

    public void setPrecoMedio(double precoMedio) {
        this.precoMedio = precoMedio;
    }

    public int getQtdeEstoque() {
        return qtdeEstoque;
    }

    public void setQtdeEstoque(int qtdeEstoque) {
        this.qtdeEstoque = qtdeEstoque;
    }

    public double getValorUltimaCompra() {
        return valorUltimaCompra;
    }

    public void setValorUltimaCompra(double valorUltimaCompra) {
        this.valorUltimaCompra = valorUltimaCompra;
    }

    public double getValorUltimaVenda() {
        return valorUltimaVenda;
    }

    public void setValorUltimaVenda(double valorUltimaVenda) {
        this.valorUltimaVenda = valorUltimaVenda;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<Fornecedor> getFornecedores() {
        return fornecedores;
    }

    public void setFornecedores(List<Fornecedor> fornecedores) {
        this.fornecedores = fornecedores;
    }

    public List<Venda> getVendas() {
        return vendas;
    }

    public void setVendas(List<Venda> vendas) {
        this.vendas = vendas;
    }
    public Produto() {
    }

    public Produto(int id, String nome, double precoMedio, int qtdeEstoque, double valorUltimaCompra, double valorUltimaVenda, Categoria categoria, List<Fornecedor> fornecedores, List<Venda> vendas) {

        this.id = id;
        this.nome = nome;
        this.precoMedio = precoMedio;
        this.qtdeEstoque = qtdeEstoque;
        this.valorUltimaCompra = valorUltimaCompra;
        this.valorUltimaVenda = valorUltimaVenda;
        this.categoria = categoria;
        this.fornecedores = fornecedores;
        this.vendas = vendas;

    }


}

