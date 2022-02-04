package com.javi.uned.pfgbackend.adapters.api.specs.model;

import com.javi.uned.pfgbackend.adapters.api.tonalities.model.TonalityDTOTransformer;
import com.javi.uned.pfgcommons.model.specs.GeneticSpecs;

public class GeneticSpecsDTOTransformer {

    public static GeneticSpecs toDomainObject(GeneticSpecsDTO geneticSpecsDTO) {
        GeneticSpecs geneticSpecs = new GeneticSpecs();
        geneticSpecs.setRequesterId(geneticSpecsDTO.getRequesterId());
        geneticSpecs.setMovementTitle(geneticSpecsDTO.getMovementTitle());
        geneticSpecs.setMovementNumber(geneticSpecsDTO.getMovementNumber());
        geneticSpecs.setAuthors(geneticSpecsDTO.getAuthors());
        geneticSpecs.setMeasures(geneticSpecsDTO.getMeasures());
        geneticSpecs.setCompas(geneticSpecsDTO.getCompas());
        geneticSpecs.setInstrumentos(geneticSpecsDTO.getInstrumentos());
        geneticSpecs.setTonalidad(TonalityDTOTransformer.toDomainObject(geneticSpecsDTO.getTonalidad()));
        geneticSpecs.setPhraseLength(geneticSpecsDTO.getPhraseLength());
        geneticSpecs.setMinFigura(geneticSpecsDTO.getMinFigura());
        geneticSpecs.setMaxFigura(geneticSpecsDTO.getMaxFigura());
        return geneticSpecs;
    }

    public static GeneticSpecsDTO toTransferObject(GeneticSpecs geneticSpecs) {
        GeneticSpecsDTO geneticSpecsDTO = new GeneticSpecsDTO();
        geneticSpecsDTO.setRequesterId(geneticSpecs.getRequesterId());
        geneticSpecsDTO.setMovementTitle(geneticSpecs.getMovementTitle());
        geneticSpecsDTO.setMovementNumber(geneticSpecs.getMovementNumber());
        geneticSpecsDTO.setAuthors(geneticSpecs.getAuthors());
        geneticSpecsDTO.setMeasures(geneticSpecs.getMeasures());
        geneticSpecsDTO.setCompas(geneticSpecs.getCompas());
        geneticSpecsDTO.setInstrumentos(geneticSpecs.getInstrumentos());
        geneticSpecsDTO.setTonalidad(TonalityDTOTransformer.toTransferObject(geneticSpecs.getTonalidad()));
        geneticSpecsDTO.setPhraseLength(geneticSpecs.getPhraseLength());
        geneticSpecsDTO.setMinFigura(geneticSpecs.getMinFigura());
        geneticSpecsDTO.setMaxFigura(geneticSpecs.getMaxFigura());
        return geneticSpecsDTO;
    }

}
