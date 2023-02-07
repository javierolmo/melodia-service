package com.javi.uned.pfgbackend.adapters.api.requests;

import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.util.BinaryData;
import com.azure.messaging.eventgrid.EventGridEvent;
import com.azure.messaging.eventgrid.EventGridPublisherClient;
import com.azure.messaging.eventgrid.EventGridPublisherClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javi.uned.melodiacore.model.Instrumento;
import com.javi.uned.melodiacore.model.constants.Figuras;
import com.javi.uned.melodiacore.model.constants.Instrumentos;
import com.javi.uned.melodiacore.model.specs.ScoreSpecs;
import com.javi.uned.melodiacore.model.specs.ScoreSpecsDTO;
import com.javi.uned.melodiacore.util.MelodiaRandom;
import com.javi.uned.pfgbackend.adapters.composer.Composer;
import com.javi.uned.pfgbackend.domain.ports.filesystem.FileSystem;
import com.javi.uned.pfgbackend.domain.ports.messagebroker.MessageBrokerGeneticComposer;
import com.javi.uned.pfgbackend.domain.sheet.SheetService;
import com.javi.uned.pfgbackend.domain.sheet.model.Request;
import com.javi.uned.pfgbackend.domain.util.UtilService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

@CrossOrigin
@RestController
@Api(tags = "Requests")
public class RequestControllerImpl implements RequestController {

    private static final Logger logger = Logger.getLogger(RequestControllerImpl.class.getName());

    @Autowired
    private SheetService sheetService;
    @Autowired
    private FileSystem fileSystem;
    @Autowired
    private MessageBrokerGeneticComposer messageBrokerGeneticComposer;
    @Autowired
    private UtilService utilService;
    @Autowired
    private Composer composer;

    @Override
    @PostMapping(value = "/api/requests/composition", produces = MediaType.APPLICATION_JSON_VALUE)
    public Request submitCompositionRequest(@RequestBody ScoreSpecsDTO scoreSpecsDTO) throws IOException {

        logger.info("POST /api/request/composition. Body:" + scoreSpecsDTO);

        // Create request and save into database
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(scoreSpecsDTO);
        Request request = new Request();
        request.setUserId(Long.valueOf(scoreSpecsDTO.getRequesterId()));
        request.setStartDateTime(LocalDateTime.now().toString());
        request.setSpecs(json);
        request.setStatus("PENDING");
        request = sheetService.submitSheetRequest(request);
        logger.info("Request saved into database with id: " + request.getId());

        // Submit request
        composer.submitRequest(request);
        return request;
    }

    @Override
    @GetMapping(value = "/api/requests/composition/sample-body", produces = MediaType.APPLICATION_JSON_VALUE)
    public ScoreSpecsDTO getRandomSpecs(@RequestParam int requesterId) {
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
