package com.javi.uned.pfgbackend.adapters.api.specs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javi.uned.pfgbackend.adapters.api.specs.model.GeneticSpecsDTO;
import com.javi.uned.pfgbackend.adapters.api.specs.model.GeneticSpecsDTOTransformer;
import com.javi.uned.pfgbackend.adapters.filesystem.FileServiceImpl;
import com.javi.uned.pfgbackend.domain.ports.messagebroker.MessageBrokerGeneticComposer;
import com.javi.uned.pfgbackend.domain.sheet.SheetService;
import com.javi.uned.pfgbackend.domain.sheet.model.Sheet;
import com.javi.uned.pfgbackend.domain.util.UtilService;
import com.javi.uned.pfgcommons.model.Instrumento;
import com.javi.uned.pfgcommons.model.constants.Compases;
import com.javi.uned.pfgcommons.model.constants.Figuras;
import com.javi.uned.pfgcommons.model.constants.Instrumentos;
import com.javi.uned.pfgcommons.model.constants.Tonalidades;
import com.javi.uned.pfgcommons.model.specs.GeneticSpecs;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@CrossOrigin
@RestController
@Api(tags = "Specs")
public class SpecsControllerImpl implements SpecsController {

    @Autowired
    private SheetService sheetService;
    @Autowired
    private FileServiceImpl fileServiceImpl;
    @Autowired
    private MessageBrokerGeneticComposer messageBrokerGeneticComposer;
    @Autowired
    private UtilService utilService;

    @Override
    public Sheet postGeneticSpecs(GeneticSpecsDTO geneticSpecsDTO) throws IOException {

        // Transform to domain object
        GeneticSpecs geneticSpecs = GeneticSpecsDTOTransformer.toDomainObject(geneticSpecsDTO);

        //Create new sheet
        Sheet sheet = new Sheet(
                null,
                geneticSpecs.getMovementTitle(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")),
                (long) geneticSpecs.getRequesterId(),
                false,
                "", "", "");

        //Save in database
        sheet = sheetService.save(sheet);

        // Save request in json
        ObjectMapper objectMapper = new ObjectMapper();
        File specsFile = new File(fileServiceImpl.getSheetFolder(sheet.getId()), "specs.json");
        objectMapper.writeValue(specsFile, geneticSpecs);

        //Order composition request
        String sheetid = "" + sheet.getId();
        messageBrokerGeneticComposer.orderComposition(sheetid, geneticSpecs);

        return sheet;
    }

    @Override
    public GeneticSpecs getRandomGeneticSpecs(int requesterId) {
        GeneticSpecs result = new GeneticSpecs();
        result.setRequesterId(requesterId);
        result.setMovementTitle(utilService.generateRandomTitle());
        result.setMovementNumber("1");
        result.setAuthors(new String[]{"Melodía"});
        result.setMeasures(ThreadLocalRandom.current().nextInt(30, 300));
        result.setCompas(Compases.random());
        result.setInstrumentos(new Instrumento[] {Instrumentos.random()});
        result.setTonalidad(Tonalidades.random());
        result.setPhraseLength(new Random().nextBoolean()? 4 : 8);
        result.setMinFigura(Figuras.SEMICORCHEA);
        result.setMaxFigura(Figuras.REDONDA);
        return result;
    }
}
