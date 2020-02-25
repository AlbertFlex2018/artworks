package albertgame.avg.action;

import afengine.component.render.RenderComponent;
import afengine.component.render.TextureRenderComponent;
import afengine.core.AppState;
import afengine.core.WindowApp;
import afengine.core.util.Debug;
import afengine.core.window.IGraphicsTech;
import afengine.core.window.ITexture;
import afengine.part.scene.Actor;
import afengine.part.scene.Scene;
import afengine.part.scene.SceneCenter;
import albertgame.avg.AvgData;
import albertgame.avg.story.IStoryAction;
import java.util.HashMap;
import java.util.Map;

public class BackAction implements IStoryAction{
    private final Map<String,ITexture> TextureMap;
    
    Actor back;
    public BackAction() {
        TextureMap=new HashMap<>();
    }
    
    @Override
    public void action(AvgData data, String... args) {
        if(args[0].equals("back"))return;
        
        if(args.length==4){
            if(args[1].equals("prepare")){
                prepare(args);
            }
        }else if(args.length==3){
            if(args[1].equals("show")){
                show(data,args);
            }
        }else Debug.log("not a back action!");                
    } 
    //back prepare name path
    private void prepare(String ... args){
        String name=args[2];
        String path=args[3];
        IGraphicsTech tech=((WindowApp)AppState.getRunningApp()).getGraphicsTech();
        ITexture texture=tech.createTexture(path);
        if(texture==null){
            Debug.log("texture prepare failed!");
            return;
        }
        TextureMap.put(name, texture);
    }
    
    //back show name
    private void show(AvgData data,String ... args){
        if(back!=null){
            Scene scene=SceneCenter.getInstance().getRunningScene();
            back=scene.findActorByName("back");            
        }
        if(back==null)
            return;

        String name=args[2];
        ITexture texture=TextureMap.get(name);
        if(texture==null){
            Debug.log("texture show failed,not prepared..");
            return;
        }
        data.setBack(texture);
        TextureRenderComponent texturecomp=(TextureRenderComponent) back.getComponent(RenderComponent.COMPONENT_NAME);
        texturecomp.setTexture(texture);
    }    
}
