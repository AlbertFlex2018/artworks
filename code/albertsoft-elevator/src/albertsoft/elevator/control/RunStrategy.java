package albertsoft.elevator.control;

/**
 *
 * @author Albert Flex
 */
public class RunStrategy implements ElevatorRunStrategy{    
    @Override
    public void run(Elevator elevator){
        //handle idle
        //handle up
        //handle down
        ElevatorState state=elevator.getState();
        switch(state){
            case IDLE:
                handleIdle(elevator);
                break;
            case MOVEUP:
                handleUp(elevator);
                break;
            case MOVEDOWN:
                handleDown(elevator);
                break;
            case UNSETUP:
                elevator.start();
                break;
            default:break;
        }
    }    
    int nowdest=-1;
    private void handleIdle(Elevator elevator){
        //如果在下层的按钮有按下，则设置目标为该最低的楼层，设置目标状态为该楼层按钮的方向，并设置运行状态为down
        //如果在上层的按钮有按下，则设置目标为该最高的楼层，设置目标状态，并设置运行状态为up
        boolean[] up=elevator.getUpClicks();
        boolean[] down=elevator.getDownClicks();
        boolean[] dest=elevator.getDestClicks();
        
        int de=-100;
        for(int i=0;i!=up.length;++i){
            if(up[i]||down[i]||dest[i]){
                de=i;
                break;
            }
        }
        if(de!=-100){
            nowdest=de;
            if(nowdest>elevator.getCurrentFloor()){                
                elevator.setState(ElevatorState.MOVEUP);                
                elevator.setCurrentFloor(elevator.getCurrentFloor()+1);
            }else if(nowdest<elevator.getCurrentFloor()){
                elevator.setState(ElevatorState.MOVEDOWN);                                
                elevator.setCurrentFloor(elevator.getCurrentFloor()-1);
            }else{
                if(down[nowdest]){
                    elevator.setState(ElevatorState.MOVEUP);                
                }else if(up[nowdest]){
                    elevator.setState(ElevatorState.MOVEDOWN);                                
                }
            }            
        }        
    }
    private void handleUp(Elevator elevator){
        //如果没有到达目标，则继续运行
        //如果到达，则更改状态为目标状态
        //如果dest中有更高层，将更高层设为目标
        //如果up中有这一层，则开门
        //如果dest中有这一层，则打开门        
        int cu=elevator.getCurrentFloor();
        boolean[] dest=elevator.getDestClicks();
        boolean[] up=elevator.getUpClicks();
        int de=-100;
        for(int i=cu+1;i!=elevator.getDownClicks().length;++i){
            if(dest[i]||up[i]){
                de=i;
            }
        }
        if(de!=-100)
            nowdest=de;
                
        boolean open = false;
        open = dest[cu] || up[cu]||nowdest==cu;
        if (open) {
            dest[cu]=up[cu]=false;
            closeDoor(elevator);
        }
        if (nowdest == cu){
            boolean[] down = elevator.getDownClicks();
            if (down[cu]) {
                elevator.setState(ElevatorState.MOVEDOWN);
                down[cu]=false;
            } else if (up[cu]) {
                elevator.setState(ElevatorState.MOVEUP);
                up[cu]=false;
            }
            else elevator.setState(ElevatorState.IDLE);
        } else {
            elevator.setState(ElevatorState.MOVEUP);
            elevator.setCurrentFloor(elevator.getCurrentFloor() + 1);
        }
    }
    private void handleDown(Elevator elevator){
        //如果没有到达目标，则继续运行
        //如果到达 ，则更改状态为目标状态
        //如果down中有更低层，将更低层设为目标
        //如果down中有这一层，则开门
        //如果dest中有这一层，则打开门        
        int cu=elevator.getCurrentFloor();
        boolean[] dest=elevator.getDestClicks();
        boolean[] down=elevator.getDownClicks();
        boolean[] up=elevator.getUpClicks();
        int de=-100;
        for(int i=cu-1;i>=0;--i){
            if(dest[i]||down[i]){
                de=i;
            }
        }
        if(de!=-100)
            nowdest=de;
        
        boolean open = false;
        open = dest[cu] || down[cu]||nowdest==cu;
        if (open) {
            dest[cu]=down[cu]=false;
            closeDoor(elevator);
        }
        if (nowdest == cu) {
            if (down[cu]) {
                elevator.setState(ElevatorState.MOVEDOWN);
                down[cu]=false;
            } else if (up[cu]) {
                elevator.setState(ElevatorState.MOVEUP);
                up[cu]=false;
            }
            else elevator.setState(ElevatorState.IDLE);
        } else {
            elevator.setState(ElevatorState.MOVEDOWN);
            elevator.setCurrentFloor(elevator.getCurrentFloor() - 1);
        }
    }
    private void closeDoor(Elevator elevator){
        elevator.setState(ElevatorState.OPENDOOR);
        try {
            System.out.println("open door...");
            Thread.sleep(300);
            System.out.println("open door end...");
        } catch (Exception ex) {
        }
        elevator.setState(ElevatorState.CLOSEDOOR);
        try {
            System.out.println("close door ...");
            Thread.sleep(300);
            System.out.println("close door end...");
        } catch (Exception ex) {
        }
    }    
}
