package myapp.jpa.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Person")
@Table(name = "TPerson",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {
                        "first_name", "birth_day"
                })
        })
@NamedQuery(
        name="findAllPersonsWithName",
        query="SELECT * FROM Person  WHERE name LIKE :persName"
)
@Data
@NoArgsConstructor
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Basic(optional = false)
    @Column(name = "first_name", length = 200, nullable = false, unique = true)
    private String firstName;

    @Basic()
    @Temporal(TemporalType.DATE)
    @Column(name = "birth_day")
    private Date birthDay;

    @Version()
    private long version = 0;

    @Transient
    public static long updateCounter = 0;

    @Embedded
    private Address address;

    public Person(String firstName, Date birthDay) {
        super();
        this.firstName = firstName;
        this.birthDay = birthDay;
    }



    @PreUpdate
    public void beforeUpdate() {
        System.err.println("PreUpdate of " + this);
    }

    @PostUpdate
    public void afterUpdate() {
        System.err.println("PostUpdate of " + this);
        updateCounter++;
    }

    @PersistenceContext
    public EntityManager em;
    public List<Person> findPersonsByName(String name){
        List<Person> res = em.createNamedQuery("findAllPersonsWithName")
                .setParameter("persName", name)
                .getResultList();
        return res;
    }


}