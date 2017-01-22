# Httper Server
This web service of Httper, an iOS REST API test tool for developers. Httper's source code is here: https://github.com/lm2343635/Httper.

Download this app from App Store: https://itunes.apple.com/app/httper/id1166884043

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
    - deviceIdentifier(String): UUID of a device
    - deviceToken(String, Optional): device token from APNs server
    - os(String, Optional): name and version of operating system 
    - lan(String, Optional): device language
  - return:
    - token(String): login token
  - error:
    - ErrorEmailNotExist(1021): This email is not exsit.
    - ErrorPasswordWrong(1022): Password is wrong.

2. Request
====
(1)`api/request/add`

  - Add new request entity
  - method: POST
  - param:
    - url(String)
    - method(String)
    - updateAt(long)
    - headers(String): JSON string of headers
    - parameters(String): JSON string of parameters
    - bodyType(String)
    - body(String)
  - return:
    - revision(int): revision for this new request
    - rid(String): physical id of request in server
  - error:
    - ErrorToken(901): Token is wrong.
    - ErrorAddRequest(2011): Add request failed because of an internel error.
