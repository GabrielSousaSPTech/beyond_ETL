package bbaETL;

import java.util.*;

public class Env {
    public final String BD_HOST;
    public final String BD_DATABASE;
    public final String BD_PORT;
    public final String BD_USER;
    public final String BD_PASSWORD;
    public final String BUCKET_NAME;
    public final String TOKEN_SLACK;
    public final HashMap<String,String> hashMapAllData;



    Env() {
        this.BD_HOST = envs.get("BD_HOST");
        this.BD_DATABASE = envs.get("BD_DATABASE");
        this.BD_PORT = envs.get("BD_PORT");
        this.BD_USER = envs.get("BD_USER");
        this.BD_PASSWORD = envs.get("BD_PASSWORD");
        this.BUCKET_NAME = envs.get("BUCKET_NAME");
        this.TOKEN_SLACK = envs.get("TOKEN_SLACK");
        this.hashMapAllData = envs;

    }



    public static Env createEnv(){
        System.out.println("Insira os valores de env");
        Scanner scanner = new Scanner(System.in);
        HashMap<String, String> validedInputs = new HashMap<>() {
            {
                put("BD_HOST", "");
                put("BD_PORT", "");
                put("BD_DATABASE", "");
                put("BD_USER", "");
                put("BD_PASSWORD", "");
                put("BUCKET_NAME", "");
                put("TOKEN_SLACK", "");
            }
        };

        for (String key : validedInputs.keySet()) {
            validedInputs.put(key, validateUntilNotNull(scanner, key));
        }

        return new Env();
    }

    private static String validateUntilNotNull(Scanner scanner, String msg){
        String inputValue = "";
        while(inputValue == null || inputValue.isBlank()){
            System.out.println(msg+": ");
            inputValue = scanner.nextLine();
            if(inputValue == null){
                System.out.println("Valor inserido está nulo, tente novamente");
                continue;
            }
            if(inputValue.isBlank()){
                System.out.println("Valor inserido está vazio, tente novamente:");
            }
        }
        return inputValue;
    }


    @Override
    public String toString() {
        return "Env{" +
                "BD_HOST='" + BD_HOST + '\'' +
                ", BD_DATABASE='" + BD_DATABASE + '\'' +
                ", BD_PORT='" + BD_PORT + '\'' +
                ", BD_USER='" + BD_USER + '\'' +
                ", BD_PASSWORD='" + BD_PASSWORD + '\'' +
                ", BUCKET_NAME='" + BUCKET_NAME + '\'' +
                ", hashMapAllData=" + hashMapAllData +
                '}';
    }
}
