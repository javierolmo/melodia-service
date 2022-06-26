package com.javi.uned.pfgbackend.domain.ports.messagebroker;

import com.javi.uned.melodiacore.model.specs.ScoreSpecs;
import com.javi.uned.pfgbackend.domain.exceptions.MelodiaIOException;
import java.io.File;

public interface MessageBrokerGeneticComposer {

    // Topics
    String PRODUCE_COMPOSITION_ORDER = "composer.genetic.specs";
    String PRODUCE_PDF_CONVERSION = "composer.genetic.converter.pdf";

    void orderComposition(String sheetId, ScoreSpecs specs);

    void orderPDFConversion(String sheetId, File xml) throws MelodiaIOException;

}
