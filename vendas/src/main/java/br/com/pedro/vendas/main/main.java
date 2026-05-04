package br.com.pedro.vendas.main;

import java.time.LocalDate;
import java.util.*;

import br.com.pedro.vendas.model.*;
import br.com.pedro.vendas.repository.*;
import br.com.pedro.vendas.service.*;

public class Main {

    static Scanner sc = new Scanner(System.in);

    static ProdutoRepository produtoRepo = new ProdutoRepository();
    static CategoriaRepository categoriaRepo = new CategoriaRepository();
    static ClienteRepository clienteRepo = new ClienteRepository();
    static FornecedorRepository fornecedorRepo = new FornecedorRepository();

    static VendaService vendaService = new VendaService();
    static CompraService compraService = new CompraService();

// ================= MENU =================
       public static void main(String[] args) {

        while (true) {
            System.out.println("\n===== MENU =====");
            System.out.println("1 - Produto");
            System.out.println("2 - Cliente");
            System.out.println("3 - Fornecedor");
            System.out.println("4 - Venda");
            System.out.println("5 - Compra");
            System.out.println("6 - Categoria");
            System.out.println("0 - Sair");

            int op = sc.nextInt();

            switch (op) {
                case 1: menuProduto(); break;
                case 2: menuCliente(); break;
                case 3: menuFornecedor(); break;
                case 4: realizarVenda(); break;
                case 5: realizarCompra(); break;
                case 6: menuCategoria(); break;
                case 0: return;
            }
        }
    }

    // ================= PRODUTO =================
    public static void menuProduto() {

        System.out.println("\n--- PRODUTO ---");
        System.out.println("1 - Listar");
        System.out.println("2 - Cadastrar");
        System.out.println("3 - Atualizar");
        System.out.println("4 - Deletar");

        int op = sc.nextInt();

        if (op == 1) {
            for (Produto p : produtoRepo.buscarTodos()) {
                System.out.printf(
                "ID: %d | Nome: %s | Preço: %.2f | Estoque: %d | Médio: %.2f | Últ. Compra: %.2f | Últ. Venda: %.2f%n",
                p.getId(),
                p.getNome(),
                p.getPreco(),
                p.getQtdeEstoque(),
                p.getPrecoMedio(),
                p.getValorUltimaCompra(),
                p.getValorUltimaVenda()
                );
            }
        }

        if (op == 2) {
            sc.nextLine();
            
            String continuarAddProd = "s";

            while (continuarAddProd.equalsIgnoreCase("s")) {

                Produto novo = new Produto();

                System.out.print("Nome: ");
                novo.setNome(sc.nextLine());

                System.out.print("Preço: ");
                novo.setPreco(sc.nextDouble());

                System.out.print("Estoque: ");
                novo.setQtdeEstoque(sc.nextInt());

                System.out.println("Escolha a categoria:");
                for (Categoria c : categoriaRepo.buscarTodos()) {
                    System.out.println(c.getId() + " - " + c.getNome());
                }

                int catId = sc.nextInt();

                Categoria c = new Categoria();
                c.setId(catId);

                novo.setCategoria(c);

                produtoRepo.salvar(novo);

                sc.nextLine(); //  limpa o ENTER do nextInt

                System.out.print("Deseja cadastrar mais um produto? (s/n): ");
                continuarAddProd = sc.nextLine();
            }
        }

        if (op == 3) {
            sc.nextLine();
            String continuarAttProd = "s";

            while (continuarAttProd.equalsIgnoreCase("s")) {

                System.out.print("ID do produto para atualizar: ");
                int idAtualizar = sc.nextInt();
                sc.nextLine();

                Produto produtoAtualizar = null;

                for (Produto p : produtoRepo.buscarTodos()) {
                    if (p.getId() == idAtualizar) {
                        produtoAtualizar = p;
                        break;
                    }
                }

                if (produtoAtualizar != null) {

                    System.out.print("Novo Nome (atual: " + produtoAtualizar.getNome() + "): ");
                    produtoAtualizar.setNome(sc.nextLine());

                    System.out.print("Novo Preço (atual: " + produtoAtualizar.getPreco() + "): ");
                    produtoAtualizar.setPreco(sc.nextDouble());

                    System.out.print("Novo Estoque (atual: " + produtoAtualizar.getQtdeEstoque() + "): ");
                    produtoAtualizar.setQtdeEstoque(sc.nextInt());

                    System.out.println("Escolha a nova categoria (atual: " + produtoAtualizar.getCategoria().getId() + "): ");
                    for (Categoria c : categoriaRepo.buscarTodos()) {
                        System.out.println(c.getId() + " - " + c.getNome());
                    }

                    int novaCatId = sc.nextInt();

                    Categoria novaCategoria = new Categoria();
                    novaCategoria.setId(novaCatId);

                    produtoAtualizar.setCategoria(novaCategoria);

                    produtoRepo.atualizar(produtoAtualizar);

                    System.out.println("Produto atualizado com sucesso!");
                } else {
                    System.out.println("Produto não encontrado.");
                }

                sc.nextLine(); // 🔥 limpar buffer

                System.out.print("Deseja atualizar mais um produto? (s/n): ");
                continuarAttProd = sc.nextLine();
            }
        }

        if (op == 4) {
            String continuarDelProd = "s";

            while (continuarDelProd.equalsIgnoreCase("s")) {

                System.out.print("ID do produto para deletar: ");
                int idDeletar = sc.nextInt();

                produtoRepo.deletar(idDeletar); 

                sc.nextLine(); // limpar buffer

                System.out.print("Deseja deletar outro produto? (s/n): ");
                continuarDelProd = sc.nextLine();
}
        }

    }

