package app;

import static spark.Spark.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import service.UserService;


public class Aplicacao {
	
	private static UserService UserService = new UserService();
	
    public static void main(String[] args) {

        port(6969);
        
        staticFiles.location("/public");
        
        post("/usuario/insert", (request, response) -> UserService.insert(request, response));

        get("/usuario/:codigo", (request, response) -> UserService.get(request, response));
        
        get("/usuario/list/:orderby", (request, response) -> UserService.getAll(request, response));

        get("/usuario/update/:codigo", (request, response) -> UserService.getToUpdate(request, response));
        
        post("/usuario/update/:codigo", (request, response) -> UserService.update(request, response));
           
        get("/usuario/delete/:codigo", (request, response) -> UserService.delete(request, response));

             
    }
}