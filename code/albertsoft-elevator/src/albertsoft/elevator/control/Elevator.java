/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albertsoft.elevator.control;


public class Elevator {
    private final long id;//电梯在楼层里的id
    private final long buildid;//电梯所在楼层的id

    private String name;//电梯名称
    private final int startFloor,endFloor;//起始楼层，结束楼层
    private int currentFloor;//目前的楼层
    private final boolean[] upClicks,downClicks,destClicks;
    private ElevatorState state;
    private ElevatorState preState;

    public Elevator(long id, long buildid, String name, int startFloor, int endFloor, int currentFloor) {
        this.id = id;
        this.buildid = buildid;
        this.name = name;
        this.startFloor = startFloor;
        this.endFloor = endFloor;
        this.currentFloor = currentFloor;
        upClicks=new boolean[endFloor-startFloor+1];
        downClicks=new boolean[endFloor-startFloor+1];
        destClicks=new boolean[endFloor-startFloor+1];
        preState=state=ElevatorState.UNSETUP;
    }
    
    public long getId(){
        return id;
    }

    public long getBuildid() {
        return buildid;
    }

    public String getName() {
        return name;
    }

    public int getStartFloor() {
        return startFloor;
    }

    public int getEndFloor() {
        return endFloor;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public boolean[] getUpClicks() {
        return upClicks;
    }

    public boolean[] getDownClicks() {
        return downClicks;
    }

    public boolean[] getDestClicks() {
        return destClicks;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }  
    public void start(){
        this.state=ElevatorState.IDLE;
    }

    public ElevatorState getState() {
        return state;
    }

    public void setState(ElevatorState state) {
        preState=this.state;
        this.state = state;
    }

    public ElevatorState getPreState() {
        return preState;
    }    
}