    // ================= CLIENTE =================
    public static void menuCliente() {

        System.out.println("\n--- CLIENTE ---");
        System.out.println("1 - Listar");
        System.out.println("2 - Cadastrar");
        System.out.println("3 - Atualizar");
        System.out.println("4 - Deletar");
        int op = sc.nextInt();

        if (op == 1) {
            for (Cliente c : clienteRepo.buscarTodos()) {
                System.out.println(c.getId() + " | " + c.getNome() + " | CPF: " + c.getCpf());
            }
        }

        if (op == 2) {
            sc.nextLine();

            String continuarAddCliente = "s";
              while (continuarAddCliente.equalsIgnoreCase("s")) {

                Cliente novo = new Cliente();

                System.out.print("Nome: ");
                novo.setNome(sc.nextLine());

                System.out.print("CPF: ");
                novo.setCpf(sc.nextLine());

                System.out.print("RG: ");
                novo.setRg(sc.nextLine());

                System.out.print("Endereço: ");
                novo.setEndereco(sc.nextLine());

                System.out.print("Telefone: ");
                novo.setTelefone(sc.nextLine());

                clienteRepo.salvar(novo);
                

                System.out.print("Deseja cadastrar mais um cliente? (s/n): ");
                continuarAddCliente = sc.nextLine();

              }
            
        }

        if (op == 3) {
            sc.nextLine();
            String continuarAttCliente = "s";

            while (continuarAttCliente.equalsIgnoreCase("s")) {

                System.out.print("ID do cliente para atualizar: ");
                int idAtualizar = sc.nextInt();
                sc.nextLine(); // limpar buffer

                Cliente clienteAtualizar = clienteRepo.buscarPorId(idAtualizar);
                if (clienteAtualizar != null) {

                    System.out.print("Novo Nome (atual: " + clienteAtualizar.getNome() + "): ");
                    clienteAtualizar.setNome(sc.nextLine());

                    System.out.print("Novo CPF (atual: " + clienteAtualizar.getCpf() + "): ");
                    clienteAtualizar.setCpf(sc.nextLine());

                    System.out.print("Novo RG (atual: " + clienteAtualizar.getRg() + "): ");
                    clienteAtualizar.setRg(sc.nextLine());

                    System.out.print("Novo Endereço (atual: " + clienteAtualizar.getEndereco() + "): ");
                    clienteAtualizar.setEndereco(sc.nextLine());

                    System.out.print("Novo Telefone (atual: " + clienteAtualizar.getTelefone() + "): ");
                    clienteAtualizar.setTelefone(sc.nextLine());

                    clienteRepo.atualizar(clienteAtualizar);

                    System.out.println("Cliente atualizado com sucesso!");

                } else {
                    System.out.println("Cliente não encontrado.");
                }

                System.out.print("Deseja atualizar mais um cliente? (s/n): ");
                continuarAttCliente = sc.nextLine();
            }
        }

        if (op == 4) {

            String continuarDelCliente = "s";

            while (continuarDelCliente.equalsIgnoreCase("s")) {

                System.out.print("ID do cliente para deletar: ");
                int idDeletar = sc.nextInt();

                clienteRepo.deletar(idDeletar); 

                sc.nextLine(); // limpar buffer

                System.out.print("Deseja deletar outro cliente? (s/n): ");
                continuarDelCliente = sc.nextLine();
            }
        }
    }

