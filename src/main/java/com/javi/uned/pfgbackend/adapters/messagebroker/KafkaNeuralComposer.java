package com.javi.uned.pfgbackend.adapters.messagebroker;

import com.javi.uned.pfgbackend.domain.exceptions.MelodiaIOException;
import com.javi.uned.pfgbackend.domain.ports.messagebroker.MessageBrokerNeuralComposer;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class KafkaNeuralComposer implements MessageBrokerNeuralComposer {

    @Autowired
    private KafkaTemplate<String, byte[]> byteArrayTemplate;

    public void trainElement(MultipartFile file) throws MelodiaIOException {
        try {
            byte[] xmlbinary = file.getBytes();
            byteArrayTemplate.send(PRODUCE_TRAIN, xmlbinary);
        } catch (IOException e) {
            throw new MelodiaIOException("Could not read xml file '" + file.getName() + "'");
        }
    }
}
