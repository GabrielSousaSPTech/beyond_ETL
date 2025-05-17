package bbaETL;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public List<ChegadaTuristas> unificarChegada(List<ChegadaTuristas> dadoOriginal){
        List<ChegadaTuristas> dadosRetornar = new ArrayList<>();

        for (ChegadaTuristas linhaAtual : dadoOriginal) {
            Boolean dadoDuplicado = false;
            if(dadosRetornar != null && !dadosRetornar.isEmpty()){
                for (ChegadaTuristas linhaAtualUnificada : dadosRetornar) {
                    if(linhaAtual.getContinente().equals(linhaAtualUnificada.getContinente()) &&
                            linhaAtual.getCodContinente().equals(linhaAtualUnificada.getCodContinente()) &&
                            linhaAtual.getPais().equals(linhaAtualUnificada.getPais()) &&
                            linhaAtual.getCodPais().equals(linhaAtualUnificada.getCodPais()) &&
                            linhaAtual.getUf().equals(linhaAtualUnificada.getUf()) &&
                            linhaAtual.getCodUf().equals(linhaAtualUnificada.getCodUf()) &&
                            linhaAtual.getVia().equals(linhaAtualUnificada.getVia()) &&
                            linhaAtual.getCodVia().equals(linhaAtualUnificada.getCodVia()) &&
                            linhaAtual.getAno().equals(linhaAtualUnificada.getAno()) &&
                            linhaAtual.getMes().equals(linhaAtualUnificada.getMes()) &&
                            linhaAtual.getCodMes().equals(linhaAtualUnificada.getCodMes())){
                        Integer chegadaUnificada = linhaAtualUnificada.getChegadas() + linhaAtual.getChegadas();

                        ChegadaTuristas dadoNovo = linhaAtualUnificada;
                        dadoNovo.setChegadas(chegadaUnificada);

                        dadosRetornar.set(dadosRetornar.indexOf(linhaAtualUnificada), dadoNovo);
                        dadoDuplicado = true;
                    }
                }
            }
            if(!dadoDuplicado){
                dadosRetornar.add(linhaAtual);
            }

        }

        return dadosRetornar;
    }

    public List<DadoTratado> tratarDados(List<ChegadaTuristas> dadosOriginais){
        log.insertLog("INFO", "Iniciando o tratamento e unificação dos dados");

        List<DadoTratado> dadosTratados = new ArrayList<>();
        List<ChegadaTuristas> dadoUnificado = unificarChegada(dadosOriginais);
        for (ChegadaTuristas dadosOriginai : dadoUnificado) {
            int mesFormatado = formatarMes(dadosOriginai.getMes());

            if (mesFormatado == 0) {
                log.insertLog("WARN", "Mês inválido encontrado: " + dadosOriginai.getMes() + " - linha ignorada.");
                continue; // pula essa linha
            }

            LocalDate data = LocalDate.of(dadosOriginai.getAno(), mesFormatado, 1);
            String ufFormatado = dadosOriginai.getUf().equals("Outras Unidades da Federação")? "Desconhecido": dadosOriginai.getUf();
            DadoTratado d = new DadoTratado(dadosOriginai.getContinente(), dadosOriginai.getPais(), ufFormatado, dadosOriginai.getVia(), data, dadosOriginai.getChegadas());

            dadosTratados.add(d);
        }
        log.insertLog("INFO", "Tratamento e unificação dos dados finalizada");
        return dadosTratados;
    }


}
