package myapp.jpa.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;
    private String street;
    private String city;
    private String country;

}