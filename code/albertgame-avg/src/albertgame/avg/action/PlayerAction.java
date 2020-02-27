/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albertgame.avg.action;

import afengine.part.scene.Actor;
import afengine.part.scene.Scene;
import afengine.part.scene.SceneCenter;
import albertgame.avg.AvgData;
import albertgame.avg.story.AvgStage;
import albertgame.avg.story.IStoryAction;
import albertgame.avg.story.Player;

public class PlayerAction implements IStoryAction{
    Actor[] player=new Actor[3];
    /*
        player change pos statename
        player show pos playername statename
        player hide pos
    */  
    boolean init=false;
    @Override
    public void action(AvgData data, String... args){
        if(init==false){
            Scene scene=SceneCenter.getInstance().getRunningScene();
            player[0]=scene.findActorByName("player-left");
            player[1]=scene.findActorByName("player-center");
            player[2]=scene.findActorByName("player-right");
        }
        int length=args.length;
        if(length<3)return;
        String cmd=args[1];
        switch(cmd){
            case "change":
                change(data,args);
                break;
            case "show":
                show(data,args);
                break;
            case "hide":
                hide(data,args);
                break;
            default:break;
        }
        
    }   
    //player change pos statename
    private void change(AvgData data,String ... args){
        String pos=args[2];
        String statename=args[2];
        AvgStage stage=data.getStageMap().get(data.getDataMap().get("stage-name"));
        Player player=stage.getPlayerByName(data.getDataMap().get("player-"+pos));
        if(player!=null){
            
        }
    }
    //player show pos playername statename
    private void show(AvgData data,String ... args){
    }    
    //player hide pos
    private void hide(AvgData data,String ... args){
    }    
}
