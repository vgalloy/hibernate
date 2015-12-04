package com.vgalloy.example.dao.impl;

import com.vgalloy.example.dao.PersonDao;
import com.vgalloy.example.entity.Person;
import com.vgalloy.example.factory.ConnectionFactory;
import com.vgalloy.example.mapper.Mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 04/12/15.
 */
public class PersonDaoImpl implements PersonDao {
    private static PersonDao INSTANCE = new PersonDaoImpl();

    @Override
    public void create(Person person) {
        Connection connection = ConnectionFactory.INSTANCE.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO personne(name) VALUE(?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, person.getName().trim());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            Long key = resultSet.getLong(1);
            person.setId(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(connection, preparedStatement, resultSet);
        }
    }

    @Override
    public List<Person> getAll() {
        Connection connection = ConnectionFactory.INSTANCE.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM personne");
            return Mapper.listPersonFromResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(connection, statement, resultSet);
        }
        return null;
    }

    @Override
    public Person getById(Long id) {
        Connection connection = ConnectionFactory.INSTANCE.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM personne WHERE id=?");
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            List<Person> result = Mapper.listPersonFromResultSet(resultSet);
            if(!result.isEmpty()) {
                return result.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(connection, preparedStatement, resultSet);
        }
        return null;
    }

    @Override
    public void update(Person person) {
        Connection connection = ConnectionFactory.INSTANCE.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("UPDATE personne SET name=? WHERE id=?");
            preparedStatement.setString(1, person.getName().trim());
            preparedStatement.setLong(2, person.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(connection, preparedStatement);
        }
    }

    @Override
    public void delete(Long id) {
        Connection connection = ConnectionFactory.INSTANCE.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM personne WHERE id=?");
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(connection, preparedStatement);
        }
    }


    private void close(Connection connection, Statement statement) {
        close(connection, statement, null);
    }

    private void close(Connection connection, Statement statement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static PersonDao getInstance() {
        return INSTANCE;
    }

}
