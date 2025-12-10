package com.esiea.pootd2.commands.parsers;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import com.esiea.pootd2.commands.*;

public class UnixLikeCommandParser implements ICommandParser {

    @Override
    public Command parse(String commandStr) {
        if (commandStr == null || commandStr.trim().isEmpty()) {
            return new ErrorCommand("Empty command");
        }


        List<String> args = splitArguments(commandStr);
        
        return mapCommand(args);
    }

    private List<String> splitArguments(String commandStr) {
        
        String[] parts = commandStr.trim().split("\\s+");
        return new ArrayList<>(Arrays.asList(parts));
    }

    private Command mapCommand(List<String> args) {
        String keyword = args.get(0); 

        switch (keyword) {
            case "ls":
                return new ListCommand();
                
            case "cd":
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