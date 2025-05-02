package bbaETL;

import java.util.List;

public class Load {
    public void carregarDados(List<DadoTratado> dadosAcarregar){
        LogDao log = new LogDao(new Connection().getConnection());
        ContinenteDao continente = new ContinenteDao();
        UfDao uf = new UfDao();
        ViaDao via = new ViaDao();
        PaisDao pais = new PaisDao();
        BaseDadosDao baseDados = new BaseDadosDao();
        log.insertLog("INFO", "Iniciando inserção dos dados no banco de dados");
        try {
            for (DadoTratado linhaDadoAtual : dadosAcarregar) {
                Integer idContinente = continente.selectByName(linhaDadoAtual.getContinente());
                Integer idUf = uf.selectByName(linhaDadoAtual.getUf());
                Integer idVia = via.selectByName(linhaDadoAtual.getVia());
                Integer idPais = pais.selectByName(linhaDadoAtual.getPais());
                if (idContinente == null) {
                    idContinente = continente.insertContinente(linhaDadoAtual.getContinente());
                }
                if (idUf == null) {
                    idUf = uf.insertUf(linhaDadoAtual.getUf());
                }
                if (idVia == null) {
                    idVia = via.insertVia(linhaDadoAtual.getVia());
                }
                if (idPais == null) {
                    idPais = pais.insertPais(linhaDadoAtual.getPais(), idContinente);
                }
                baseDados.insertBaseDados(linhaDadoAtual.getData(), linhaDadoAtual.getChegadas(), idVia, idPais, idContinente, idUf);
            }

            log.insertLog("INFO", "Dados inseridos com sucesso! Linhas inseridas: " + dadosAcarregar.size());

        }catch (Exception e){
            log.insertLog("ERROR", String.valueOf(e));
        }
    }
}
