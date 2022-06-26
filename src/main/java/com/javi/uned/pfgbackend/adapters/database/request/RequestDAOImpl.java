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
        RequestEntity requestEntity = RequestEntityTransformer.toEntity(request);
        return RequestEntityTransformer.toDomainObject(requestRepository.save(requestEntity));
    }

    @Override
    public Request findById(long id) throws EntityNotFound {
        Optional<RequestEntity> requestEntity = requestRepository.findById(id);
        if (requestEntity.isPresent()) {
            return RequestEntityTransformer.toDomainObject(requestEntity.get());
        } else {
            throw new EntityNotFound("Request with id " + id + " not found");
        }
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
                .map(RequestEntityTransformer::toDomainObject)
                .collect(Collectors.toList());
    }
}
