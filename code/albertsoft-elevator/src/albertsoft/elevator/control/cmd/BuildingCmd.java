package albertsoft.elevator.control.cmd;

import albertsoft.elevator.control.Building;
import albertsoft.elevator.control.BuildingCenter;
import albertsoft.elevator.control.CmdInterface;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class BuildingCmd implements CmdInterface{
    //building look-all
    //building look-id [buildingid]
    //building look-name [buildingname]
    //building add [name] [last] [top]
    //building modify [id] [newname]
    //building remove [id] 
    @Override
    public String cmd(String[] cmds) {
        if(cmds.length<2){
            System.err.println("format error!");
            return "failed";
        }
        switch (cmds[1]) {
            case "look-all":
                return look_all();
            case "look-id":
                return look_id(cmds[2]);
            case "look-name":
                return look_name(cmds[2]);
            case "add":
                return add(cmds[2],cmds[3],cmds[4]);
            case "modify":
                return modify(cmds[2],cmds[3]);
            case "remove":
                return remove(cmds[2]);
            default:
                break;
        }
        return "building end.";
    }    
    @Override
    public String getName() {
        return "building";
    }    
    
    private String look_all(){
        BuildingCenter center=BuildingCenter.getInstance();
        List<Building> buildinglist=center.getBuildList();
        System.out.println("=============");
        System.out.println("id\tname\tlast\ttop");
        buildinglist.forEach((build)->{
            System.out.print(build.getId()+"\t");
            System.out.print(build.getName()+"\t");
            System.out.print(build.getLastLevel()+"\t");
            System.out.println(build.getTopLevel());            
        });
        System.out.println("============");
        return "look all end.";
    }
    private String look_id(String id){
        BuildingCenter center=BuildingCenter.getInstance();
        long lid=Long.parseLong(id);
        Building build=center.getBuildingById(lid);
        System.out.print(build.getId() + "\t");
        System.out.print(build.getName() + "\t");
        System.out.print(build.getLastLevel() + "\t");
        System.out.println(build.getTopLevel());
        return "look id end.";
    }
    private String look_name(String name){
        BuildingCenter center=BuildingCenter.getInstance();
        List<Building> buildlist=center.getBuildList();
        Building build=null;
        for(Building bu:buildlist){
            if(bu.getName().equals(name)){
                build=bu;
                break;
            }
        }
        if(build!=null){
            System.out.print(build.getId() + "\t");
            System.out.print(build.getName() + "\t");
            System.out.print(build.getLastLevel() + "\t");
            System.out.println(build.getTopLevel());
        }else{
            System.err.println("building for "+name+" not found.");
        }
        return "look name end.";
    }
    private String add(String name,String last,String top){
        BuildingCenter center=BuildingCenter.getInstance();
        Building build=center.addBuilding(name, Integer.parseInt(last), Integer.parseInt(top));
        System.out.println("build for add. id is :"+build.getId());
        return "add end.";
    }
    private String modify(String id,String newname){
        BuildingCenter center=BuildingCenter.getInstance();
        long lid=Long.parseLong(id);
        Building build=center.getBuildingById(lid);
        if(build!=null){
            build.setName(newname);            
        }else{
            System.err.println("building not found by id:"+id);
        }
        System.out.println("modify id for :"+id);
        return "modify end.";
    }
    private String remove(String id){
        BuildingCenter center=BuildingCenter.getInstance();
        long lid=Long.parseLong(id);
        center.removeBuildingById(lid);
        return  "remove end.";
    }
}
