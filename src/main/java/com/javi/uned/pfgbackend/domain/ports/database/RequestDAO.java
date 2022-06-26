package com.javi.uned.pfgbackend.domain.ports.database;

import com.javi.uned.pfgbackend.domain.exceptions.EntityNotFound;
import com.javi.uned.pfgbackend.domain.sheet.model.Request;

import java.util.List;

public interface RequestDAO {

    Request save(Request request);

    Request findById(long id) throws EntityNotFound;

    void delete(long id);

    void deleteAll();

    List<Request> findAll();

}
