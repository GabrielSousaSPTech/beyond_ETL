package bbaETL;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Env env = Env.createEnv();

        BucketAWS b = new BucketAWS(env.BUCKET_NAME);
        LogDao log = new LogDao(new Connection(env).getConnection());
        Extract e = new Extract();
        Transform t = new Transform();
        Load l = new Load();



        List<Arquivos> arquivos = b.listAllFiles();

        if (arquivos.isEmpty()) {

            log.insertLog("ERRO", "Nenhum arquivo encontrado no bucket. Processo interrompido.");
        } else {
            l.carregarDados(t.tratarDados(e.extrairChegada(arquivos)));
        }
    }
}