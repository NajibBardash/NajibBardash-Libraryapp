package se.yrgo.libraryapp.controllers;

import javax.inject.Inject;

import io.jooby.annotations.GET;
import io.jooby.annotations.POST;
import io.jooby.annotations.Path;
import io.jooby.annotations.QueryParam;
import se.yrgo.libraryapp.dao.UserDao;
import se.yrgo.libraryapp.entities.forms.RegisterUserData;
import se.yrgo.libraryapp.services.UserService;
import se.yrgo.libraryapp.validators.RealName;
import se.yrgo.libraryapp.validators.Username;

@Path("/register")
public class RegisterUserController {
    private UserDao userDao;
    private UserService service;

    @Inject
    RegisterUserController(UserService service) {
        this.service = service;
    }

    @POST
    public boolean register(RegisterUserData userData) {
        return service.register(userData.getName(), userData.getRealName(), userData.getPassword());
    }

    @GET
    @Path("/available")
    public boolean isNameAvailable(@QueryParam String name) {
        return userDao.isNameAvailable(name);
    }
}
