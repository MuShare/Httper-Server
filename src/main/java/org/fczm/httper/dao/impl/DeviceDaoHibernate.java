package org.fczm.httper.dao.impl;

import org.fczm.common.hibernate.support.PageHibernateDaoSupport;
import org.fczm.httper.dao.DeviceDao;
import org.fczm.httper.domain.Device;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DeviceDaoHibernate extends PageHibernateDaoSupport<Device> implements DeviceDao {

    public DeviceDaoHibernate() {
        super();
        setClass(Device.class);
    }

    public Device getByIdentifier(String identifier) {
        String hql = "from Device where identifier = ?";
        List<Device> devices = (List<Device>)getHibernateTemplate().find(hql, identifier);
        if (devices.size() == 0) {
            return null;
        }
        return devices.get(0);
    }

    public Device getByToken(String token) {
        String hql = "from Device where loginToken =?";
        List<Device> devices = (List<Device>)getHibernateTemplate().find(hql, token);
        if (devices.size() == 0) {
            return null;
        }
        return devices.get(0);
    }

}
