package bbaETL;

import org.springframework.jdbc.core.JdbcTemplate;

public class LogDao {

    private final JdbcTemplate jdbcTemplate;
    public LogDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertLog(String tipo, String log){
        if(!log.isBlank()){
            try{
                String comandoSQL = "INSERT INTO TB_ETL_LOG(TIPO_LOG, DESCRICAO) VALUES(?,?)";
                jdbcTemplate.update(comandoSQL, tipo, log);
            }catch(Exception e){
                System.out.println("Erro ao inserir log: " + e.getMessage());
            }
        }
    }
}
