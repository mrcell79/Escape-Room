package adventure.type;

public class Room {
    private  short id;
    private String name;
    private String description;
    private String look;
    private short logic_rms;
    private short logic_rmn;
    private short logic_rme;
    private short logic_rmw;
    private short logic_rmne;
    private short logic_rmnw;

    //Costruttore
    public Room() {}

    //Metodi
    public void setId(short id) {
        this.id = id;
    }
    public short getId() {
        return id;
    }
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getDescription(){
        return this.description;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setLook(String look) { this.look = look; }
    public void setLogic_rm_n(short logic_rm){ this.logic_rmn = logic_rm; }
    public short getLogic_rm_n(){ return logic_rmn; }
    public void setLogic_rm_s(short logic_rm){ this.logic_rms = logic_rm; }
    public short getLogic_rm_s(){ return logic_rms; }
    public void setLogic_rm_e(short logic_rm){ this.logic_rme = logic_rm; }
    public short getLogic_rm_e(){ return logic_rme; }
    public void setLogic_rm_w(short logic_rm){ this.logic_rmw = logic_rm; }
    public short getLogic_rm_w(){ return logic_rmw; }
    public void setLogic_rm_ne(short logic_rm){ this.logic_rmne = logic_rm; }
    public short getLogic_rm_ne(){ return logic_rmne; }
    public void setLogic_rm_nw(short logic_rm){ this.logic_rmnw = logic_rm; }
    public short getLogic_rm_nw(){ return logic_rmnw; }


    @Override
    public int hashCode(){
        int hash = 3;
        hash = 83 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if(obj == null){
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Room other = (Room) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}
