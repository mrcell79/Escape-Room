package adventure.games;

import adventure.GameDescription;
import adventure.db.DB_Manager;

public class EscapeRooms extends GameDescription {
    //Inizializza la mappa del gioco
    @Override
    public void init() throws Exception {
        DB_Manager db = new DB_Manager();
        db.InitConnection();

        getGame_obj().putAll(db.loadGame_Object());
        getRooms().addAll(db.loadRooms().values());
        getLogicRoom().addAll(db.loadLogicRooms().values());
        getDescriptions().putAll(db.loadDescription());
        setCurrentRoom(getRooms().get(0));

        db.CloseConnection();
    }

}
