package springbootkafka.springbootkafka.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springbootkafka.springbootkafka.models.User;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/producer-users")
public class ProducerUser {

    private final Logger logger = LoggerFactory.getLogger(ProducerString.class);


    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    @GetMapping
    public ResponseEntity<User> sendUser() {
        User user = User.builder()
                .id(10)
                .name("Gustavo - " + LocalDateTime.now())
                .email("inge@gus.com")
                .build();
        logger.info("Usuario a enviar: " + user);

        ObjectMapper mapper = new ObjectMapper();
        String userJson = null;
        try {
            userJson = mapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        this.kafkaTemplate.send("topic-users", "usuario", userJson);

        return ResponseEntity.ok(user);
    }


    @GetMapping("/partitions")
    public ResponseEntity<User> sendUserPatitions() {
        User user = User.builder()
                .id(10)
                .name("Gustavo - " + LocalDateTime.now())
                .email("inge@gus.com")
                .build();
        logger.info("Usuario a enviar: " + user);

        ObjectMapper mapper = new ObjectMapper();
        String userJson = null;
        try {
            userJson = mapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        // 2 // Numero de particion a enviar
        this.kafkaTemplate.send("topic-users-partitions", 1, "usuario", userJson);

        return ResponseEntity.ok(user);
    }



}
