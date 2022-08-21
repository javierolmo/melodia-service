package com.javi.uned.pfgbackend.adapters.api.administration;

import com.javi.uned.pfgbackend.adapters.api.RestException;
import com.javi.uned.pfgbackend.adapters.database.log.LogEntity;
import com.javi.uned.pfgbackend.adapters.database.log.LogRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@Api(tags = "Logs")
@PreAuthorize("hasRole('ADMIN')")
public class AdminControllerImpl implements AdminController {

    @Autowired
    private LogRepository logRepository;

    public Page<LogEntity> getLogs(int size, int index, boolean ascendent) throws RestException {
        Sort sort = Sort.by(ascendent ? Sort.Direction.ASC : Sort.Direction.DESC, "id");
        PageRequest pageRequest = PageRequest.of(index, size, sort);
        return logRepository.findAll(pageRequest);
    }

}
