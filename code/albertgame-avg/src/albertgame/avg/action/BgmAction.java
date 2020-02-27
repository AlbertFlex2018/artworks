package albertgame.avg.action;

import afengine.core.util.Debug;
import afengine.part.sound.SoundCenter;
import albertgame.avg.AvgData;
import albertgame.avg.story.IStoryAction;
import java.util.HashMap;
import java.util.Map;

public class BgmAction implements IStoryAction{
    private final Map<String,Long> midiMap=new HashMap<>();
    private final Map<String,String> midiPathMap=new HashMap<>();

    @Override
    public void action(AvgData data, String... args) {
        if(args[0].equals("bgm"))return;
        
        switch (args.length) {
            case 4:
                if(args[1].equals("prepare")){
                    prepare(args);
                }   break;
            case 3:
                if(args[1].equals("play")){
                    play(data,args);
                }   break;
            default:
                Debug.log("not a bgm action!");
                break;
        }
    }
    
    //bgm prepare name path
    private void prepare(String ... args){
        String name=args[2];
        String path=args[3];
        long id=SoundCenter.getInstance().addMidi(path);
        midiMap.put(name, id);
        midiPathMap.put(name,path);
    }
    //bgm play name
    private void play(AvgData data,String ... args){
        String name=args[2];
        if(midiMap.get(name)==null){
            Debug.log("bgm is not correct.");
        }else{
            long id=midiMap.get(name);
            String path=midiPathMap.get(name);
            SoundCenter.getInstance().playMidi(id, true);
            data.getDataMap().replace("bgm-path",path);
        }
    }        
}
