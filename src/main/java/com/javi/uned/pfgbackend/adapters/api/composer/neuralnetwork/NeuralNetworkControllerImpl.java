package com.javi.uned.pfgbackend.adapters.api.composer.neuralnetwork;

import com.javi.uned.pfgbackend.adapters.messagebroker.KafkaService;
import com.javi.uned.pfgbackend.domain.exceptions.MelodiaIOException;
import com.javi.uned.pfgbackend.domain.ports.messagebroker.MessageBrokerNeuralComposer;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@Api(tags = "Neural Network")
public class NeuralNetworkControllerImpl implements NeuralNetworkController {

    @Autowired
    private MessageBrokerNeuralComposer messageBrokerNeuralComposer;

    @Override
    public ResponseEntity<String> trainNeuralNetwork(MultipartFile xmlSheet) {
        try {
            messageBrokerNeuralComposer.trainElement(xmlSheet);
            return ResponseEntity.ok("Todo ok!");
        } catch (MelodiaIOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
