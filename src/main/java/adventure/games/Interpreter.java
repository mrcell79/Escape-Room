package adventure.games;

import adventure.GameDescription;
import adventure.type.GameObject;
import adventure.type.Logic_Room;
import adventure.type.Room;
import adventure.type.gameObjectContainer;


public class Interpreter {

    private static final String OPENED_CONT = "Il Baule si à aperto!";
    private static final String PICKED = "Chiave raccolta!";
    private static final String NO_CONT_OBJ = "Non c'è più niente da raccogliere in questo baule!";
    private static final String ALREADY_OPEN = "Hai già aperto questo baule!";

    public Interpreter() { }

    public Logic_Room north(GameDescription g, short lr_N_id ){
        lr_N_id = g.getCurrentRoom().getLogic_rm_n();
        int i = 0;
        boolean found = false;
        while (i < g.getLogicRoom().size() && found == false) {
            if (g.getLogicRoom().get(i).getId() == lr_N_id){
                Logic_Room lr_N = g.getLogicRoom().get(i);
                g.setCurrentLogicRoom(lr_N);
                found = true;
            }else {
                i++;
            }
        }
        Logic_Room NORTH = g.getCurrentLogicRoom();
        return NORTH;
    }

    public Logic_Room north_east(GameDescription g, short lr_NE_id){
        lr_NE_id = g.getCurrentRoom().getLogic_rm_ne();
        int i = 0;
        boolean found = false;
        while (i < g.getLogicRoom().size() && found == false) {
            if (g.getLogicRoom().get(i).getId() == lr_NE_id){
                Logic_Room lr_NE = g.getLogicRoom().get(i);
                g.setCurrentLogicRoom(lr_NE);
                found = true;
            }else {
                i++;
            }
        };
        Logic_Room NORTH_EAST = g.getCurrentLogicRoom();
        return NORTH_EAST;
    }

    public Logic_Room north_west(GameDescription g, short lr_NW_id){
        lr_NW_id = g.getCurrentRoom().getLogic_rm_nw();
        int i = 0;
        boolean found = false;
        while (i < g.getLogicRoom().size() && found == false) {
            if (g.getLogicRoom().get(i).getId() == lr_NW_id){
                Logic_Room lr_NW = g.getLogicRoom().get(i);
                g.setCurrentLogicRoom(lr_NW);
                found = true;
            }else {
                i++;
            }
        }
        Logic_Room NORTH_WEST = g.getCurrentLogicRoom();
        return NORTH_WEST;
    }

    public Logic_Room east(GameDescription g, short lr_E_id){
        lr_E_id = g.getCurrentRoom().getLogic_rm_e();
        int i = 0;
        boolean found = false;
        while (i < g.getLogicRoom().size() && found == false) {
            if (g.getLogicRoom().get(i).getId() == lr_E_id){
                Logic_Room lr_E = g.getLogicRoom().get(i);
                g.setCurrentLogicRoom(lr_E);
                found = true;
            }else {
                i++;
            }
        }
        Logic_Room EAST = g.getCurrentLogicRoom();
        return EAST;
    }

    public Logic_Room west(GameDescription g, short lr_W_id){
        lr_W_id = g.getCurrentRoom().getLogic_rm_w();
        int i = 0;
        boolean found = false;
        while (i < g.getLogicRoom().size() && found == false) {
            if (g.getLogicRoom().get(i).getId() == lr_W_id){
                Logic_Room lr_W = g.getLogicRoom().get(i);
                g.setCurrentLogicRoom(lr_W);
                found = true;
            }else {
                i++;
            }
        }
        Logic_Room WEST = g.getCurrentLogicRoom();
        return WEST;
    }

