package com.example.finchiktelegrambot.configuration;

import com.example.finchiktelegrambot.enums.ReservedMessage;
import com.example.finchiktelegrambot.service.message.MessageHandler;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class BeanConfiguration {

  @Bean
  @Primary
  public Map<ReservedMessage, List<MessageHandler>> messageHandlers(List<MessageHandler> list) {
    return list.stream()
        .collect(Collectors.groupingBy(MessageHandler::getReservedMessage));
  }

}