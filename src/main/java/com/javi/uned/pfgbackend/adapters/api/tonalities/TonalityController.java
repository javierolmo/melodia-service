package com.javi.uned.pfgbackend.adapters.api.tonalities;

import com.javi.uned.pfgbackend.adapters.api.tonalities.model.TonalityDTO;
import com.javi.uned.pfgcommons.model.Tonalidad;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

public interface TonalityController {

    /**
     * Obtiene el conjunto de tonalidades disponibles
     *
     * @return
     */
    @GetMapping(value = "/api/tonalities", produces = MediaType.APPLICATION_JSON_VALUE)
    TonalityDTO[] getAvailableTonality();
}
