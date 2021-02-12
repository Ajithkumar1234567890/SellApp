package com.ajith.sellapp;

import com.google.firebase.database.FirebaseDatabase;

public class SingletonClass {
    public static FirebaseDatabase rootNode;

    public static FirebaseDatabase getRootNode() {
        if(rootNode==null){
            return FirebaseDatabase.getInstance();
        }
        return rootNode;
    }

}
