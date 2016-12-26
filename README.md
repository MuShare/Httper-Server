# Httper's API Document
This is the REST API document of Httper Web service.

1. User
====
(1)`api/user/register`
   
   - Register a user by email.
   - method: POST
   - param: 
      - email(String): email address
      - name(String): user name
      - password(String): user password
   - return:
      - uid(String): physical id of user
   - error:
      - ErrorEmailExist(1011): This email has been registered.

(2)`api/user/login`
   
   - Login by email and password.
   - method: POST
   - param: 
      - email(String): email address
      - password(String): user password
      - password(String): user password
      - deviceIdentifier(String): UUID of a device
      - deviceToken(String, Optional): device token from APNs server
      - os(String, Optional): name and version of operating system 
      - lan(String, Optional): device language
   - return:
      - token(String): login token
   - error:
      - ErrorEmailNotExist(1021): This email is not exsit.
      - ErrorPasswordWrong(1023): Password is wrong.
