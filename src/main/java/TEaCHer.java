import java.util.Random;

import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.telegram.abilitybots.api.objects.Flag.*;
import static org.telegram.abilitybots.api.objects.Locality.*;
import static org.telegram.abilitybots.api.objects.Privacy.*;

//TODO fix /command@name_bot not working
public class TEaCHer extends AbilityBot implements Constants {

    private final ResponseHandler responseHandler;

    //<editor-fold desc="constructors and basic getters">
    TEaCHer(String botToken, String botUsername) {
        super(botToken, botUsername);
        responseHandler = new ResponseHandler(sender, db);
    }

    TEaCHer() {
        this(BOT_TOKEN, BOT_USERNAME);
    }

    @Override
    public int creatorId() {
        return CREATOR_ID;
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public boolean checkGlobalFlags(Update update) {
        return true;
    }
    //</editor-fold>

    public Ability allInfo() { //lets me find all info about a chat and my message
        return Ability
                .builder()
                .name("allinfo")
                .info("gives all info about a certain chat")
                .locality(ALL)
                .privacy(CREATOR)
                .action(ctx -> silent.send(
                        ctx.update().getMessage().toString(),
                        ctx.chatId()
                        )
                )
                .build();
    }

    public Ability roll() {
        return Ability
                .builder()
                .name("roll")
                .info("/roll xdy, rolls qty(x) of y-sided dice")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> silent.send(Student.rollDice(ctx), ctx.chatId()))
                .build();
    }

    public Ability shipping() {
        return Ability
                .builder()
                .name("shipping")
                .info("i dont like shipping bot")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> silent.send("I don't ship it", ctx.chatId()))
                .build();
    }

    public Ability ratePhoto() {
        return Ability
                .builder()
                .name(DEFAULT)
                .info("Rates every captions photo")
                .flag(CAPTION)
                .privacy(PUBLIC)
                .locality(ALL)
                .action(ctx -> {
                    if(ctx.update().getMessage().getCaption().toLowerCase().contains("rate")) {
                        System.out.println("contains rate");
                        silent.send(
                                "I give it a " + Student.getPhotoRating(ctx)
                                , ctx.chatId()
                        );
                    } else {
                        System.out.println("doesn't contain rate");
                    }
                })
                .build();
    }

    public Ability replyToStart() {
        return Ability
                .builder()
                .name("start")
                .info(Constants.START_DESCRIPTION)
                .locality(ALL)
                .privacy(Privacy.PUBLIC)
                .action(ctx -> responseHandler.replyToStart(ctx.chatId()))
                .build();
    }

    public Ability replyViaDM() {
        return Ability
                .builder()
                .name("dm")
                .info("DMs you when you use this in a group chat")
                .locality(ALL)
                .privacy(Privacy.PUBLIC)
                .action(ctx -> silent.send("Hi " + ctx.user().getFirstName() + ", I just wanted to pull you aside so the other people wouldn't hear me call you a " + System.getenv("mysteryVar"), (long) ctx.user().getId()))
                .build();
    }
}