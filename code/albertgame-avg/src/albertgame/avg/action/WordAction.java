/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albertgame.avg.action;

import afengine.component.render.RenderComponent;
import afengine.component.render.TextRenderComponent;
import afengine.core.util.Debug;
import afengine.part.scene.Actor;
import afengine.part.scene.Scene;
import afengine.part.scene.SceneCenter;
import albertgame.avg.AvgData;
import albertgame.avg.story.IStoryAction;

public class WordAction implements IStoryAction{

        Actor _word[]=new Actor[4];
    @Override
    public void action(AvgData data, String... args) {
        if(args.length!=2||!args[0].equals("word")){
            Debug.log("action failed:word text");
            return;
        }
        data.getDataMap().replace("word",args[1]);        
        act(data,args[1]);
    }
    private void act(AvgData data,String word){
        String[] words=new String[4];
        int size=word.length()/16;
        if(size==0){
            words[0]=word;
        }
        if(1<=size){
            words[0]=word.substring(0,15);
        }
        if(2<=size){
            words[1]=word.substring(16,31);
        }
        if(3<=size){
            words[2]=word.substring(32,47);
        }
        if(4<=size){
            words[3]=word.substring(48,65);
        }
        if(word!=null){
            Scene scene=SceneCenter.getInstance().getRunningScene();
            _word[0]=scene.findActorByName("word-line1");
            _word[1]=scene.findActorByName("word-line2");
            _word[2]=scene.findActorByName("word-line3");
            _word[3]=scene.findActorByName("word-line4");
            setText(_word[0],words[0]);
            setText(_word[1],words[1]);
            setText(_word[2],words[2]);
            setText(_word[3],words[3]);
            data.getDataMap().replace("word",word);
        }
    }
    private void setText(Actor actor,String word){
        TextRenderComponent text=(TextRenderComponent) actor.getComponent(RenderComponent.COMPONENT_NAME);
        text.getText().value="";
        if(word!=null&&text!=null){
            text.getText().value=word;
        }
    }
}
