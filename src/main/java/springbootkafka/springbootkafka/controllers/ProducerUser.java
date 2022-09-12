package springbootkafka.springbootkafka.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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


    @Autowired()
    private KafkaTemplate<String, Object> kafkaTemplate;


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
    public ResponseEntity<String> sendUsersPatitions() {


        for (int i = 0; i < 5; i++) {
            User user = User.builder()
                    .id(i)
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

            // 1 // Numero de particion a enviar
            this.kafkaTemplate.send("topic-users-partitions", 1, "usuario", userJson);
            /*
            // Con el get() se espera a que se envie el mensaje de forma sincrona

            try {
                this.kafkaTemplate.send("topic-users-partitions", 1, "usuario", userJson).get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }*/
        }


        return ResponseEntity.ok("Mensaje con los usuarios enviados");
    }
}
