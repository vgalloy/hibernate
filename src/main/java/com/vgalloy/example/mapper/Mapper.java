package com.vgalloy.example.mapper;

import com.vgalloy.example.entity.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 09/02/16.
 */
public interface Mapper {

    static Person personFromResultSet(ResultSet resultSet) {
        try {
            Long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Person(id, name);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    static List<Person> listPersonFromResultSet(ResultSet resultSet) {
        List<Person> result = new ArrayList<>();
        try {
            while (resultSet.next()) {
                result.add(personFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