    // ================= FORNECEDOR =================
    public static void menuFornecedor() {

        System.out.println("\n--- FORNECEDOR ---");
        System.out.println("1 - Listar");
        System.out.println("2 - Cadastrar");
        System.out.println("3 - Atualizar");
        System.out.println("4 - Deletar");

        int op = sc.nextInt();

        if (op == 1) {
            for (Fornecedor f : fornecedorRepo.buscarTodos()) {
                System.out.println(f.getId() + " | " + f.getNomeFantasia() + " | CNPJ: " + f.getCnpj());
            }
        }

        if (op == 2) {
            sc.nextLine();
            String continuarAddForn = "s";

            while (continuarAddForn.equalsIgnoreCase("s")) {

                Fornecedor novo = new Fornecedor();

                System.out.print("Nome Fantasia: ");
                novo.setNomeFantasia(sc.nextLine());

                System.out.print("Razão Social: ");
                novo.setRazaoSocial(sc.nextLine());

                System.out.print("CNPJ: ");
                novo.setCnpj(sc.nextLine());

                fornecedorRepo.salvar(novo);
                

                System.out.print("Deseja cadastrar mais um fornecedor? (s/n): ");
                continuarAddForn = sc.nextLine();
            }
            
        }

        if (op == 3) {
            sc.nextLine();

            String continuarAttForn = "s";

            while (continuarAttForn.equalsIgnoreCase("s")) {

                System.out.print("ID do fornecedor para atualizar: ");
                int idAtualizar = sc.nextInt();
                sc.nextLine();

                Fornecedor fornecedorAtualizar = fornecedorRepo.buscarPorId(idAtualizar);

                if (fornecedorAtualizar != null) {

                    System.out.print("Novo Nome Fantasia (atual: " + fornecedorAtualizar.getNomeFantasia() + "): ");
                    fornecedorAtualizar.setNomeFantasia(sc.nextLine());

                    System.out.print("Nova Razão Social (atual: " + fornecedorAtualizar.getRazaoSocial() + "): ");
                    fornecedorAtualizar.setRazaoSocial(sc.nextLine());

                    System.out.print("Novo CNPJ (atual: " + fornecedorAtualizar.getCnpj() + "): ");
                    fornecedorAtualizar.setCnpj(sc.nextLine());

                    fornecedorRepo.atualizar(fornecedorAtualizar);

                    System.out.println("Fornecedor atualizado com sucesso!");

                } else {
                    System.out.println("Fornecedor não encontrado.");
                }

                System.out.print("Deseja atualizar mais um fornecedor? (s/n): ");
                continuarAttForn = sc.nextLine();
            }
        }

        if (op == 4) {
            String continuar = "s";

            while (continuar.equalsIgnoreCase("s")) {

                System.out.print("ID do fornecedor para deletar: ");
                int idDeletar = sc.nextInt();

                fornecedorRepo.deletar(idDeletar); 

                sc.nextLine(); // limpar buffer

                System.out.print("Deseja deletar outro fornecedor? (s/n): ");
                continuar = sc.nextLine();
            }
        }
    }

    // ================= VENDA =================
    public static void realizarVenda() {

        sc.nextLine();

        System.out.print("CPF: ");
        String cpf = sc.nextLine();

        Cliente cliente = clienteRepo.buscarPorCpf(cpf);

        if (cliente == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }

        System.out.println("Produtos:");
        for (Produto p : produtoRepo.buscarTodos()) {
            System.out.println(p.getId() + " - " + p.getNome());
        }

        Map<Produto, Integer> itens = new HashMap<>();
        String continuar = "s";

        while (continuar.equalsIgnoreCase("s")) {

            System.out.print("ID do produto: ");
            int idProduto = sc.nextInt();

            Produto produto = produtoRepo.buscarPorId(idProduto);

            if (produto == null) {
                System.out.println("Produto não encontrado!");
            } else {

                System.out.print("Quantidade: ");
                int quantidade = sc.nextInt();

                itens.put(produto, quantidade);
            }

            sc.nextLine(); // limpar buffer

            System.out.print("Adicionar outro produto? (s/n): ");
            continuar = sc.nextLine();
        }

        if (!itens.isEmpty()) {
            vendaService.realizarVenda(cliente, itens);
        } else {
            System.out.println("Nenhum produto adicionado à venda.");
        }
    }

