package com.javi.uned.pfgbackend.adapters.database.request;

import com.javi.uned.pfgbackend.adapters.database.sheet.SheetEntity;
import com.javi.uned.pfgbackend.domain.exceptions.EntityNotFound;
import com.javi.uned.pfgbackend.domain.ports.database.RequestDAO;
import com.javi.uned.pfgbackend.domain.ports.database.SheetDAO;
import com.javi.uned.pfgbackend.domain.sheet.model.Request;
import com.javi.uned.pfgbackend.domain.sheet.model.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RequestDAOImpl implements RequestDAO {

    @Autowired
    private RequestRepository requestRepository;


    @Override
    public Request save(Request request) {
        RequestEntity requestEntity = request.toEntity();
        requestEntity = requestRepository.save(requestEntity);
        return requestEntity.toRequest();
    }

    @Override
    public Request findById(long id) throws EntityNotFound {
        RequestEntity requestEntity = requestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound("Request with id " + id + " not found"));
        return requestEntity.toRequest();
    }

    @Override
    public void delete(long id) {
        requestRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        requestRepository.deleteAll();
    }

    @Override
    public List<Request> findAll() {
        return requestRepository.findAll().stream()
                .map(RequestEntity::toRequest)
                .collect(Collectors.toList());
    }
}
