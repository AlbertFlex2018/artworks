/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action.test;

import afengine.core.AppState;
import afengine.core.IAppLogic;
import afengine.core.WindowApp;
import afengine.core.util.XMLEngineBoot;
import afengine.core.window.IGraphicsTech;
import afengine.core.window.ITexture;
import albertgame.avg.AvgData;
import albertgame.avg.story.AvgStage;
import albertgame.avg.story.Player;

/**
 *
 * @author Administrator
 */
public class Logic implements IAppLogic {
    public static void main(String[] args) {
        XMLEngineBoot.bootEngine("test/action/test/scenetest-test2-boot.xml");
    }
    @Override
    public boolean init() {
        Player player=new Player("player1");
        putState("state1","test/action/test/player1.png",player);
        putState("state2","test/action/test/player2.png",player);
        putState("state3","test/action/test/player3.png",player);
        
        AvgStage stage=new AvgStage("stage1");
        stage.getPlayerMap().put(player.getName(), player);
        AvgData.getInstance().getDataMap().put("stage-name","stage1");
        AvgData.getInstance().getStageMap().put(stage.getName(), stage);
        
        return true;
    }
    private void putState(String state,String path,Player player){
        ITexture texture=(((WindowApp)AppState.getRunningApp()).getGraphicsTech()).createTexture(path);
        if(texture!=null){
            player.getTextureMap().put(state, texture);
        }
    }

    @Override
    public boolean update(long l) {
        return true;
    }

    @Override
    public boolean shutdown() {
        return true;
    }
    
}
