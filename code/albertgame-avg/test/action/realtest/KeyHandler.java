/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action.realtest;

import afengine.core.util.Debug;
import afengine.part.message.IMessageHandler;
import afengine.part.message.Message;
import afengine.part.message.MessageCenter;
import albertgame.avg.AvgData;
import albertgame.avg.message.ActionMessageRoute;
import albertgame.avg.story.AvgStage;
import albertgame.avg.story.Story;
import java.awt.event.KeyEvent;

public class KeyHandler implements IMessageHandler{
    //key ENTER
    @Override
    public boolean handle(Message msg) {
        KeyEvent event=(KeyEvent)msg.extraObjs[0];
        if(event.getKeyCode()==KeyEvent.VK_ENTER){
            AvgData data=AvgData.getInstance();
            String stagename=data.getDataMap().get("stage-name");
            String storyname=data.getDataMap().get("story-name");
            int index=Integer.parseInt(data.getDataMap().get("action-index"));
            AvgStage stage=data.getStageMap().get(stagename);
            Debug.log("stage-story:"+stagename+"-"+storyname);
            Story story=stage.getStoryByName(storyname);
            String action=story.getActionByIndex(index);
            sendMessage(action);
            System.out.println("message:"+action);
            return true;
        }
        return false;
    }
    public static void sendMessage(String action){
        Message msg=new Message(ActionMessageRoute.ROUTE_ID,0,0,"",new Object[]{action},System.currentTimeMillis(),0,0,null,0,0,0);
        MessageCenter.getInstance().sendMessage(msg);
    }
}
