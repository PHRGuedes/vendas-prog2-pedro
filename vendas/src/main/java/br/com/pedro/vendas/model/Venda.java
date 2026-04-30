package br.com.pedro.vendas.model;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

public class Venda {

    public int id;
    public LocalDate dataVenda;
    public double valorTotal;
    private Cliente cliente;
    private List<Produto> produtos;

    public Venda() {
    this.produtos = new ArrayList<>();
}

    public Venda(int id, LocalDate dataVenda, double valorTotal, Cliente cliente, List<Produto> produtos) {
        this.id = id;
        this.dataVenda = dataVenda;
        this.valorTotal = valorTotal;
        this.cliente = cliente;
        this.produtos = produtos != null ? new ArrayList<>(produtos) : new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDate dataVenda) {
        this.dataVenda = dataVenda;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Produto> getProdutos() {
        return new ArrayList<>(produtos);
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos != null ? new ArrayList<>(produtos) : new ArrayList<>();
    }

}
