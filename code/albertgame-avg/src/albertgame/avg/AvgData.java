package albertgame.avg;

import albertgame.avg.story.AvgStage;
import java.util.HashMap;
import java.util.Map;

public class AvgData {    
        
    private static AvgData data;

    public static AvgData getInstance(){
        if(data==null)
            data=new AvgData();
        return data;
    }
    
    //avgconfig path
    //stage-name,story-name,action-index    
    //display-left,display-center,display-right
    //left-name,left-state,center-name,center-state,right-name,right-state :name
    //word,word-line1,word-line2,word-line3,word-line4,word-new 
    //midi-path    
    private final Map<String,String> dataMap;
    
    //stage - story - action
    //load stage to this map for stage    
    private final Map<String,AvgStage> stageMap;    
            
    private AvgData(){
        dataMap=new HashMap<>();
        stageMap=new HashMap<>();
        clear();
    }

    public Map<String, String> getDataMap() {
        return dataMap;
    }
    public final void clear(){
        dataMap.clear();

        dataMap.put("back-path","");//背景图片路径
        dataMap.put("stage-name","");//当前舞台名称
        dataMap.put("story-name","");//当前故事名称
        dataMap.put("action-index","");//当前动作索引

        dataMap.put("display-left","false");//是否显示左边立绘图        
        dataMap.put("display-center","false");//是否显示中间立绘图
        dataMap.put("display-right","false");//是否显示右边立绘图
        
        dataMap.put("left-name","");//左边人物名称
        dataMap.put("center-name","");//中间
        dataMap.put("right-name","");//右边

        dataMap.put("left-state","");//左边人物的状态
        dataMap.put("center-state","");//中间
        dataMap.put("right-state","");

        dataMap.put("word","");
        dataMap.put("word-line1","");
        dataMap.put("word-line2","");
        dataMap.put("word-line3","");
        dataMap.put("word-line4","");        

        dataMap.put("midi-path","");//背景音乐路径
        
        dataMap.put("default-stage","");
        dataMap.put("default-story","");
        dataMap.put("avgconfig-path", "");        
    }

    public Map<String, AvgStage> getStageMap() {
        return stageMap;
    }                
}
