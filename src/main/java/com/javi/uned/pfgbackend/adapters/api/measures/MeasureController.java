package com.javi.uned.pfgbackend.adapters.api.measures;

import com.javi.uned.melodiacore.model.Compas;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

public interface MeasureController {

    @GetMapping(value = "/api/measures", produces = MediaType.APPLICATION_JSON_VALUE)
    Compas[] getAvailableMeasures();

}
