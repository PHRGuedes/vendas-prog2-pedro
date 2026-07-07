package venda.p2.dao;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

public class Conexao {
    private static EntityManagerFactory emf;

    private static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            // Carrega variáveis do arquivo .env
            Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
            
            Map<String, String> properties = new HashMap<>();
            properties.put("jakarta.persistence.jdbc.url", dotenv.get("DB_URL"));
            properties.put("jakarta.persistence.jdbc.user", dotenv.get("DB_USER"));
            properties.put("jakarta.persistence.jdbc.password", dotenv.get("DB_PASSWORD"));

            // Cria o factory com as propriedades lidas
            emf = Persistence.createEntityManagerFactory("siscom", properties);
        }
        return emf;
    }

    public static EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }

    public static void fecharConexao() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
