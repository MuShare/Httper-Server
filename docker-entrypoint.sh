#!/usr/bin/env bash
sed -i 's/127.0.0.1:3306/'$DB_SERVER':'$DB_PORT'/g' /usr/local/tomcat/webapps/ROOT/WEB-INF/hibernate.properties
sed -i 's/customer/'$DB_NAME'/g' /usr/local/tomcat/webapps/ROOT/WEB-INF/hibernate.properties
sed -i 's/db.username=www/db.username='$DB_USER'/g' /usr/local/tomcat/webapps/ROOT/WEB-INF/hibernate.properties
sed -i 's/db.password=/db.password='$DB_PASSWORD'/g' /usr/local/tomcat/webapps/ROOT/WEB-INF/hibernate.properties
sed -i 's/global_languages/'$GLOBAL_LANGUAGES'/g' /usr/local/tomcat/webapps/ROOT/WEB-INF/config.json
sed -i 's/global_http_protocol/'$GLOBAL_HTTP_PROTOCOL'/g' /usr/local/tomcat/webapps/ROOT/WEB-INF/config.json
sed -i 's/global_domain/'$GLOBAL_DOMAIN'/g' /usr/local/tomcat/webapps/ROOT/WEB-INF/config.json
sed -i 's/600/'$GLOBAL_VALIDITY'/g' /usr/local/tomcat/webapps/ROOT/WEB-INF/config.json
sed -i 's/mail_sender_address/'$MAIL_SENDER'/g' /usr/local/tomcat/webapps/ROOT/WEB-INF/config.json
sed -i 's/mail_smtp_server/'$MAIL_SMTP'/g' /usr/local/tomcat/webapps/ROOT/WEB-INF/config.json
sed -i 's/mail_username/'$MAIL_USERNAME'/g' /usr/local/tomcat/webapps/ROOT/WEB-INF/config.json
sed -i 's/mail_password/'$MAIL_PASSWORD'/g' /usr/local/tomcat/webapps/ROOT/WEB-INF/config.json
sed -i 's/facebook_app_id/'$FACEBOOK_APP_Id'/g' /usr/local/tomcat/webapps/ROOT/WEB-INF/config.json
sed -i 's/aliyun_base_url/'$ALIYUN_BASE_URL'/g' /usr/local/tomcat/webapps/ROOT/WEB-INF/config.json
sed -i 's/aliyun_endpoint/'$ALIYUN_ENDPOINT'/g' /usr/local/tomcat/webapps/ROOT/WEB-INF/config.json
sed -i 's/aliyun_bucket/'$ALIYUN_BUCKET'/g' /usr/local/tomcat/webapps/ROOT/WEB-INF/config.json
sed -i 's/aliyun_access_key_id/'$ALIYUN_ACCESS_KEY_ID'/g' /usr/local/tomcat/webapps/ROOT/WEB-INF/config.json
sed -i 's/aliyun_access_key_secret/'$ALIYUN_ACCESS_KEY_SECRET'/g' /usr/local/tomcat/webapps/ROOT/WEB-INF/config.json
sh /usr/local/tomcat/bin/catalina.sh run
