package br.com.incode.base.application.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.incode.base.application.generics.ControllerGenericImpl;
import br.com.incode.base.infrastructure.persistence.entities.User;


@RestController
@RequestMapping("/user")
public class UserController extends ControllerGenericImpl<User, Long> {
    
}
