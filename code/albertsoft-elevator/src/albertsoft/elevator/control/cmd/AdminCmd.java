package albertsoft.elevator.control.cmd;

import albertsoft.elevator.admin.Admin;
import albertsoft.elevator.admin.AdminCenter;
import albertsoft.elevator.admin.AdminGrunt;
import albertsoft.elevator.control.CmdInterface;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class AdminCmd implements CmdInterface{
    //admin look-registed
    //admin look-logined
    //admin look-id [adminid]
    //admin regist [grunt] [password] [operateadminid]
    //admin unregist [id] [password] [operatedadminid]
    //admin login [id] [password]
    //admin logout [id] [password]
    @Override
    public String cmd(String[] cmds) {
        if(cmds.length<2){
            System.err.println("cmd format error!");
            return "failed";
        }
        switch (cmds[1]) {
            case "look-registed":
                return look_registed();
            case "look-logined":
                return look_logined();
            case "regist":
                return registed(cmds[2],cmds[3],cmds[4]);
            case "unregist":
                return unregisted(cmds[2],cmds[3],cmds[4]);
            case "login":
                return login(cmds[2],cmds[3]);
            case "logout":
                return logout(cmds[2],cmds[3]);
            default:
                break;
        }
        return "no tag found.";
    }    

    @Override
    public String getName() {
        return "admin";
    }
    
    private String look_registed(){
        AdminCenter center=AdminCenter.getInstance();
        List<Admin> adminlist=center.getRegistedAdmins();
        System.out.println("=================");
        System.out.println("id\tgrunt");
        adminlist.forEach((admin)->{
            System.out.print(admin.getId()+"\t");
            System.out.println(admin.getGrunt());
        });
        System.out.println("================");
        return "look registed end.";
    }
    private String look_logined(){
        AdminCenter center=AdminCenter.getInstance();
        List<Admin> adminlist=center.getLogedAdmins();
        System.out.println("=================");
        System.out.println("id\tgrunt");
        adminlist.forEach((admin)->{
            System.out.print(admin.getId()+"\t");
            System.out.println(admin.getGrunt());
        });
        System.out.println("================");
        return "look logined end.";
    }
    private String look_id(String id){
        AdminCenter center=AdminCenter.getInstance();
        Admin admin= center.getRegistedAdminByUserId(Long.parseLong(id));
        if(admin!=null){
            System.out.println("=================");
            System.out.println("id\tpassword\tgrunt");
            System.out.print(admin.getId()+"\t");
            System.out.print(admin.getPassword()+"\t");            
            System.out.println(admin.getGrunt());            
        }else{
            System.err.println("no admin found for id:"+id);
        }
        System.out.println("================");
        return "look id end.";
    }
    //admin regist [grunt] [password] [operateadminid]
    private String registed(String grunt,String password,String operatedadminid){
        //look for id for operatedadmindid
        AdminCenter center=AdminCenter.getInstance();        
        List<Admin> adminlist=center.getLogedAdmins();
        long lop=Long.parseLong(operatedadminid);
        boolean is=false;
        for(Admin admin:adminlist){
            if(admin.getId()==lop){
                is=true;
                break;
            }
        }
        if(is!=false){
            AdminGrunt gru=AdminGrunt.valueOf(grunt);
            long lid=center.registIn(password, gru);
            System.out.println("id for registed is :"+lid);            
        }
        else{
            System.err.println("id for operatedadminid not found in logined adminlist");            
        }
        return "registed id end.";
    }
    //admin unregist [id] [operatedadminid]
    private String unregisted(String id,String password,String operatedadminid){
        //look for id for operatedadmindid
        AdminCenter center=AdminCenter.getInstance();        
        List<Admin> adminlist=center.getLogedAdmins();
        long lop=Long.parseLong(operatedadminid);
        boolean is=false;
        for(Admin admin:adminlist){
            if(admin.getId()==lop){
                is=true;
                break;
            }
        }
        if(is!=false){
            center.registOut(Long.parseLong(id),password);
            System.out.println("id for unregisted is :"+id);            
        }
        else{
            System.err.println("id for operatedadminid not found in logined adminlist");            
        }        
        return "unregisted id end.";
    }
    private String login(String id,String password){
        //look for id for operatedadmindid
        AdminCenter center=AdminCenter.getInstance();        
        long lid=Long.parseLong(id);
        center.logIn(lid, password);
        return "login admin end.";
    }
    private String logout(String id,String password){
        AdminCenter center=AdminCenter.getInstance();        
        long lid=Long.parseLong(id);
        center.logOut(lid, password);
        return "logOut admin end.";
    }    
}
