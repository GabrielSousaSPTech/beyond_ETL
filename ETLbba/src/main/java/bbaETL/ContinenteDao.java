package bbaETL;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;

public class ContinenteDao {

    private final JdbcTemplate jdbcTemplate;
    private LogDao log;
    public ContinenteDao(Env env) {
        this.jdbcTemplate = new Connection(env).getConnection();
        log = new LogDao(env);
    }


    public Integer insertContinente(String nome){
        if(!nome.isBlank()){
            try{
                String comandoSQL = "INSERT INTO CONTINENTE(NOME) VALUES(?)";
                KeyHolder keyHolder = new GeneratedKeyHolder();

                jdbcTemplate.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement(comandoSQL, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, nome);
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
                String comandoSQL = "SELECT ID_CONTINENTE FROM CONTINENTE WHERE NOME = ?";
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
