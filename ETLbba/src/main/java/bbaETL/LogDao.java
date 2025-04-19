package bbaETL;

import org.springframework.jdbc.core.JdbcTemplate;

public class LogDao {

    private final JdbcTemplate jdbcTemplate;

    public LogDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertLog(String log){
        if(!log.isBlank()){
            try{
                String comandoSQL = "INSERT INTO Registro_Log(descricao) VALUES(?)";
                jdbcTemplate.update(comandoSQL, log);
            }catch(Exception e){
                System.out.println("Erro ao inserir log: " + e.getMessage());
            }
        }
    }
}
