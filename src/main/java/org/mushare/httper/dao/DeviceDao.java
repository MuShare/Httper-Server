package org.mushare.httper.dao;

import org.mushare.common.hibernate.support.CrudDao;
import org.mushare.httper.domain.Device;

public interface DeviceDao extends CrudDao<Device> {

    /**
     * Get a device by identifier.
     * @param identifier
     * @return
     */
    Device getByIdentifier(String identifier);

    /**
     * Get a device by login token
     * @param token
     * @return
     */
    Device getByToken(String token);
}
