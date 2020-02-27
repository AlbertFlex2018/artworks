/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albertgame.avg.action;

import albertgame.avg.AvgData;
import albertgame.avg.story.IStoryAction;


public class LinkAction implements IStoryAction{
    
    //link story storyname
    //link stage-story stagename storyname
    @Override
    public void action(AvgData data, String... args){
        
    }
    public void linkStage(AvgData data,String ... args){
        String stagename=args[2];
        String storyname=args[3];
        data.getDataMap().replace("stage-name",stagename);
        data.getDataMap().replace("story-name",storyname);
        data.getDataMap().replace("action-index","0");
        data.getDataMap().replace("display-left","false");
        data.getDataMap().replace("display-center","false");
        data.getDataMap().replace("display-right","false");
    }
    public void linkStory(AvgData data,String ... args){
        
    }
}
