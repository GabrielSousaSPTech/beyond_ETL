package bbaETL;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.paginators.ListObjectsV2Iterable;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class BucketAWS {
    private String bucketName;
    private Region bucketRegion;
    private S3Client client;



    public BucketAWS(String bucketName) {
        this.bucketName = bucketName;
        this.bucketRegion = Region.US_EAST_1;
        this.client = S3Client.builder()
                .region(bucketRegion)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
    }





    public ResponseInputStream<GetObjectResponse> getFile(String fileName) {
        if (fileName == null || fileName.isBlank()) throw new RuntimeException("fileName invalid");

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        ResponseInputStream<GetObjectResponse> s3Object = client.getObject(getObjectRequest);
             return s3Object;
    }

    public List<Arquivos> listAllFiles() {
        ListObjectsV2Iterable response = client.listObjectsV2Paginator(
                ListObjectsV2Request.builder()
                        .bucket(bucketName)
                        .prefix("naFila/")
                        .build()
        );

        List<Arquivos>  listaDeArquivos = new ArrayList<>();

        for (ListObjectsV2Response page : response) {
            for (S3Object object : page.contents()) {

                if(object.key().endsWith("xlsx")){

                    GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                            .bucket(bucketName)
                            .key(object.key())
                            .build();
                    System.out.println(object.key());
                    InputStream inputStream = client.getObject(getObjectRequest);
                    listaDeArquivos.add(new Arquivos(object.key(), inputStream));
                }
            }
        }

        return listaDeArquivos;
    }

    public void moveFileToProcessed(String fileName) {
        if (fileName == null || fileName.isBlank()) {
            throw new RuntimeException("fileName invalid");
        }

        // Define os nomes dos "diretórios"

        String nomeArquivo = fileName.substring(fileName.lastIndexOf("/") + 1);
        String destinationKey = "processado/" + nomeArquivo;

        CopyObjectRequest copyObjectRequest = CopyObjectRequest.builder()
                .sourceBucket(bucketName)
                .sourceKey(fileName)
                .destinationBucket(bucketName)
                .destinationKey(destinationKey)
                .build();


        client.copyObject(copyObjectRequest);


        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        client.deleteObject(deleteObjectRequest);
    }
}


