package com.javi.uned.pfgbackend.adapters.api.specs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javi.uned.melodiacore.model.Instrumento;
import com.javi.uned.melodiacore.model.constants.Figuras;
import com.javi.uned.melodiacore.model.constants.Instrumentos;
import com.javi.uned.melodiacore.model.specs.ScoreSpecs;
import com.javi.uned.melodiacore.model.specs.ScoreSpecsDTO;
import com.javi.uned.melodiacore.util.MelodiaRandom;
import com.javi.uned.pfgbackend.domain.ports.filesystem.FileSystem;
import com.javi.uned.pfgbackend.domain.ports.messagebroker.MessageBrokerGeneticComposer;
import com.javi.uned.pfgbackend.domain.sheet.SheetService;
import com.javi.uned.pfgbackend.domain.sheet.model.Request;
import com.javi.uned.pfgbackend.domain.util.UtilService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.time.LocalDateTime;
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
    public Request postGeneticSpecs(ScoreSpecsDTO scoreSpecsDTO) throws IOException {

        logger.info("POST geneticSpecsDTO: " + scoreSpecsDTO);

        // Create request
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(scoreSpecsDTO);
        Request request = new Request(
                scoreSpecsDTO.getRequesterId(),
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
    public ScoreSpecsDTO getRandomGeneticSpecs(int requesterId) {
        MelodiaRandom melodiaRandom = new MelodiaRandom(); //TODO: Add method randomSpecs() to MelodiaRandom
        ScoreSpecs scoreSpecs = new ScoreSpecs();
        scoreSpecs.setRequesterId(requesterId);
        scoreSpecs.setMovementTitle(utilService.generateRandomTitle());
        scoreSpecs.setMovementNumber("1");
        scoreSpecs.setAuthors(Arrays.asList("Melodía"));
        scoreSpecs.setMeasures(ThreadLocalRandom.current().nextInt(30, 300));
        scoreSpecs.setCompas(melodiaRandom.randomCompas());
        scoreSpecs.setInstrumentos(new Instrumento[] {Instrumentos.PIANO});
        scoreSpecs.setTonalidad(melodiaRandom.randomTonality());
        scoreSpecs.setPhraseLength(new Random().nextBoolean()? 4 : 8);
        scoreSpecs.setMinFigura(Figuras.SEMICORCHEA);
        scoreSpecs.setMaxFigura(Figuras.REDONDA);
        return scoreSpecs.toDTO();
    }
}
