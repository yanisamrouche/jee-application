package myapp.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.lang.model.element.Name;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Repository
@Transactional
public class SpringNameDao {

    @Autowired
    JdbcTemplate jt;

    @Autowired
    DataSource dataSource;


    private Connection newConnection() throws SQLException {
        //return DriverManager.getConnection(url, user, password);
        return dataSource.getConnection();
    }

    @PostConstruct
    public void initSchema() throws SQLException {
        var query = "create table if not exists NAME (" //
                + " id integer not null, " //
                + " name varchar(50) not null, " //
                + " primary key (id) )";
//        try (var conn = newConnection()) {
//            conn.createStatement().execute(query);
//        }
        jt.execute(query);

    }

    public void addName(myapp.jdbc.Name name) throws SQLException {
        var query = "insert into NAME values (?,?)";
        jt.update(query, new Object[]{name.getId(),name.getName()});


    }

    public void deleteName(int id) throws SQLException {
        var query = "Delete From NAME where (id = ?)";
        jt.update(query, id);
    }


    /*===== UPDATE ===== */
    public void updateName(int id, String newName) throws SQLException{
        var query = "Update NAME SET name = ? WHERE id = ?";
        jt.update(query, newName, id);
    }

    public String findName(int id) throws SQLException {
        var query = "Select name From NAME where id = "+id;
        return jt.queryForObject(query, String.class);
    }

    public List<myapp.jdbc.Name> findNames() throws SQLException {
        var query = "Select * From NAME order by name";
        List<myapp.jdbc.Name> list = jt.query(query, SpringNameDao::nameMapper);
        return list;
    }

    private static Name nameMapper(ResultSet rs, int i) throws SQLException {
        var n = new myapp.jdbc.Name();
        n.setId(rs.getInt("id"));
        n.setName(rs.getString("name"));
        return (Name) n;
    }

    public void longWork() {
        try (var c = newConnection()) {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        } catch (SQLException e1) {
        }
    }
    public void addNameTwoTimes(int id, String name) throws SQLException {
        /*
        addName(id, name);
        addName(id, name);
         */
    }


    public int countNames(String pattern){
        var query = "Select count(*) from NAME where name = ?";
        return jt.queryForObject(query, Integer.class,pattern);
    }
}
