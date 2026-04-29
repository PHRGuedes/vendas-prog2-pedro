package br.com.pedro.vendas.model;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

public class Compra {

    private int id;
    private LocalDate dataCompra;
    private double valorTotal;
    private Fornecedor fornecedor;
    private List<Produto> produtos;


    public Compra() {
        this.produtos = new ArrayList<>();
    }

    public Compra(int id, LocalDate dataCompra, double valorTotal, Fornecedor fornecedor, List<Produto> produtos) {
        this.id = id;
        this.dataCompra = dataCompra;
        this.valorTotal = valorTotal;
        this.fornecedor = fornecedor;
        this.produtos = produtos != null ? produtos : new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(LocalDate dataCompra) {
        this.dataCompra = dataCompra;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }


}
