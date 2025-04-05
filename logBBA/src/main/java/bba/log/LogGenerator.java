package bba.log;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;


public class LogGenerator {



    private static final DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");



    public String formarterLog(String acao){

        String timestamp = formatador.format(LocalDateTime.now());
        return timestamp + " - " +  acao;
    }

    public String processLog(String acaoOcorrida){
        return formarterLog(acaoOcorrida);
    }

    public void gerarLogAleatorio(String [] acao){

        //-TimerTask é o que encapsula o codigo que será executado no intervalo de tempo
        //scheduleAtFixedRate é um método da classe Timer que executa o codigo em um intervalo fixo de tempo

        new java.util.Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                Integer indiceAcaoEscolhida = ThreadLocalRandom.current().nextInt(0, acao.length);
                System.out.println(processLog(acao[indiceAcaoEscolhida]));
            }
        }, 0, 5000);
    }

}
