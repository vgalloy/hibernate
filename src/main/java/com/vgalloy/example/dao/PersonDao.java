package com.vgalloy.example.dao;

import com.vgalloy.example.entity.Person;

import java.util.List;

/**
 * Created by vgalloy on 04/12/15.
 */
public interface PersonDao {
    /**
     * Insert Person into DB.
     *
     * @param person
     */
    void create(Person person);

    /**
     * Get All person.
     *
     * @return list of all person in DB
     */
    List<Person> getAll();

    /**
     * Find a person with its own id.
     *
     * @param id the given id
     * @return the person with the given id
     */
    Person getById(Long id);

    /**
     * Update a person.
     *
     * @param person the person to update
     */
    void update(Person person);

    /**
     * Delete a person.
     *
     * @param id the person's id to delete
     */
    void delete(Long id);

}
