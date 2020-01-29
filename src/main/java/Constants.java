public interface Constants {
    public static final int SOCKET_TIMEOUT = 75000;
    // Initialization
    String BOT_USERNAME = "TEaCHer";
    String BOT_TOKEN = System.getenv("wagieBotToken");
    int CREATOR_ID = Integer.parseInt(System.getenv("creatorId"));
    String CHAT_STATES = "CHAT_STATES";

    String START_DESCRIPTION = "Starts the WagieBot (not really)";
    String START_REPLY = "BEEP BOOP. Started";

    String STARTEXERCISE_DESCRIPTION = "Starts your exercise reminders";
    String STARTEXERCISE_REPLY = "Oh, so you want to be reminded to work out, huh?";

    String TRAINING_TODAY_REPLY = "Okay then take this as a reminder ;)";
    String TRAINING_TOMORROW_REPLY = "Okay I'll remind you tomorrow at nine o'clock!";

    String TRAINING_TODAY = "Today";
    String TRAINING_TOMORROW = "Tomorrow";
    String FIND_TRAINING_DATE = "Do you want to have a workout today or tomorrow?";
}