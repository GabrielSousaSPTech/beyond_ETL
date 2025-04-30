package bbaETL;

public class Main {
    public static void main(String[] args) {


        BucketAWS b = new BucketAWS("bucketgabrielsousasptech");
        Extract e = new Extract();
        Transform t = new Transform();
        System.out.println( t.tratarDados(e.extrairChegada(b.listAllFiles())));
    }
}