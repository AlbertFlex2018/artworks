package albertsoft.elevator.control;

public enum ElevatorState {
    UNSETUP,//未启动
    IDLE,//空闲
    MOVEUP,//向上移动
    MOVEDOWN,//向下移动
    OPENDOOR,//打开门
    OPENDOOR_END,//开门完毕
    CLOSEDOOR,//关闭门            
    CLOSEDOOR_END,//关门完毕
}
