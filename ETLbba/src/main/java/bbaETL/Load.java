package bbaETL;

import java.util.List;

public class Load {
    private Env env;
    private LogDao log;
    public Load(Env env) {
        this.env = env;
        log = new LogDao(env);
    }
    public void carregarDados(List<DadoTratado> dadosAcarregar){

        ContinenteDao continente = new ContinenteDao(env);
        UfDao uf = new UfDao(env);
        ViaDao via = new ViaDao(env);
        PaisDao pais = new PaisDao(env);
        BaseDadosDao baseDados = new BaseDadosDao(env);
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
            beyondSlack slack = new beyondSlack(env.TOKEN_SLACK);
            slack.enviarMensagem("C08SPL9KM3L", "Olá nicolly e fillipe");

        }catch (Exception e){
            log.insertLog("ERROR", String.valueOf(e));
        }
    }
}
