package bbaETL;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;

public class UfDao {
    private final JdbcTemplate jdbcTemplate;
    private LogDao log;
    public UfDao(Env env) {
        this.jdbcTemplate = new Connection(env).getConnection();
        log = new LogDao(env);
    }


    public Integer insertUf(String uf){
        if(!uf.isBlank()){
            try{
                String comandoSQL = "INSERT INTO FEDERACAO_BRASIL(NOME) VALUES(?)";

                KeyHolder keyHolder = new GeneratedKeyHolder();
                jdbcTemplate.update(connection ->{
                    PreparedStatement ps = connection.prepareStatement(comandoSQL, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, uf);
                    return ps;
                }, keyHolder);

                return keyHolder.getKey().intValue();
            }catch (Exception e){
                log.insertLog("ERROR", String.valueOf(e));
            }
        }
        return null;
    }

    public Integer selectByName(String nome) {
        if (!nome.isBlank()) {
            try {
                String comandoSQL = "SELECT ID_FEDERACAO_BRASIL FROM FEDERACAO_BRASIL WHERE NOME = ?";
                return jdbcTemplate.queryForObject(comandoSQL, new Object[]{nome}, Integer.class);
            } catch (EmptyResultDataAccessException e) {
                return null;
            } catch (Exception e) {
                log.insertLog("ERROR", String.valueOf(e));
            }
        }
        return null;
    }
}
