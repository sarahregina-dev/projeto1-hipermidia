package org.example.model;

import java.util.Map;

public class World {

    private Map<String, Room> rooms;
    private Room startingRoom;
    private Room endingRoom;
    private Room currentRoom;
    private int inventaryMaxItems;
    private boolean isGameOver = false;

    public World(Map<String, Room> rooms, Room startingRoom, Room currentRoom, Room endingRoom, int inventaryMaxItems) {
        this.rooms = rooms;
        this.startingRoom = startingRoom;
        this.currentRoom = currentRoom;
        this.endingRoom = endingRoom;
        this.inventaryMaxItems = inventaryMaxItems;

    }

    public Map<String, Room> getRooms() {
        return rooms;
    }

    public void setRooms(Map<String, Room> rooms) {
        this.rooms = rooms;
    }

    public Room getStartingRoom() {
        return startingRoom;
    }

    public void setStartingRoom(Room startingRoom) {
        this.startingRoom = startingRoom;
    }

    public Room getEndingRoom() {
        return endingRoom;
    }

    public void setEndingRoom(Room endingRoom) {
        this.endingRoom = endingRoom;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public void setGameOver(boolean isGameOver) {
        this.isGameOver = isGameOver;
    }
    public boolean isGameOver() {
        return isGameOver;
    }

    public int getInventaryMaxItems() {
        return inventaryMaxItems;
    }

    public void setInventaryMaxItems(int inventaryMaxItems) {
        this.inventaryMaxItems = inventaryMaxItems;
    }


}
