/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albertgame.avg.action;

import afengine.core.util.Debug;
import albertgame.avg.AvgData;
import albertgame.avg.story.IStoryAction;

public class WordAction implements IStoryAction{

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
        
    }
}
