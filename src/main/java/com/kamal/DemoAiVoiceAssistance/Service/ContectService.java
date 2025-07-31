package com.kamal.demoAi.Service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class ContectService {
	
	    private final Map<String, String> contacts = new HashMap<>();

	    public ContectService() {
	        contacts.put("amma", "whatsapp:+91XXXXXXXXXX");
	        contacts.put("kamal", "whatsapp:+91YYYYYYYYYY");
	    }

	    public String getNumber(String name) {
	        return contacts.get(name.toLowerCase());
	    }
	

	}



