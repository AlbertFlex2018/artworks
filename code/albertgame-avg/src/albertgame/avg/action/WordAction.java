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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class WordAction implements IStoryAction{
    
    Actor _word[]=new Actor[4];
    //word text
    //word blank
    //word prepare filepath 准备文本,并且切换到第一行
    //word continue 继续获取文本的下一行
    
    @Override
    public void action(AvgData data, String... args) {
        if(args.length>3||!args[0].equals("word")){
            Debug.log("action failed:word text");
            return;
        }
        data.getDataMap().replace("word",args[1]);   
        if(args[1].equals("blank")){
            act(data,"");
        }
        else if(args[1].equals("prepare")){
            prepare(args[2]);
        }
        else if(args[1].equals("continue")){
            continueWord();
        }
        else act(data,args[1]);
    }
    
    List<String> textlines=new ArrayList<>();
    int index=0;
    int size=0;
    private void prepare(String filepath){
        textlines.clear();
        File file = new File(filepath);
        Debug.log("prepare text - "+filepath);
        if(file.isFile() && file.exists()){
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream,"UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);                
                String text = null;
                while((text = bufferedReader.readLine()) != null){
                    textlines.add(text);
                    Debug.log("line - "+text);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }        
        String word=textlines.get(0);
        act(AvgData.getInstance(),word);
        index=1;
        size=textlines.size();
        Debug.log("size - "+size);
    }
    private void continueWord(){
        if(textlines.size()==0){
            Debug.log("has not load word,please load text word.");
            return;
        }
        String word=textlines.get(index);
        act(AvgData.getInstance(),word);
        if(index<size){
            index++;
        }
    }
    private void act(AvgData data,String word){
        String[] words=new String[4];
        int size=word.length()/32;
        if(size==0){
            words[0]=word;
        }
        if(size==1){
            words[0]=word.substring(0,32);
            words[1]=word.substring(32,word.length());
        }
        else if(size==2){
            words[0]=word.substring(0,32);
            words[1]=word.substring(32,64);
            words[2]=word.substring(48,word.length());
        }
        else if(size==3){
            words[0]=word.substring(0,32);
            words[1]=word.substring(32,64);
            words[2]=word.substring(64,96);
            words[3]=word.substring(96,word.length());
        };

        if(word!=null){
            Scene scene=SceneCenter.getInstance().getRunningScene();
            _word[0]=scene.findActorByName("word-line1");
            _word[1]=scene.findActorByName("word-line2");
            _word[2]=scene.findActorByName("word-line3");
            _word[3]=scene.findActorByName("word-line4");
            setText(_word[0],"      "+words[0]);
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
