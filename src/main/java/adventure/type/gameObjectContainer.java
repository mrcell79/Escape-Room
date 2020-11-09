package adventure.type;

import java.util.ArrayList;
import java.util.List;

public class gameObjectContainer extends GameObject{
    private List<GameObject> containerList = new ArrayList<>();

    //Costruttore
    public gameObjectContainer() {
        super();
    }

    //Metodi
    public List<GameObject> getContainerList() {
        return containerList;
    }

    public void addAllGameObjList(List<GameObject> ls){
        containerList.addAll(ls);
    }

    public void removeContList(GameObject gameObj){
        containerList.remove(gameObj);
    }
}
