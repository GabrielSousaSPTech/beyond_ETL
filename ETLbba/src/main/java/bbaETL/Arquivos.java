package bbaETL;

import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.io.InputStream;

public class Arquivos {
    private String nome;
    private InputStream inputStream;

    public Arquivos (String nome, InputStream inputStream) {
        this.nome = nome;
        this.inputStream = inputStream;
    }

    public String getNome() {
        return nome;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    @Override
    public String toString() {
        return "Arquivos{" +
                "nome='" + nome + '\'' +
                ", inputStream=" + inputStream +
                '}';
    }
}
