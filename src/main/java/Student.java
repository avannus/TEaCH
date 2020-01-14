import org.jetbrains.annotations.NotNull;
import org.telegram.abilitybots.api.objects.MessageContext;
import java.util.Random;

public class Student { //methods for TEaCHer that aren't primary functions

    static String rollDice(@NotNull MessageContext ctx) { //takes input of XdY, where X is num of dice and Y is num of sides on each die TODO get sum for rolls 100>x>10 TODO print 20 rolls to DM
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