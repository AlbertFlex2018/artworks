/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albertgame.avg.story;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class Story {
    private final List<String> actionList;
    private final String name;

    public Story(String name) {
        this.actionList = new LinkedList<>();
        this.name = name;
    }

    public String getName() {
        return name;
    }
        
    public List<String> getActionList() {
        return actionList;
    }
    public String getActionByIndex(int index){
        return actionList.get(index);
    }    
}
