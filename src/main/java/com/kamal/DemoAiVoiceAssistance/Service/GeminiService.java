package com.kamal.demoAi.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamal.Model.GeminiCommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
@Service
public class GeminiService{

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GeminiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }
    
    @Autowired
    GeminiParserService parser;
    
    private final String prompt = """
            i want you to rephrase the word and send me in json formate example if i send Send Hello to amma you have to take the action 
            either only send or the open and send it to a key named as action in json formate and then take  the message and store in message key and 
            then take whom to send to contact for
            
            
            example Send Hello to amma the action:Send message:Hello, contact:amma  in json formate. 
            
            example Send I am going to collage to amma and also tell her i will come home by 8pm tonight the 
            action:Send 
            message:I am going to collage and i will come home by 8pm tonight , 
            contact:amma  
            in json formate
           
            So you have to rephrase the enirtire sentance from input and sent a json formate as i want action=send or open message =rephrased messages from input and contact=to who it has to be sent
                
            So if I sent open Google You have to send the url as a string formate
            action=open message=www.google.com and contact=google
            example if the input is Open Youtube you have to give action=open message=www.youtube.com contact=youtube
                   
            dont give extra information give only jason dont even mention jason dont give\\n also give everything in one line doe break one by one 
            
            Now Convert this:""";

    public void getCommandJson(String userInput) {
        try {
        	String fullPrompt = prompt + userInput;

            String requestBody = String.format("""
                {
                  "contents": [
                    {
                      "parts": [
                        {
                          "text": "%s"
                        }
                      ]
                    }
                  ]
                }
                """, fullPrompt);
            
            System.out.println(requestBody);

            String response= webClient.post()
                    .uri(geminiApiUrl + geminiApiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
                         
            String cleanJson = extractJsonFromGeminiResponse(response);           
            GeminiCommand command=parser.parseJsonToCommand(cleanJson);
            
            
                         
       } catch (Exception e) {
           System.err.println("Exception in QnAService: this is response" + e.getMessage());           

        }
        
    }
    
    
    
    private String extractJsonFromGeminiResponse(String response) {
        try {
            JsonNode root = objectMapper.readTree(response);
            String rawJson = root.path("candidates")
                                 .get(0)
                                 .path("content")
                                 .path("parts")
                                 .get(0)
                                 .path("text")
                                 .asText();

            // Remove markdown formatting like ```json or ```
            return rawJson.replaceAll("(?s)```json|```", "").trim();

        } catch (Exception e) {
            System.err.println("Failed to extract clean JSON: this is parsestring " + e.getMessage());
            return "{}"; // Return empty JSON on failure
        }
    }
}
            

