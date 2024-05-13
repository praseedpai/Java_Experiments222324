package com.chistadata.Services.Auxilary;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;


@Path( "/hello/{name}" )
public class SimpleHello
{
    @GET
    public String getThing( @PathParam( "name" ) String name )
    {
        return "Hello, " + name;
    }
}