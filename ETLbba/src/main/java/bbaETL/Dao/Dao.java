package bbaETL.Dao;

import bbaETL.Connection;
import bbaETL.Env;
import bbaETL.LogDao;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class Dao {
    protected final JdbcTemplate jdbcTemplate;
    protected final LogDao log;

    public Dao(Env env) {
        this.jdbcTemplate = new Connection(env).getJdbcTemplate();
        this.log = new LogDao(env);
    }

    protected void logInfo(String conteudo) {
        log.insertLog("ERROR", conteudo);
    }

    protected void logErro(String conteudo) {
        log.insertLog("ERROR", conteudo);
    }
}
