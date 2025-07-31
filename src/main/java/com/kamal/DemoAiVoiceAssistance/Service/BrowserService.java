package com.kamal.demoAi.Service;

import java.net.URI;

import org.springframework.stereotype.Service;

@Service
public class BrowserService {

	    public void openWebsite(String site) {
	        try {
	            String url = site.startsWith("http") ? site : site;
	            java.awt.Desktop.getDesktop().browse(new URI(url));
	        } catch (Exception e) {
	            System.out.println("❌ Cannot open site");
	        }
	    }
	}

	

