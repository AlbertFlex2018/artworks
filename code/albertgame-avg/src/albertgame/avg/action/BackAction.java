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
        
        switch (args.length) {
            case 4:
                if(args[1].equals("prepare")){
                    prepare(args);
                }   break;
            case 3:
                if(args[1].equals("show")){
                    show(data,args);
                }   break;                
            default:
                Debug.log("not a back action!");
                break;
        }
    } 
    //back prepare name path
    private void prepare(String ... args){
        String name=args[2];
        String path=args[3];
        IGraphicsTech tech=((WindowApp)(AppState.getRunningApp())).getGraphicsTech();
        ITexture texture=tech.createTexture(path);
        if(texture==null){
            Debug.log("prepare failed,path for image not correct,path:"+path);
        }else{
            TextureMap.put(name, texture);
        }
    }
    
    //back show name
    private void show(AvgData data,String ... args){
        String name=args[2];
        ITexture texture=TextureMap.get(name);
        if(texture==null){
            Debug.log("show back failed,texture not found name:"+name);
        }else{
            if(back!=null){
                Scene scene=SceneCenter.getInstance().getRunningScene();
                if(scene!=null){
                    back=scene.findActorByName("back");
                    if(back!=null){
                        TextureRenderComponent img=(TextureRenderComponent) back.getComponent(RenderComponent.COMPONENT_NAME);
                        img.setTexture(texture);
                        data.getDataMap().put("back-path",texture.getTexturePath());
                    }
                }
            }
        }
    }    
}
