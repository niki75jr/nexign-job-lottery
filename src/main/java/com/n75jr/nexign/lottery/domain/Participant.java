package com.n75jr.nexign.lottery.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Setter
@Getter
@Entity
@Table(name = "participants")
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@JsonIgnoreProperties(value = "hibernateLazyInitializer", ignoreUnknown = true)
public class Participant implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "first_name")
    private String firstName;

    @NotBlank
    @Column(name = "last_name")
    private String lastName;

    @Min(value = 18L)
    @Column(name = "age")
    private Integer age;

    @NotBlank
    @Column(name = "city")
    private String city;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("#%6d: ", id))
                .append(String.format("[%s %s]", lastName, firstName))
                .append(String.format("[%3d years]", age))
                .append(String.format("[%s]", city));
        return sb.toString();
    }
}
