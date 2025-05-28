package bbaETL;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Env env = Env.createEnv();

            BucketAWS b = new BucketAWS(env);
            LogDao log = new LogDao(env);
            Extract e = new Extract(env);
            Transform t = new Transform(env);
            Load l = new Load(env);


        List<ArquivoExcel> arquivos = b.listAllFiles();

        if (arquivos.isEmpty()) {

            log.insertLog("ERRO", "Nenhum arquivo encontrado no bucket. Processo interrompido.");
        } else {
            l.carregarDados(t.tratarDados(e.extrairChegada(arquivos)));
        }
    }
}