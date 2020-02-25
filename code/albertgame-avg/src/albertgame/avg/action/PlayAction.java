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
import albertgame.avg.story.IStoryAction;

public class PlayAction implements IStoryAction{
    
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
        String statename=args[3];
        data.getDataMap().replace(pos+"-state",statename);
    }
    //player show pos playername statename
    private void show(AvgData data,String ... args){
        String pos=args[2];
        String playername=args[3];
        String statename=args[4];
        data.getDataMap().replace(pos+"-name",playername);
        data.getDataMap().replace(pos+"-state",statename);
    }    
    //player hide pos
    private void hide(AvgData data,String ... args){
        String pos = args[2];
        data.getDataMap().replace("display-"+pos,"false");
    }    
}
