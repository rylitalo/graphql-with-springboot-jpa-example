# graphql-with-springboot-jpa-example
An example of GraphQL endpoint that leverages Springboot and JPA

## Build the Sample Application
Run **./gradlew build**

## Run the Sample Application
Run **./gradlew bootRun**


## Access via Postman
After starting the application, use postman to POST a request to the following url
**http://localhost:8080/graphql** in a browser.

## Request body of POST for Single Customer
 ```
 {
 	customer (id: "1") {
 		id
 		firstName
 		lastName
 		accounts {
 			id
 			name
 			balance

 		}
 	}
 }
```

 ## Response Body of POST for Single Customer
 ```json
 {
     "customer": {
         "id": "1",
         "firstName": "Bob",
         "lastName": "Johnson",
         "accounts": [
             {
                 "id": "1",
                 "name": "Bob's Checking Account",
                 "balance": 2100.5
             },
             {
                 "id": "2",
                 "name": "Bob's Savings Account",
                 "balance": 325.5
             }
         ]
     }
 }
 ```


 ## Request body of POST for All Customers
  ```
  {
  	allCustomers {
  		id
  		firstName
  		lastName
  		accounts {
  			id
  			name
  			balance

  		}
  	}
  }
  ```

  ## Response Body of POST for All Customers
  ```json
{
    "allCustomers": [
        {
            "id": "1",
            "firstName": "Bob",
            "lastName": "Johnson",
            "accounts": [
                {
                    "id": "1",
                    "name": "Bob's Checking Account",
                    "balance": 2100.5
                },
                {
                    "id": "2",
                    "name": "Bob's Savings Account",
                    "balance": 325.5
                }
            ]
        },
        {
            "id": "2",
            "firstName": "Sally",
            "lastName": "Brown",
            "accounts": [
                {
                    "id": "3",
                    "name": "Sally's Checking Account",
                    "balance": 500.5
                },
                {
                    "id": "4",
                    "name": "Sally's Savings Account",
                    "balance": 5555.5
                }
            ]
        }
    ]
}
```