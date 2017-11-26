package com.hermod.bottomline.fps.storage;

import java.util.HashMap;

public class InMemoryMessageStorage {

    private HashMap<String, MessageBean> storage = new HashMap<>();

    private static final InMemoryMessageStorage instance = new InMemoryMessageStorage();

    //private constructor to avoid client applications to use constructor
    private InMemoryMessageStorage(){}

    public static InMemoryMessageStorage getInstance(){
        return instance;
    }
}
