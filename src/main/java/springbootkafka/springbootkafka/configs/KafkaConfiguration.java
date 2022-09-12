package springbootkafka.springbootkafka.configs;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {

    private Map<String, Object> producerProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9093");
        props.put(ProducerConfig.RETRIES_CONFIG, 0); // intentos de reenvio en caso de error
        /*
        * 0 // nO INTERESA SI LLEGA
        * 1 // Requiere al menos una confirmacion de llegada
        * all // Todos los nodos deben confirmar
        * */

        props.put(ProducerConfig.ACKS_CONFIG, "0");
        // Define el tamaño de agrupaciones de mensajes, hasta quese alcance el tamaño definido, envaria otro lote de batch
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        // frecuencia en MS, para realizar el commit a los offset
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }

    @Bean
    public KafkaTemplate<String, Object> createTemplate(){
        Map<String, Object> senderProps = this.producerProps();
        ProducerFactory<String, Object> producerFactory = new DefaultKafkaProducerFactory<>(senderProps);
        return new KafkaTemplate<>(producerFactory);
    }

}
