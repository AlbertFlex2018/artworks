package albertgame.avg.message;

import afengine.core.util.Debug;
import afengine.core.util.IDCreator;
import afengine.part.message.IMessageRoute;
import afengine.part.message.Message;
import albertgame.avg.AvgData;
import albertgame.avg.action.BackAction;
import albertgame.avg.action.BgmAction;
import albertgame.avg.action.EndAction;
import albertgame.avg.action.LinkAction;
import albertgame.avg.action.PlayerAction;
import albertgame.avg.action.WordAction;
import albertgame.avg.story.IStoryAction;
import java.util.HashMap;
import java.util.Map;

public class ActionMessageRoute implements IMessageRoute{

    public static final long ROUTE_ID=IDCreator.createId();
    private Map<String,IStoryAction> actionMap=new HashMap<>();

    public ActionMessageRoute() {
        actionMap.put("back",new BackAction());
        actionMap.put("bgm",new BgmAction());
        actionMap.put("link",new LinkAction());
        actionMap.put("player",new PlayerAction());
        actionMap.put("word",new WordAction());
        actionMap.put("end",new EndAction());
    }
    
    @Override
    public long getRouteType(){
        return ROUTE_ID;
    }
    
    //ActionInfo msg.extraObject[0]
    @Override
    public void routeMessage(Message msg) {
        String action=(String) msg.extraObjs[0];
        String[] cmds=action.split(" ");
        IStoryAction act=actionMap.get(cmds[0]);
        if(act!=null){
            act.action(AvgData.getInstance(), cmds);
            Debug.log("action:"+action);
        }
        if(!cmds[0].equals("end")){
            AvgData data=AvgData.getInstance();
            int index=Integer.parseInt(data.getDataMap().get("action-index"));            
            data.getDataMap().replace("action-index",(index+1)+"");           
        }
    }
}
