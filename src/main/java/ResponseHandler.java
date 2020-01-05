import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Map;

public class ResponseHandler {
    private final MessageSender sender;
    private final Map<Long, State> chatStates;

    public ResponseHandler(MessageSender sender, DBContext db) {
        this.sender = sender;
        chatStates = db.getMap(Constants.CHAT_STATES);
    }

    public void replyToStart(long chatId) {
        try {
            sender.execute(new SendMessage()
                    .setText(Constants.START_REPLY)
                    .setChatId(chatId));

            sender.execute(new SendMessage()
                    .setText(Constants.FIND_TRAINING_DATE)
                    .setChatId(chatId)
                    .setReplyMarkup(KeyboardFactory.withTodayTomorrowButtons()));

            chatStates.put(chatId, State.AWAITING_TRAINING_DAY);
        } catch (TelegramApiException e) {
            e.printStackTrace();

        }
    }
}