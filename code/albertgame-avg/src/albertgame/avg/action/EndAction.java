/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albertgame.avg.action;

import afengine.core.util.Debug;
import afengine.core.util.XMLEngineBoot;
import albertgame.avg.AvgConfigUtil;
import albertgame.avg.AvgData;
import albertgame.avg.story.IStoryAction;
import org.dom4j.Document;
import org.dom4j.Element;

/**
 *
 * @author Administrator
 */
public class EndAction implements IStoryAction{
    //end reset
    @Override
    public void action(AvgData data, String... args) {
        Debug.log("avg end---");
        if(args[1].equals("reset")){
            String defaultstage=data.getDataMap().get("default-stage");
            String defaultstory=data.getDataMap().get("default-story");
            Debug.log("into defaultstage/story:"+defaultstage+"/"+defaultstory);
            data.getDataMap().replace("stage-name", defaultstage);
            data.getDataMap().replace("story-name", defaultstory);
            data.getDataMap().replace("action-index", "0");
        }else{
            String path=data.getDataMap().get("avgconfig-path");
            Document doc=XMLEngineBoot.getXMLWritableDocument(path);
            Element root=doc.getRootElement();
            AvgConfigUtil util=new AvgConfigUtil();
            data.clear();
            data.getDataMap().replace("avgconfig-path",path);
            util.loadOutConfig(root);
            XMLEngineBoot.writeXMLFile(path, doc);
        }
    }
}
