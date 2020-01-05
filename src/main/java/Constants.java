public interface Constants {
    public static final int SOCKET_TIMEOUT = 75000;
    // Initialization
    String BOT_USERNAME = "Yes Man";
    String BOT_TOKEN = System.getenv("wagieBotToken");
    int CREATOR_ID = Integer.parseInt(System.getenv("creatorId"));
    String START_DESCRIPTION = "Starts the WagieBot (not really)";
    String CHAT_STATES = "CHAT_STATES";
    String START_REPLY = "BEEP BOOP. Started";
    String TRAINING_TODAY = "Today";
    String TRAINING_TOMORROW = "Tomorrow";
    String FIND_TRAINING_DATE = "Do you want to have a workout today or tomorrow?";
}