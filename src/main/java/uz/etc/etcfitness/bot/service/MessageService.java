package uz.etc.etcfitness.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import uz.etc.etcfitness.bot.config.TelegramBotConfig;
import uz.etc.etcfitness.enums.UserStatus;
import uz.etc.etcfitness.user.User;
import uz.etc.etcfitness.user.UserRepository;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final UserRepository userRepository;
    private final TelegramBotConfig telegramBotConfig;

    public void handleStartMessage(Message message) {
        boolean present = userRepository.findByTelegramId(message.getChatId()).isPresent();
        var sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        if (!present) {
            User user = new User();
            user.setTelegramId(message.getChatId());
            user.setEnabled(false);
            user.setStatus(UserStatus.WAITING);
            userRepository.save(user);
            sendMessage.setText("Murojaat qabul qilindi");
            telegramBotConfig.sendMsg(sendMessage);
            return;
        }
        sendMessage.setText("Siz allaqachon ro'yxatdan o'tgansiz");
        telegramBotConfig.sendMsg(sendMessage);

    }
}
