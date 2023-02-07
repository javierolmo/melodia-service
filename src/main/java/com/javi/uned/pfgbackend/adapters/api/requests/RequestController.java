package com.javi.uned.pfgbackend.adapters.api.requests;

import com.javi.uned.melodiacore.model.specs.ScoreSpecsDTO;
import com.javi.uned.pfgbackend.domain.sheet.model.Request;

import java.io.IOException;

public interface RequestController {

    Request submitCompositionRequest(ScoreSpecsDTO geneticSpecsDTO) throws IOException;

    ScoreSpecsDTO getRandomSpecs(int requesterId);

}
