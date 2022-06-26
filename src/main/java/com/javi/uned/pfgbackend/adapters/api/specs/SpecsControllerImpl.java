package com.javi.uned.pfgbackend.adapters.api.specs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javi.uned.melodiacore.model.Instrumento;
import com.javi.uned.melodiacore.model.constants.Figuras;
import com.javi.uned.melodiacore.model.constants.Instrumentos;
import com.javi.uned.melodiacore.model.specs.ScoreSpecs;
import com.javi.uned.melodiacore.util.MelodiaRandom;
import com.javi.uned.pfgbackend.adapters.api.specs.model.GeneticSpecsDTO;
import com.javi.uned.pfgbackend.adapters.api.specs.model.GeneticSpecsDTOTransformer;
import com.javi.uned.pfgbackend.domain.ports.filesystem.FileSystem;
import com.javi.uned.pfgbackend.domain.ports.messagebroker.MessageBrokerGeneticComposer;
import com.javi.uned.pfgbackend.domain.sheet.SheetService;
import com.javi.uned.pfgbackend.domain.sheet.model.Request;
import com.javi.uned.pfgbackend.domain.sheet.model.Sheet;
import com.javi.uned.pfgbackend.domain.util.UtilService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

@CrossOrigin
@RestController
@Api(tags = "Specs")
public class SpecsControllerImpl implements SpecsController {

    private static final Logger logger = Logger.getLogger(SpecsControllerImpl.class.getName());

    @Autowired
    private SheetService sheetService;
    @Autowired
    private FileSystem fileSystem;
    @Autowired
    private MessageBrokerGeneticComposer messageBrokerGeneticComposer;
    @Autowired
    private UtilService utilService;

    @Override
    public Request postGeneticSpecs(GeneticSpecsDTO geneticSpecsDTO) throws IOException {

        logger.info("POST geneticSpecsDTO: " + geneticSpecsDTO);

        // Transform to domain object
        ScoreSpecs specs = GeneticSpecsDTOTransformer.toDomainObject(geneticSpecsDTO);

        // Create request
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(geneticSpecsDTO);
        Request request = new Request(
                geneticSpecsDTO.getRequesterId(),
                LocalDateTime.now(),
                null,
                null,
                json
        );

        // Save request in database
        request = sheetService.submitSheetRequest(request);

        return request;
    }

    @Override
    public ScoreSpecs getRandomGeneticSpecs(int requesterId) {
        MelodiaRandom melodiaRandom = new MelodiaRandom();
        ScoreSpecs result = new ScoreSpecs();
        result.setRequesterId(requesterId);
        result.setMovementTitle(utilService.generateRandomTitle());
        result.setMovementNumber("1");
        result.setAuthors(Arrays.asList("Melodía"));
        result.setMeasures(ThreadLocalRandom.current().nextInt(30, 300));
        result.setCompas(melodiaRandom.randomCompas());
        result.setInstrumentos(new Instrumento[] {Instrumentos.PIANO});
        result.setTonalidad(melodiaRandom.randomTonality());
        result.setPhraseLength(new Random().nextBoolean()? 4 : 8);
        result.setMinFigura(Figuras.SEMICORCHEA);
        result.setMaxFigura(Figuras.REDONDA);
        return result;
    }
}
