/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albertgame.avg.action;

import afengine.component.render.RenderComponent;
import afengine.component.render.TextRenderComponent;
import afengine.component.render.TextureRenderComponent;
import afengine.core.window.ITexture;
import afengine.part.scene.Actor;
import afengine.part.scene.Scene;
import afengine.part.scene.SceneCenter;
import albertgame.avg.AvgData;
import albertgame.avg.story.IStoryAction;
import albertgame.avg.story.Player;

public class PlayerAction implements IStoryAction{
    Actor[] player=new Actor[3];
    Actor[] title=new Actor[3];
    /*
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
            title[0]=scene.findActorByName("title-left");
            title[1]=scene.findActorByName("title-center");
            title[2]=scene.findActorByName("title-right");
            
            init=true;
        }
        int length=args.length;
        if(length<3)return;
        String cmd=args[1];
        switch(cmd){
            case "show":
                show(data,args);
                break;
            case "hide":
                hide(data,args);
                break;
            default:break;
        }
    }   
    //player show pos playername statename
    private void show(AvgData data,String ... args){
        String playername=args[3];
        String statename=args[4];
        String pos=args[2];
        Player _player=data.getStageMap().get(data.getDataMap().get("stage-name")).getPlayerByName(playername);
        if(_player!=null){
            Actor dest=null,desttitle=null;
            if(pos.equals("left")){
                dest=player[0];
                desttitle=title[0];
            }else if(pos.equals("center")){
                dest=player[1];
                desttitle=title[1];
            }else if(pos.equals("right")){
                dest=player[2];
                desttitle=title[2];
            }else;
            
            if(dest!=null){
                TextureRenderComponent comp=(TextureRenderComponent) dest.getComponent(RenderComponent.COMPONENT_NAME);
                TextRenderComponent titlecomp=(TextRenderComponent) desttitle.getComponent(RenderComponent.COMPONENT_NAME);
                ITexture texture=_player.getTexture(statename);
                if(texture!=null){
                    comp.setTexture(texture);
                    data.getDataMap().replace(pos+"-name",playername);
                    data.getDataMap().replace(pos+"-state",statename);
                    data.getDataMap().replace("display-"+pos,"true");
                    titlecomp.getText().value=playername;
                }
            }
        }
    }    
    //player hide pos
    private void hide(AvgData data,String ... args){
        String pos = args[2];
        Actor dest = null;
        if (pos.equals("left")) {
            dest = player[0];
        } else if (pos.equals("center")) {
            dest = player[1];
        } else if (pos.equals("right")) {
            dest = player[2];
        } else;
        
        if(dest!=null){
            TextureRenderComponent comp = (TextureRenderComponent) dest.getComponent(RenderComponent.COMPONENT_NAME);
            comp.setTexture(null);
            data.getDataMap().replace("display-" + pos, "false");
        }
    }    
}
