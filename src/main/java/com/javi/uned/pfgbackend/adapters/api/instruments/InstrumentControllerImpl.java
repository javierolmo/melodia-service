package com.javi.uned.pfgbackend.adapters.api.instruments;

import com.javi.uned.pfgcommons.model.Instrumento;
import com.javi.uned.pfgcommons.model.constants.Instrumentos;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@Api(tags = "Instruments")
public class InstrumentControllerImpl implements InstrumentController {

    public Instrumento[] getAvailableInstruments() {
        return Instrumentos.getInstrumentos();
    }
}
