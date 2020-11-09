package adventure.db;

import adventure.type.*;

import java.sql.*;
import java.util.*;

public class DB_Manager {
    public DB_Manager() {
    }

    //Nome driver JDBC e URL di adventure.database
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:./database h2/dbescape";

    //Credenziali database
    private static final String USER = "Davide";
    private static final String PASS = "12345";

    //Connessione a adventure.database
    private Connection conn = null;

    private Door dr = new Door();
    private gameObjectContainer container = new gameObjectContainer();
    private GameObject go = new GameObject();
    private Room rm = new Room();
    private Logic_Room l_rm = new Logic_Room();

    private Map<Integer, String> descr = new HashMap<>();
    private Map<Integer, Door> doors = new HashMap<>();
    private Map<Integer, GameObject> game_object = new HashMap<Integer, GameObject>();
    private Map<Integer, Room> rooms = new HashMap<>();
    private Map<Integer, Logic_Room> logic_r = new HashMap<>();


    private static final String query1 = "select * from description";
    private static final String query2 = "select * from doors";
    private static final String query3 = "select * from game_object";
    private static final String query4 = "select * from rooms";
    private static final String query5 = "select * from game_object where l_rooms_id = ?";
    private static final String query6 = "select * from logic_rooms";

    //Metodi

    public static String getQuery1() {
        return query1;
    }

    public static String getQuery2() {
        return query2;
    }

    public static String getQuery3() {
        return query3;
    }

    public static String getQuery4() {
        return query4;
    }

    public static String getQuery5() {
        return query5;
    }

    public static String getQuery6() {
        return query6;
    }

