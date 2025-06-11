package bbaETL;

import java.util.*;

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
        Set<String> anosSet = new HashSet<>();

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

                anosSet.add(linhaDadoAtual.getData().toString().substring(0,4));

                dadosInsert.add(new ObjetoInsercao(linhaDadoAtual.getData(), linhaDadoAtual.getChegadas(), idVia, idPais, idContinente, idUf));
                linhasInseridas++;
                iterator.remove();
            }
            baseDados.insertBaseDados(dadosInsert);

            log.insertLog("INFO",
                        "Novos dados chegaram!  Venha conferir nossas dashboards:\n" +
                             "•\tForam inseridas %f linhas referentes ao ano de %d.  \n" +
                             "Beyond Analytics — Um Horizonte de Possibilidades..\n" + linhasInseridas + anosSet.stream().toList());
            Slack slack = new Slack(env);
            slack.enviarParaVariosCanais(String.format("Novos dados chegaram!  Venha conferir nossas dashboards:\n" +
                                                    "•\tForam inseridas %d linhas referentes ao ano de %s.  \n" +
                                                    "Beyond Analytics — Um Horizonte de Possibilidades..\n", linhasInseridas, anosSet.stream().toList()));

        }catch (Exception e){
            log.insertLog("ERROR", String.valueOf(e));
        }
    }
}
