package albertweb.artworks.rest;

import albertweb.artworks.entity.User;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


@Path("works")
public class WorksRest {
    
    @GET
    @Produces("text/json")
    public User getHtml(){
        User user=new User();
        user.setUser("albertflex");
        user.setPassword("moeyui0624");
        return user;
    }
    
}
