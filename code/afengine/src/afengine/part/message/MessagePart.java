package afengine.part.message;

import afengine.core.AbPartSupport;

/**
 * partsupport for message
 * @author Albert Flex
 */
public class MessagePart extends AbPartSupport{
    public static final String PART_NAME="MessagePart";
    public MessagePart(){
        super(MessagePart.PART_NAME);
    }
    
    @Override
    public boolean init(){
        return true;
    }

    @Override
    public boolean update(long time) {
        MessageCenter.getInstance().updateSendMessage(time);
        return true;
    }

    @Override
    public boolean shutdown(){        
        return true;
    }    
}
