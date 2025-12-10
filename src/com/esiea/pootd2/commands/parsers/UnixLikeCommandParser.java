package com.esiea.pootd2.commands.parsers;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import com.esiea.pootd2.commands.*;

/**
 * Parses commands using Unix-like syntax (e.g., "ls", "mkdir name").
 */
public class UnixLikeCommandParser implements ICommandParser {

    @Override
    public Command parse(String commandStr) {
        // Basic safety check
        if (commandStr == null || commandStr.trim().isEmpty()) {
            return new ErrorCommand("Empty command");
        }

        // 1. Split the command line into words
        List<String> args = splitArguments(commandStr);
        
        // 2. Convert the list of words into a Command object
        return mapCommand(args);
    }

    /**
     * Splits string by spaces, handling multiple spaces correctly.
     */
    private List<String> splitArguments(String commandStr) {
        String[] parts = commandStr.trim().split("\\s+");
        return new ArrayList<>(Arrays.asList(parts));
    }

    /**
     * Creates the right Command object based on the first word (keyword).
     */
    private Command mapCommand(List<String> args) {
        String keyword = args.get(0);

        switch (keyword) {
            case "ls":
                return new ListCommand();
                
            case "cd":
                // If no argument is provided, default to root "/"
                if (args.size() < 2) 
                    return new ChangeDirectoryCommand("/");
                return new ChangeDirectoryCommand(args.get(1));
                
            case "mkdir":
                if (args.size() < 2) 
                    return new ErrorCommand("Usage: mkdir <directory_name>");
                return new MakeDirectoryCommand(args.get(1));
                
            case "touch":
                if (args.size() < 2) 
                    return new ErrorCommand("Usage: touch <file_name>");
                return new TouchCommand(args.get(1));
                
            default:
                return new ErrorCommand("Unknown command: " + keyword);
        }
    }
}