    public void InitConnection() {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //Per il caricamento completo degli oggetti
    public Map<Integer, GameObject> loadGame_Object() throws SQLException {
        descr = loadDescription();
        Statement stmt = null;
        try {
            stmt = getConn().createStatement();
            ResultSet rs = stmt.executeQuery(getQuery3());
            while (rs.next()) {
                if (rs.getBoolean("is_container")) {
                    container = new gameObjectContainer();
                    container.setID((short) rs.getInt("O_ID"));
                    container.setObjName(rs.getString("NAME"));
                    container.setOpenable(rs.getBoolean("OPENABLE"));
                    container.setPickable(rs.getBoolean("PICKABLE"));
                    container.setIs_container(rs.getBoolean("IS_CONTAINER"));
                    container.setObjDescription(descr.get(rs.getInt("O_DESC_ID")));
                    container.setPushable(rs.getBoolean("PUSHABLE"));
                    container.setReadable(rs.getBoolean("READABLE"));
                    container.setVisible(rs.getBoolean("VISIBLE"));
                    container.setUsable(rs.getBoolean("USABLE"));
                    container.setL_RoomId(rs.getInt("L_ROOMS_ID"));
                    container.setInner_Object(rs.getInt("INNER_OBJECT"));
                    container.setWhere_contained(rs.getInt("WHERE_CONTAINED"));

                    game_object.put(rs.getInt("O_ID"), container);
                } else {
                    go = new GameObject();
                    go.setID((short) rs.getInt("O_ID"));
                    go.setObjName(rs.getString("NAME"));
                    go.setOpenable(rs.getBoolean("OPENABLE"));
                    go.setPickable(rs.getBoolean("PICKABLE"));
                    go.setIs_container(rs.getBoolean("IS_CONTAINER"));
                    go.setObjDescription(descr.get(rs.getInt("O_DESC_ID")));
                    go.setPushable(rs.getBoolean("PUSHABLE"));
                    go.setReadable(rs.getBoolean("READABLE"));
                    go.setVisible(rs.getBoolean("VISIBLE"));
                    go.setUsable(rs.getBoolean("USABLE"));
                    go.setL_RoomId(rs.getInt("L_ROOMS_ID"));
                    go.setInner_Object(rs.getInt("INNER_OBJECT"));
                    go.setWhere_contained(rs.getInt("WHERE_CONTAINED"));

                    game_object.put(rs.getInt("O_ID"), go);
                }
            }
            rs.close();
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (stmt != null)
                stmt.close();
        }
        return game_object;
    }


    public Map loadDescription() throws SQLException {
        Statement stmt = null;
        try {
            stmt = getConn().createStatement();
            ResultSet rs = stmt.executeQuery(getQuery1());
            while (rs.next()) {
                descr.put(rs.getInt("ID"), rs.getString("DESCR"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (stmt != null)
                stmt.close();
        }
        return descr;
    }


    //Per il caricamento completo delle porte
    public Map loadDoors() throws SQLException {
        descr = loadDescription();
        Statement stmt = null;
        try {
            stmt = getConn().createStatement();
            ResultSet rs = stmt.executeQuery(getQuery2());
            while (rs.next()) {
                dr = new Door();
                dr.setName(rs.getString("NAME"));
                dr.setPrevious_Room(rs.getInt("PRE_ROOM"));
                dr.setNext_Room(rs.getInt("NEXT_ROOM"));
                dr.setLocked(rs.getBoolean("IS_LOCKED"));
                dr.setDescription(descr.get(rs.getInt("DESCR")));
                doors.put(rs.getInt("ID"), dr);
                ;
            }
            rs.close();
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (stmt != null)
                stmt.close();
        }
        return doors;
    }


    public Map loadLogicRooms() throws SQLException {
        descr = loadDescription();
        doors = loadDoors();

        Statement stmt = null;
        try {
            stmt = getConn().createStatement();
            ResultSet rs = stmt.executeQuery(getQuery6());
            while (rs.next()) {
                l_rm = new Logic_Room();
                l_rm.setId(rs.getShort("ID_LR"));
                l_rm.setName(rs.getString("NAME"));
                l_rm.setDescription(descr.get(rs.getInt("LR_DESC_ID")));
                l_rm.setFirstObject(rs.getInt("GO_ID"));
                l_rm.addDoor(doors.get(rs.getInt("DOOR_ID")));
                logic_r.put(rs.getInt("ID_LR"), l_rm);
            }
            rs.close();
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (stmt != null)
                stmt.close();
        }
        //metodo per aggiungere oggetti alla stanza
        loadL_RoomObj();
        return logic_r;
    }

    //Per il caricamento completo di Rooms
    public Map loadRooms() throws SQLException {
        descr=loadDescription();
        logic_r = loadLogicRooms();

        Statement stmt = null;
        try {
            stmt = getConn().createStatement();
            ResultSet rs = stmt.executeQuery(getQuery4());
            while (rs.next()) {
                rm = new Room();
                rm.setId(rs.getShort("ID_R"));
                rm.setName(rs.getString("NAME"));
                rm.setLogic_rm_n(rs.getShort("NORTH"));
                rm.setLogic_rm_s(rs.getShort("SOUTH"));
                rm.setLogic_rm_e(rs.getShort("EAST"));
                rm.setLogic_rm_ne(rs.getShort("N_EAST"));
                rm.setLogic_rm_w(rs.getShort("WEST"));
                rm.setLogic_rm_nw(rs.getShort("N_WEST"));
                rm.setDescription(descr.get(rs.getInt("DESC_ID")));
                rm.setLook(descr.get(rs.getInt("LOOK_ID")));
                rooms.put(rs.getInt("ID_R"), rm);
            }
            rs.close();
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (stmt != null)
                stmt.close();
        }
        return rooms;
    }
    //metodo per aggiungere oggetti alla stanza
    public List<GameObject> loadL_RoomObj() throws SQLException {
        game_object = loadGame_Object();
        int index = 0;
        List<GameObject> objectList = new ArrayList<>();
        PreparedStatement stmt;
        ResultSet rs;
        stmt = getConn().prepareStatement(getQuery5());
        try{
            for (int i=1; i <= logic_r.size(); i++) {
                index=0;
                stmt.setInt(1, i);
                rs = stmt.executeQuery();
                objectList= new ArrayList<>();
                container= new gameObjectContainer();
                while(rs.next()){
                        int id = rs.getInt("O_ID");
                        int whereContained = rs.getInt("where_contained");
                        if(rs.getInt("where_contained")!=0){    //SE SONO OGGETTI CONTENUTI
                            //mi riempie una lista con altri oggetti corrispondenti
                            index=rs.getInt("where_contained");
                            GameObject obj = game_object.get(id);
                            objectList.add(game_object.get(rs.getInt("O_ID")));
                        }else if(!game_object.get(rs.getInt("O_ID")).isIs_container()){ //SE NON SONO NE CONTENUTI NE CONTENITORI
                            //mi carica gli oggetti che non sono ne contenitori e neanche contenuti
                            logic_r.get(i).addObject( game_object.get(rs.getInt("O_ID")));
                        }
                }
                if(index!=0) {
                    container = (gameObjectContainer) game_object.get(index);
                    container.addAllGameObjList(objectList);
                    logic_r.get(i).addObject(container);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (stmt != null)
                stmt.close();
        }
        return objectList;
    }

    public Connection getConn(){
        return conn;
    }

    public void CloseConnection() {
        try{
            if(conn!=null)
                conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }
    }

}

