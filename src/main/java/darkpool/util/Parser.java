package darkpool.util;

import java.time.format.DateTimeParseException;
import java.util.Objects;

import darkpool.command.AddCommand;
import darkpool.command.Command;
import darkpool.command.DeleteCommand;
import darkpool.command.ExitCommand;
import darkpool.command.FindCommand;
import darkpool.command.ListCommand;
import darkpool.command.MarkCommand;
import darkpool.command.UnmarkCommand;
import darkpool.task.Deadline;
import darkpool.task.Event;
import darkpool.task.Todo;


/**
 * Parses user input and returns the corresponding command.
 */
public class Parser {

    /**
     * Parses the user input and returns the corresponding command.
     *
     * @param input The user input string.
     * @return The command corresponding to the user input.
     * @throws DarkpoolException If the user input is invalid.
     */
    public static Command parse(String input) throws DarkpoolException {
        String[] userInput = input.split(" ", 2);

        if (userInput.length == 2) {
            userInput[1] = userInput[1].trim();
        }

        switch (userInput[0]) {
        case "bye" -> {
            return new ExitCommand();
        }

        case "list" -> {
            return new ListCommand();
        }

        case "mark" -> {
            int num = taskAction(userInput);
            return new MarkCommand(num);
        }

        case "unmark" -> {
            int num = taskAction(userInput);
            return new UnmarkCommand(num);
        }

        case "delete" -> {
            int num = taskAction(userInput);
            return new DeleteCommand(num);
        }

        case "find" -> {
            if (userInput.length < 2 || Objects.equals(userInput[1], "")) {
                throw new DarkpoolException("you missed the keyword bruh");
            }
            return new FindCommand(userInput[1]);
        }

        case "todo" -> {
            if (userInput.length < 2 || Objects.equals(userInput[1], "")) {
                throw new DarkpoolException("you missed the task description bruh");
            }
            String desc = userInput[1];
            return new AddCommand(new Todo(desc, false));
        }

        case "deadline" -> {
            if (userInput.length < 2 || Objects.equals(userInput[1], "")) {
                throw new DarkpoolException("wheres the deadline of the task, and whats the task of the deadline??");
            }
            String text = userInput[1];
            if (!text.contains("/by")) {
                throw new DarkpoolException("bruh might as well use todo");
            }
            String[] array = getStrings(text);
            String desc = array[0].trim();
            String by = array[1].trim();
            if (desc.isEmpty()) {
                throw new DarkpoolException("whats the task of the deadline??");
            }
            return new AddCommand(new Deadline(desc, by, false));
        }

        case "event" -> {
            if (userInput.length < 2 || Objects.equals(userInput[1], "")) {
                throw new DarkpoolException("the event syntax is [task_desc] /from [from_date] /to [to_date]");
            }

            String[] fromParts = getParts(userInput);

            String[] toParts = getToParts(fromParts);
            String desc = fromParts[0];
            String from = toParts[0];
            String to = toParts[1];
            try {
                return new AddCommand(new Event(desc, from.trim(), to.trim(), false));
            } catch (DateTimeParseException e) {
                throw new DarkpoolException("bro you know what a date time format is?");
            }
        }

        default -> throw new DarkpoolException("what???");
        }
    }

    /**
     * Splits the input string into parts based on the "/to" keyword.
     *
     * @param fromParts The input string split by "/from".
     * @return An array containing the parts split by "/to".
     * @throws DarkpoolException If the input is invalid.
     */
    private static String[] getToParts(String[] fromParts) throws DarkpoolException {
        String[] toParts = fromParts[1].split("/to ");

        if (toParts.length > 3) {
            throw new DarkpoolException("only one to pls");
        } else if (toParts.length < 2 || toParts[1].trim().isEmpty()) {
            throw new DarkpoolException("to where???");
        } else if (toParts[0].trim().isEmpty()) {
            throw new DarkpoolException("from where???");
        }
        return toParts;
    }

    /**
     * Splits the user input into parts based on the "/from" keyword.
     *
     * @param userInput The user input string.
     * @return An array containing the parts split by "/from".
     * @throws DarkpoolException If the input is invalid.
     */
    private static String[] getParts(String[] userInput) throws DarkpoolException {
        String[] fromParts = userInput[1].split("/from");

        if (fromParts.length > 2) {
            throw new DarkpoolException("only one from pls");
        } else if (fromParts.length < 2 || fromParts[1].trim().isEmpty()) {
            throw new DarkpoolException("where is from bruh");
        } else if (fromParts[0].trim().isEmpty()) {
            throw new DarkpoolException("task where???");
        }
        return fromParts;
    }

    /**
     * Splits the input string into parts based on the "/by" keyword.
     *
     * @param input The input string.
     * @return An array containing the parts split by "/by".
     * @throws DarkpoolException If the input is invalid.
     */
    private static String[] getStrings(String input) throws DarkpoolException {
        if ((input.length() - input.replace("/by", "").length()) / "/by".length() > 1) {
            throw new DarkpoolException("everything good? there cant be multiple deadlines");
        }

        String[] array = input.split("/by");

        if (array.length > 2) {
            throw new DarkpoolException("everything good? there cant be multiple deadlines");
        } else if (array.length == 0) {
            throw new DarkpoolException("wheres the deadline of the task, and whats the task of the deadline??");
        } else if (array.length == 1) {
            throw new DarkpoolException("so close! you forgot to enter the deadline");
        }
        return array;
    }

    /**
     * Parses the task number from the user input.
     *
     * @param userInput The user input string.
     * @return The task number.
     * @throws DarkpoolException If the task number is invalid.
     */
    private static int taskAction(String[] userInput) throws DarkpoolException {
        if (userInput.length < 2 || Objects.equals(userInput[1], "")) {
            throw new DarkpoolException("you missed the task number bruh");
        }

        int num;

        try {
            num = Integer.parseInt(userInput[1]) - 1;
        } catch (NumberFormatException e) {
            throw new DarkpoolException("do you know what a number is?");
        }
        return num;
    }
}
