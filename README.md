# SpringBoot banking application.

This project was created for the Mobile Banking App.

## Project properties

- **SpringBoot:** 2.7.4
- **Java:** 17

---

## Registration

**Path:** `http://sbbankingapp-env.eba-3teik5g7.eu-central-1.elasticbeanstalk.com/register`

**Method:** Post

**Format:** JSON

**Raw body example:**

`Old passport format: 2 letters and 6 numbers`
```
{
    "firstName": "name",
    "lastName": "surname",
    "phoneNumber": "+380501297847",
    "ipn": "5291807314",
    "passportNumber": "БФ345678",
    "password": "examplePassword"
}
```
`2016 passport format: 13 numbers`
```
{
    "firstName": "name",
    "lastName": "surname",
    "phoneNumber": "+380501297847",
    "ipn": "5291807314",
    "passportNumber": "8412452412002",
    "password": "examplePassword"
}
```
`2020 passport format: 9 numbers`
```
{
    "firstName": "name",
    "lastName": "surname",
    "phoneNumber": "+380501297847",
    "ipn": "5291807314",
    "passportNumber": "523516743",
    "password": "examplePassword"
}
```
**Response body example:**

```
{
    "jwt": "eyJ0eXAiOiJ141QiLCJhbGciOiJIUzI1NiJ9.eyJwaG9uZU9ySXBuIjoiNTI5MTgwNzMxMSIsInN1YiI6Ik1CQSIsImlzcyI6Ik1CQV9Jc3N1ZXIiLCJleHAiOjE2NjY1MjkzNzQsI4lhdCI6MTY2NjUyNTc3NH0.NkgoKCYJrJXXT23MH0SFeHBTsUJsBOl2DENSY_NRc94"
}
```

**Response body example in case when not all data is received:**

```
{
    "error": "Client-data has null fields"
}
```

**Response body example in case when such ipn already exists:**

```
{
    "error": "User with such ipn already exists."
}
```

**Response body example in case when such phone-number already exists:**

```
{
    "error": "User with such phone-number already exists."
}
```

**Response body example in case when such passport-number already exists:**

```
{
    "error": "User with such passport-number already exists."
}
```

**Response body example in case when ipn length do not equal 10:**

```
{
    "error": "bad ipn"
}
```

**Response body example in case when phone-number is in incorrect format:**

```
{
    "error": "bad phone number"
}
```

**Response body example in case when passport-number is in incorrect format:**

```
{
    "error": "bad passport number"
}
```

---

## Login

**Path:** `http://sbbankingapp-env.eba-3teik5g7.eu-central-1.elasticbeanstalk.com/login`

**Method:** Post

**Format:** JSON

**Raw body example:**

Through a **phone number**:

```
{
    "phoneNumber": "+38010101010",
    "password": "examplePassword"
}
```
Validation:
```
checks is it ukrainian number
checks length of number
checks operator's codes
```
"error": "bad phone number"

Through an **identification code**:

```
{
    "phoneNumber": "5291807314",
    "password": "examplePassword"
}
```
Validation:
```
checks ipn length
```
return "error": "bad ipn"

**Response body example:**

```
{
    "jwt": "eyJ0eXAiOiJ141QiLCJhbGciOiJIUzI1NiJ9.eyJwaG9uZU9ySXBuIjoiNTI5MTgwNzMxMSIsInN1YiI6Ik1CQSIsImlzcyI6Ik1CQV9Jc3N1ZXIiLCJleHAiOjE2NjY1MjkzNzQsI4lhdCI6MTY2NjUyNTc3NH0.NkgoKCYJrJXXT23MH0SFeHBTsUJsBOl2DENSY_NRc94"
}
```

---

## Work with jwt

**Headers**

    Key: Authorization
    Value: Bearer eyJ0eXAiOiJ141QiLCJhbGciOiJIUzI1NiJ9.eyJwaG9uZU9ySXBuIjoiNTI5MTgwNzMxMSIsInN1YiI6Ik1CQSIsImlzcyI6Ik1CQV9Jc3N1ZXIiLCJleHAiOjE2NjY1MjkzNzQsI4lhdCI6MTY2NjUyNTc3NH0.NkgoKCYJrJXXT23MH0SFeHBTsUJsBOl2DENSY_NRc94

## Transactions

**Path:** `http://sbbankingapp-env.eba-3teik5g7.eu-central-1.elasticbeanstalk.com/user/transfer`

**Method:** Post

**Headers**

    Key: Authorization
    Value: Bearer eyJ0eXAiOiJ141QiLCJhbGciOiJIUzI1NiJ9.eyJwaG9uZU9ySXBuIjoiNTI5MTgwNzMxMSIsInN1YiI6Ik1CQSIsImlzcyI6Ik1CQV9Jc3N1ZXIiLCJleHAiOjE2NjY1MjkzNzQsI4lhdCI6MTY2NjUyNTc3NH0.NkgoKCYJrJXXT23MH0SFeHBTsUJsBOl2DENSY_NRc94

**Format:** JSON

**Raw body example:**

```
{
    "senderCardNumber": "5168324249821302",
    "receiverCardNumber": "5168758323722993",
    "receiverName": "Igor",
    "sum": 150,
    "purpose": "sending 150$"
}
```

**Response body examples:**

```
{
    "message": "success"
}
```

```
{
    "error": "sender do not have that card"
}
```

```
{
    "error": "receiver card was not found"
}
```

```
{
    "error": "limit is lower than specified sum"
}
```

```
{
    "error": "not enough money on the card"
}
```

```
{
    "error": "transfer data is not valid"
}
```
