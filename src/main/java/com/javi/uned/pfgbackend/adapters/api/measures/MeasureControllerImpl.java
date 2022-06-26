package com.javi.uned.pfgbackend.adapters.api.measures;

import com.javi.uned.melodiacore.model.Compas;
import com.javi.uned.melodiacore.model.constants.Compases;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@Api(tags = "Measures")
public class MeasureControllerImpl implements MeasureController {

    public Compas[] getAvailableMeasures() {
        return Compases.getCompases();
    }
}
