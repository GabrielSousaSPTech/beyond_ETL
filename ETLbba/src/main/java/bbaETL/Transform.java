package bbaETL;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Transform {

    public static Integer formatarMes(String mes){
        switch (mes){
            case "Janeiro":
                return 1;
            case "Fevereiro":
                return 2;
            case "Março":
                return 3;
            case "Abril":
                return 4;
            case "Maio":
                return 5;
            case "Junho":
                return 6;
            case "Julho":
                return 7;
            case "Agosto":
                return 8;
            case "Setembro":
                return 9;
            case "Outubro":
                return 10;
            case "Novembro":
                return 11;
            case "Dezembro":
                return 12;
        }
        return 0;
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

        List<DadoTratado> dadosTratados = new ArrayList<>();
        List<ChegadaTuristas> dadoUnificado = unificarChegada(dadosOriginais);
        for (ChegadaTuristas dadosOriginai : dadoUnificado) {
            LocalDate data = LocalDate.of(dadosOriginai.getAno(), formatarMes(dadosOriginai.getMes()), 1);
            String ufFormatado = dadosOriginai.getUf().equals("Outras Unidades da Federação")? "Desconhecido": dadosOriginai.getUf();
            DadoTratado d = new DadoTratado(dadosOriginai.getContinente(), dadosOriginai.getPais(), ufFormatado, dadosOriginai.getVia(), data, dadosOriginai.getChegadas());

            dadosTratados.add(d);
        }
        return dadosTratados;
    }


}
