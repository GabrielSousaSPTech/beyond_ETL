package bbaETL;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import software.amazon.awssdk.services.s3.model.S3Object;

public class Extract {

    public List<ChegadaTuristas> extrairChegada(List<Arquivos> nomeArquivo) {
            try {
                List<ChegadaTuristas> dadosExtraidos = new ArrayList<>();
                for (Arquivos arquivoAtual : nomeArquivo) {
                    System.out.println("\nIniciando leitura do arquivo %s\n".formatted(nomeArquivo));

                    Workbook workbook = new XSSFWorkbook(arquivoAtual.getInputStream());


                    Sheet planilha = workbook.getSheetAt(0);


                    for (Row linha : planilha) {

                        if (linha.getRowNum() == 0) {
                            System.out.println("\nLendo Cabeçalho");

                            for (int i = 0; i < linha.getLastCellNum(); i++) {
                                String coluna = linha.getCell(i) != null ? linha.getCell(i).getStringCellValue() : "(vazio)";
                                System.out.println("Coluna" + i + ": " + coluna);
                            }
                            continue;

                        }
                        if (arquivoAtual.getNome().endsWith("xlsx")) {
                            ChegadaTuristas dadoTurista = new ChegadaTuristas();
                            dadoTurista.setContinente(linha.getCell(0).getStringCellValue());
                            dadoTurista.setCodContinente((int) linha.getCell(1).getNumericCellValue());
                            dadoTurista.setPais(linha.getCell(2).getStringCellValue());
                            dadoTurista.setCodPais((int) linha.getCell(3).getNumericCellValue());
                            dadoTurista.setUf(linha.getCell(4).getStringCellValue());
                            dadoTurista.setCodUf((int) linha.getCell(5).getNumericCellValue());
                            dadoTurista.setVia(linha.getCell(6).getStringCellValue());
                            dadoTurista.setCodVia((int) linha.getCell(7).getNumericCellValue());
                            dadoTurista.setAno((int) linha.getCell(8).getNumericCellValue());
                            dadoTurista.setMes(linha.getCell(9).getStringCellValue());
                            dadoTurista.setCodMes((int) linha.getCell(10).getNumericCellValue());
                            dadoTurista.setChegadas((int) linha.getCell(11).getNumericCellValue());

                            dadosExtraidos.add(dadoTurista);
                        } else {
                            System.out.println("Nenhum arquivo para ler");
                        }
                    }

                    workbook.close();

                    System.out.println("\nLeitura do arquivo: "+ arquivoAtual.getNome() + " finalizda\n");

                    BucketAWS b = new BucketAWS("bucketgabrielsousasptech");
                    b.moveFileToProcessed(arquivoAtual.getNome());
                    System.out.println(arquivoAtual.getNome());
                }
                return dadosExtraidos;

            } catch (IOException e) {

                throw new RuntimeException(e);
            }


    }
}
