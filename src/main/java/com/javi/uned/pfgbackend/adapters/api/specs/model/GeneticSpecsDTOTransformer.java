package com.javi.uned.pfgbackend.adapters.api.specs.model;

import com.javi.uned.melodiacore.model.specs.ScoreSpecs;
import com.javi.uned.pfgbackend.adapters.api.tonalities.model.TonalityDTOTransformer;

public class GeneticSpecsDTOTransformer {

    public static ScoreSpecs toDomainObject(GeneticSpecsDTO geneticSpecsDTO) {
        ScoreSpecs geneticSpecs = new ScoreSpecs();
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

    public static GeneticSpecsDTO toTransferObject(ScoreSpecs scoreSpecs) {
        GeneticSpecsDTO geneticSpecsDTO = new GeneticSpecsDTO();
        geneticSpecsDTO.setRequesterId(scoreSpecs.getRequesterId());
        geneticSpecsDTO.setMovementTitle(scoreSpecs.getMovementTitle());
        geneticSpecsDTO.setMovementNumber(scoreSpecs.getMovementNumber());
        geneticSpecsDTO.setAuthors(scoreSpecs.getAuthors());
        geneticSpecsDTO.setMeasures(scoreSpecs.getMeasures());
        geneticSpecsDTO.setCompas(scoreSpecs.getCompas());
        geneticSpecsDTO.setInstrumentos(scoreSpecs.getInstrumentos());
        geneticSpecsDTO.setTonalidad(TonalityDTOTransformer.toTransferObject(scoreSpecs.getTonalidad()));
        geneticSpecsDTO.setPhraseLength(scoreSpecs.getPhraseLength());
        geneticSpecsDTO.setMinFigura(scoreSpecs.getMinFigura());
        geneticSpecsDTO.setMaxFigura(scoreSpecs.getMaxFigura());
        return geneticSpecsDTO;
    }

}
