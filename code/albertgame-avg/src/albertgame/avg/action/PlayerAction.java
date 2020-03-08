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
    Actor[] frame=new Actor[3];
    /*
        player show pos playername statename
        player hide pos
        player hideall
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
            frame[0]=scene.findActorByName("image-left");
            frame[1]=scene.findActorByName("image-center");
            frame[2]=scene.findActorByName("image-right");
            TextureRenderComponent titleimgcomp=(TextureRenderComponent) frame[0].getComponent(RenderComponent.COMPONENT_NAME);
            titleimg[0]=titleimgcomp.getTexture();
            TextureRenderComponent titleimgcomp1=(TextureRenderComponent) frame[1].getComponent(RenderComponent.COMPONENT_NAME);
            titleimg[1]=titleimgcomp.getTexture();
            TextureRenderComponent titleimgcomp2=(TextureRenderComponent) frame[2].getComponent(RenderComponent.COMPONENT_NAME);
            titleimg[2]=titleimgcomp.getTexture();
            init=true;
        }
        int length=args.length;
        if(length<2)return;
        String cmd=args[1];
        switch(cmd){
            case "show":
                show(data,args);
                break;
            case "hide":
                hide(data,args);
                break;
            case "hideall":
                hide(data,"player","hide","left");
                hide(data,"player","hide","center");
                hide(data,"player","hide","right");
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
        Actor dest = null, desttitle = null, destframe = null;
        int index=-1;
        if (pos.equals("left")) {
            dest = player[0];
            index=0;
            desttitle = title[0];
            destframe = frame[0];
        } else if (pos.equals("center")) {
            dest = player[1];
            index=1;
            desttitle = title[1];
            destframe = frame[1];
        } else if (pos.equals("right")) {
            dest = player[2];
            index=2;
            desttitle = title[2];
            destframe = frame[2];
        } else;
        if(index==-1)return;

        
            if(dest!=null){
                TextureRenderComponent comp=(TextureRenderComponent) dest.getComponent(RenderComponent.COMPONENT_NAME);
                TextRenderComponent titlecomp=(TextRenderComponent) desttitle.getComponent(RenderComponent.COMPONENT_NAME);
                TextureRenderComponent titleimgcomp=(TextureRenderComponent) destframe.getComponent(RenderComponent.COMPONENT_NAME);
                ITexture texture=_player.getTexture(statename);
                if(texture!=null){
                    comp.setTexture(texture);
                    data.getDataMap().replace(pos+"-name",playername);
                    data.getDataMap().replace(pos+"-state",statename);
                    data.getDataMap().replace("display-"+pos,"true");
                    titlecomp.getText().value=playername;
                    titleimgcomp.setTexture(titleimg[index]);
                }
            }
        }
    }    
    ITexture[] titleimg=new ITexture[3];
    //player hide pos
    private void hide(AvgData data,String ... args){
        String pos = args[2];
        int index=-1;
        Actor dest = null, desttitle = null, destframe = null;
        if (pos.equals("left")) {
            dest = player[0];
            index=0;
            desttitle = title[0];
            destframe = frame[0];
        } else if (pos.equals("center")) {
            dest = player[1];
            index=1;
            desttitle = title[1];
            destframe = frame[1];
        } else if (pos.equals("right")) {
            dest = player[2];
            index=2;
            desttitle = title[2];
            destframe = frame[2];
        } else;
        if(index==-1)return;
        
        if(dest!=null){
            TextureRenderComponent comp = (TextureRenderComponent) dest.getComponent(RenderComponent.COMPONENT_NAME);
            TextRenderComponent titlecomp=(TextRenderComponent) desttitle.getComponent(RenderComponent.COMPONENT_NAME);
            TextureRenderComponent titleimgcomp=(TextureRenderComponent) destframe.getComponent(RenderComponent.COMPONENT_NAME);
            comp.setTexture(null);
            titlecomp.getText().value="";
            titleimg[index]=titleimgcomp.getTexture();
            titleimgcomp.setTexture(null);
            data.getDataMap().replace("display-" + pos, "false");
        }
    }    
}
