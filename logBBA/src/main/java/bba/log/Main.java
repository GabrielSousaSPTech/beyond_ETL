package bba.log;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {


        var formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        String[] acao = {
                "INFO [API] Requisição recebida para rio-de-janeiro - Tempo de resposta: 120ms",
                "ERROR [DB] Falha ao buscar dados da cidade São Paulo - Conexão recusada na porta 3307",
                "INFO [Job] Processo de atualização de dados iniciado",
                "WARN [Cache] Tempo de expiração dos dados excedido para salvador",
                "ERROR [Auth] Tentativa de login falhou para o usuário ID 45678 - Credenciais inválidas",
                "INFO [API] Requisição recebida para belo-horizonte - Tempo de resposta: 98ms",
                "DEBUG [Worker] Tarefa agendada executada com sucesso - ID da tarefa: 789",
                "ERROR [DB] Tempo limite excedido ao tentar consultar registros de fortaleza",
                "INFO [Job] Sincronização de usuários concluída com sucesso",
                "WARN [API] Requisição para curitiba pode estar lenta - Tempo de resposta: 450ms",
                "INFO [DB] Conexão com o banco de dados estabelecida com sucesso na porta 3307",
                "INFO [API] Dados do usuário ID 12345 retornados com sucesso - Tempo de resposta: 85ms",
                "INFO [Worker] Relatório financeiro gerado com sucesso - Arquivo: report_2025-03-06.pdf",
                "INFO [Auth] Login realizado com sucesso para o usuário ID 98765",
                "INFO [Job] Backup diário concluído sem erros",
                "INFO [Cache] Dados da cidade recife armazenados com sucesso",
                "INFO [API] Requisição para porto-alegre processada com sucesso - Tempo de resposta: 75ms",
                "INFO [DB] Atualização de registros concluída - 250 linhas afetadas",
                "INFO [Worker] E-mails de notificação enviados com sucesso para 1.200 usuários"
        };

//        -TimerTask é o que encapsula o codigo que será executado no intervalo de tempo
//        scheduleAtFixedRate é um método da classe Timer que executa o codigo em um intervalo fixo de tempo
        new java.util.Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                var dataHoraAtual = LocalDateTime.now();
                Integer indiceAcaoEscolhida = ThreadLocalRandom.current().nextInt(0, acao.length);
                System.out.println(acao[indiceAcaoEscolhida] + " - " + formatador.format(dataHoraAtual));
            }
        }, 0, 5000);


    }
}