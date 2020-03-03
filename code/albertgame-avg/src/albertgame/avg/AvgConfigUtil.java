/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albertgame.avg;

import albertgame.avg.story.AvgStage;
import albertgame.avg.story.StageFileUtil;
import java.util.Iterator;
import java.util.Map;
import org.dom4j.Element;


public class AvgConfigUtil{
    /*
        <avg-config>
            <stages-config>
                <stages>                    
                    <stage path=""/>
                    <stage path=""/>
                    <stage path=""/>
                </stages>
                <saved-config>
                    <stage-name value=""/>
                    <story-name value=""/>
                    <action-index value=""/>
                </saved-config>
            </stages-config>
            <datas>
                <key value=""/>
                <key value=""/>
                <key value=""/>
                ...
            </datas>
        </avg-config>
    */
    public void loadConfigs(Element root){
        Element stagesconfig=root.element("statges-config");
        AvgData config=AvgData.getInstance();
        Element stages=stagesconfig.element("stages");
        Iterator<Element> eiter=stages.elementIterator();
        while(eiter.hasNext()){
            Element ele=eiter.next();
            AvgStage stage=StageFileUtil.loadStoryStage(ele);
            String value=ele.attributeValue("path");
            stage.setPath(value);
            config.getStageMap().put(stage.getName(),stage);
        }
        Element savedconfig=root.element("saved-config");
        Element stagename=savedconfig.element("stage-name");
        Element storyname=savedconfig.element("story-name");
        Element actionindex=savedconfig.element("action-index");
        config.getDataMap().replace("stage-name",stagename.getText());
        config.getDataMap().replace("story-name",storyname.getText());
        config.getDataMap().replace("action-index",actionindex.getText());
        
        Element data=root.element("datas");
        Iterator<Element> eeiter=data.elementIterator();
        while(eeiter.hasNext()){
            Element ele=eiter.next();
            String value=ele.attributeValue("value");
            config.getDataMap().replace(ele.getName(), value);
        }
    }
    public void loadOutConfig(Element element){
        element.clearContent();
        element.setName("avg-config");
        AvgData data = AvgData.getInstance();
        Element stageconfigs=element.addElement("stages-config");
        Element stages=stageconfigs.addElement("stages");
        Iterator<AvgStage> eiter=data.getStageMap().values().iterator();
        while(eiter.hasNext()){
            AvgStage stage=eiter.next();
            Element s=stages.addElement(stage.getName());
            s.addAttribute("path",stage.getPath());
        }
        Element savedconfigs=stageconfigs.addElement("saved-config");
        String stagename=data.getDataMap().get("stage-name");
        String storyname=data.getDataMap().get("story-name");
        String actionindex=data.getDataMap().get("action-index");
        Element ele=savedconfigs.addElement("stage-name");
        ele.addAttribute("stage-name",stagename);
        Element ele2=savedconfigs.addElement("story-name");
        ele.addAttribute("story-name",storyname);
        Element ele3=savedconfigs.addElement("stage-name");
        ele.addAttribute("action-index",actionindex);
        
        Element datas=element.addElement("datas");
        Iterator<Map.Entry<String,String>> entryiter=data.getDataMap().entrySet().iterator();
        while(entryiter.hasNext()){
            Map.Entry<String,String> entry=entryiter.next();
            String key=entry.getKey();
            String value=entry.getValue();
            Element e=datas.addElement(key);
            e.addAttribute("value",value);
        }
    }
}
