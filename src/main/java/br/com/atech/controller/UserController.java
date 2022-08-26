package br.com.atech.controller;

import br.com.atech.model.User;
import br.com.atech.request.UserRequest;
import br.com.atech.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(method = {RequestMethod.GET})
    @RolesAllowed({"role-user", "role-admin"})
    public ResponseEntity<List<User>> findAll(){
        List<User> userList = userService.findAll();
        return new ResponseEntity(userList, HttpStatus.OK);
    }

    @RolesAllowed({"role-admin"})
    @RequestMapping(method = {RequestMethod.POST})
    public ResponseEntity save(@RequestBody UserRequest userRequest){
        User userRequestMap = modelMapper.map(userRequest, User.class);
        User user = userService.save(userRequestMap);
        return ResponseEntity.ok().body(user);
    }

    @RolesAllowed({"role-user", "role-admin"})
    @RequestMapping(path = "/{id}", method = {RequestMethod.GET})
    public ResponseEntity<User> detail(@PathVariable("id") int id){
        return userService.findById(id).map((User user) -> {
            return ResponseEntity.ok().body(user);
        }).orElse(ResponseEntity.notFound().build());
    }

    @RolesAllowed("role-admin")
    @RequestMapping(path = "/{id}", method = {RequestMethod.PUT})
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @RequestBody UserRequest userRequest){
        User userRequestMap = modelMapper.map(userRequest, User.class);
        return userService.findById(id).map((User user) -> {
            user.setName(userRequestMap.getName());
            user.setPassword(userRequestMap.getPassword());
            user.setEmail(userRequestMap.getEmail());
            User updated = userService.save(user);
            return ResponseEntity.ok().body(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @RolesAllowed("role-admin")
    @RequestMapping(path = "/{id}", method = {RequestMethod.DELETE})
    public ResponseEntity delete(@PathVariable("id") int id){
        return userService.findById(id).map((User user) -> {
            userService.delete(user);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
