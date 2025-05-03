package bbaETL;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.FileNotFoundException;

public class Workflow {
    public static void main(String[] args) throws FileNotFoundException {
        //Dotenv dotenv = Dotenv.configure().directory("C:caminhoAtéAPastaDoEnv").load();
        //dotenv.get("NOME_DA_VARIAVEL");

        BucketAWS bucketAWS = new BucketAWS("bu-beyond-etl");
        ConvertExcel.convertFileToXLSX("ETLbba/csvFiles", bucketAWS, "/out/");
    }
}
