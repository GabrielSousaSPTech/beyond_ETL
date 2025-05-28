package bbaETL;

import java.util.ArrayList;
import java.util.Iterator;
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
        Iterator<DadoTratado> iterator = dadosAcarregar.iterator();
        log.insertLog("INFO", "Iniciando inserção dos dados no banco de dados");
        try {
            List<ObjetoInsercao> dadosInsert = new ArrayList<>();
            int linhasInseridas = 0;
            while(iterator.hasNext()){
                DadoTratado linhaDadoAtual = iterator.next();
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

<<<<<<< HEAD
            log.insertLog("INFO", "Dados inseridos com sucesso! Linhas inseridas: " + dadosAcarregar.size());
            beyondSlack slack = new beyondSlack(env);
            slack.enviarMensagem("C08SPL9KM3L", "Olá nicolly e fillipe");
=======
                dadosInsert.add(new ObjetoInsercao(linhaDadoAtual.getData(), linhaDadoAtual.getChegadas(), idVia, idPais, idContinente, idUf));
                linhasInseridas++;
                iterator.remove();
            }
            baseDados.insertBaseDados(dadosInsert);

            log.insertLog("INFO", "Dados inseridos com sucesso! Linhas inseridas: " + linhasInseridas);
>>>>>>> b7112101626d279d28f2ccb7454e257fd0a5398c

        }catch (Exception e){
            log.insertLog("ERROR", String.valueOf(e));
        }
    }
}
