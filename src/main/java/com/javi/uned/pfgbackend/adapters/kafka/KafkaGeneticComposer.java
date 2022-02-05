package com.javi.uned.pfgbackend.adapters.kafka;

import com.javi.uned.pfgbackend.adapters.filesystem.FileServiceImpl;
import com.javi.uned.pfgbackend.domain.exceptions.MelodiaIOException;
import com.javi.uned.pfgbackend.domain.ports.messagebroker.MessageBrokerGeneticComposer;
import com.javi.uned.pfgbackend.domain.sheet.SheetService;
import com.javi.uned.pfgcommons.model.specs.GeneticSpecs;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class KafkaGeneticComposer implements MessageBrokerGeneticComposer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaGeneticComposer.class);

    @Autowired
    private KafkaTemplate<String, GeneticSpecs> specsTemplate;
    @Autowired
    private KafkaTemplate<String, byte[]> byteArrayTemplate;
    @Autowired
    private FileServiceImpl fileServiceImpl;
    @Autowired
    private SheetService sheetService;

    @Override
    public void orderComposition(String sheetId, GeneticSpecs specs) {
        specsTemplate.send(PRODUCE_COMPOSITION_ORDER, sheetId, specs);
    }

    @Override
    public void orderPDFConversion(String sheetId, File xml) throws MelodiaIOException {
        try {
            byte[] xmlbinary = FileUtils.readFileToByteArray(xml);
            String sheetIdString = "" + sheetId;
            byteArrayTemplate.send(PRODUCE_PDF_CONVERSION, sheetIdString, xmlbinary);
        } catch (IOException e) {
            throw new MelodiaIOException("Could not read xml file '" + xml.getAbsolutePath() + "'");
        }
    }

}
