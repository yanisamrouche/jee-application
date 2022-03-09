package myapp.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import myapp.jpa.dao.JpaDao;
import myapp.jpa.model.Person;

@SpringBootTest
public class TestJpaDao {

    @Autowired
    JpaDao dao;

    @Test
    public void addAndFindPerson() {
        // Création
        var p1 = new Person("Jean", null);
        p1 = dao.addPerson(p1);
        assertTrue(p1.getId() > 0);
        // relecture
        var p2 = dao.findPerson(p1.getId());
        assertEquals("Jean", p2.getFirstName());
        assertEquals(p1.getId(), p2.getId());

    }

    @Test
    public void updateAndRemovePerson(){
        // creation
        var p1 = new Person("Luc", null);
        p1 = dao.addPerson(p1);
        // mise à jour
        p1.setFirstName("Jean-Luc");
        dao.updatePerson(p1);
        assertEquals("Jean-Luc", p1.getFirstName());
        // suppression
        dao.removePerson(p1.getId());
        assertTrue(dao.findPerson(p1.getId()) == null);


    }

}