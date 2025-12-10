package com.esiea.pootd2.models;

/**
 * Represents a standard file in the file system.
 * Unlike folders, a file has a fixed size determined at creation.
 */
public class FileInode extends Inode {
    
    private int size;

    public FileInode(String name) {
        super(name);
        // Assign a random size between 1 and 100,000 as requested in the instructions
        this.size = (int) (Math.random() * 100000) + 1;
    }

    /**
     * Returns the file's size (stored in memory).
     */
    @Override
    public int getSize() {
        return this.size;
    }
}