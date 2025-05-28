package bbaETL;

import bbaETL.Dao.Dao;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.sql.Connection;

public class BaseDadosDao extends Dao {
    public BaseDadosDao(Env env) {
        super(env);
    }

    public void insertBaseDados(List<ObjetoInsercao> objInsert){
        final int batchSize = 5000;
        String comandoSQL = "INSERT INTO TB_BASE_DADOS (DATA_CHEGADA, CHEGADAS, FK_VIA, FK_PAIS, FK_CONTINENTE, FK_FEDERACAO_BRASIL) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(comandoSQL)) {
                int count = 0;

                for (ObjetoInsercao bd : objInsert) {
                    ps.setDate(1, bd.getData());
                    ps.setInt(2, bd.getChegadas());
                    ps.setInt(3, bd.getIdVia());
                    ps.setInt(4, bd.getIdPais());
                    ps.setInt(5, bd.getIdContinente());
                    ps.setInt(6, bd.getIdUf());
                    ps.addBatch();

                    if (++count % batchSize == 0) {
                        ps.executeBatch();
                        conn.commit();
                    }
                }

                ps.executeBatch();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                logErro("Erro no batch insert: " + e.getMessage());
            }
        } catch (Exception e) {
            logErro("Erro na conexão: " + e.getMessage());
        }
    }
}
