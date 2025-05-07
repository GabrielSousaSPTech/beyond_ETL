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
        return switch (mes) {
            case "Janeiro" -> 1;
            case "Fevereiro" -> 2;
            case "Março" -> 3;
            case "Abril" -> 4;
            case "Maio" -> 5;
            case "Junho" -> 6;
            case "Julho" -> 7;
            case "Agosto" -> 8;
            case "Setembro" -> 9;
            case "Outubro" -> 10;
            case "Novembro" -> 11;
            case "Dezembro" -> 12;
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
//                        log.insertLog("DEBUG", "Registro duplicado encontrado. Unindo chegadas de " +
//                                linhaAtualUnificada.getChegadas() + " com " + linhaAtual.getChegadas() + ". Total: " + chegadaUnificada);

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
            LocalDate data = LocalDate.of(dadosOriginai.getAno(), formatarMes(dadosOriginai.getMes()), 1);
            String ufFormatado = dadosOriginai.getUf().equals("Outras Unidades da Federação")? "Desconhecido": dadosOriginai.getUf();
            DadoTratado d = new DadoTratado(dadosOriginai.getContinente(), dadosOriginai.getPais(), ufFormatado, dadosOriginai.getVia(), data, dadosOriginai.getChegadas());

            dadosTratados.add(d);
        }
        log.insertLog("INFO", "Tratamento e unificação dos dados finalizada");
        return dadosTratados;
    }


}
