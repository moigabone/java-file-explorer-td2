package com.esiea.pootd2.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a folder (directory) in the file system.
 * A folder contains a list of children (Files or other Folders) and its size is the sum of their sizes.
 */
public class FolderInode extends Inode {
    
    /**
     * The list of elements contained in this folder.
     * Thanks to polymorphism, it can hold both FileInode and FolderInode objects.
     */
    private List<Inode> children;

    public FolderInode(String name) {
        super(name);
        this.children = new ArrayList<>();
    }

    /**
     * Adds a new file or folder to this directory.
     * It also updates the child's parent reference to point to this folder.
     */
    public void addInode(Inode inode) {
        inode.setParent(this);
        this.children.add(inode);
    }
    
    public List<Inode> getChildren() {
        return children;
    }

    /**
     * Calculates the size of the folder by summing the sizes of all its children.
     * This is a recursive operation if the folder contains sub-folders.
     */
    @Override
    public int getSize() {
        int totalSize = 0;
        for (Inode child : children) {
            totalSize += child.getSize();
        }
        return totalSize;
    }
}