package bbaETL;

import bbaETL.Dao.Dao;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;

public class PaisDao extends Dao {
    public PaisDao(Env env) {
        super(env);
    }

    public Integer insertPais(String nome, Integer fkContinente){
        if(!nome.isBlank()){
            try{
                String comandoSQL = "INSERT INTO PAIS(NOME_PAIS, FK_CONTINENTE) VALUES(?,?)";
                KeyHolder keyHolder = new GeneratedKeyHolder();

                jdbcTemplate.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement(comandoSQL, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, nome);
                    ps.setInt(2, fkContinente);
                    return ps;
                }, keyHolder);

                return keyHolder.getKey().intValue();

            }catch (Exception e){
                logErro( String.valueOf(e));
            }
        }
        return null;
    }

    public Integer selectByName(String nome) {
        if (!nome.isBlank()) {
            try {
                String comandoSQL = "SELECT ID_PAIS FROM PAIS WHERE NOME_PAIS = ?";
                return jdbcTemplate.queryForObject(comandoSQL, new Object[]{nome}, Integer.class);
            } catch (EmptyResultDataAccessException e) {
                return null;
            } catch (Exception e) {
                logErro( String.valueOf(e));
            }
        }
        return null;
    }
}
