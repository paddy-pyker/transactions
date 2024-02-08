# Getting Started

🎯 STEP 1

    git clone https://github.com/paddy-pyker/transactions.git && cd transactions


<br>

🎯 STEP 2

    set up mysql database credentials in src/main/resources/application.properties

<br>

🎯 STEP 3

    docker build -t transactions:latest .

<br>

🎯 STEP 4

    docker run -dp 8080:8080 transactions

<br>

🎯 STEP 5

    create user accounts first, who will make transactions to one another:

    curl -X POST http://localhost:8080/users/create   \
         -H 'Content-Type: application/json'          \
         -d '{ 
             "username": "paddy",
             "accountBalance": "100.00"
            }'

<br>

🎯 STEP 6

    return all users with their id which is used for making transactions

    curl -X GET http://localhost:8080/users/

<br>

🎯 STEP 7

    the application can now be used as per the requirements. eg: make transactions between two users

    curl -X POST http://localhost:8080/transactions/create   \
         -H 'Content-Type: application/json'                 \
         -d '{ 
             "sender": "1",
             "receiver":"2",
             "amount": "10.39"
            }'
