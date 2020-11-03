package org.mushare.httper.service;

import org.mushare.httper.bean.ProjectBean;

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

    /**
     * Get all updated projects for an user
     * @param revision
     * @param uid
     * @return
     */
    List<ProjectBean> getUpdatedProjectsByRevision(int revision, String uid);

    /**
     * Remove project by pid for an user
     * @param pid
     * @param uid
     * @return
     */
    int removeProjectByPid(String pid, String uid);
}
