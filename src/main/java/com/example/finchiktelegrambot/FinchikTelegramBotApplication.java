package com.example.finchiktelegrambot;

import com.example.finchiktelegrambot.service.TelegramBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class FinchikTelegramBotApplication {

	public static void main(String[] args) throws TelegramApiException {
		ConfigurableApplicationContext ctx = SpringApplication.run(FinchikTelegramBotApplication.class, args);
		TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
		botsApi.registerBot(ctx.getBean(TelegramBot.class));
	}

}
