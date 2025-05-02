package bbaETL;

import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;


public class Connection {

    private final DataSource dataSource;

    public Connection() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl("jdbc:mysql://localhost:3306/beyond_db");
        basicDataSource.setUsername("root");
        basicDataSource.setPassword("393741Gs*");
        basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        this.dataSource = basicDataSource;
    }

    public JdbcTemplate getConnection() {
        return new JdbcTemplate(dataSource);
    }

    public static void testConnection() {
        try {
            JdbcTemplate jdbcTemplate = new Connection().getConnection();
            List<String> bancos = jdbcTemplate.queryForList("SHOW DATABASES", String.class);

            System.out.println("✅ Conectado! Bancos disponíveis:");
            for (String banco : bancos) {
                System.out.println("- " + banco);
            }
        } catch (Exception e) {
            System.out.println("❌ Erro na conexão: " + e.getMessage());
        }
    }


}


