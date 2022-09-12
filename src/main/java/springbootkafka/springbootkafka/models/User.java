package springbootkafka.springbootkafka.models;

import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Builder
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String email;
}
