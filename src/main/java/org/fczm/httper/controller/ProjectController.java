package org.fczm.httper.controller;

import org.fczm.httper.bean.ProjectBean;
import org.fczm.httper.bean.UserBean;
import org.fczm.httper.controller.common.ControllerTemplate;
import org.fczm.httper.controller.common.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/project")
public class ProjectController extends ControllerTemplate {

    @RequestMapping(value = "/push", method = RequestMethod.POST)
    public ResponseEntity pushProjects(@RequestParam String projectsJSONArray, HttpServletRequest request) {
        UserBean user = auth(request);
        if (user == null) {
            return generateBadRequest(ErrorCode.ErrorToken);
        }
        final List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
        final List<ProjectBean> projectBeans = projectManager.receiveClientProjects(projectsJSONArray, user.getUid());
        for (final ProjectBean projectBean: projectBeans) {
            results.add(new HashMap<String, Object>(){{
                put("revision", projectBean == null? -1: projectBean.getRevision());
                put("pid", projectBean == null? "": projectBean.getPid());
            }});
        }
        return generateOK(new HashMap<String, Object>(){{
            put("results", results);
            put("revision", projectBeans.get(projectBeans.size() - 1).getRevision());
        }});
    }

    @RequestMapping(value = "/pull", method = RequestMethod.GET)
    public ResponseEntity pullUpdatedProject(@RequestParam int revision, HttpServletRequest request) {
        UserBean user = auth(request);
        if (user == null) {
            return generateBadRequest(ErrorCode.ErrorToken);
        }
        List<ProjectBean> projects = projectManager.getUpdatedProjectsByRevision(revision, user.getUid());
        final List<ProjectBean> updated = new ArrayList<ProjectBean>();
        final List<String> deleted = new ArrayList<String>();
        for (ProjectBean project: projects) {
            if (project.isDeleted()) {
                deleted.add(project.getPid());
            } else {
                updated.add(project);
            }
        }
        final int globalRevision = (projects.size() == 0)? revision: projects.get(projects.size() - 1).getRevision();
        return generateOK(new HashMap<String, Object>(){{
            put("updated", updated);
            put("deleted", deleted);
            put("revision", globalRevision);
        }});
    }
}
