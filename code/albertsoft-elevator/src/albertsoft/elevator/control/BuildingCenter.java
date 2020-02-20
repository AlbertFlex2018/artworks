package albertsoft.elevator.control;

import java.util.LinkedList;
import java.util.List;

public class BuildingCenter {
    private static BuildingCenter center;
    public static BuildingCenter getInstance(){
        if(center==null)
            center=new BuildingCenter();
        return center;
    }
    private long bid=1000;
    private final List<Building> buildList;
    private BuildingCenter(){
        bid=1000;
        buildList=new LinkedList<>();
    }
    public Building addBuilding(String name,int lastLevel,int topLevel){        
        ++bid;
        Building building=new Building(bid,name,lastLevel,topLevel);
        buildList.add(building);
        return building;
    }
    public void removeBuildingById(long id){
        Building build=null;
        for(Building b:buildList){
            if(b.getId()==id){
                build=b;
                break;
            }
        }
        if(build!=null){
            buildList.remove(build);
        }
    }
    public void removeBuildingByName(String name){
        Building build=null;
        for(Building b:buildList){
            if(b.getName().equals(name)){
                build=b;
                break;
            }
        }
        if(build!=null){
            buildList.remove(build);
        }
    }    
    public Building getBuildingById(long buildingid){
        for(Building b:buildList){
            if(b.getId()==buildingid)
                return b;
        }
        return null;
    }

    public long getBid() {
        return bid;
    }

    public void setBid(long bid) {
        this.bid = bid;
    }

    public List<Building> getBuildList() {
        return buildList;
    }    
}
