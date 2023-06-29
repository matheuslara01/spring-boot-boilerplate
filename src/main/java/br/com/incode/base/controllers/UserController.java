package br.com.incode.base.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.incode.base.common.generics.ControllerGenericImpl;
import br.com.incode.base.models.entities.User;


@RestController
@RequestMapping("/user")
public class UserController extends ControllerGenericImpl<User, Long> {
    
}
