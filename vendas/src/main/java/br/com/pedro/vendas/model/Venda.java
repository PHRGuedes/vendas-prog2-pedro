package br.com.pedro.vendas.model;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

public class Venda {

    public int id;
    public LocalDate data_venda;
    public double valor_total;
    private Cliente cliente;
    private List<Produto> produtos;

    public Venda() {
    this.produtos = new ArrayList<>();
}

    public Venda(int id, LocalDate data_venda, double valor_total, Cliente cliente, List<Produto> produtos) {
        this.id = id;
        this.data_venda = data_venda;
        this.valor_total = valor_total;
        this.cliente = cliente;
        this.produtos = produtos != null ? new ArrayList<>(produtos) : new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getData_venda() {
        return data_venda;
    }

    public void setData_venda(LocalDate data_venda) {
        this.data_venda = data_venda;
    }

    public double getValor_total() {
        return valor_total;
    }

    public void setValor_total(double valor_total) {
        this.valor_total = valor_total;
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
