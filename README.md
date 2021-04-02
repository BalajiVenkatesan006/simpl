# simpl
#Paylater problem

To run this project:

Maven should be installed in the system.

Run the below command to run the applciation

Command:  mvn spring-boot:run

To see the database: hit this in your browser http://localhost:8084/h2-console/ and enter the credentials as specified in properties file and you can view the data.

I have used email as the customer identification paramater:

Sample Execution commands:
==============================
You can Start entering the commands
==============================
Please Enter the command 

new txn user1@gmail.com m1 90

Success false  User not  found

Please Enter the command

new user user1@gmail.com 900

 Invalid Input!!!!!!!!
 
Please Enter the command

new user user1 user1@gmail.com 900

Success true  null

Please Enter the command

new txn user1@gmail.com m1 90

Success false  Invalid merchant

Please Enter the command

new merchant m1 8

Success true  null

Please Enter the command

new txn user1@gmail.com m1 90

Success true  null

Please Enter the command

report total-dues

Success true null

user1@gmail.com 90.00

Please Enter the command

payback user1@gmail.com 90

Success true  null

Please Enter the command

report total-dues

Success true null

user1@gmail.com 0.00

Please Enter the command

report discount m1

Success true  null 7.2000

Please Enter the command

report discount

 Invalid Input!!!!!!!!
 
Please Enter the command

report discount m2

Success false  Merchant not found null

Please Enter the command

report users-at-credit-limit

Success true null

Please Enter the command

exit
==============================
Logging out
==============================



API's also exposed to perform the operation sharing the curl to test the endpoints.

curl --location --request POST 'http://localhost:8084/user/create' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name":"test",
    "email":"test@gmail.com",
    "creditLimit": 900
}'


curl --location --request POST 'http://localhost:8084/merchant/create' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name":"M10",
    "offer":5
}'


curl --location --request POST 'http://localhost:8084/user/txn' \
--header 'Content-Type: application/json' \
--data-raw '{
    "txnAmount": 200,
    "merchantName": "M10",
    "userEmail":"sam@gmail.com"
}'


curl --location --request POST 'http://localhost:8084/user/pay' \
--header 'Content-Type: application/json' \
--data-raw '{
    "amount":500,
    "userEmail":"bala@gmail.com"
}'

curl --location --request GET 'http://localhost:8084/report/totalDue'