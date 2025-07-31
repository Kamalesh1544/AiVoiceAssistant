package com.kamal.demoAi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.kamal.Model.GeminiCommand;
import com.kamal.demoAi.Service.BrowserService;
import com.kamal.demoAi.Service.ContectService;
import com.kamal.demoAi.Service.GeminiParserService;
import com.kamal.demoAi.Service.GeminiService;
import com.kamal.demoAi.Service.SphinxVoiceService;
import com.kamal.demoAi.Service.WhatsAppService;

@SpringBootApplication
public class DemoAiApplication {

	@SpringBootApplication
	public class VoiceWhatsappApplication implements CommandLineRunner {

	    @Autowired
	    private SphinxVoiceService speechService;

	    @Autowired
	    private GeminiService geminiService;

	    @Autowired
	    private WhatsAppService whatsappService;

	    @Autowired
	    private ContectService contactService;

	    @Autowired
	    private BrowserService browserService;
	    
	    @Autowired
        GeminiCommand parsed;

	    public static void main(String[] args) {
	        SpringApplication.run(VoiceWhatsappApplication.class, args);
	    }

	    @Override
	    public void run(String... args) {
	        while (true) {
	            String spoken = speechService.listen();
	            if (spoken == null || spoken.equalsIgnoreCase("exit")) break;
	            
	            geminiService.getCommandJson(spoken);

	            if ("send".equalsIgnoreCase(parsed.getAction())) {
	                String number = contactService.getNumber(parsed.getContact());
	                if (number != null) {
	                    whatsappService.sendMessage(number, parsed.getMessage());
	                } else {
	                    System.out.println("❌ Contact not found");
	                }
	            } else if ("open".equalsIgnoreCase(parsed.getAction())) {
	                browserService.openWebsite(parsed.getMessage()); // message holds site URL
	            } else {
	                System.out.println("❓ Command not recognized");
	            }
	        }
	    }
	}

}
