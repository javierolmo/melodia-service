package com.javi.uned.pfgbackend.adapters.api.specs;

import com.javi.uned.melodiacore.model.specs.ScoreSpecs;
import com.javi.uned.pfgbackend.adapters.api.specs.model.GeneticSpecsDTO;
import com.javi.uned.pfgbackend.domain.sheet.model.Request;
import com.javi.uned.pfgbackend.domain.sheet.model.Sheet;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

public interface SpecsController {

    @PostMapping(value = "/api/specs/genetic-specs", produces = MediaType.APPLICATION_JSON_VALUE)
    Request postGeneticSpecs(@RequestBody GeneticSpecsDTO geneticSpecsDTO) throws IOException;

    @GetMapping(value = "/api/specs/genetic-specs/random", produces = MediaType.APPLICATION_JSON_VALUE)
    ScoreSpecs getRandomGeneticSpecs(
            @RequestParam int requesterId);

}
