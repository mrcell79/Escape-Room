package adventure.type;

import java.util.*;

public class GameObject {
    private short ID;
    private String objName;
    private String objDescription;
    private boolean is_container;
    private int where_contained;
    private int inner_object;
    private int l_roomId;
    private boolean Visible;
    private boolean Openable;
    private boolean Pickable;
    private boolean Pushable;
    private boolean Readable;
    private boolean Usable;
    private boolean Open = false;
    private boolean Push = false;

    //Costruttori
    public GameObject(boolean is_container, int where_contained) {
        this.where_contained = where_contained;
        this.is_container = is_container;
    }

    public GameObject(short ID, boolean is_container, int where_contained){
        this.ID = ID;
        this.where_contained = where_contained;
        this.is_container = is_container;
    }

    public GameObject(short ID, String objName, boolean is_container, int where_contained){
        this.ID = ID;
        this.objName = objName;
        this.where_contained = where_contained;
        this.is_container = is_container;
    }

    public GameObject(short ID, String objName, String objDescription, boolean is_container, int where_contained){
        this.ID = ID;
        this.objName = objName;
        this.objDescription = objDescription;
        this.where_contained = where_contained;
        this.is_container = is_container;
    }

    public GameObject(short ID, String objName, String objDescription, boolean is_container, int where_contained, int l_roomId, boolean Visible){
        this.ID = ID;
        this.objName = objName;
        this.objDescription = objDescription;
        //this.alias_name = alias;
        this.where_contained = where_contained;
        this.is_container = is_container;
        this.l_roomId=l_roomId;
        this.Visible=Visible;
    }

    public GameObject() {}

    //Metodi

    public short getID() {
        return ID;
    }
    public void setID(short ID) {
        this.ID = ID;
    }
    public String getObjName(){
        return objName;
    }
    public void setObjName(String objName){
        this.objName = objName;
    }
    public String getObjDescription(){
        return objDescription;
    }
    public void setObjDescription(String objDescription){
        this.objDescription = objDescription;
    }
    public void setL_RoomId(int l_roomId) {
        this.l_roomId = l_roomId;
    }
    public boolean isVisible() {
        return Visible;
    }
    public void setVisible(boolean visible) {
        Visible = visible;
    }
    public boolean isOpenable(){
        return Openable;
    }
    public void setOpenable(boolean Openable){  this.Openable = Openable; }
    public boolean isPickable(){
        return Pickable;
    }
    public void setPickable(boolean Pickable){
        this.Pickable = Pickable;
    }
    public boolean isPushable(){
        return Pushable;
    }
    public void setPushable(boolean Pushable){
        this.Pushable = Pushable;
    }
    public boolean isReadable() { return Readable; }
    public void setReadable(boolean Readable) { this.Readable = Readable; }
    public boolean isUsable() { return Usable; }
    public void setUsable(boolean usable) { Usable = usable; }

    public boolean isIs_container() {
        return is_container;
    }
    public void setIs_container(boolean is_container) {
        this.is_container = is_container;
    }
    public void setWhere_contained(int where_contained) {
        this.where_contained = where_contained;
    }
    public void setInner_Object(int inner_object){ this.inner_object = inner_object;}
    public int getInner_Object(){ return inner_object;}
    public boolean isOpen(){
        return Open;
    }
    public void setOpen(boolean Open){
        this.Open = Open;
    }
    public boolean isPush(){
        return Push;
    }
    public void setPush(boolean Push){
        this.Push = Push;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameObject that = (GameObject) o;
        return ID == that.ID &&
                is_container == that.is_container &&
                where_contained == that.where_contained &&
                l_roomId == that.l_roomId &&
                Visible == that.Visible &&
                Openable == that.Openable &&
                Pickable == that.Pickable &&
                Pushable == that.Pushable &&
                Readable == that.Readable &&
                Usable == that.Usable &&
                Open == that.Open &&
                Push == that.Push;

    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, objName, objDescription, is_container, where_contained, l_roomId, Visible, Openable, Pickable, Pushable, Readable, Usable, Open, Push);
    }
}

