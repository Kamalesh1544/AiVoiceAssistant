🛡️ Project Tagline


"Speak the command, let the system weave the action—WhatsApp, open portals, all parsed by Gemini's wisdom."



🧭 Overview


A voice-controlled assistant built with Java Spring Boot that listens via CMU Sphinx, parses speech through Gemini API into structured JSON, and executes actions such as sending WhatsApp messages (via Twilio) or opening websites. Designed as a modular, production-ready backend-ready system—the perfect blend of magic and engineering


      🔮 Key Features (Sticker-style Highlights)

      🎙️ Speech-to-Text: Real-time voice capture using CMU Sphinx.

      🧠 Natural Language Parsing: Gemini API returns structured JSON: action, recipient, message, etc.

      📩 WhatsApp Sending: Twilio integration when action == "send".

      🌐 Portal Opening: Opens websites (Gmail, Drive, etc.) for action == "open".

      ⚙️ Clean Architecture: Controller → Service (QnAService) → Gemini client → Action executor.

      🔁 Extensible Command Model: Easily add new actions (notify, search, etc.).

      🔐 Validation & Safety: Input parsing, action whitelisting, rate limits.

      🧰 Architecture (Workflow Stickers)



1. Controller

      Receives REST endpoint calls (can also be triggered internally after STT).
   
      Example endpoint: /api/command/process

   
2.  QnAService
   
Responsible for:
   
      Sending raw text to Gemini API.
      
      Receiving and validating JSON response.
      
      Mapping JSON to a typed model class (e.g., ParsedCommand).
      
      Routing to appropriate executor based on action.

   
3. Gemini Client

      Handles authentication, request formatting, retry/backoff.

      Example expected Gemini JSON:

             {
                "action": "send",
                "recipient": "Amma",
                "message": "Hello from the voice assistant!"
             }

   
4. Action Executors

        Send Action: Fetch contact mapping, format message, send via Twilio WhatsApp API.

        Open Action: Resolve known URL shortcuts and open in default browser.

        Extensible: Plug new executors by implementing a common interface.


5. Contact Mapping

        A configurable in-memory or persisted map of names to targets (WhatsApp numbers, URLs).

         Example:

                contacts:
                Amma: "+91XXXXXXXXXX"
                WorkEmail: "https://mail.google.com"


⚙️ Setup & Installation (Step-by-Step)

       1 Install Java (minimum required version; e.g., Java 17+).
       
       2 Clone repository to local machine.
       
       3 Set up environment variables (example .env or application properties):


            GEMINI_API_KEY
            TWILIO_ACCOUNT_SID
            TWILIO_AUTH_TOKEN
            TWILIO_WHATSAPP_FROM


       4 Build the Maven project:
       
            mvn clean install
            
       5 Run the Spring Boot app:
       
           mvn spring-boot:run


  🧪 Example Flow (Full Detail)
       
        1 User says: "Send hello to Amma".
        
        2 CMU Sphinx converts audio to text: Send hello to Amma.
        
        3 QnAService sends this string to Gemini API.
        
        4 Gemini returns:
        
            {"action":"send","recipient":"Amma","message":"Hello from the voice assistant!"}
        
        5 Parsed into ParsedCommand object.
        
        6 SendActionExecutor looks up Amma in contact map, finds +91XXXXXXXXXX.
        
        7 Formats WhatsApp message and calls Twilio API.
        
        8 Twilio responds with success; system logs and returns confirmation.
        
        9 User gets optional audible or UI confirmation: "Message delivered to Amma." 🎉



    Picture diagram
        A[🎙 User speaks] --> B[🗣️ CMU Sphinx STT]
        B --> C[📤 Raw text]
        C --> D[🤖 Gemini API]
        D --> E[📦 Structured JSON]
        E --> F{Action}
        F -->|send| G[📩 Twilio WhatsApp Sender]
        F -->|open| H[🌐 Website Launcher]
        F -->|other| I[🔧 Custom Action Handler]
        G --> J[✅ Delivery Confirmation]
        H --> J
        I --> J
        J --> K[📝 Audit Log / Response to User]

🛡 Error Handling & Safety
    
        Invalid action → fallback response with suggested valid actions.
        
        Unknown recipient → prompt user for clarification.
        
        Gemini parse confidence threshold; if low, ask for rephrasing.
        
        Twilio failures retried with exponential backoff; persistent failures are logged and notified.

