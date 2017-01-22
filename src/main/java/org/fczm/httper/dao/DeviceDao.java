package org.fczm.httper.dao;

import org.fczm.common.hibernate.support.CrudDao;
import org.fczm.httper.domain.Device;

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
