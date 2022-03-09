package myapp.jpa.dao;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.*;

import org.springframework.stereotype.Service;

import myapp.jpa.model.Person;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class JpaDao {

    private EntityManagerFactory factory = null;

    @PersistenceContext
    EntityManager em;

    @PostConstruct
    public void init() {
        factory = Persistence.createEntityManagerFactory("myBase");
    }

    @PreDestroy
    public void close() {
        if (factory != null) {
            factory.close();
        }
    }

    /*
     * Ajouter une personne
     */
    public Person addPerson(Person p) {
       em.persist(p);
       return p;
    }

    /*
     * Charger une personne
     */
    public Person findPerson(long id) {
        return em.find(Person.class, id);
    }

    public void updatePerson(Person p) {
            em.merge(p);
    }

    public void removePerson(long id) {
        Person person = em.find(Person.class,id);
        em.remove(person);
    }
    /*
     * Fermeture d'un EM (avec rollback éventuellement)
     */
    private void closeEntityManager(EntityManager em) {
        if (em == null || !em.isOpen())
            return;

        var t = em.getTransaction();
        if (t.isActive()) {
            try {
                t.rollback();
            } catch (PersistenceException e) {
                e.printStackTrace(System.err);
            }
        }
        em.close();
    }


    public List<Person> findAllPersons() {
        String query = "SELECT p FROM Person p";
        TypedQuery<Person> q = em.createQuery(query, Person.class);
        return q.getResultList();
    }

    public List<Person> findPersonsByFirstName(String pattern) {
        String query = "SELECT * FROM Person Where name Like :pattern";
        Query q = em.createQuery(query);
        q.setParameter(1, pattern);
        List<Person> results = q.getResultList();
        return results;
    }



}