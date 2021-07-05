# UserManagementApp
Build an User Management Application 

Used Dependencies:

Spring Security - To Authentication/Authorization
Spring Data JPA
Lombok

DB:
posgresql

Rest End Points:

Register the user:

http:localhost:9091/api/v1/user/registration Method: POST

// To Store the password -- Used BCrypt
// To store token -- Used UUID (Instead of JWT)  

Token Confirmation: // Once the token is confirmed, the user is enabled and can Login into the application

localhost:9091/api/v1/registration/confirm?token=b02fa8cc-4720-4b7e-9594-e7c7639169be Method: GET

GET all the users

http:localhost:9091/api/v1/users Method: GET 

Create the users

http:localhost:9091/api/v1/user/create Method: POST 

Update users based on user_id

http:localhost:9091/api/v1/user/1 Method: PUT

Delete users based on user_id

http:localhost:9091/api/v1/user/1 Methid: Delete

Delete all the users

http:localhost:9091/api/v1/users Methid: Delete

