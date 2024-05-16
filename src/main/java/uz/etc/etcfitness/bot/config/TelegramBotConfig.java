package uz.etc.etcfitness.bot.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.etc.etcfitness.bot.service.MessageService;

@Component
@RequiredArgsConstructor
public class TelegramBotConfig extends TelegramLongPollingBot {
    private final MessageService messageService;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                if (update.getMessage().getText().equals("/start")) {
                    messageService.handleStartMessage(update.getMessage());
                }
            }
        }

    }


    @Autowired
    @Lazy
    public TelegramBotConfig(TelegramBotsApi telegramBotsApi, MessageService messageService) throws TelegramApiException {
        super("6774325921:AAEU3OXJTpmjoWhM2LkO6DXUQ1iIjPLO5L0");
        this.messageService = messageService;
        telegramBotsApi.registerBot(this);
    }


    @Override
    public String getBotUsername() {
        return "@etcfitness_bot";
    }

    public void sendMsg(Object obj) {
        try {
            if (obj instanceof SendMessage) {
                this.execute((SendMessage) obj);
            } else if (obj instanceof SendPhoto) {
                this.execute((SendPhoto) obj);
            } else if (obj instanceof SendVideo) {
                this.execute((SendVideo) obj);
            } else if (obj instanceof SendLocation) {
                this.execute((SendLocation) obj);
            } else if (obj instanceof SendVoice) {
                this.execute((SendVoice) obj);
            } else if (obj instanceof SendContact) {
                this.execute((SendContact) obj);
            } else if (obj instanceof EditMessageText) {
                this.execute((EditMessageText) obj);
            } else if (obj instanceof SendDocument) {
                this.execute((SendDocument) obj);
            } else if (obj instanceof DeleteMessage) {
                this.execute((DeleteMessage) obj);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}
