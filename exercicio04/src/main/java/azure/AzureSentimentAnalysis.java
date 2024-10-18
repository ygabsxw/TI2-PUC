package azure;

import com.azure.ai.textanalytics.TextAnalyticsClient;
import com.azure.ai.textanalytics.TextAnalyticsClientBuilder;
import com.azure.ai.textanalytics.models.DocumentSentiment;
import com.azure.core.credential.AzureKeyCredential;
import io.github.cdimascio.dotenv.Dotenv;

public class AzureSentimentAnalysis {

    private final TextAnalyticsClient client;

    public AzureSentimentAnalysis() {
        // Carregue o arquivo .env
        Dotenv dotenv = Dotenv.load();

        // Obtenha a chave e o endpoint
        String apiKey = dotenv.get("AZURE_API_KEY");
        String endpoint = dotenv.get("AZURE_ENDPOINT");

        // Debug: Verifique se as variáveis foram carregadas
        System.out.println("AZURE_API_KEY: " + apiKey);
        System.out.println("AZURE_ENDPOINT: " + endpoint);

        // Verifique se as variáveis foram carregadas
        // Validação para evitar erros
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalArgumentException("AZURE_API_KEY não pode ser nulo ou vazio.");
        }

        if (endpoint == null || endpoint.isEmpty()) {
            throw new IllegalArgumentException("AZURE_ENDPOINT não pode ser nulo ou vazio.");
        }

        client = new TextAnalyticsClientBuilder()
                .credential(new AzureKeyCredential(apiKey))
                .endpoint(endpoint)
                .buildClient();
    }

    public String analyzeSentiment(String text) {
        DocumentSentiment sentiment = client.analyzeSentiment(text);
        System.out.printf("Texto: %s%nSentimento: %s%n", text, sentiment.getSentiment());
        return sentiment.getSentiment().toString();  // Retorna o sentimento como String
    }
}