package org.mushare.common.hibernate.support;

import java.io.Serializable;
import java.util.List;

public interface CrudDao<T extends Serializable> {

    /**
     * Get an entity by physical id.
     * @param id
     * @return
     */
    T get(String id);

    /**
     * Save an entity.
     * @param entity
     * @return
     */
    String save(T entity);

    /**
     * Update an entity.
     * @param entity
     */
    void update(T entity);

    /**
     * Delete an entity.
     * @param entity
     */
    void delete(T entity);

    /**
     * Delete an entity by its physical id.
     * @param id
     */
    void delete(String id);

    /**
     * Fins all entities.
     * @return
     */
    List<T> findAll();

    /**
     * Find all entity order by an attribute.
     * @param orderby
     * @param desc
     * @return
     */
    List<T> findAll(String orderby, boolean desc);

}
