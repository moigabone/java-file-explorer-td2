package com.esiea.pootd2.models;

/**
 * Abstract base class representing any element in the file system (File or Folder).
 * It defines the common structure: a name and a parent folder.
 */
public abstract class Inode {
    
    private String name;
    
    /**
     * The parent folder containing this inode.
     * 'protected' allows direct access by child classes if needed.
     */
    protected FolderInode parent;

    public Inode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public FolderInode getParent() {
        return parent;
    }

    public void setParent(FolderInode parent) {
        this.parent = parent;
    }

    /**
     * Abstract method to get the size.
     * Files have a fixed size, while Folders calculate it dynamically.
     */
    public abstract int getSize();
}