package org.fczm.httper.controller;

import org.fczm.httper.controller.common.ControllerTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
@RequestMapping("/api/project")
public class ProjectController extends ControllerTemplate {

    @RequestMapping(name = "/push", method = RequestMethod.POST)
    public ResponseEntity pushProjects(@RequestParam String projectsJSONArray, HttpServletRequest request) {

        return generateOK(new HashMap<String, Object>(){{

        }});
    }

}
