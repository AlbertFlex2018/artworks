package scenetest2;

import afengine.core.IAppLogic;
import afengine.core.util.XMLEngineBoot;
import afengine.part.message.IMessageHandler;
import afengine.part.message.Message;
import afengine.part.scene.Scene;
import afengine.part.scene.SceneCenter;
import java.awt.event.KeyEvent;

public class SceneTest2Logic implements IAppLogic{

    public static void main(String[] args) {
        XMLEngineBoot.bootEngine("test/scenetest2/scenetest-test2-boot.xml");
    }
    @Override
    public boolean init() {
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
    
    public static class CmdHandler implements IMessageHandler{        
        Scene scene;        
        @Override
        public boolean handle(Message msg) {
            KeyEvent event=(KeyEvent)msg.extraObjs[0];
            if(event.getKeyCode()==KeyEvent.VK_ENTER){
                if(scene!=null){
                    scene=SceneCenter.getInstance().getRunningScene();
                }
            }
            return false;
        }
    }
}
