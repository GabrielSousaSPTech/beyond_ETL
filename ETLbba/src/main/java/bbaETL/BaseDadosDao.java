package bbaETL;

import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;


public class BaseDadosDao {
    private final JdbcTemplate jdbcTemplate;
    private LogDao log;
    public BaseDadosDao(Env env) {
        this.jdbcTemplate = new Connection(env).getConnection();
        log = new LogDao(env);
    }

    public void insertBaseDados(LocalDate dataChegada, Integer chegadas, Integer fkVia, Integer fkPais, Integer fkContinente, Integer fkUf){
        try{
            String comandoSQL = "INSERT INTO TB_BASE_DADOS (DATA_CHEGADA,CHEGADAS, FK_VIA, FK_PAIS, FK_CONTINENTE, FK_FEDERACAO_BRASIL) VALUES(?,?,?,?,?,?)";
            jdbcTemplate.update(comandoSQL, dataChegada, chegadas, fkVia, fkPais, fkContinente, fkUf);
        }catch (Exception e) {
            log.insertLog("ERROR", String.valueOf(e));
        }

    }
}
