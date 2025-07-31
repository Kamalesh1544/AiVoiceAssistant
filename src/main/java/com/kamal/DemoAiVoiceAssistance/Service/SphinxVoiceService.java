package com.kamal.demoAi.Service;

import org.springframework.stereotype.Service;

import edu.cmd.sphinx.api.Configuration;
import edu.cmd.sphinx.api.LiveSpeechRecognizer;

@Service
public class SphinxVoiceService {
    public String listen() {
        try {
            Configuration config = new Configuration();
            
            config.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
            config.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
            config.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");

            LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(config);
            recognizer.startRecognition(true);
            System.out.println("🎙 Speak now...");

            String utterance = recognizer.getResult().getHypothesis();
            recognizer.stopRecognition();

            return utterance;
        } catch (Exception e) {
            return null;
        }
    }
}

