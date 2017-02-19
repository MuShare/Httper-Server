package org.fczm.httper.service.impl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.fczm.httper.bean.ProjectBean;
import org.fczm.httper.domain.Project;
import org.fczm.httper.domain.Request;
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
            String pid = projectObject.getString("pid");
            long updateAt = projectObject.getLong("updateAt");
            Project project = null;
            if (!pid.equals("")) {
                project = projectDao.get(pid);
                if (project != null) {
                    if (project.getUser() != user) {
                        project = null;
                    }
                }
            }
            if (project != null) {
                project.setPname(pname);
                project.setPrivilege(privilege);
                project.setIntroduction(introduction);
                // Update revision
                project.setRevision(projectDao.getMaxRevision(user) + 1);
                projectDao.update(project);
                projects.add(new ProjectBean(project));
            } else {
                // Add new project entity.
                projects.add(addProject(pname, privilege, introduction, updateAt, uid));
            }

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

    public int removeProjectByPid(String pid, String uid) {
        User user = userDao.get(uid);
        if (user == null) {
            return -1;
        }
        Project project = projectDao.get(pid);
        if (project == null) {
            return -1;
        }
        if (project.getUser() != user) {
            return -1;
        }
        // Set requests' project to null
        for (Request request : requestDao.findByProject(project)) {
            request.setProject(null);
            requestDao.update(request);
        }
        // Set attributes to null.
        project.setPname(null);
        project.setPrivilege(null);
        project.setIntroduction(null);
        project.setUpdateAt(null);
        project.setRevision(projectDao.getMaxRevision(user) + 1);
        project.setDeleted(true);
        projectDao.update(project);
        return project.getRevision();
    }
}
