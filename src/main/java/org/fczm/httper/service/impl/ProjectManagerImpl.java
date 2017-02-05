package org.fczm.httper.service.impl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.fczm.httper.bean.ProjectBean;
import org.fczm.httper.domain.Project;
import org.fczm.httper.domain.User;
import org.fczm.httper.service.ProjectManager;
import org.fczm.httper.service.common.ManagerTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectManagerImpl extends ManagerTemplate implements ProjectManager {

    public ProjectBean addProject(String pname, String privilege, String introduction, long updateAt, String uid) {
        User user = userDao.get(uid);
        if (user == null) {
            return null;
        }
        Project project = new Project();
        project.setPname(pname);
        project.setPrivilege(privilege);
        project.setIntroduction(introduction);
        project.setUpdateAt(updateAt);
        project.setRevision(projectDao.getMaxRevision(user) + 1);
        project.setDeleted(false);
        project.setUser(user);
        if (projectDao.save(project) == null) {
            return null;
        }
        return new ProjectBean(project);
    }

    public List<ProjectBean> receiveClientProjects(String projectsJSONArray, String uid) {
        User user = userDao.get(uid);
        if (user == null) {
            return null;
        }
        List<ProjectBean> projects = new ArrayList<ProjectBean>();
        JSONArray projectArray = JSONArray.fromObject(projectsJSONArray);
        for (int i = 0; i < projectArray.size(); i++) {
            JSONObject projectObject = projectArray.getJSONObject(i);
            String pname = projectObject.getString("pname");
            String privilege = projectObject.getString("privilege");
            String introduction = projectObject.getString("introduction");
            long updateAt = projectObject.getLong("updateAt");
            // Add new project entity.
            projects.add(addProject(pname, privilege, introduction, updateAt, uid));
        }
        return projects;
    }

    public List<ProjectBean> getUpdatedProjectsByRevision(int revision, String uid) {
        User user = userDao.get(uid);
        if (user == null) {
            return null;
        }
        List<ProjectBean> projects = new ArrayList<ProjectBean>();
        for (Project project: projectDao.findUpdatedByRevision(revision, user)) {
            projects.add(new ProjectBean(project));
        }
        return projects;
    }
}
