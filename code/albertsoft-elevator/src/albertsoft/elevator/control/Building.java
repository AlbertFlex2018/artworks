package albertsoft.elevator.control;

import java.util.LinkedList;
import java.util.List;

public class Building{

    private long eid=1000;
    private String name;
    private final long id;
    private final List<Elevator> elevatorList;
    private final int lastLevel,topLevel;
    public Building(long id,String name,int lastLevel,int topLevel,List<Elevator> elist){
        this.id=id;
        this.name=name;
        this.lastLevel=lastLevel;
        this.topLevel=topLevel;
        elevatorList=elist;                
    }
    public Building(long id,String name,int lastLevel,int topLevel){
        this.id=id;
        this.name=name;
        this.lastLevel=lastLevel;
        this.topLevel=topLevel;
        elevatorList=new LinkedList<>();
    }
    public Elevator addElevator(String name,int start,int end){
        if(start>end){
            System.err.println("elevator start is big than end error!");
            return null;
        }
        if(start<lastLevel||end>topLevel){
            System.err.println("start smaller than lastLevel or end bigger than topLevel error!");
            return null;
        }

        ++eid;
        Elevator ele =new Elevator(eid,id,name,start,end,start);
        elevatorList.add(ele);
        return ele;
    }
    public void removeElevatorById(long eid){
        Elevator e =null;
        for(Elevator el: elevatorList){
            if(el.getId()==eid){
                e=el;
                break;
            }                
        }
        if(e!=null){
            elevatorList.remove(e);
        }else{
            System.err.println("not found id of elevator:"+id);
        }
    }    
    public void removeElevatorByName(String name){
        Elevator e =null;
        for(Elevator el: elevatorList){
            if(el.getName().equals(name)){
                e=el;
                break;
            }                
        }
        if(e!=null){
            elevatorList.remove(e);
        }else{
            System.err.println("not found name of elevator:"+name);
        }        
    }
    public Elevator getElevatorByName(String ename){
        for(Elevator e:elevatorList){
            if(e.getName().equals(ename))
                return e;
        }
        return null;
    }    
    public Elevator getElevatorById(long eleid){
        for(Elevator e:elevatorList){
            if(e.getId()==eleid)
                return e;
        }
        return null;        
    }
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Elevator> getElevatorList() {
        return elevatorList;
    }

    public int getLastLevel() {
        return lastLevel;
    }

    public int getTopLevel() {
        return topLevel;
    }

    public long getEid() {
        return eid;
    }

    public void setEid(long eid) {
        this.eid = eid;
    }
    
}
