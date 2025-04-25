package bbaETL;

import io.github.cdimascio.dotenv.Dotenv;

public class Workflow {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load(); // carrega o .env

        // pega os valores do .env
        String dbHost = dotenv.get("DB_HOST");
        String dbPort = dotenv.get("DB_PORT");
        String dbDatabase = dotenv.get("DB_DATABASE");
        String dbUser = dotenv.get("DB_USER");
        String dbPassword = dotenv.get("DB_PASSWORD");

        String buName = dotenv.get("BU_NAME");
    }
}
