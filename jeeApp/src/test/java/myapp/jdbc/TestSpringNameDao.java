package myapp.jdbc;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
public class TestSpringNameDao {

    @Autowired
    JdbcTemplate jt;

    @Autowired
    SpringNameDao dao;

    @Test
    public void testNames() throws SQLException {
        dao.deleteName(100);
        dao.deleteName(200);
        dao.addName(100, "Hello");
        dao.addName(200, "Salut");
        assertEquals("Hello", dao.findName(100));
        assertEquals("Salut", dao.findName(200));
        dao.findNames().forEach(System.out::println);

    }

    @Test
    public void testUpdateName() throws SQLException{
        dao.deleteName(400);
        dao.addName(400,"Bonjour");
        dao.updateName(400, "Bye");
        assertEquals("Bye", dao.findName(400));
        dao.findNames().forEach(System.out::println);
    }

    @Test
    public void testSqlInjectionForUpdate() throws SQLException{
        dao.deleteName(500);
        dao.addName(500, "Injection");
        try{
            dao.updateName(500, "test ; UPDATE NAME SET name=testSQLInjection WHERE id = 500");
        }catch (SQLException e){
            e.printStackTrace();
        }
//        assertEquals("testSQLInjection", dao.findName(500));
    }

    @Test
    public void testErrors() throws SQLException {
        dao.deleteName(300);
        assertThrows(SQLException.class, () -> {
            dao.addName(300, "Bye");
            dao.addName(300, "Au revoir");
        });
        assertEquals("Bye", dao.findName(300));
    }

    @Test
    public void testWorks() throws Exception {
        long debut = System.currentTimeMillis();

        // exécution des threads
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 1; (i < 5); i++) {
            executor.execute(dao::longWork);
        }

        // attente de la fin des threads
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.HOURS);

        // calcul du temps de réponse
        long fin = System.currentTimeMillis();
        System.out.println("duree = " + (fin - debut) + "ms");
    }



}