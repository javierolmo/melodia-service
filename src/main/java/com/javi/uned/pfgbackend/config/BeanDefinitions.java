package com.javi.uned.pfgbackend.config;

import com.javi.uned.pfgbackend.adapters.database.log.LogDAOImpl;
import com.javi.uned.pfgbackend.adapters.database.role.RoleDAOImpl;
import com.javi.uned.pfgbackend.adapters.database.sheet.SheetDAOImpl;
import com.javi.uned.pfgbackend.adapters.database.user.UserDAOImpl;
import com.javi.uned.pfgbackend.adapters.messagebroker.KafkaGeneticComposer;
import com.javi.uned.pfgbackend.adapters.messagebroker.KafkaNeuralComposer;
import com.javi.uned.pfgbackend.adapters.messagebroker.KafkaService;
import com.javi.uned.pfgbackend.domain.ports.database.LogDAO;
import com.javi.uned.pfgbackend.domain.ports.database.RoleDAO;
import com.javi.uned.pfgbackend.domain.ports.database.SheetDAO;
import com.javi.uned.pfgbackend.domain.ports.database.UserDAO;
import com.javi.uned.pfgbackend.domain.ports.messagebroker.MessageBrokerGeneticComposer;
import com.javi.uned.pfgbackend.domain.ports.messagebroker.MessageBrokerNeuralComposer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanDefinitions {

    @Bean
    public UserDAO userDAO(UserDAOImpl userDAO) {
        return userDAO;
    }

    @Bean
    public MessageBrokerNeuralComposer messageBrokerNeuralComposer(KafkaNeuralComposer kafkaNeuralComposer) {
        return kafkaNeuralComposer;
    }

    @Bean
    public MessageBrokerGeneticComposer messageBrokerGeneticComposer(KafkaGeneticComposer kafkaGeneticComposer) {
        return kafkaGeneticComposer;
    }

    @Bean
    public RoleDAO roleDAO(RoleDAOImpl roleDAO) {
        return roleDAO;
    }

    @Bean
    public LogDAO logDAO(LogDAOImpl logDAO) {
        return logDAO;
    }

    @Bean
    public SheetDAO sheetDAO(SheetDAOImpl sheetDAO) {
        return sheetDAO;
    }

}
