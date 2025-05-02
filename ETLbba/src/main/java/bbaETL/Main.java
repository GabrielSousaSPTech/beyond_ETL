package bbaETL;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        BucketAWS b = new BucketAWS("bucketgabrielsousasptech");
        Extract e = new Extract();
        Transform t = new Transform();
        Load l = new Load();
        LogDao log = new LogDao(new Connection().getConnection());


        List<Arquivos> arquivos = b.listAllFiles();

        if (arquivos.isEmpty()) {

            log.insertLog("ERRO", "Nenhum arquivo encontrado no bucket. Processo interrompido.");
        } else {
            l.carregarDados(t.tratarDados(e.extrairChegada(arquivos)));
        }
    }
}