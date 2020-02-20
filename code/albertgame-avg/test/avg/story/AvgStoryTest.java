package avg.story;

import afengine.core.AppState;
import afengine.core.IAppLogic;
import afengine.core.util.XMLEngineBoot;
import albertgame.avg.story.AvgStage;
import albertgame.avg.story.StageFileUtil;
import java.util.Iterator;
import org.dom4j.Document;
import org.dom4j.Element;


public class AvgStoryTest implements IAppLogic{
    
    public static void main(String[] args) {
        XMLEngineBoot.bootEngine("test/avg/story/test1-boot.xml");
    }
    
    @Override
    public boolean init() {
        Document doc=XMLEngineBoot.readXMLFileDocument("test/avg/story/avg-stages.xml");
        Element root=doc.getRootElement();
        Iterator<Element> eleiter=root.elementIterator();
        while(eleiter.hasNext()){
            Element ele=eleiter.next();
            AvgStage stage=StageFileUtil.loadStoryStage(ele);            
        }
        AppState.setValue("run","false");
        return true;
    }

    @Override
    public boolean update(long l) {
        return true;
    }

    @Override
    public boolean shutdown() {
        return true;
    }    
}
