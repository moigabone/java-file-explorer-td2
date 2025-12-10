package com.esiea.pootd2.commands.parsers;

import com.esiea.pootd2.commands.Command;

/**
 * Interface to translate user input (String) into an executable Command.
 */
public interface ICommandParser {

    /**
     * Parses the text entered by the user and returns the corresponding Command object.
     * Returns an ErrorCommand if the input is invalid.
     */
    Command parse(String commandStr);
}