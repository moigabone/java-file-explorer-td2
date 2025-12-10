package com.esiea.pootd2.models;

public class FileInode extends Inode {
    private int size;

    public FileInode(String name) {
        super(name);
        this.size = (int) (Math.random() * 100000) + 1;
    }

    @Override
    public int getSize() {
        return this.size;
    }
}