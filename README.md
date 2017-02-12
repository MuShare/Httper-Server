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
    - name(String): user name
  - error:
    - ErrorEmailNotExist(1021): This email is not exsit.
    - ErrorPasswordWrong(1022): Password is wrong.

(3)`api/user`

  - Get user information.
  - method: GET
  - header:
    - token(String): login token for authentication
  - return:
    - user(UserBean): user information
  - error:
    - ErrorToken(901): Token is wrong.

(4)`api/user/name`

  - Modify user name.
  - method: POST
  - header:
    - token(String): login token for authentication
  - param:
    - name(String): user name
  - return:
    - success(bool)
  - error:
    - ErrorToken(901): Token is wrong.

(5)`api/user/password/reset`

  - Send reset password email to user.
  - method: GET
  - param:
    - email(String): user's email address
  - return:
    - success(bool)
  - error:
    - ErrorToken(901): Token is wrong.
    - ErrorSendResetPasswordMail(1031): Send reset password email failed.

2. Request
====

(1)`api/request/push`

  - Push existed request entitiesn.
  - method: POST
  - header:
    - token(String): login token for authentication
  - param:
    - requestsJSONArray(String): JSON array string of request entities
  - return:
    - results(List\<Map>): Push result.
  - error:
    - ErrorToken(901): Token is wrong.
    
(2)`api/request/push`

  - Delete a request entity in server
  - method: DELETE
  - header:
    - token(String): login token for authentication
  - param:
    - rid(String): physical id in server of this request entity
  - return:
    - revision(int): revision for this delete request, client should update its local revision by this
  - error:
    - ErrorToken(901): Token is wrong.
    - ErrorDeleteRequest(2011): Request not found. This may caused by commiting a wrong request.
 
(3)	`api/request/pull`

  - Pull updated request entity.
  - method: GET
  - header:
    - token(String): login token for authentication
  - param:
    - revision(int): global request revision in client
  - return
    - updated(List\<RequestBean>): updated requests
    - deleted(List\<String>): rid list of deleted requests
    - revision: global request revision in server, return the revision from parameters if there is no updated requests from server.
  - error:
    - ErrorToken(901): Token is wrong.
 
 2. Project
====

(1)`api/project/push`

  - Push existed project entities.
  - method: POST
  - header:
    - token(String): login token for authentication
  - param:
    - projectsJSONArray(String): JSON array string of project entities
  - return:
    - results(List\<Map>): Push result.
  - error:
    - ErrorToken(901): Token is wrong.
    
(2)`api/project/push`

  - Delete a project entity in server
  - method: DELETE
  - header:
    - token(String): login token for authentication
  - param:
    - rid(String): physical id in server of this project entity
  - return:
    - revision(int): revision for this delete project, client should update its local revision by this
  - error:
    - ErrorToken(901): Token is wrong.
    - ErrorDeleteProject(3011): Cannot delete this project..
 
(3)	`api/project/pull`

  - Pull updated project entity.
  - method: GET
  - header:
    - token(String): login token for authentication
  - param:
    - revision(int): global project revision in client
  - return
    - updated(List\<ProjectBean>): updated projects
    - deleted(List\<String>): pid list of deleted projects
    - revision: global project revision in server, return the revision from parameters if there is no updated projects from server.
  - error:
    - ErrorToken(901): Token is wrong.