# SisCom - Sistema Comercial (vendas-prog2-pedro)

Este é um projeto acadêmico de Sistema Comercial (SisCom), originalmente desenvolvido em Java com JDBC puro e padrão MVC-DAO, agora em processo de evolução para utilizar **JPA/Hibernate**, módulos financeiros e geração de relatórios com **JasperReports**.

## 🚀 Arquitetura e Padrões

O projeto segue o padrão **MVC-DAO** (Model-View-Controller associado ao Data Access Object) e utiliza o **Maven** para gerenciamento de dependências.

- **Model (`venda.p2.model`)**: Contém as entidades do sistema mapeadas para o banco de dados via anotações JPA (`@Entity`, `@Id`, `@OneToMany`, etc). Inclui módulos base (Produto, Venda, Cliente) e novos módulos (Compra, Financeiro, Usuario).
- **DAO (`venda.p2.dao`)**: Responsável pela persistência. Toda a comunicação com o banco PostgreSQL é feita pelo `EntityManager` do JPA, substituindo as antigas `PreparedStatement` do JDBC. O arquivo `Conexao.java` funciona como um provedor de `EntityManagerFactory`.
- **Controller (`venda.p2.controller`)**: Onde residem as **Regras de Negócio**.
  - *Estoque*: Ao realizar uma Compra/Venda, o estoque é incrementado/decrementado. Vendas bloqueiam se o estoque for `< 1`.
  - *Preço Médio*: Ao realizar compras, o sistema calcula a Média Aritmética Simples (`(precoAtual + valorCompra) / 2`).
  - *Financeiro*: Compras geram títulos a pagar automaticamente (`pagarOuReceber = 0`), e Vendas geram títulos a receber (`pagarOuReceber = 1`).
  - *Restrições*: Limite de 3 vendas mensais por CPF.
- **View (`venda.p2.view`)**: Interface construída em Console (CLI interativo) focada na operação de regras do backend.

## ⚙️ Configuração do Ambiente

As credenciais do banco de dados não ficam mais fixas no código-fonte por motivos de segurança (RNF-T003). Utilizamos variáveis de ambiente.

1. Instale o **PostgreSQL** na sua máquina.
2. Crie um banco de dados vazio chamado `SisCom_comercial_prog2_pedro`.
3. Na pasta `venda_p2`, crie um arquivo chamado `.env` (baseado no `.env.example` existente):
   ```env
   DB_URL=jdbc:postgresql://localhost:5432/SisCom_comercial_prog2_pedro
   DB_USER=seu_usuario_postgres
   DB_PASSWORD=sua_senha_postgres
   ```
4. O JPA (via `persistence.xml` + `Conexao.java`) usará esse `.env` para conectar. A propriedade `hibernate.hbm2ddl.auto = update` fará com que as tabelas sejam criadas automaticamente na primeira execução.

## 📝 Relatórios e Logs

- **Log4j**: Todos os acessos a dados (DAOs) e lógicas de Controllers emitem logs (INFO/ERROR) que são impressos no terminal e salvos em arquivo.
- **JasperReports**: O sistema gera relatórios em `.pdf` salvos fisicamente em disco, filtrando Vendas, Compras e Contas a Pagar/Receber.