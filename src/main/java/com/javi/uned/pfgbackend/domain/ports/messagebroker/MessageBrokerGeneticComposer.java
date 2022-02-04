package com.javi.uned.pfgbackend.domain.ports.messagebroker;

import com.javi.uned.pfgbackend.domain.exceptions.MelodiaIOException;
import com.javi.uned.pfgcommons.model.specs.GeneticSpecs;

import java.io.File;

public interface MessageBrokerGeneticComposer {

    // Topics
    String PRODUCE_COMPOSITION_ORDER = "composer.genetic.specs";
    String PRODUCE_PDF_CONVERSION = "composer.genetic.converter.pdf";

    void orderComposition(String sheetId, GeneticSpecs specs);

    void orderPDFConversion(String sheetId, File xml) throws MelodiaIOException;

}
