package com.javi.uned.pfgbackend.adapters.api.instruments;

import com.javi.uned.melodiacore.model.Instrumento;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

public interface InstrumentController {

    @GetMapping(value = "/api/instruments", produces = MediaType.APPLICATION_JSON_VALUE)
    Instrumento[] getAvailableInstruments();

}
