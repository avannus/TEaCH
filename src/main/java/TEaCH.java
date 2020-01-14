import java.util.Random;

import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.telegram.abilitybots.api.objects.Flag.*;
import static org.telegram.abilitybots.api.objects.Locality.*;
import static org.telegram.abilitybots.api.objects.Privacy.*;

//TODO fix /command@name_bot not working
public class TEaCH extends AbilityBot implements Constants {

    private final ResponseHandler responseHandler;

    //<editor-fold desc="constructors and basic getters">
    TEaCH(String botToken, String botUsername) {
        super(botToken, botUsername);
        responseHandler = new ResponseHandler(sender, db);
    }

    TEaCH() {
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
                .action(ctx -> silent.send(rollDice(ctx), ctx.chatId()))
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
                .name("DEFAULT")
                .info("Rates every captions photo")
                .flag(CAPTION)
                .privacy(PUBLIC)
                .locality(ALL)
                .action(ctx -> {
                    silent.send("photo rating: " + (new Random().nextInt(10) + 1), ctx.chatId());
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

    public String rollDice(MessageContext ctx) { //takes input of XdY, where X is num of dice and Y is num of sides on each die TODO get sum for rolls 100>x>10 TODO print 20 rolls to DM
        String badInput = "Try again, see examples of good input below: \n/roll 2d6\t[rolls (2) 6-sided dice]\n/roll 1d20\t[rolls (1) 20-sided die]\n/roll d20\t[rolls (1) 20-sided die]";
        if (ctx.arguments().length == 0) return badInput;

        String userArg1 = ctx.firstArg().toLowerCase();

        final boolean CONTAINS_ONE_D = userArg1.contains("d") &&
                userArg1.replace("d", "").length() == userArg1.length() - 1;

        if (CONTAINS_ONE_D &&
                userArg1.indexOf("d") != 0 &&
                userArg1.indexOf("d") != userArg1.length() &&
                userArg1.length() >= 3 &&
                userArg1.replace("d", "").chars().allMatch(Character::isDigit)) { //check for 2d6 type format
            int dieCount = Integer.parseInt(userArg1.substring(0, userArg1.indexOf("d")));
            int dieFaces = Integer.parseInt(userArg1.substring(userArg1.indexOf("d") + 1));
            if (dieFaces == 1) {
                return "please reconsider what you're doing right now";
            }
            if (dieCount < 1 || dieFaces < 1) {
                return badInput;
            }

            Random rand = new Random();
            StringBuilder output = new StringBuilder();

            if (dieCount > 10) {
                dieCount = 10;
                output.append("You are limited to 10 rolls at a time\n");
            }
            if (dieCount == 1) {
                return (rand.nextInt(dieFaces) + 1) + "";
            }
            int randomInput;
            int sum = 0;

            output.append("Rolling (").append(dieCount).append(") ").append(dieFaces).append("-sided dice\n");
            for (int i = 0; i < dieCount; i++) {
                randomInput = rand.nextInt(dieFaces) + 1;
                output.append("Roll ").append(i + 1).append(": ").append(randomInput).append("\n");
                sum += randomInput;
            }
            output.append("Sum: ").append(sum);
            return output.toString();
        } else if (CONTAINS_ONE_D &&
                userArg1.indexOf("d") == 0 &&
                userArg1.replace("d", "").chars().allMatch(Character::isDigit)) { //check for d6 type format
            int dieFaces = Integer.parseInt(userArg1.substring(1));
            if (dieFaces == 1) {
                return "please reconsider what you're doing right now";
            }
            if (dieFaces < 1) {
                return badInput;
            }
            Random rand = new Random();
            return rand.nextInt(dieFaces) + 1 + "";
        }
        return badInput;
    }
}