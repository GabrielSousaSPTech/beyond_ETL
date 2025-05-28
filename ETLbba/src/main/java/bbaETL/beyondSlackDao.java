package bbaETL;

import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;
import java.util.Collections;

public class beyondSlackDao {
    private final JdbcTemplate jdbcTemplate;
    private final LogDao log;

    public beyondSlackDao(Env env) {
        this.jdbcTemplate = new Connection(env).getConnection();
        this.log = new LogDao(env);
    }

    public List<String> listarTodosCanais() {
        try {
            String comandoSQL = "SELECT DISTINCT ID_CANAL_SLACK FROM TB_EMPRESA WHERE ID_CANAL_SLACK IS NOT NULL;";
            return jdbcTemplate.queryForList(comandoSQL, String.class);
        } catch (Exception e) {
            log.insertLog("ERROR", String.valueOf(e));
            return Collections.emptyList();
        }
    }
}
