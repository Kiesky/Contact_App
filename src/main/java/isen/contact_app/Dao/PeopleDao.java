package isen.contact_app.Dao;

import isen.contact_app.Entities.People;

import java.util.List;
import java.util.ArrayList;
import java.sql.*;

import static isen.contact_app.Dao.DataSourceFactory.getDataSource;

public class PeopleDao {

    public List<People> listOfPeople() {

        initDb();

        List<People> listOfPeople = new ArrayList<>();

        try (Connection connection = getDataSource().getConnection()){
            try (Statement statement = connection.createStatement()){
                try (ResultSet resultSet = statement.executeQuery("SELECT * FROM people")){
                    while (resultSet.next()){
                        listOfPeople.add(new People(resultSet.getInt("idpeople"),
                                resultSet.getString("lastname"),
                                resultSet.getString("firstname"),
                                resultSet.getString("nickname"),
                                resultSet.getString("phoneNumber"),
                                resultSet.getString("address"),
                                resultSet.getString("emailAddress"),
                                resultSet.getDate("birthDate")
                                ));
                    }
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return listOfPeople;
    }

    public People getPeople(String firstName){
        try (Connection connection = getDataSource().getConnection()){
            try (PreparedStatement statement = connection.prepareStatement(
                    "Select * FROM person WHERE firstname =?")){
                statement.setString(1, firstName);
                try(ResultSet resultSet = statement.executeQuery()){
                    if (resultSet.next()){
                        return new People(resultSet.getInt("idpeople"),
                                resultSet.getString("lastname"),
                                resultSet.getString("firstname"),
                                resultSet.getString("nickname"),
                                resultSet.getString("phoneNumber"),
                                resultSet.getString("address"),
                                resultSet.getString("emailAddress"),
                                resultSet.getDate("birthDate"));
                    }
                }
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public People addPeople(People people){
        try (Connection connection = getDataSource().getConnection()){
            String sqlQuery = "INSERT INTO people(lastname, firstname, nickname, phoneNumber, address, emailAddress, birthDate) VALUES(?,?,?,?,?,?,?)";
            try(PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)){
                statement.setString(1, people.getLastName());
                statement.setString(2, people.getFirstName());
                statement.setString(3, people.getNickName());
                statement.setString(4, people.getPhoneNumber());
                statement.setString(5,people.getAddress());
                statement.setString(6, people.getEmailAddress());
                statement.setDate(7, people.getBirthDate());
                statement.executeUpdate();
                ResultSet id = statement.getGeneratedKeys();
                if (id.next()){
                    return new People(id.getInt(1),
                            people.getLastName(),
                            people.getFirstName(),
                            people.getNickName(),
                            people.getPhoneNumber(),
                            people.getAddress(),
                            people.getEmailAddress(),
                            people.getBirthDate());
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean updatePeople(String name, String value, Integer id){
        try (Connection connection = getDataSource().getConnection()){
            try(PreparedStatement statement = connection.prepareStatement("UPDATE people SET " + name + " = ? WHERE idpeople = ?")){
                statement.setString(1, value);
                statement.setInt(2, id);
                statement.executeUpdate();
            }

            return true;

        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    public void deletePeople(int id){
        try (Connection connection = getDataSource().getConnection()) {
            try(PreparedStatement statement = connection.prepareStatement("DELETE FROM people WHERE idpeople = ?")){
                statement.setInt(1,id);
                statement.executeUpdate();
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean updatePeopleDate(String name, Date date, Integer id){
        try (Connection connection = getDataSource().getConnection()){
            try (PreparedStatement statement = connection.prepareStatement("UPDATE people SET " + name + " =? WHERE idpeople =?")){
                statement.setDate(1,date);
                statement.setInt(2, id);
                statement.executeUpdate();
            }
            return true;

        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    public void initDb(){
        try {
            Connection connection = DataSourceFactory.getDataSource().getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS people (\r\n"
                            + " idpeople INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\r\n"
                            + "  lastname VARCHAR(45) NOT NULL,\r\n"
                            + " firstname VARCHAR(45) NOT NULL,\r\n"
                            + " nickname VARCHAR(45) NOT NULL,\r\n"
                            + " phoneNumber VARCHAR(15) NULL,\r\n"
                            + " address VARCHAR(200)  NULL,\r\n"
                            + " emailAddress VARCHAR(150) NULL,\r\n"
                            + "birthDate DATETIME NULL);"
            );

            statement.close();
            connection.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
