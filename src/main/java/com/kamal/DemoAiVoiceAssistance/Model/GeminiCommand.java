package com.kamal.Model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GeminiCommand {
    private String action;
    private String message;
    private String contact; // changed from receptionist

    public GeminiCommand() {}

    @JsonCreator
    public GeminiCommand(@JsonProperty("action") String action,
                         @JsonProperty("message") String message,
                         @JsonProperty("contact") String contact) {
        this.action = action;
        this.message = message;
        this.contact = contact;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getContact() {
        return contact;
    }

    public void setRecipient(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "GeminiCommand{" +
               "action='" + action + '\'' +
               ", message='" + message + '\'' +
               ", recipient='" + contact + '\'' +
               '}';
    }

	
}
