package com.javi.uned.pfgbackend.domain.ports.messagebroker;

import com.javi.uned.pfgbackend.domain.exceptions.MelodiaIOException;
import org.springframework.web.multipart.MultipartFile;

public interface MessageBrokerNeuralComposer {

    String PRODUCE_TRAIN = "composer.neural.train";

    void trainElement(MultipartFile file) throws MelodiaIOException;

}
