package org.fczm.httper.component;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.ObjectMetadata;
import org.fczm.common.util.Debug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Component
public class AliyunOSSComponent {

    @Autowired
    private ConfigComponent config;

    private OSSClient createClient() {
        return new OSSClient(config.aliyun.endpoint, config.aliyun.accessKeyId, config.aliyun.accessKeySecret);
    }

    public boolean upload(String pathname, String contentType) {
        File file = new File(config.getCachePath() + pathname);
        if (!file.exists()) {
            // Delete the database reference if the file is not existing.
            deleteFromOSS(pathname);
            return false;
        }
        Debug.log("Upload " + pathname + " to Aliyun OSS");
        OSSClient ossClient = createClient();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);
        CannedAccessControlList acl = CannedAccessControlList.PublicRead;
        try {
            ossClient.putObject(config.aliyun.bucket, pathname, new FileInputStream(file), metadata);
            ossClient.setObjectAcl(config.aliyun.bucket, pathname, CannedAccessControlList.PublicRead);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            ossClient.shutdown();
            return false;
        }
        file.delete();
        return true;
    }

    public void deleteFromOSS(String pathname) {
        OSSClient ossClient = createClient();
        ossClient.deleteObject(config.aliyun.bucket, pathname);
        ossClient.shutdown();
    }

}
