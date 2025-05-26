package bbaETL;

import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class Connection {

    private final DataSource dataSource;

    // Construtor com configurações dinâmicas (vindo do .env ou similar)
    public Connection(Env env) {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl("jdbc:mysql://" + env.BD_HOST + ":" + env.BD_PORT + "/" + env.BD_DATABASE);
        basicDataSource.setUsername(env.BD_USER);
        basicDataSource.setPassword(env.BD_PASSWORD);
        basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        this.dataSource = basicDataSource;
    }

    // Retorna JdbcTemplate para operações simples
    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }

    // Retorna DataSource para controle avançado (ex: transações manuais)
    public DataSource getDataSource() {
        return this.dataSource;
    }
}
