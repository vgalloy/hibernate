package com.vgalloy.example.service.impl;

import com.vgalloy.example.dao.PersonDao;
import com.vgalloy.example.entity.Person;
import com.vgalloy.example.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

/**
 * Created by vgalloy on 04/12/15.
 */
@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonDao personDao;

    @Override
    @Transactional
    public void create(Person person) {
        personDao.create(person);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Person> getAll() {
        return personDao.getAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Person getById(Long id) {
        return personDao.getById(id);
    }

    @Override
    @Transactional
    public void update(Person person) {
        personDao.update(person);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        personDao.delete(id);
    }

}
