package com.esiea.pootd2.controllers;

import com.esiea.pootd2.models.*;
import com.esiea.pootd2.commands.*;
import com.esiea.pootd2.commands.parsers.*;

public class ExplorerController implements IExplorerController {
    private FolderInode root;
    private FolderInode currentFolder;
    private ICommandParser parser;

    public ExplorerController() {
        this.root = new FolderInode("/");
        this.currentFolder = this.root;
        this.parser = new UnixLikeCommandParser();
    }

    @Override
    public String executeCommand(String commandStr) {
        Command cmd = parser.parse(commandStr);
        
        return doCommand(cmd);
    }

    private String doCommand(Command cmd) {
        if (cmd instanceof ListCommand) {
            StringBuilder sb = new StringBuilder();
            for (Inode inode : currentFolder.getChildren()) {
                sb.append(inode.getName()).append("\n");
            }
            return sb.toString();
        }
        else if (cmd instanceof MakeDirectoryCommand) {
            String name = ((MakeDirectoryCommand) cmd).folderName;
            FolderInode newFolder = new FolderInode(name);
            currentFolder.addInode(newFolder);
            return "";
        }
        else if (cmd instanceof TouchCommand) {
            String name = ((TouchCommand) cmd).fileName;
            FileInode newFile = new FileInode(name);
            currentFolder.addInode(newFile);
            return "";
        }
        else if (cmd instanceof ChangeDirectoryCommand) {
            return handleChangeDirectory(((ChangeDirectoryCommand) cmd).path);
        }
        else if (cmd instanceof ErrorCommand) {
            return ((ErrorCommand) cmd).message + "\n";
        }
        return "Command not implemented yet\n";
    }

    private String handleChangeDirectory(String path) {
        if (path.equals("/")) {
            currentFolder = root;
            return "";
        }
        if (path.equals("..")) {
            if (currentFolder.getParent() != null) {
                currentFolder = currentFolder.getParent();
            }
            return "";
        }

        for (Inode inode : currentFolder.getChildren()) {
            if (inode instanceof FolderInode && inode.getName().equals(path)) {
                currentFolder = (FolderInode) inode;
                return "";
            }
        }
        return "cd: no such file or directory: " + path + "\n";
    }
}