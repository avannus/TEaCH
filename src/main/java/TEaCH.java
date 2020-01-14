import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.*;
import java.util.Random;
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
    //</editor-fold>

//    public Ability d20() { useless due to /roll
//        Random rand = new Random();
//        return Ability
//                .builder()
//                .name("d20")
//                .info("rolls a d20")
//                .locality(Locality.ALL)
//                .privacy(Privacy.PUBLIC)
//                .action(ctx -> silent.send(rand.nextInt(20) + 1 + "", (long) ctx.chatId()))
//                .build();
//    }

    public Ability allInfo() { //lets me find all info about a chat and my message
        return Ability
                .builder()
                .name("allinfo")
                .info("gives all info about a certain chat")
                .locality(Locality.ALL)
                .privacy(Privacy.CREATOR)
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
                .locality(Locality.ALL)
                .privacy(Privacy.PUBLIC)
                .action(ctx -> silent.send(
                        rollDice(ctx.update().getMessage().getText().replaceAll("\\s+", "").substring(5)), //TODO fix substring so it works when someone does /roll@wagie_bot instead of /roll
                        ctx.chatId()
                        )
                )
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

    public Ability ratePhoto(){
        return Ability
                .builder()
                .name(DEFAULT)
                .flag(Flag.PHOTO)
                .privacy(Privacy.PUBLIC)
                .locality(Locality.ALL)
                .action(ctx -> silent.send(getRandPhotoRating(ctx), ctx.chatId()))
                .build();
    }

    public String getRandPhotoRating(MessageContext ctx){//TODO this isn't the right way to do it lmao
        Random rand = new Random();
        if(ctx.update().getMessage().getCaption().toLowerCase().contains("rate")){
            return ("I give it a "+rand.nextInt(11)+".");
        }
        return "";
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

    public String rollDice(String userInput) { //takes input of XdY, where X is num of dice and Y is num of sides on each die TODO get sum for rolls 100>x>10 TODO print 20 rolls to DM
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
}