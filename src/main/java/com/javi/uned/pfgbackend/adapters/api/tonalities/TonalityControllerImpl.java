package com.javi.uned.pfgbackend.adapters.api.tonalities;

import com.javi.uned.melodiacore.model.Tonalidad;
import com.javi.uned.melodiacore.model.constants.Tonalidades;
import com.javi.uned.pfgbackend.adapters.api.tonalities.model.TonalityDTO;
import com.javi.uned.pfgbackend.adapters.api.tonalities.model.TonalityDTOTransformer;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@Api(tags = "Tonality")
public class TonalityControllerImpl implements TonalityController {

    public TonalityDTO[] getAvailableTonality() {
        List<TonalityDTO> result = new ArrayList<>();
        for (Tonalidad tonalidad : Tonalidades.getTonalidades()) {
            result.add(TonalityDTOTransformer.toTransferObject(tonalidad));
        }
        return result.toArray(new TonalityDTO[]{});
    }

}
