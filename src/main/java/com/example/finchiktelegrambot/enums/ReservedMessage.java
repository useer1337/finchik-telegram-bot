package com.example.finchiktelegrambot.enums;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReservedMessage {

  START("/start"), CHANGE_NAME("/change_name"), NOT_RESERVED("");

  private final String text;

  public static ReservedMessage valueOfByText(String text) {
    return Arrays.stream(values())
        .filter(element -> element.getText().equalsIgnoreCase(text))
        .findFirst()
        .orElse(NOT_RESERVED);
  }

}