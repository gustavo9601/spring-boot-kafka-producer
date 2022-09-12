package springbootkafka.springbootkafka.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/producer-strings")
public class ProducerString {


    private final Logger logger = LoggerFactory.getLogger(ProducerString.class);

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;


    @GetMapping
    public ResponseEntity<String> sendString() {
        logger.info("Enviando mensaje to Kafka");
        this.kafkaTemplate.send("topic-strings", "llave-string", "Hola desde Spring Boot a las :\t" + LocalDateTime.now());
        return ResponseEntity.ok("Mensaje enviado");
    }

}
