package com.kamal.demoAi.Service;

import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class WhatsAppService {
	

	    public WhatsAppService() {
	        Twilio.init("YOUR_SID", "YOUR_AUTH_TOKEN");
	    }

	    public void sendMessage(String to, String message) {
	        Message.creator(
	            new PhoneNumber(to),
	            new PhoneNumber("whatsapp:+14155238886"),
	            message
	        ).create();

	        System.out.println("📤 Sent to " + to);
	    }
	


}
