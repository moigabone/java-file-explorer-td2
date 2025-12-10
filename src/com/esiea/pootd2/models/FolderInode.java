package com.esiea.pootd2.models;

import java.util.ArrayList;
import java.util.List;

public class FolderInode extends Inode {
    private List<Inode> children;

    public FolderInode(String name) {
        super(name);
        this.children = new ArrayList<>();
    }

    public void addInode(Inode inode) {
        inode.setParent(this);
        this.children.add(inode);
    }
    
    public List<Inode> getChildren() {
        return children;
    }

    @Override
    public int getSize() {
        int totalSize = 0;
        for (Inode child : children) {
            totalSize += child.getSize();
        }
        return totalSize;
    }
}