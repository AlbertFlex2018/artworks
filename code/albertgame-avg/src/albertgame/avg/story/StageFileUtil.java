/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albertgame.avg.story;

import afengine.core.AppState;
import afengine.core.WindowApp;
import afengine.core.util.Debug;
import afengine.core.util.XMLEngineBoot;
import afengine.core.window.IGraphicsTech;
import afengine.core.window.ITexture;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.Element;

/**
 *
 * @author Admin
 */
public class StageFileUtil {
    /*
        <stage name="">
            <players>
                <playername>
                    <statename texture=""/>
                    <statename texture=""/>
                </playername>
            </players>
            <stories>
                <story actionpath="assets/story1-1.xml"/>
                <story actionpath="assets/story1-2.xml"/>
                <story actionpath="assets/story1-3.xml"/>
                <story actionpath="assets/story1-4.xml"/>
                ...
            </stories>
        </stage>
    */
    public static AvgStage loadStoryStage(Element element){
        String stagename =element.attributeValue("name");
        Map<String,Story> storyMap=new HashMap<>();
        Map<String,Player> playerMap=new HashMap<>();        
        Debug.log("load avgstage - "+stagename);
        Element playerse=element.element("players");
        if(playerse!=null){            
            Iterator<Element> eiter=playerse.elementIterator();
            while(eiter.hasNext()){
                Element ele=eiter.next();
                String playername=ele.getName();
                Iterator<Element> textureiter=ele.elementIterator();
                Player player=new Player(playername);
                Debug.log("load player :"+player.getName());
                while(textureiter.hasNext()){
                    Element playerstate=textureiter.next();
                    String statename=playerstate.getName();
                    String texturepath=playerstate.attributeValue("texture");
                    ITexture texture=loadTexture(texturepath);
                    if(texture==null){
                        Debug.log("texture for "+texturepath+" is not valid path.");
                    }else{
                        player.getTextureMap().put(statename,texture);
                        Debug.log("load player - state: "+player.getName()+" - "+statename);
                    }
                }
                playerMap.put(playername, player);
            }            
        }else{
            Debug.log("players for stage not found!!");
        }
        Element stories=element.element("stories");
        if(stories!=null){
            Iterator<Element> eiter=stories.elementIterator();
            while(eiter.hasNext()){
                Element storye=eiter.next();
                String actionpath=storye.attributeValue("actionpath");                
                if(actionpath!=null){
                    Document doc=XMLEngineBoot.readXMLFileDocument(actionpath);
                    Story story=null;
                    if(doc!=null)
                        story=loadStory(doc.getRootElement());
                    if(story!=null){
                        storyMap.put(story.getName(), story);                    
                        Debug.log("load story - "+story.getName());                        
                    }
                }else{
                    Debug.log("actionpath for story not found!");
                }
            }
        }else{
            Debug.log("stories for stage not found!");
        }
        
        return new AvgStage(stagename,storyMap,playerMap);
    }
    private static ITexture loadTexture(String texturepath){
        IGraphicsTech tech=((WindowApp)AppState.getRunningApp()).getGraphicsTech();
        return tech.createTexture(texturepath);
    }
    /*
        <story name="sss">
            <action>type arg1 arg2 arg3</action>
            <action>type arg1 arg2 arg3</action>
            <action>type arg1 arg2 arg3</action>
            <action>type arg1 arg2 arg3</action>
        </story>
    */
    public static Story loadStory(Element root){
       
        if(!root.getName().equals("story")){
            Debug.log("not a story file!");
            return null;
        }
        
        
        String name=root.attributeValue("name");
        if(name==null)name="default";
        Story story=new Story(name);
        Iterator<Element> eleiter=root.elementIterator();
        while(eleiter.hasNext()){
            Element action=eleiter.next();
            String text=action.getText();
            Debug.log("load action - "+text);
            story.getActionList().add(text);
        }
        return story;
    }       
}
