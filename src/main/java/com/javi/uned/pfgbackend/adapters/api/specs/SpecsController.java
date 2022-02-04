package com.javi.uned.pfgbackend.adapters.api.specs;

import com.javi.uned.pfgbackend.adapters.api.specs.model.GeneticSpecsDTO;
import com.javi.uned.pfgbackend.domain.sheet.model.Sheet;
import com.javi.uned.pfgcommons.model.specs.GeneticSpecs;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

public interface SpecsController {

    @PostMapping(value = "/api/specs/genetic-specs", produces = MediaType.APPLICATION_JSON_VALUE)
    Sheet postGeneticSpecs(@RequestBody GeneticSpecsDTO geneticSpecsDTO) throws IOException;

    @GetMapping(value = "/api/specs/genetic-specs/random", produces = MediaType.APPLICATION_JSON_VALUE)
    GeneticSpecs getRandomGeneticSpecs(
            @RequestParam int requesterId);

}
