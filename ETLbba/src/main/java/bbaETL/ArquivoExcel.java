package bbaETL;

import java.io.InputStream;

public class ArquivoExcel {
    private String nome;
    private InputStream inputStream;

    public ArquivoExcel(String nome, InputStream inputStream) {
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
