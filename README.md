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

Through a **phone number**:

```
{
    "firstName": "name",
    "lastName": "surname",
    "phoneNumber": "+38010101010",
    "password": "examplePassword"
}
```

Through an **identification code**:

```
{
    "firstName": "name",
    "lastName": "surname",
    "ipn": "5291807314",
    "password": "examplePassword"
}
```

**Response body example:**

```
{
    "jwt": "eyJ0eXAiOiJ141QiLCJhbGciOiJIUzI1NiJ9.eyJwaG9uZU9ySXBuIjoiNTI5MTgwNzMxMSIsInN1YiI6Ik1CQSIsImlzcyI6Ik1CQV9Jc3N1ZXIiLCJleHAiOjE2NjY1MjkzNzQsI4lhdCI6MTY2NjUyNTc3NH0.NkgoKCYJrJXXT23MH0SFeHBTsUJsBOl2DENSY_NRc94"
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

Through an **identification code**:

```
{
    "phoneNumber": "5291807314",
    "password": "examplePassword"
}
```

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
