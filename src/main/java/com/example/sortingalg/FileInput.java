package com.example.sortingalg;

import java.io.BufferedReader;
import java.io.FileReader;

public class FileInput {
    public static int[] loadFromFile(String path) {

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line = br.readLine();
            br.close();

            String[] parts = line.split(",");
            int[] arr = new int[parts.length];

            for (int i = 0; i < parts.length; i++) {
                arr[i] = Integer.parseInt(parts[i].trim());
            }

            return arr;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new int[0];
    }
}
