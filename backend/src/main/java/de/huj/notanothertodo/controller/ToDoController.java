package de.huj.notanothertodo.controller;

import de.huj.notanothertodo.model.ToDo;
import de.huj.notanothertodo.model.User;
import de.huj.notanothertodo.payload.in.ToDoRequest;
import de.huj.notanothertodo.service.JwtUserDetailsService;
import de.huj.notanothertodo.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/todo/")
public class ToDoController {

    @Autowired
    private JwtUserDetailsService userService;

    @Autowired
    private ToDoService toDoService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<ToDo> getToDos(Authentication authentication) throws Exception {
        User user = userService.getUserByName(authentication.getName());
        return toDoService.getToDo(user);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<ToDo> getToDosParam(Authentication authentication, @RequestParam String planed,  @RequestParam Optional<LocalDate> from,  @RequestParam Optional<LocalDate> to) throws Exception {
        User user = userService.getUserByName(authentication.getName());

        if(planed != null ){
            return toDoService.getToDo(user, LocalDate.parse(planed));
        }

        if(from.isPresent() && to.isPresent()){
            return toDoService.getToDo(user, from.get(), to.get());
        }

        return toDoService.getToDo(user);
    }

    @RequestMapping(value = "/{todoId}", method = RequestMethod.GET)
    public ToDo getToDo(@PathVariable long todoId, Authentication authentication) throws Exception {
        User user = userService.getUserByName(authentication.getName());
        return toDoService.getToDo(user, todoId);
    }

    @RequestMapping(value = "/{todoId}", method = RequestMethod.DELETE)
    public void removeToDo(@PathVariable long todoId, Authentication authentication) throws Exception {
        User user = userService.getUserByName(authentication.getName());
        toDoService.removeToDo(user, todoId);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ToDo saveToDo(@RequestBody ToDoRequest toDoRequest, Authentication authentication) throws Exception {
        User user = userService.getUserByName(authentication.getName());
        return toDoService.saveToDo(user, toDoRequest);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ToDo updateToDo(@RequestBody ToDoRequest toDoRequest, Authentication authentication) throws Exception {
        User user = userService.getUserByName(authentication.getName());
        return toDoService.updateToDo(user, toDoRequest);
    }
}
