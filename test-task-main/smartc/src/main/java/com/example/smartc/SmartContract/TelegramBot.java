package com.example.smartc.SmartContract;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private Map<Long, Boolean> userResponses = new HashMap<>();
    @Override
    public String getBotUsername() {
        return "springboot_smartcontract_bot";
    }

    @Override
    public String getBotToken() {
        return "6439633782:AAGnBiqNSDbk9A9ARiS79Z1ZD7hEjnFP_70";
    }

    @Override
    public void onUpdateReceived(Update update) {
        var originalMessage = update.getMessage();
        var chatId = originalMessage.getChatId();
        var userId = originalMessage.getFrom().getId();

        if (originalMessage.hasText()) {
            String messageText = originalMessage.getText().toLowerCase();

            if (messageText.equals("согласен") || messageText.equals("отказываюсь") || messageText.equals("Согласен") || messageText.equals("Отказываюсь")) {
                if (!userResponses.containsKey(userId)) {
                    boolean agree = messageText.equals("согласен");
                    userResponses.put(userId, agree);

                    if (userResponses.size() == 2) {
                        boolean dealAgreed = userResponses.values().stream().allMatch(Boolean::booleanValue);

                        SendMessage message = new SendMessage();
                        message.setChatId(String.valueOf(chatId));

                        if (dealAgreed) {
                            message.setText("Сделка состоялась!");
                        } else {
                            message.setText("Сделка не состоялась.");
                        }

                        try {
                            execute(message);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        userResponses.clear();
                    }
                }
            }
        }
    }

}