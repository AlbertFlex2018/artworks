package albertsoft.elevator.admin;

import java.util.LinkedList;
import java.util.List;

public class AdminCenter {
    private static AdminCenter center=null;
    public static AdminCenter getInstance(){
        if(center==null)
            center=new AdminCenter();
        
        return center;
    }
    
    private long s_id=100000;
    
    private final List<Admin> registedAdmins;
    private final List<Admin> logedAdmins;
    private AdminCenter(){
        registedAdmins=new LinkedList<>();
        logedAdmins=new LinkedList<>();
    }    
    //登录
    public void logIn(long userid,String password){
        Admin admin = getRegistedAdminByUserId(userid);
        if(admin==null){
            System.err.println("id not found.["+userid+"]");
        }else{
            String pass=admin.getPassword();
            if(pass.equals(password)){
                if(logedAdmins.contains(admin)){
                    System.err.println("already logIned.please do not log again.or you can logOut");
                }else{
                    logedAdmins.add(admin);
                    System.out.println("logIn successfully.["+userid+"]");
                }
            }else{
                System.err.println("password not fit for["+userid+"]");
            }
        }
    }    
    //登出
    public void logOut(long userid,String password){
        Admin admin = getRegistedAdminByUserId(userid);
        if(admin==null){
            System.err.println("id not found.["+userid+"]");
        }else{
             if(!logedAdmins.contains(admin)){
                System.err.println("does not has logIned.please do logIn");
             }else{
                 String ps=admin.getPassword();
                 if(ps.equals(password)){
                    logedAdmins.remove(admin);
                    System.out.println("logOut successfully.["+userid+"]");                     
                 }else{
                     System.err.println("password error!");
                 }
             }            
        }
    }
    //注册
    public long registIn(String password,AdminGrunt grunt){
        ++s_id;
        Admin admin=new Admin(s_id,password,grunt);
        registedAdmins.add(admin);
        System.out.println("resgitIn successfully.");
        System.out.println("userid:"+s_id);
        System.out.println("password:"+password);
        System.out.println("grunt:"+grunt);                
        return s_id;
    }
    //注销
    public void registOut(long userid,String password){
        Admin admin = getRegistedAdminByUserId(userid);
        if(admin==null){
            System.err.println("userid not found.["+userid+"]");
            return;
        }
        String ps=admin.getPassword();
        if(!ps.equals(password)){
            System.err.println("password input error");
            return;
        }
        if(!registedAdmins.remove(admin)){
            System.out.println("resgitOut failed.["+userid+"]");            
        }
        else System.out.println("resgitOut successfully.["+userid+"]");
    }
    public Admin getRegistedAdminByUserId(long userid){
        for(Admin admin:registedAdmins){
            if(admin.getId()==userid)
                return admin;
        }
        return null;
    }

    public List<Admin> getRegistedAdmins() {
        return registedAdmins;
    }

    public List<Admin> getLogedAdmins() {
        return logedAdmins;
    }
}
