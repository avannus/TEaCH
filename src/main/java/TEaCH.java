import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Flag;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;

import java.util.Random;

public class TEaCH extends AbilityBot implements Constants {

    private final ResponseHandler responseHandler;

    TEaCH(String botToken, String botUsername) {
        super(botToken, botUsername);
        responseHandler = new ResponseHandler(sender, db);
    }

    TEaCH() {
        this(BOT_TOKEN, BOT_USERNAME);
    }

    public Ability d20() {
        Random rand = new Random();
        return Ability
                .builder()
                .name("d20")
                .info("rolls a d20")
                .locality(Locality.ALL)
                .privacy(Privacy.PUBLIC)
                .action(ctx -> silent.send(rand.nextInt(20) + 1 + "", (long) ctx.chatId()))
                .build();
    }

    public Ability roll() {
        return Ability
                .builder()
                .name("roll")
                .info("/roll xdy, rolls qty(x) of y-sided dice")
                .locality(Locality.ALL)
                .privacy(Privacy.PUBLIC)
                .action(ctx -> silent.send(
                        rollDice(ctx.update().getMessage().getText().replaceAll("\\s+", "").substring(5)),
                        ctx.chatId()))  //.action(ctx -> silent.send(rand.nextInt(20)+1+"", (long) ctx.user().getId()))
                .build();
    }

    public Ability shipping() {
        return Ability
                .builder()
                .name("shipping")
                .info("i dont like shipping bot")
                .locality(Locality.ALL)
                .privacy(Privacy.PUBLIC)
                .action(ctx -> silent.send("I don't ship it", ctx.chatId()))
                .build();
    }

    public String rollDice(String userInput) { //takes input of XdY, where X is num of dice and Y is num of sides on each die
        userInput = userInput.toLowerCase();
        String badInput = "bad input. Try again, see examples of good input below: \n/roll 2d6 [rolls (2) 6-sided dice]\n/roll 1d20 [rolls (1) 20-sided die]\n/roll d20 [rolls (1) 20-sided die]";

        final boolean CONTAINS_ONE_D = userInput.contains("d") &&
                userInput.replace("d", "").length() == userInput.length() - 1;

        if (CONTAINS_ONE_D &&
                userInput.indexOf("d") != 0 &&
                userInput.indexOf("d") != userInput.length() &&
                userInput.length() >= 3 &&
                userInput.replace("d", "").chars().allMatch(Character::isDigit)) { //check for 2d6 type format
            int dieCount = Integer.parseInt(userInput.substring(0, userInput.indexOf("d")));
            int dieFaces = Integer.parseInt(userInput.substring(userInput.indexOf("d") + 1));
            if (dieFaces == 1) return "please reconsider what you're doing right now";
            if (dieCount < 1 || dieFaces < 2) return badInput;

            Random rand = new Random();
            StringBuilder output = new StringBuilder();

            if (dieCount > 10) {
                dieCount = 10;
                output.append("You are limited to 10 rolls at a time\n");
            }
            if (dieCount == 1) {
                return (rand.nextInt(dieFaces) + 1) + "";
            }
            int randomInput = 0;
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
                userInput.indexOf("d") == 0 &&
                userInput.replace("d", "").chars().allMatch(Character::isDigit)) { //check for d6 type format
            int dieFaces = Integer.parseInt(userInput.substring(1));
            Random rand = new Random();
            return rand.nextInt(dieFaces) + 1 + "";
        }
        return badInput;
    }

    /**
     * This ability has an extra "flag". It needs a photo to activate! This feature is activated by default if there is no /command given.
     */
    public Ability sayNiceToPhoto() {
        return Ability
                .builder()
                .name(DEFAULT) // DEFAULT ability is executed if user did not specify a command -> Bot needs to have access to messages (check FatherBot)
                .flag(Flag.PHOTO)
                .privacy(Privacy.PUBLIC)
                .locality(Locality.ALL)
                .input(0)
                .action(ctx -> silent.send("Daaaaang, what a nice photo!", ctx.chatId()))
                .build();
    }

    public Ability replyToStart() {
        return Ability
                .builder()
                .name("start")
                .info(Constants.START_DESCRIPTION)
                .locality(Locality.ALL)
                .privacy(Privacy.PUBLIC)
                .action(ctx -> responseHandler.replyToStart(ctx.chatId()))
                .build();
    }

    public Ability replyViaDM() {
        return Ability
                .builder()
                .name("dm")
                .info("DMs you when you use this in a group chat")
                .locality(Locality.ALL)
                .privacy(Privacy.PUBLIC)
                .action(ctx -> silent.send("Hi " + ctx.user().getFirstName() + ", I just wanted to pull you aside so the other people wouldn't hear me call you a " + System.getenv("mysteryVar"), (long) ctx.user().getId()))
                .build();
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
}