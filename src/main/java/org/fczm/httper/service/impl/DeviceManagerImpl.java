package org.fczm.httper.service.impl;

import org.fczm.httper.domain.Device;
import org.fczm.httper.domain.User;
import org.fczm.httper.service.DeviceManager;
import org.fczm.httper.service.common.ManagerTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeviceManagerImpl extends ManagerTemplate implements DeviceManager {

    public String registerDevice(String identifier, String os, String lan, String deviceToken, String ip, String uid) {
        User user = userDao.get(uid);
        if (user == null) {
            return null;
        }
        Device device = deviceDao.getByIdentifier(identifier);
        if (device == null) {
            device = new Device();
            device.setIdentifier(identifier);
            device.setOs(os);
            device.setLan(lan);
            device.setDeviceToken(deviceToken);
            device.setLastLoginIp(ip);
            device.setUser(user);
            device.setLoginToken(UUID.randomUUID().toString());
            String did = deviceDao.save(device);
            if (did == null) {
                return null;
            }
        } else {
            device.setOs(os);
            device.setLan(lan);
            device.setDeviceToken(deviceToken);
            device.setLastLoginIp(ip);
            device.setUser(user);
            device.setLoginToken(UUID.randomUUID().toString());
            deviceDao.update(device);
        }
        return device.getLoginToken();
    }

}
