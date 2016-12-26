package org.fczm.httper.dao.impl;

import org.fczm.common.hibernate.support.PageHibernateDaoSupport;
import org.fczm.httper.dao.DeviceDao;
import org.fczm.httper.domain.Device;
import org.springframework.stereotype.Repository;

@Repository
public class DeviceDaoHibernate extends PageHibernateDaoSupport<Device> implements DeviceDao {

    public DeviceDaoHibernate() {
        super();
        setClass(Device.class);
    }

}
