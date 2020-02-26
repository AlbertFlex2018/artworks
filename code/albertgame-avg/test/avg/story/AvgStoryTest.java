package avg.story;

import afengine.core.AppState;
import afengine.core.IAppLogic;
import afengine.core.util.XMLEngineBoot;
import afengine.core.window.ITexture;
import albertgame.avg.story.AvgStage;
import albertgame.avg.story.Player;
import albertgame.avg.story.StageFileUtil;
import albertgame.avg.story.Story;
import java.util.Iterator;
import java.util.Map;
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
            printStage(stage);
        }
        AppState.setValue("run","false");
        return true;
    }
    
    public void printStage(AvgStage stage){
        
        Map<String,Player> playerMap=stage.getPlayerMap();
        Map<String,Story> storyMap=stage.getStoryMap();
        String name=stage.getName();
        System.out.println("-----------------stage---------------");
        System.out.println("stage\t"+name);
        
        Iterator<Map.Entry<String,Player>> iter=playerMap.entrySet().iterator();
        while(iter.hasNext()){
           Map.Entry<String,Player> entry=iter.next();
           printPlayer(entry.getValue());
        }

        Iterator<Map.Entry<String,Story>> eiter=storyMap.entrySet().iterator();
        while(eiter.hasNext()){
           Map.Entry<String,Story> entry=eiter.next();
           printStory(entry.getValue());
        }
    }
    
    public void printPlayer(Player player){
       Map<String,ITexture> textureMap=player.getTextureMap();
       String name=player.getName();
       System.out.println("-----------------player---------------");
       System.out.println("name\t"+name);
       Iterator<Map.Entry<String,ITexture>> iter=textureMap.entrySet().iterator();
       while(iter.hasNext()){
           Map.Entry<String,ITexture> entry=iter.next();
           System.out.println("state/path\t"+entry.getKey()+"/ "+entry.getValue().getTexturePath());
       }
    }
    public void printStory(Story story){
        System.out.println("----------------story-----------------");
        String name=story.getName();
        System.out.println("name\t"+name);
        int i=0;
        for(String action:story.getActionList()){
            System.out.print("index:"+i+"\t");
            System.out.println("action:"+action);
            ++i;
        }        
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
