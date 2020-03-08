/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action.test;

import afengine.part.message.IMessageHandler;
import afengine.part.message.Message;
import afengine.part.message.MessageCenter;
import albertgame.avg.message.ActionMessageRoute;
import java.awt.event.KeyEvent;

public class KeyHandler implements IMessageHandler{
    
    String[] cmd={
        "bgm prepare back test/action/test/midi1.mid",
        "bgm play back",
        "back show test/action/test/1.png",
        "back show test/action/test/back.jpg",
        "player show left player1 state1",
        "player hide left",
        "player show left player1 state1",
        "player show left player1 state2",
        "player show left player1 state3",
        "word 我是夏文纯一,夏文纯一亿亿亿亿i一一一一一一一一一一一一一一一一一一一一一一一一一以一一一一一一一一一一一一一一一一一"
    };
    
    int index=0;
    
    //key ENTER
    @Override
    public boolean handle(Message msg) {
        KeyEvent event=(KeyEvent)msg.extraObjs[0];
        if(event.getKeyCode()==KeyEvent.VK_ENTER){
            String action=cmd[index];
            ++index;
            index%=cmd.length;
            sendMessage(action);
            System.out.println("message:"+action);
            return true;
        }
        return false;
    }
    public void sendMessage(String action){
        Message msg=new Message(ActionMessageRoute.ROUTE_ID,0,0,"",new Object[]{action},System.currentTimeMillis(),0,0,null,0,0,0);
        MessageCenter.getInstance().sendMessage(msg);
    }
}
