package albertsoft.elevator.control;

import albertsoft.elevator.admin.Admin;
import albertsoft.elevator.admin.AdminCenter;
import albertsoft.elevator.admin.AdminGrunt;
import java.util.Iterator;
import java.util.List;
import org.dom4j.Element;

/**
 *
 * @author Albert Flex
 */
public class XMLFileHelp {
    
    /*
        <Configs>
            <AdminCenter>
                <RegistedAdmins>
                    <Admin id="" password="" grunt=""/>
                    ...
                </RegistedAdmins>
                <LoginedAdmins>
                    <Admin id="" />
                    ...
                </LoginedAdmins>
            </AdminCenter>
            <BuildingCenter>
                <Building name="" id="" last="" top="">
                    <Elevator name="" id="" start="" end=""/>
                    <Elevator name="" id="" start="" end=""/>
                    ...
                </Building>
                <Building name="" id="" last="" top="">
                </Building>
                ...
            </BuildingCenter>
        </Configs>
    */
    public BuildingCenter readSystemFromXML(Element root){
        Element admincenter=root.element("AdminCenter");
        if(admincenter!=null){
            createAdminCenter(admincenter);
        }
        Element buildcenter=root.element("BuildingCenter");
        if(buildcenter!=null){
            createBuildCenter(buildcenter);
        }
        return BuildingCenter.getInstance();
    }   

