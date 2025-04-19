package bbaETL;

import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;


public class Connection {

    private final DataSource dataSource;

    private Boolean activeConnection;

    public Boolean getActiveConnection() {
        return activeConnection;
    }

    public void setActiveConnection(Boolean activeConnection) {
        this.activeConnection = activeConnection;
    }

    public Connection() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl("jdbc:mysql://54.210.153.246:3306/DbDesafioMySQLRemoto ");
        basicDataSource.setUsername("root");
        basicDataSource.setPassword("urubu100");
        basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        this.activeConnection = true;
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


