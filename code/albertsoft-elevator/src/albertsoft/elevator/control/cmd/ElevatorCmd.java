package albertsoft.elevator.control.cmd;

import albertsoft.elevator.control.Building;
import albertsoft.elevator.control.BuildingCenter;
import albertsoft.elevator.control.CmdInterface;
import albertsoft.elevator.control.Elevator;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class ElevatorCmd implements CmdInterface{

    //elevator look-elevators [buildingid]
    //elevator look-id [buildingid] [elevatorid]
    //elevator look-name [buildingid] [elevatorname]
    //elevator add [buildingid] [name] [start] [end]
    //elevator modify [buildingid] [id] [newname]
    //elevator remove [buildingid] [id] 
    @Override
    public String cmd(String[] cmds) {
        switch(cmds[1]){
            case "look-elevators":
                return look_elevators(cmds[2]);
            case "look-id":
                return look_id(cmds[2],cmds[3]);
            case "look-name":
                return look_name(cmds[2],cmds[3]);
            case "add":
                return add(cmds[2],cmds[3],cmds[4],cmds[5]);
            case "modify":
                return modify(cmds[2],cmds[3],cmds[4]);
            case "remove":
                return remove(cmds[2],cmds[3]);
            default:
                break;
        }
        return "elevator end.";
    }
    @Override
    public String getName() {
        return "elevator";
    }    
    private String look_elevators(String buildingid){
        BuildingCenter center=BuildingCenter.getInstance();
        long lid=Long.parseLong(buildingid);
        Building build=center.getBuildingById(lid);
        List<Elevator> elevatorlist=build.getElevatorList();
        System.out.println("===============");
        System.out.println("buildingdid\tid\tname\tstart\tend");
        elevatorlist.forEach((elevator)->{
            System.out.print(elevator.getBuildid()+"\t");
            System.out.print(elevator.getId()+"\t");
            System.out.print(elevator.getName()+"\t");
            System.out.print(elevator.getStartFloor()+"\t");
            System.out.println(elevator.getEndFloor());            
        });
        System.out.println("===============");
        return "look elevators end.";
    }
    //elevator look-id [buildingid] [elevatorid]
    private String look_id(String buildingid,String elevatorid){
        long buildid=Long.parseLong(buildingid);
        long eid=Long.parseLong(elevatorid);
        BuildingCenter center=BuildingCenter.getInstance();
        Building build=center.getBuildingById(buildid);
        Elevator elevator=build.getElevatorById(eid);
        System.out.println("elevator - ");
        System.out.print("buildingid:"+elevator.getBuildid()+"\t");
        System.out.print("elevatorid:"+elevator.getId()+"\t");
        System.out.print("start:"+elevator.getStartFloor()+"\t");
        System.out.print("end:"+elevator.getEndFloor()+"\t");
        System.out.print("now:"+elevator.getCurrentFloor()+"\t");
        return "look elevator id end.";
    }

    //elevator look-name [buildingid] [elevatorname]
    private String look_name(String buildingid,String elevatorname){
        long buildid=Long.parseLong(buildingid);
        BuildingCenter center=BuildingCenter.getInstance();
        Building build=center.getBuildingById(buildid);
        Elevator elevator=build.getElevatorByName(elevatorname);
        System.out.println("elevator - ");
        System.out.print("buildingid:"+elevator.getBuildid()+"\t");
        System.out.print("elevatorid:"+elevator.getId()+"\t");
        System.out.print("start:"+elevator.getStartFloor()+"\t");
        System.out.print("end:"+elevator.getEndFloor()+"\t");
        System.out.print("now:"+elevator.getCurrentFloor()+"\t");
        return "look elevator name end.";
    }

    //elevator add [buildingid] [name] [start] [end]
    private String add(String buildingid,String name,String start,String end){
        long buildid=Long.parseLong(buildingid);
        BuildingCenter center=BuildingCenter.getInstance();
        Building build=center.getBuildingById(buildid);
        Elevator e=build.addElevator(name,Integer.parseInt(start),Integer.parseInt(end));
        System.out.println("add elevator id:"+e.getId());
        return "add elevator end.";
    }
    private String modify(String buildingid,String id,String newname){
        long buildid=Long.parseLong(buildingid);
        BuildingCenter center=BuildingCenter.getInstance();
        Building build=center.getBuildingById(buildid);
        Elevator e=build.getElevatorById(Long.parseLong(id));
        if(e!=null)e.setName(newname);
        return "modify elevator end,";
    }
    private String remove(String buildingid,String id){
        long buildid=Long.parseLong(buildingid);
        BuildingCenter center=BuildingCenter.getInstance();
        Building build=center.getBuildingById(buildid);
        Elevator e=build.getElevatorById(Long.parseLong(id));
        if(e!=null){
            build.getElevatorList().remove(e);
        }
        return "remove elevator end.";
    }    
}