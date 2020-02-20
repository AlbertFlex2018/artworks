package scenetest;

import afengine.component.render.RenderComponent;
import afengine.component.render.TextureRenderComponent;
import afengine.core.AppState;
import afengine.core.IAppLogic;
import afengine.core.WindowApp;
import afengine.core.util.Debug;
import afengine.core.util.XMLEngineBoot;
import afengine.core.window.IGraphicsTech;
import afengine.core.window.ITexture;
import afengine.part.message.IMessageHandler;
import afengine.part.message.Message;
import afengine.part.scene.Actor;
import afengine.part.scene.Scene;
import afengine.part.scene.SceneCenter;
import afengine.part.uiinput.InputServlet;
import java.awt.event.KeyEvent;

/**
 *
 * @author Admin
 */
public class Test1BootLogic implements IAppLogic{
    
    public static void main(String[] args) {
        XMLEngineBoot.bootEngine("test/asset/scenetest-test1-boot.xml");
    }
    
    @Override
    public boolean init() {
        Debug.log("init logic");
        IGraphicsTech tech=((WindowApp)AppState.getRunningApp()).getGraphicsTech();
        on=tech.createTexture("test/asset/on.png");
        off=tech.createTexture("test/asset/start-c.png");
        Scene scene=SceneCenter.getInstance().getRunningScene();
        Actor start=scene.findActorByName("start-circle");
        Actor continuex=scene.findActorByName("continue-circle");
        Actor quit=scene.findActorByName("quit-circle");
        actor=new Actor[]{start,continuex,quit};        
        return true;
    }

    @Override
    public boolean update(long l) {
        return true;
    }

    @Override
    public boolean shutdown() {
        Debug.log("shutdown logic");
        return true;
    }    
    public static class MenuServlet implements IMessageHandler{
        private int i=1;
        @Override
        public boolean handle(Message msg){
            KeyEvent key = (KeyEvent)msg.extraObjs[0];
            if(msg.msgType!=InputServlet.INPUT_KEY_UP){
                return false;
            }
            int code=key.getKeyCode();
            if(code==KeyEvent.VK_DOWN){
                if(i==3){
                    i=1;
                }else ++i;
                flipRender(Test1BootLogic.actor,i);
                return true;                
            }else if(code==KeyEvent.VK_UP){
                if(i==1){
                    i=3;
                }else --i;
                flipRender(Test1BootLogic.actor,i);
                return true;                                
            }else if(code==KeyEvent.VK_ENTER){
                switch (i) {
                    case 1:
                        Debug.log("start game ....");
                        break;
                    case 2:
                        Debug.log("continue game ....");
                        break;
                    case 3:
                        Debug.log("exit game ....");
                        AppState.setValue("run","false");
                        break;
                    default:
                        break;
                }
                return true;
            }
            return false;
        }        
        private void flipRender(Actor[] actor,int i){
            for(Actor a:actor){
                TextureRenderComponent render=(TextureRenderComponent) a.getComponent(RenderComponent.COMPONENT_NAME);
                render.setTexture(off);
            }
            TextureRenderComponent render = (TextureRenderComponent) actor[i-1].getComponent(RenderComponent.COMPONENT_NAME);
            render.setTexture(on);
        }
    }
    public static ITexture on,off;
    public static Actor[] actor;
}
