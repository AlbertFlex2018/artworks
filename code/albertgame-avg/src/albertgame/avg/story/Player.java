/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albertgame.avg.story;

import afengine.core.window.ITexture;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class Player {
    private final String name;
    private final Map<String,ITexture> textureMap;

    public Player(String name) {
        this.name = name;
        this.textureMap = new HashMap<>();
    }
    public ITexture getTexture(String name){
        return textureMap.get(name);
    }

    public String getName() {
        return name;
    }

    public Map<String, ITexture> getTextureMap() {
        return textureMap;
    }        
}
