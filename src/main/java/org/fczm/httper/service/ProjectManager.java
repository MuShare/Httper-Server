package org.fczm.httper.service;

import org.fczm.httper.bean.ProjectBean;

import java.util.List;

public interface ProjectManager {

    /**
     * Add new project
     * @param pname
     * @param privilege
     * @param introduction
     * @param updateAt
     * @param uid
     * @return
     */
    ProjectBean addProject(String pname, String privilege, String introduction, long updateAt, String uid);

    /**
     * Receive multiple project entities from client by JSON string of project array.
     * @param projectsJSONArray
     * @param uid
     * @return
     */
    List<ProjectBean> receiveClientProjects(String projectsJSONArray, String uid);

}
