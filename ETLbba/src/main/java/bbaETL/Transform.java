package bbaETL;

import java.time.LocalDate;
import java.util.*;
import java.sql.Date;

public class Transform {


    private LogDao log;
    public Transform(Env env) {
        log = new LogDao(env);
    }

    public static Integer formatarMes(String mes){
        return switch (mes.toLowerCase()) {
            case "janeiro" -> 1;
            case "fevereiro" -> 2;
            case "março", "marco" -> 3;
            case "abril" -> 4;
            case "maio" -> 5;
            case "junho" -> 6;
            case "julho" -> 7;
            case "agosto" -> 8;
            case "setembro" -> 9;
            case "outubro" -> 10;
            case "novembro" -> 11;
            case "dezembro" -> 12;
            default -> 0;
        };
    }

    private String gerarChaveUnificacao(DadoChegadaOriginal d) {
        return String.join("_",
                String.valueOf(d.getContinente()),
                String.valueOf(d.getCodContinente()),
                String.valueOf(d.getPais()),
                String.valueOf(d.getCodPais()),
                String.valueOf(d.getUf()),
                String.valueOf(d.getCodUf()),
                String.valueOf(d.getVia()),
                String.valueOf(d.getCodVia()),
                String.valueOf(d.getAno()),
                String.valueOf(d.getMes()),
                String.valueOf(d.getCodMes())
        );
    }

    public List<DadoChegadaOriginal> unificarChegada(List<DadoChegadaOriginal> dadoOriginal) {
        Map<String, DadoChegadaOriginal> mapa = new HashMap<>();

        for (DadoChegadaOriginal dado : dadoOriginal) {
            String chave = gerarChaveUnificacao(dado);

            if (mapa.containsKey(chave)) {
                DadoChegadaOriginal existente = mapa.get(chave);
                existente.setChegadas(existente.getChegadas() + dado.getChegadas());
            } else {
                mapa.put(chave, dado);
            }
        }

        return new ArrayList<>(mapa.values());
    }

    public List<DadoTratado> tratarDados(List<DadoChegadaOriginal> dadosOriginais){
        log.insertLog("INFO", "Iniciando o tratamento e unificação dos dados");

        List<DadoTratado> dadosTratados = new ArrayList<>();
        List<DadoChegadaOriginal> dadoUnificado = unificarChegada(dadosOriginais);
        Iterator <DadoChegadaOriginal> iterator = dadoUnificado.iterator();
        while(iterator.hasNext()) {
            DadoChegadaOriginal dadosOriginai = iterator.next();
            int mesFormatado = formatarMes(dadosOriginai.getMes());

            if (mesFormatado == 0) {
                log.insertLog("WARN", "Mês inválido encontrado: " + dadosOriginai.getMes() + " - linha ignorada.");
                continue;
            }

            LocalDate data = LocalDate.of(dadosOriginai.getAno(), mesFormatado, 1);
            Date dataSql = Date.valueOf(data);
            String ufFormatado = dadosOriginai.getUf().equals("Outras Unidades da Federação")? "Desconhecido": dadosOriginai.getUf();
            String viaFormatada = dadosOriginai.getVia().equals("Aéreo") ? "Aérea": dadosOriginai.getVia().equals("Marítimo") ? "Marítima": dadosOriginai.getVia();
            DadoTratado d = new DadoTratado(dadosOriginai.getContinente(), dadosOriginai.getPais(), ufFormatado, viaFormatada, dataSql, dadosOriginai.getChegadas());

            dadosTratados.add(d);
            iterator.remove();
        }
        log.insertLog("INFO", "Tratamento e unificação dos dados finalizada");
        System.gc();
        return dadosTratados;
    }


}
