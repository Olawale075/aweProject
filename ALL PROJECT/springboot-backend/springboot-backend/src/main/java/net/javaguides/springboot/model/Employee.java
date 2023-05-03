package net.javaguides.springboot.model;

import lombok.*;
import org.hibernate.annotations.GeneratorType;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name")
    private String FirstName;

    @Column(name="last_name")
    private String LastName;

    @Column(name="email_id")
    private String emailId;
}
