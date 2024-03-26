package org.example.fineasy.models;


public class DataManagementSingleton {
    private static DataManagement instance;

    private DataManagementSingleton() {}

    public static DataManagement getInstance() {
        if (instance == null) {
            instance = new DataManagement();
        }
        return instance;
    }
}

