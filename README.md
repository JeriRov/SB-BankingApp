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
    "access_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwaG9uZU9ySXBuIjoiMDI5MTExMTgwOSIsInN1YiI6Ik1CQSIsImlzcyI6Ik1CQV9Jc3N1ZXIiLCJleHAiOjE2NjgzNDc1NTIsImlhdCI6MTY2ODM0Mzk1Mn0.P0HA4LwqqhZyW9bexmRNcYipMYPbzRnrAq_ZogOXDvk",
    "refresh_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJNQkEiLCJwaG9uZU51bWJlciI6IiszODA1MDAzNTEwMzciLCJpcG4iOiIwMjkxMTExODA5IiwiaXNzIjoiTUJBX0lzc3VlciIsImV4cCI6MTY3MDkzNTk1MiwiaWF0IjoxNjY4MzQzOTUyfQ.6X8TuQYIsU2n0i-CCv8lx7Tpq8O0AnswcZaUgwdlyDw"
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
    "refresh_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJNQkEiLCJwaG9uZU51bWJlciI6IiszODA1MDEzOTc4MzAiLCJpcG4iOiIxMjM4NTAwMDAiLCJpc3MiOiJNQkFfSXNzdWVyIiwiZXhwIjoxNjcwOTM2MTYyLCJpYXQiOjE2NjgzNDQxNjJ9.pPACQ94iMxuVhhu48wU7NoH_KjrFnJu3lPC4qK5gzsA",
    "access_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwaG9uZU9ySXBuIjoiKzM4MDUwMTM5NzgzMCIsInN1YiI6Ik1CQSIsImlzcyI6Ik1CQV9Jc3N1ZXIiLCJleHAiOjE2NjgzNDc3NjIsImlhdCI6MTY2ODM0NDE2Mn0.Iwx3fBUbI7zXdc6TgYT68Ngp4VL4opGqc0sXGym-b4s"
}
```

---

## Work with jwt

**Headers**

    Key: Authorization
    Value: Bearer eyJ0eXAiOiJ141QiLCJhbGciOiJIUzI1NiJ9.eyJwaG9uZU9ySXBuIjoiNTI5MTgwNzMxMSIsInN1YiI6Ik1CQSIsImlzcyI6Ik1CQV9Jc3N1ZXIiLCJleHAiOjE2NjY1MjkzNzQsI4lhdCI6MTY2NjUyNTc3NH0.NkgoKCYJrJXXT23MH0SFeHBTsUJsBOl2DENSY_NRc94

---

## Refresh Token
**Path:** `http://sbbankingapp-env.eba-3teik5g7.eu-central-1.elasticbeanstalk.com/refresh_token`

**Method:** Post

**Format:** JSON

**Raw body example:**

```
{
    "refreshToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJNQkEiLCJwaG9uZU51bWJlciI6IiszODA1MDAzNTEwMzciLCJpcG4iOiIwMjkxMTExODA5IiwiaXNzIjoiTUJBX0lzc3VlciIsImV4cCI6MTY3MDkzNjAxOSwiaWF0IjoxNjY4MzQ0MDE5fQ.47FW4SGRVQx9bRsvDNsmHFUpuwiP8NhzQdc83QMiCq4"
}
```

**Response body example:**

```
{
    "refresh_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJNQkEiLCJwaG9uZU51bWJlciI6IiszODA1MDEzOTc4MzAiLCJpcG4iOiIxMjM4NTAwMDAiLCJpc3MiOiJNQkFfSXNzdWVyIiwiZXhwIjoxNjcwOTM2MTYyLCJpYXQiOjE2NjgzNDQxNjJ9.pPACQ94iMxuVhhu48wU7NoH_KjrFnJu3lPC4qK5gzsA",
    "access_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwaG9uZU9ySXBuIjoiKzM4MDUwMTM5NzgzMCIsInN1YiI6Ik1CQSIsImlzcyI6Ik1CQV9Jc3N1ZXIiLCJleHAiOjE2NjgzNDc3NjIsImlhdCI6MTY2ODM0NDE2Mn0.Iwx3fBUbI7zXdc6TgYT68Ngp4VL4opGqc0sXGym-b4s"
}
```

**Response body example in case when such refresh token is not found in DB:**

```
{
    "error": "user with such refresh-token was not found"
}
```

---

## Log out
**Path:** `http://sbbankingapp-env.eba-3teik5g7.eu-central-1.elasticbeanstalk.com/refresh_token/logout`

**Method:** Post

**Format:** JSON

**Raw body example:**

```
{
    "refreshToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJNQkEiLCJwaG9uZU51bWJlciI6IiszODA1MDAzNTEwMzciLCJpcG4iOiIwMjkxMTExODA5IiwiaXNzIjoiTUJBX0lzc3VlciIsImV4cCI6MTY3MDkzNjAxOSwiaWF0IjoxNjY4MzQ0MDE5fQ.47FW4SGRVQx9bRsvDNsmHFUpuwiP8NhzQdc83QMiCq4"
}
```

**Response body example:**

```
{
    "success": "logout"
}
```

---

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
