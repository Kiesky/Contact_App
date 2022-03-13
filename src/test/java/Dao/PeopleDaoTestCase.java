package Dao;

import isen.contact_app.Dao.DataSourceFactory;
import isen.contact_app.Dao.PeopleDao;
import isen.contact_app.Entities.People;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.tuple;
import static org.assertj.core.api.Assertions.assertThat;

public class PeopleDaoTestCase {
    PeopleDao peopleDao = new PeopleDao();
    @Before
    public void initDb() throws Exception {
        Connection connection = DataSourceFactory.getDataSource().getConnection();
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS people (\r\n"
                        + "  idpeople INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\r\n" + "  lastname VARCHAR(45) NOT NULL,\r\n"
                        + " firstname VARCHAR(45) NOT NULL,\r\n"	+ " nickname VARCHAR(45) NOT NULL,\r\n"
                        + " phoneNumber VARCHAR(15) NULL,\r\n"	+ " address VARCHAR(200)  NULL,\r\n"
                        + " emailAddress VARCHAR(150) NULL,\r\n"	+ "  birthDate DATETIME NULL);" );
        stmt.executeUpdate("DELETE FROM people");
        stmt.executeUpdate("INSERT INTO people(idpeople,lastname, firstname, nickname, phoneNumber, address, emailAddress,birthDate) "
                + "VALUES (1, 'Boateng','Samuel','Sammybee','0751544735','1 rue d`austerlitz tourcoing','sbee1796@gmail.com', 20/10/1997 )");
        stmt.close();
        connection.close();
    }

    @Test
    public void shouldListPeoples() {

        // WHEN
        List<People> films = peopleDao.listOfPeople();


        // THEN
        assertThat(films).hasSize(1);
        assertThat(films).extracting("id","lastName", "firstName", "nickName", "phoneNumber", "address", "emailAddress","birthDate").containsOnly(
                tuple(1,"Boateng", "Samuel", "Sammybee", "0751544735", "1 rue d`austerlitz tourcoing", "sbee1796@gmail.com", new Date(20/10/1997)));


    }


    @Test
    public void shouldUpdatePeople() throws Exception {
        //    private Integer id;

        // WHEN

        peopleDao.updatePeople("firstname","kwame",1);
        // THEN
        Connection connection = DataSourceFactory.getDataSource().getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM people WHERE firstname='kwame'");
        assertThat(resultSet.next()).isTrue();
        assertThat(resultSet.getInt("idpeople")).isNotNull();
        assertThat(resultSet.getString("firstname")).isEqualTo("kwame");
        assertThat(resultSet.next()).isFalse();
        assertThat(resultSet.next()).isFalse();
        resultSet.close();
        statement.close();
        connection.close();
    }

    @Test
    public void shouldAddPeople() throws Exception {
        //    private Integer id;

        // WHEN
        People people = new People(0, "Tobbal", "Kevin", "Kevin", "072694590", "52 rue des fleurs", "kevintobbal@gmail.com",
                new Date(20/03/1998));
        peopleDao.addPeople(people);
        // THEN
        Connection connection = DataSourceFactory.getDataSource().getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM people WHERE lastname='Tobbal'");
        assertThat(resultSet.next()).isTrue();
        assertThat(resultSet.getInt("idpeople")).isNotNull();
        assertThat(resultSet.getString("firstname")).isEqualTo("Kevin");
        assertThat(resultSet.getDate("birthDate")).isEqualTo(new Date(20/02/1999));
        assertThat(resultSet.getString("nickname")).isEqualTo("Kevin");
        assertThat(resultSet.getString("phoneNumber")).isEqualTo("072694590");
        assertThat(resultSet.getString("address")).isEqualTo("52 rue des fleurs");
        assertThat(resultSet.getString("emailAddress")).isEqualTo("kevintobbal@gmail.com");
        assertThat(resultSet.next()).isFalse();
        assertThat(resultSet.next()).isFalse();
        resultSet.close();
        statement.close();
        connection.close();
    }
    @Test
    public void shouldDeletePeople(){
        peopleDao.deletePeople(1);
        List<People> films = peopleDao.listOfPeople();


        // THEN
        assertThat(films).hasSize(0);
    }

}

