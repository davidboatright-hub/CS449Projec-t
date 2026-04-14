package com.mycompany.sprint3;

import java.io.*;
import java.util.ArrayList;

class MoveIO {

    static void saveToFile(ArrayList<Move> moves, int boardSize, String boardType, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // write board type
            writer.println(String.format("%s,%d", boardType, boardSize));
            // write moves
            for (Move m : moves) {
                writer.println(m.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    static String[] getBoardData(String filename){
        String[] board = new String[2];
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line= reader.readLine();
            board = line.split(",");
     
        } catch (IOException e) {
            e.printStackTrace();
        }
        return board;
    }
    
    static ArrayList<Move> loadFromFile(String filename) {
    ArrayList<Move> moves = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
        String line;
        // Skip board type line
        line = reader.readLine();
        while ((line = reader.readLine()) != null) {
            if (line.contains(",")) {
                String[] parts = line.split(" -> ");
                String[] start = parts[0].split(",");
                String[] end = parts[1].split(",");

                moves.add(new Move(
                    Integer.parseInt(start[0]),
                    Integer.parseInt(start[1]),
                    Integer.parseInt(end[0]),
                    Integer.parseInt(end[1])
                ));
            }
            else{
                moves.add(new RandomizeMove(Long.parseLong(line)));
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    return moves;
}
}