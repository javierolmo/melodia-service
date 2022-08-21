package com.javi.uned.pfgbackend.adapters.api.requests;

import com.javi.uned.melodiacore.model.specs.ScoreSpecsDTO;
import com.javi.uned.pfgbackend.domain.sheet.model.Request;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

public interface RequestController {

    @PostMapping(value = "/api/requests/composition", produces = MediaType.APPLICATION_JSON_VALUE)
    Request sendCompositionRequest(@RequestBody ScoreSpecsDTO geneticSpecsDTO) throws IOException;

    @GetMapping(value = "/api/requests/composition/sample-body", produces = MediaType.APPLICATION_JSON_VALUE)
    ScoreSpecsDTO getRandomSpecs(
            @RequestParam int requesterId);

}
