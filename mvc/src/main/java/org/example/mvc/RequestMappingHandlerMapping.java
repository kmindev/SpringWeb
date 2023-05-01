package org.example.mvc;

import org.example.mvc.controller.*;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class RequestMappingHandlerMapping {
    // [key]: url 경로, [value]: Controller
    private Map<HandlerKey, Controller> mappings = new HashMap<>();

    void init() { // 어떠한 경로도 설정하지 않았을 때
        mappings.put(new HandlerKey(RequestMethod.GET, "/"), new HomeController()); // HomeController를 실행하기 위함
        mappings.put(new HandlerKey(RequestMethod.GET, "/users"), new UserListController()); // UserListController 실행하기 위함
        mappings.put(new HandlerKey(RequestMethod.POST, "/users"), new UserCreateController());
        mappings.put(new HandlerKey(RequestMethod.GET, "/user/form"), new ForwardController("/user/form"));
    }

    public Controller findHandler(HandlerKey handlerKey) { // urlPath에 일치하는 controller를 리턴
        return mappings.get(handlerKey);
    }
}
