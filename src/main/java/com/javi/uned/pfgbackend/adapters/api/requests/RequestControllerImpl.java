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

    @Override
    public Request sendCompositionRequest(ScoreSpecsDTO scoreSpecsDTO) throws IOException {

        logger.info("POST geneticSpecsDTO: " + scoreSpecsDTO);

        // Create request
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(scoreSpecsDTO);
        Request request = new Request();
        request.setUserId(Long.valueOf(scoreSpecsDTO.getRequesterId()));
        request.setStartDateTime(LocalDateTime.now().toString());
        request.setSpecs(json);
        request.setStatus("PENDING");

        // Save request in database
        request = sheetService.submitSheetRequest(request);

        // Send request to message broker
        AzureKeyCredential key = new AzureKeyCredential("d8OTHzjPGDrE9Y31AoaviizxCUkFGr7Qftn0eTg3dxQ=");
        EventGridPublisherClient<EventGridEvent> ege = new EventGridPublisherClientBuilder()
                .endpoint("https://specs.eastus2-1.eventgrid.azure.net/api/events")
                .credential(key)
                .buildEventGridEventPublisherClient();
        EventGridEvent event = new EventGridEvent("melodia","composition-request",BinaryData.fromObject(request),"1.0");
        ege.sendEvent(event);

        return request;
    }

    @Override
    public ScoreSpecsDTO getRandomSpecs(int requesterId) {
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
