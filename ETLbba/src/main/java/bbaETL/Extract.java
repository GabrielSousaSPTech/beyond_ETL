package bbaETL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class Extract {


    private Env env;
    private LogDao log;
    public Extract(Env env) {
        this.env = env;
        log = new LogDao(env);
    }

    public List<ChegadaTuristas> extrairChegada(List<Arquivos> nomeArquivo) {
        try {
            List<ChegadaTuristas> dadosExtraidos = new ArrayList<>();

            Iterator<Arquivos> iterator = nomeArquivo.iterator();

            while(iterator.hasNext()) {
                Arquivos arquivoAtual = iterator.next();

                log.insertLog("INFO", "Iniciando leitura do arquivo: " + arquivoAtual.getNome());
                Workbook workbook = new XSSFWorkbook(arquivoAtual.getInputStream());
                Sheet planilha = workbook.getSheetAt(0);

                for (Row linha : planilha) {
                    if (linha.getRowNum() == 0) continue;

                    if (arquivoAtual.getNome().endsWith("xlsx")) {
                        if (linha.getCell(11) != null && (int) linha.getCell(11).getNumericCellValue() != 0) {
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
                        }
                    } else {
                        log.insertLog("ERROR", "Arquivo inválido para leitura: " + arquivoAtual.getNome());
                    }
                }

                workbook.close();

                log.insertLog("INFO", "Leitura do arquivo: " + arquivoAtual.getNome() + " finalizada");

                BucketAWS b = new BucketAWS(env);
                b.moveFileToProcessed(arquivoAtual.getNome());
                log.insertLog("INFO", "Arquivo movido para processados: " + arquivoAtual.getNome());

                iterator.remove();
            }

            return dadosExtraidos;
        } catch (IOException e) {
            log.insertLog("ERRO", "Erro ao extrair dados dos arquivos: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }


}
