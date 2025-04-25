package bbaETL;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class LogGenerator {



    private static final DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");





    public void processLog(String info, String acaoOcorrida){
        String timestamp = formatador.format(LocalDateTime.now());
        LogDao log = new LogDao(new Connection().getConnection());
        log.insertLog(info, acaoOcorrida);

    }

//    public void gerarLogAleatorio(String [] acao){
//
//        //-TimerTask é o que encapsula o codigo que será executado no intervalo de tempo
//        //scheduleAtFixedRate é um método da classe Timer que executa o codigo em um intervalo fixo de tempo
//
//        new java.util.Timer().scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//
//                Integer indiceAcaoEscolhida = ThreadLocalRandom.current().nextInt(0, acao.length);
//                System.out.println(processLog(acao[indiceAcaoEscolhida]));
//            }
//        }, 0, 5000);
//    }

}