    // ================= COMPRA =================
    public static void realizarCompra() {

        System.out.println("Produtos:");
        for (Produto p : produtoRepo.buscarTodos()) {
            System.out.println(p.getId() + " - " + p.getNome());
        }

        List<Produto> produtosCompra = new ArrayList<>();
        Map<Integer, Integer> quantidades = new HashMap<>();
        String continuarAdicionandoProduto = "s";

        while (continuarAdicionandoProduto.equalsIgnoreCase("s")) {
            System.out.print("ID do produto: ");
            int idProduto = sc.nextInt();
            sc.nextLine(); // Consumir a nova linha

            Produto BuscarProdId = produtoRepo.buscarPorId(idProduto);

            if (BuscarProdId == null) {
                System.out.println("Produto não encontrado!");
            } else {
                System.out.print("Quantidade para " + BuscarProdId.getNome() + ": ");
                int quantidadeProduto = sc.nextInt();
                sc.nextLine(); // Consumir a nova linha

                produtosCompra.add(BuscarProdId);
                quantidades.put(BuscarProdId.getId(), quantidadeProduto);

                System.out.print("Adicionar outro produto à compra? (s/n): ");
                continuarAdicionandoProduto = sc.nextLine();
            }
        }

        if (produtosCompra.isEmpty()) {
            System.out.println("Nenhum produto adicionado à compra.");
            return;
        }

        System.out.print("Data da compra (AAAA-MM-DD): ");
        LocalDate data = LocalDate.parse(sc.nextLine());

        System.out.println("Fornecedores:");
        for (Fornecedor f : fornecedorRepo.buscarTodos()) {
            System.out.println(f.getId() + " - " + f.getNomeFantasia());
        }

        System.out.print("ID do fornecedor: ");
        int fornecedorId = sc.nextInt();
        sc.nextLine(); // Consumir a nova linha

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setId(fornecedorId);

        compraService.realizarCompra(produtosCompra, quantidades, data, fornecedor);
    }

    // ================= CATEGORIA =================
    public static void menuCategoria() {

        System.out.println("\n--- CATEGORIA ---");
        System.out.println("1 - Listar");
        System.out.println("2 - Cadastrar");
        System.out.println("3 - Atualizar");
        System.out.println("4 - Deletar");
        int op = sc.nextInt();

        if (op == 1) {
            for (Categoria c : categoriaRepo.buscarTodos()) {
                System.out.printf("ID: %d | Nome: %s%n", c.getId(), c.getNome());
            }
        }

        if (op == 2) {

            sc.nextLine(); // limpar buffer do menu

            String continuar = "s";

            while (continuar.equalsIgnoreCase("s")) {

                Categoria c = new Categoria();

                System.out.print("Nome: ");
                c.setNome(sc.nextLine());

                categoriaRepo.salvar(c);

                System.out.print("Deseja cadastrar mais uma categoria? (s/n): ");
                continuar = sc.nextLine();
            }
        }

        if (op == 3) {

            String continuar = "s";

            while (continuar.equalsIgnoreCase("s")) {

                System.out.print("ID da categoria para atualizar: ");
                int idAtualizar = sc.nextInt();
                sc.nextLine(); // limpar buffer

                Categoria categoriaAtualizar = categoriaRepo.buscarPorId(idAtualizar); 

                if (categoriaAtualizar != null) {

                    System.out.print("Novo Nome (atual: " + categoriaAtualizar.getNome() + "): ");
                    categoriaAtualizar.setNome(sc.nextLine());

                    categoriaRepo.atualizar(categoriaAtualizar);

                    System.out.println("Categoria atualizada com sucesso!");

                } else {
                    System.out.println("Categoria não encontrada.");
                }

                System.out.print("Deseja atualizar outra categoria? (s/n): ");
                continuar = sc.nextLine();
            }
        }

        if (op == 4) {

            String continuar = "s";

            while (continuar.equalsIgnoreCase("s")) {

                System.out.print("ID da categoria para deletar: ");
                int idDeletar = sc.nextInt();

                categoriaRepo.deletar(idDeletar); 

                sc.nextLine(); // limpar buffer

                System.out.print("Deseja deletar outra categoria? (s/n): ");
                continuar = sc.nextLine();
            }
        }
       
    }
    
}