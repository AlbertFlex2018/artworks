package albertweb.artworks.rest;

import albertweb.artworks.entity.UserInfo;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("userinfo")
public class UserInfoRest {
    
    @PersistenceContext
    private EntityManager em;
    private static final Logger logger = Logger.getLogger(UserInfoRest.class.getName());
    
    @Path("add/{username}/{password}")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public UserInfo addUserInfo(String userResponse,
            @PathParam("username") String username,
            @PathParam("password") String password) {
        UserInfo user=new UserInfo();
        user.setUsername(username);
        user.setPassword(password);
        user.setCreatedTime(new Date());
        em.persist(user);
        logger.log(Level.INFO,"add userinfo successfully:name-{0},password-{1}",new Object[]{user.getUsername(),user.getPassword()});
        return user;
    }
    
    @Path("login/{userid}/{password}")
    @GET
    @Produces("text/plain")
    public String loginUser(@PathParam("userid") Long userid,
            @PathParam("password") String password){
        UserInfo user=em.find(UserInfo.class,userid);
        if(user!=null){
            String innerpassword=user.getPassword();
            if(password.equals(innerpassword)){
                return "login success.";
            }
        }else{
            logger.log(Level.INFO, "userinfo not found!");
        }
        return "login error:password or userid not correct!";
    }
    
    @Path("find/{userid}")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public UserInfo findUserInfo(@PathParam("userid") Long userId){
        UserInfo user=em.find(UserInfo.class,userId);
        return user;
    }
    
    @Path("all")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<UserInfo> findAllUsers(){
        List<UserInfo> userlist=em.createNamedQuery("entity.userinfo.selectall").getResultList();
        return userlist;
    }
}
