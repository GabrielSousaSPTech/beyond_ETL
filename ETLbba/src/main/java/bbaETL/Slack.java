package bbaETL;

import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import java.util.List;

public class Slack {
    private MethodsClient metodos;
    private SlackDao dao;
    private List<String> canais;

    public Slack(Env env) {
        com.slack.api.Slack slack = com.slack.api.Slack.getInstance();
        this.metodos = slack.methods(env.TOKEN_SLACK);
        this.dao = new SlackDao(env);
        this.canais = dao.listarTodosCanais();
    }

    public void enviarParaVariosCanais(String mensagem) {
        for (String canal : canais) {
            enviarMensagem(canal, mensagem);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void enviarMensagem(String canal, String mensagem) {
        try {
            ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                    .channel(canal)
                    .text(mensagem)
                    .username("Beyond_bot")
                    .iconEmoji(":robot_face:")
                    .build();

            ChatPostMessageResponse response = metodos.chatPostMessage(request);

            if (response.isOk()) {
                System.out.println("Mensagem enviada para o canal: " + canal);
            } else {
                System.out.println("Erro no canal " + canal + ": " + response.getError());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
