package bbaETL;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConvertExcel {

    public static void convertFileToXLSX(String inputPath, BucketAWS bucket, String outputPathBucket) {

        File directory = new File(inputPath);

        if (directory.isDirectory()) {
            System.out.println("Pasta Inserida: " + inputPath);
            File[] files = directory.listFiles();
            if (files != null) {
                System.out.println(files.length+" arquivo(s) Encontrados dentro de " + directory.getPath());
                for (File file : files) {
                    if (file.isFile()) {
                        List<String> fileContent = new ArrayList<>();
                        System.out.println("Tentando converter: " + file.getName());
                        System.out.println("Tamanho de Arquivo: "+file.length());
                        try (Scanner scanner = new Scanner(file, StandardCharsets.ISO_8859_1)) {
                            int linhasLidas = 0;
                            while (scanner.hasNextLine()) {
                                String line = scanner.nextLine();
                                fileContent.add(line);
                                linhasLidas++;
                            }
                            System.out.println("Linhas lidas: " + linhasLidas);
                        } catch (RuntimeException | IOException e) {
                            throw new RuntimeException(e);
                        }

                        System.out.println("Arquivo lido com sucesso");
                        System.out.println("Iniciando conversão");
                        try (Workbook workbook = new XSSFWorkbook()) {
                            Sheet sheet = workbook.createSheet("Planilha1");
                            int celulasCriadas = 0;
                            for (int i = 0; i < fileContent.size(); i++) {
                                Row row = sheet.createRow(i);
                                String[] fields = fileContent.get(i).split(";");

                                for (int j = 0; j < fields.length; j++) {
                                    celulasCriadas++;
                                    Cell cell = row.createCell(j);
                                    cell.setCellValue(fields[j]);
                                }
                            }
                            System.out.println("Celulas criadas: "+celulasCriadas);

                            String outputFilePath = getString("ETLbba/converted/", file);

                            try (FileOutputStream out = new FileOutputStream(outputFilePath)) {
                                workbook.write(out);
                                System.out.println("Arquivo XLSX criado com sucesso em: " + outputFilePath);
                                bucket.putObject(outputPathBucket+outputFilePath, outputFilePath);
                                System.out.println("Item inserido no bucket em: "+outputPathBucket+outputFilePath);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else {
                        System.out.println("Não foi possivel listar arquivos neste caminho: " + inputPath);
                    }
                }
            }
        } else {
            System.out.println("The specified path is not a directory or does not exist: " + inputPath);
        }
    }

    private static String getString(String outputPathBucket, File file) {
        String originalFileName = file.getName();
        String baseFileName;

        int lastDotIndex = originalFileName.lastIndexOf('.');

        if (lastDotIndex > 0) {
            baseFileName = originalFileName.substring(0, lastDotIndex);
        } else {
            baseFileName = originalFileName;
        }

        return outputPathBucket + baseFileName + ".xlsx";
    }
}