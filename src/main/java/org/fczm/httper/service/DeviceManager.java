package org.fczm.httper.service;

public interface DeviceManager {

    /**
     * Register a device
     * @param identifier
     * @param os
     * @param lan
     * @param deviceToken
     * @param ip
     * @return
     */
    String registerDevice(String identifier, String os, String lan, String deviceToken, String ip, String uid);

}
