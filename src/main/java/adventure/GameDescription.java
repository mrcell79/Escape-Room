package adventure;

import adventure.type.*;
import java.util.*;

public abstract class GameDescription {
    private GameDescription game;
    private List<Room> rooms = new ArrayList<>();
    private List<GameObject> inventory = new ArrayList<>();
    private Map<Integer, GameObject> game_obj = new HashMap<>();
    private Map<Integer, String> descriptions = new HashMap<>();
    private List<Logic_Room> l_room = new ArrayList<>();
    private Room currentRoom;
    private Logic_Room currentLogicRoom;

    //Metodi
    public GameDescription getGameDescription(){
        return game;
    }
    public void setGame(GameDescription game) {
        this.game = game;
    }
    public Map<Integer, String> getDescriptions() {
        return descriptions;
    }
    public Map<Integer, GameObject> getGame_obj() {
        return game_obj;
    }
    public List<Logic_Room> getLogicRoom(){return l_room;}
    public Room getCurrentRoom() {
        return currentRoom;
    }
    public void setCurrentRoom(Room room){
        this.currentRoom = room;
    }
    public Logic_Room getCurrentLogicRoom() { return currentLogicRoom; }
    public void setCurrentLogicRoom(Logic_Room logic_room) { this.currentLogicRoom = logic_room; }

    public List<GameObject> getInventory() {
        return inventory;
    }
    public List<Room> getRooms() {
        return rooms;
    }


    public abstract void init() throws Exception;



}
