package bbaETL;

import bbaETL.Dao.Dao;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;

public class ViaDao extends Dao {
    public ViaDao(Env env) {
        super(env);
    }

    public Integer insertVia(String tipo){
        if(!tipo.isBlank()){
            try{
                String comandoSQL = "INSERT INTO VIA(TIPO_VIA) VALUES(?)";
                KeyHolder keyHolder = new GeneratedKeyHolder();
                jdbcTemplate.update(connection ->{
                    PreparedStatement ps = connection.prepareStatement(comandoSQL, PreparedStatement.RETURN_GENERATED_KEYS);
                    ps.setString(1, tipo);

                    return ps;
                }, keyHolder);

                return keyHolder.getKey().intValue();
            }catch (Exception e){
                logErro( String.valueOf(e));
            }
        }
        return null;
    }

    public Integer selectByName(String tipo) {
        if (!tipo.isBlank()) {
            try {
                String comandoSQL = "SELECT ID_VIA FROM VIA WHERE TIPO_VIA = ?";
                return jdbcTemplate.queryForObject(comandoSQL, new Object[]{tipo}, Integer.class);
            } catch (EmptyResultDataAccessException e) {
                return null;
            } catch (Exception e) {
                logErro( String.valueOf(e));
            }
        }
        return null;
    }
}
