/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albertweb.artworks.rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;


@Path("works")
public class WorksRest {
    @Context
    private UriInfo context;
    public WorksRest() {
    }
    
    @GET
    @Produces("text/html")
    public String getHtml(){
        return "<html><body>Hello Works</body></html>";
    }
    
    @POST
    public String postHtml(){
        return "";
    }
}
