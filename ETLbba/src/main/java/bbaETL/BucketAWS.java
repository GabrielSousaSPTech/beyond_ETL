package bbaETL;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.paginators.ListObjectsV2Iterable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BucketAWS {
    private String bucketName;
    private Region bucketRegion;
    private S3Client client;

    public void BucketAWS(String bucketName, Region bucketRegion) {
        this.bucketName = bucketName;
        this.bucketRegion = bucketRegion;
        this.client = S3Client.builder()
                .region(bucketRegion)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
    }

    public List<String> getFile(String fileName) {
        if (fileName == null || fileName.isBlank()) throw new RuntimeException("fileName invalid");

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        try (ResponseInputStream<GetObjectResponse> s3Object = client.getObject(getObjectRequest);
             BufferedReader reader = new BufferedReader(new InputStreamReader(s3Object))) {

            String line;
            List<String> result = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                result.add(line);
            }
            return result;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void listAllFiles() {
        ListObjectsV2Iterable response = client.listObjectsV2Paginator(ListObjectsV2Request.builder().bucket(bucketName).build());


        for (ListObjectsV2Response page : response) {
            for (S3Object object : page.contents()) {
                //TODO: Retornar uma lista de objetos da S3 e mudar de void para list<S3Object object>
            }
        }
    }
}


