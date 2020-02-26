/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albertgame.avg.story;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class AvgStage {
    private final String name;
    private final Map<String,Story> storyMap;
    private final Map<String,Player> playerMap;

    public AvgStage(String name,Map<String, Story> storyMap, Map<String, Player> playerMap) {
        this.storyMap = storyMap;
        this.playerMap = playerMap;
        this.name=name;
    }

    
    public AvgStage(String name) {
        this(name,new HashMap<>(),new HashMap<>());
    }

    public Map<String, Story> getStoryMap() {
        return storyMap;
    }
    public Story getStoryByName(String name){
        return storyMap.get(name);
    }
    public Map<String, Player> getPlayerMap() {
        return playerMap;
    }    
    public Player getPlayerByName(String name){
        return playerMap.get(name);
    }

    public String getName() {
        return name;
    }
    
}