    /*
            <AdminCenter>
                <RegistedAdmins>
                    <Admin id="" password="" grunt=""/>
                    ...
                </RegistedAdmins>
                <LoginedAdmins>
                    <Admin id="" />
                    ...
                </LoginedAdmins>
            </AdminCenter>    
    */
    private AdminCenter createAdminCenter(Element centerroot){
        AdminCenter center=AdminCenter.getInstance();
        Element regist=centerroot.element("RegistedAdmins");
        if(regist!=null){
            Iterator<Element> eleiter=regist.elementIterator();
            while(eleiter.hasNext()){
                Element element=eleiter.next();
                String id=element.attributeValue("id");
                String password=element.attributeValue("password");
                String grunt=element.attributeValue("grunt");
                if(id!=null&&password!=null&&grunt!=null){
                    Admin admin=new Admin(Long.parseLong(id),password,AdminGrunt.valueOf(grunt));                    
                    center.getRegistedAdmins().add(admin);
                }
            }
        }
        /*
                    <Admin id="" />        
        */
        Element login=centerroot.element("LoginedAdmins");
        if(login!=null){
            Iterator<Element> eleiter=login.elementIterator();
            while(eleiter.hasNext()){
                Element element=eleiter.next();
                String id=element.attributeValue("id");
                long idd=Long.parseLong(id);
                Admin admin=center.getRegistedAdminByUserId(idd);
                if(admin!=null){
                    center.getLogedAdmins().add(admin);
                }else{
                    System.out.println("logined admin id not found.");
                }
            }
        }
        return center;
    }
/*
            <BuildingCenter>
                <Building name="" id="" last="" top="">
                    <Elevator name="" id="" start="" end=""/>
                    <Elevator name="" id="" start="" end=""/>
                    ...
                </Building>
                <Building name="" id="" last="" top="">
                </Building>
                ...
            </BuildingCenter>    
    */
    private BuildingCenter createBuildCenter(Element centerroot){
        BuildingCenter center=BuildingCenter.getInstance();
        Iterator<Element> builditer=centerroot.elementIterator();
        while(builditer.hasNext()){
            Element build=builditer.next();
            Building building=createBuilding(build);
            center.getBuildList().add(building);
            center.setBid(building.getId());
        }
        return center;
    }
    /*
       <Building name="" id="" last="" top="">    
    */
    private Building createBuilding(Element element){
       String name=element.attributeValue("name");
       String id=element.attributeValue("id");
       String last=element.attributeValue("last");
       String top=element.attributeValue("top");
       if(name!=null&&id!=null&last!=null&top!=null){
           long idd=Long.parseLong(id);
           int lastt=Integer.parseInt(last);
           int topp=Integer.parseInt(top);
           Building build=new Building(idd,name,lastt,topp);
           Iterator<Element> elevatoriter=element.elementIterator();
           while(elevatoriter.hasNext()){
               Element ele=elevatoriter.next();
               Elevator elevator=createElevator(ele,build.getId());               
               build.getElevatorList().add(elevator);
               build.setEid(elevator.getId());
           }
           return build;
       }
       else{
           System.err.println("name,id,last,top is not full");
       }
       return null;
    }
    private Elevator createElevator(Element element,long buildid){
        String name=element.attributeValue("name");
        String id=element.attributeValue("id");
        String start=element.attributeValue("start");
        String end=element.attributeValue("end");
        if(name!=null&&id!=null&&start!=null&&end!=null){
            Elevator elevator=new Elevator(Long.parseLong(id),buildid,name,Integer.parseInt(start),
                    Integer.parseInt(end),Integer.parseInt(start));
            return elevator;
        }else{
            System.err.println("name,id,start,end is not full");
        }
        return null;
    }
    
    
    /*
        <Configs>
            <AdminCenter>
                <RegistedAdmins>
                    <Admin id="" password="" grunt=""/>
                    ...
                </RegistedAdmins>
                <LoginedAdmins>
                    <Admin id="" />
                    ...
                </LoginedAdmins>
            </AdminCenter>
            <BuildingCenter>
                <Building name="" id="" last="" top="">
                    <Elevator name="" id="" start="" end=""/>
                    <Elevator name="" id="" start="" end=""/>
                    ...
                </Building>
                <Building name="" id="" last="" top="">
                </Building>
                ...
            </BuildingCenter>
        </Configs>
    */
    public void writeSystemToXML(Element root){
        root.clearContent();
        root.setName("Configs");
        Element admincenter=root.addElement("AdminCenter");
        writeAdminCenter(admincenter);
        Element buildcenter=root.addElement("BuildingCenter");
        writeBuildCenter(buildcenter);
    }    
    private void writeAdminCenter(Element centerroot){
        AdminCenter center=AdminCenter.getInstance();
        Element regi=centerroot.addElement("RegistedAdmins");
        List<Admin> registed=center.getRegistedAdmins();
        registed.forEach((admin) -> {
            Element ele=regi.addElement("Admin");
            ele.addAttribute("id",admin.getId()+"");
            ele.addAttribute("password",admin.getPassword());
            ele.addAttribute("grunt",admin.getGrunt().toString());
        });
        Element logi=centerroot.addElement("LoginedAdmins");
        List<Admin> logined=center.getLogedAdmins();
        logined.forEach((admin)->{
            Element ele=logi.addElement("Admin");
            ele.addAttribute("id",admin.getId()+"");            
        });
    }
    
    private void writeBuildCenter(Element centerroot){
        BuildingCenter center=BuildingCenter.getInstance();
        List<Building> buildlist=center.getBuildList();
        buildlist.forEach((build)->{
            Element building=centerroot.addElement("Building");
            building.addAttribute("id",build.getId()+"");
            building.addAttribute("name",build.getName());
            building.addAttribute("last",build.getLastLevel()+"");
            building.addAttribute("top",build.getTopLevel()+"");
            List<Elevator> elevatorlist=build.getElevatorList();
            elevatorlist.forEach((ele)->{
                writeElevator(building,ele);
            });
        });
    }
    private void writeElevator(Element buildelement,Elevator ele){
        Element elevatorelement=buildelement.addElement("Elevator");
        elevatorelement.addAttribute("id",ele.getId()+"");
        elevatorelement.addAttribute("name",ele.getName());
        elevatorelement.addAttribute("start",ele.getStartFloor()+"");
        elevatorelement.addAttribute("end",ele.getEndFloor()+"");
    }
}
