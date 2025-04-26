package bbaETL;

public class Main {
    public static void main(String[] args) {


        BucketAWS b = new BucketAWS("bucketgabrielsousasptech");
        Extract e = new Extract();
        System.out.println( e.extrairChegada(b.listAllFiles()));
    }
}