    public Logic_Room south(GameDescription g, short lr_S_id){
        lr_S_id = g.getCurrentRoom().getLogic_rm_s();
        int i = 0;
        boolean found = false;
        while (i < g.getLogicRoom().size() && found == false) {
            if (g.getLogicRoom().get(i).getId() == lr_S_id){
                Logic_Room lr_S = g.getLogicRoom().get(i);
                g.setCurrentLogicRoom(lr_S);
                found = true;
            }else {
                i++;
            }
        }
        Logic_Room SOUTH = g.getCurrentLogicRoom();
        return SOUTH;
    }

    public Room insert(GameDescription g){

            if(g.getCurrentLogicRoom().getId() == 4) {
                g.getCurrentLogicRoom().getDoors().get(0).setLocked(false);
                g.setCurrentRoom(g.getRooms().get(1));
            }else  if(g.getCurrentLogicRoom().getId() == 7) {
                g.getCurrentLogicRoom().getDoors().get(0).setLocked(false);
                g.setCurrentRoom(g.getRooms().get(2));
            }else  if(g.getCurrentLogicRoom().getId() == 12) {
                g.getCurrentLogicRoom().getDoors().get(0).setLocked(false);
            }

            g.getInventory().clear();

        Room r = g.getCurrentRoom();
        return r;
    }
    public String open(GameDescription g, int o_id){
        String desc = "";
        o_id = g.getCurrentLogicRoom().getFirstObject();

        int i = 0;
        boolean found = false;
        while (i < g.getCurrentLogicRoom().getObjects().size() && found == false){
            if (g.getCurrentLogicRoom().getObjects().get(i).getID() == o_id  && g.getCurrentLogicRoom().getObjects().get(i).isOpenable()
                    && !g.getCurrentLogicRoom().getObjects().get(i).isOpen()) {
                g.getCurrentLogicRoom().getObjects().get(i).setOpen(true);

                desc = OPENED_CONT + "\n" + g.getCurrentLogicRoom().getObjects().get(i).getObjDescription();
                found = true;
            } else if(g.getCurrentLogicRoom().getObjects().get(i).getID() == o_id  && g.getCurrentLogicRoom().getObjects().get(i).isOpen()){
                desc = ALREADY_OPEN;
                g.getCurrentLogicRoom().getObjects().get(i).setOpen(false);
                found = true;
            }else {
                i++;
            }

        }

        return desc;
    }

    public String read(GameDescription g){
        String desc = "";

        gameObjectContainer container = (gameObjectContainer) g.getCurrentLogicRoom().getObjects().get(0);
        GameObject enigma = container.getContainerList().get(0);

        if (!enigma.isVisible()) {
            desc = NO_CONT_OBJ;
        } else {
            if (enigma.isReadable() && enigma != null) {
                container.removeContList(enigma);
                desc = enigma.getObjDescription();
                enigma.setVisible(false);
            }
        }
        return desc;
    }

    public String use(GameDescription g){
        String desc = "";

        gameObjectContainer container = (gameObjectContainer) g.getCurrentLogicRoom().getObjects().get(0);
        GameObject potion = container.getContainerList().get(0);

        if (!potion.isVisible()) {
            desc = NO_CONT_OBJ;
        } else {
            if (potion.isUsable() && potion != null) {
                container.removeContList(potion);
                desc = potion.getObjDescription();
                potion.setVisible(false);
            }
        }
        return desc;
    }

    public String pick_up(GameDescription g){
        String desc = "";

        gameObjectContainer container = (gameObjectContainer) g.getCurrentLogicRoom().getObjects().get(0);
        GameObject key = container.getContainerList().get(0);

        if (!key.isVisible()) {
            desc = NO_CONT_OBJ;
        } else {
            if (key.isPickable() && key != null) {
                g.getInventory().add(key);
                container.removeContList(key);

                desc = PICKED + "\n";
                key.setVisible(false);
            }
        }
        return desc;
    }

    public Room push(GameDescription g){
        g.setCurrentRoom(g.getRooms().get(1));
        g.getInventory().clear();
        Room r = g.getCurrentRoom();
        return r;
    }

}
