# Weekly-Project-2-U2 -- REST API for Enterprise devices manager

This project is built to simulate the devices management of an Enterprise through a REST API

--AUTHENTICATION--
For the system to correctly run, it's required to be registered into the database.
Authentication it's provided by JWT Bearer Token, and there are three different roles of user:
  
    **Every API call described will be available in the attached PostmanCollection, named as {D.Manager [WP2U2].postman_collection}
  
    - 1 USER: being authenticated as a user will only permit to the client to see what devices of the enterprise are still under 'available' status;
    - 2 MODERATOR: being authenticated as a moderator will permit to the client to perform every GET & PUT call available in the API;
    - 3 ADMIN: being authenticated as a admin will permit to the client to perform every call available in the API;
  
  To proceed with the authentication process, there are two different ways:
  
    A) Using the Postman Collection provided in the repository:
       To register --> under the path JWT Authorization/Register, are available all the three differents POST calls to register a new user in to the Database.
       ** note that the examples in the Postman Collection already exists in the Database **
       
       To Login --> under the path JWT Authorization/Login, are available all the three differents POST calls to Login in the system.
       ** the calls are already filled with vaild data, client just have to send the call to receive the bearer token **
       
       After sending the Login POST call, the server will respond with the generated Bearer Token, to use in every other future call;
       
    B) Manually Registering & Loggin-in as a new User:
    
       - REGISTER 
         Using the endpoint -> POST api/auth/register

         Data have to be sent as JSON following the reported format:
            {
              "name": "name surname",
              "username": "...",
              "email": "...@...",
              "password": "...",
              "roles": ["ROLE_..."]
            }
         ** Note, to correctly compile the roles field, you can:
                1. Select the desired role by passing into a List as a string --> "roles": ["ROLE_USER"] or ["ROLE_MODERATOR"] or ["ROLE_ADMIN"]
                2. Select more than one role to pass into a List as a string  --> "roles": ["ROLE_USER", "ROLE_ADMIN"]
       
       - LOGIN
         Using the endpoint -> POST api/auth/login
         
         Data has to be sent as JSON following the reported format:
           {
              "username": "--user passed in the registration process--",
              "password": "--password passed in the registration process--"
           }
           
         After this, server will respond with the generated Bearer Token, that will last for over 100h. 
      
  
  LIST OF AVAILABLE ENDPOINTS:
  
   **please note that in endpoints terminating with /paged, PagingAndSortingRepository has been used, and to properly
   make it work, parameters will be required, following the example here reported:
      --> Example [.../employees/paged?page=0&size=6&sort=email,ASC] <--
          a. page = number of the page that the client want to visualize;
          b. size = how many items per page have to be shown;
          c. sort = not required but available in case of filtering.
    
    EMPLOYEES endpoints
  
      GET /employees
      Will return every employee present in the database;

      GET /employees/{id}
      Will return the employee with PK id passed as parameter, if not found, will handle exception;

      GET /employees/paged
      Will return every employee present in the database, paged;
      
      POST /employees
      Will require a JSON body with the desired data to Post, as the following:
          {
            "firstname": "...",
            "lastname": "...",
            "username": "..._...",
            "email": "...@..."
          }
      
      PUT /employees
      Will require the desired employee to update as a JSON body, as the following:
          {
            "id": ...,
            "firstname": "...",
            "lastname": "...",
            "username": "..._...",
            "email": "...@..."
          }
    
      DELETE /employees
      Will delete the desired employee passed as a JSON body, as the following:
          {
            "id": ...,
            "firstname": "...",
            "lastname": "...",
            "username": "..._...",
            "email": "...@..."
          }
          
      DELETE /employees/{id}
      Will delete the desired employee by searching for the id passed as parameter;
          
    DEVICES endpoints
    
      GET /devices
      Will return every devices present in the Database;
      
      GET /devices/{id}
      Will return the device with PK id passed as parameter, if not found, will handle exception;
 
      GET /devices/paged
      Will return every device present in the database, paged;
 
      GET /devices/paged/available
      Will return every device with status 'available' in the Database, note that this call is the only call available for user authenticated;
      
      GET /devices/paged/type/{type}
      Will return every device with field 'type' equals to the one passed, type available:
      [SMARTPHONE, LAPTOP, DESKTOP, HARDDRIVE, VEHICLE]
      
      GET /devices/paged/status/{status}
      Will return every device with field 'status' equals to the one passed, status available:
      [AVAILABLE, ASSIGNED, ON_MAINTENANCE, ABANDONED]
      
      POST /devices
      Will require a JSON body with the desired data to Post, as the following:
          {
            "type": "...",
            "status": "...",
            "owner": null
          }
      
      PUT /devices
      Will require the desired device to update as a JSON body, as the following:
          {
            "id": ...,
            "type": "...",
            "status": "...",
            "owner": null
          }
 
      PUT /devices/add/{e_id}/{d_id}
      Will permit to assign a device to a employee, passing both of their id as parameters:
      e_id --> id of the employee to assign the device;
      d_id --> id of the device to assign to the employee;
      ** please, note that only AVAILABLE devices can be assigned, otherwise, exceptions will be handled by the system **
      
      PUT /devices/remove/{d_id}
      Will remove the device found by the id passed as parameter from the employee that owns it, if not assigned, will handle exception;
 
      DELETE /devices
      Will remove the desired device passed as JSON body, as the following:
          {
            "id": 7,
            "type": "LAPTOP",
            "status": "AVAILABLE",
            "owner": null
          }
          
      DELETE /devices/{id}
      Will remove the desired device searching for the id passed as parameter;       

Wrote by AleOnta
