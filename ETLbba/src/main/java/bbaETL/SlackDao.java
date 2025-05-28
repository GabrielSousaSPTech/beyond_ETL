package bbaETL;

import bbaETL.Dao.Dao;

import java.util.List;
import java.util.Collections;

public class SlackDao extends Dao {
    public SlackDao(Env env) {
        super(env);
    }

    public List<String> listarTodosCanais() {
        try {
            String comandoSQL = "SELECT DISTINCT ID_CANAL_SLACK FROM TB_EMPRESA WHERE NOTIFICACAO_STATUS = 1 AND ID_CANAL_SLACK IS NOT NULL;";
            return jdbcTemplate.queryForList(comandoSQL, String.class);
        } catch (Exception e) {
            log.insertLog("ERROR", String.valueOf(e));
            return Collections.emptyList();
        }
    }
}
