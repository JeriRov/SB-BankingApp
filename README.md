# SpringBoot banking application.

This project was created for the Mobile Banking App.

## Project properties

- **SpringBoot:** 2.7.4
- **Java:** 17

---

## Registration

<details>

**Path:** `http://sbbankingapp-env.eba-3teik5g7.eu-central-1.elasticbeanstalk.com/register`

**Method:** Post

**Format:** JSON

**Raw body example:**

`Old passport format: 2 letters and 6 numbers`
```json
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
```json
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
```json
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

```json
{
    "access_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwaG9uZU9ySXBuIjoiMDI5MTExMTgwOSIsInN1YiI6Ik1CQSIsImlzcyI6Ik1CQV9Jc3N1ZXIiLCJleHAiOjE2NjgzNDc1NTIsImlhdCI6MTY2ODM0Mzk1Mn0.P0HA4LwqqhZyW9bexmRNcYipMYPbzRnrAq_ZogOXDvk",
    "refresh_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJNQkEiLCJwaG9uZU51bWJlciI6IiszODA1MDAzNTEwMzciLCJpcG4iOiIwMjkxMTExODA5IiwiaXNzIjoiTUJBX0lzc3VlciIsImV4cCI6MTY3MDkzNTk1MiwiaWF0IjoxNjY4MzQzOTUyfQ.6X8TuQYIsU2n0i-CCv8lx7Tpq8O0AnswcZaUgwdlyDw",
    "access_expire_date": "14-11-2022 22:27:30+02:00",
    "refresh_expire_date": "14-12-2022 21:27:30+02:00"
}
```

**Response body example in case when not all data is received:**

```json
{
    "error": "Client-data has null fields"
}
```

**Response body example in case when such ipn already exists:**

```json
{
    "error": "User with such ipn already exists."
}
```

**Response body example in case when such phone-number already exists:**

```json
{
    "error": "User with such phone-number already exists."
}
```

**Response body example in case when such passport-number already exists:**

```json
{
    "error": "User with such passport-number already exists."
}
```

**Response body example in case when ipn length do not equal 10:**

```json
{
    "error": "Bad ipn"
}
```

**Response body example in case when phone-number is in incorrect format:**

```json
{
    "error": "Bad phone number"
}
```

**Response body example in case when passport-number is in incorrect format:**

```json
{
    "error": "Bad passport number"
}
```
</details>


## Login

<details>

**Path:** `http://sbbankingapp-env.eba-3teik5g7.eu-central-1.elasticbeanstalk.com/login`

**Method:** Post

**Format:** JSON

**Raw body example:**

Through a **phone number**:

```json
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
"error": "Bad phone number"

Through an **identification code**:

```json
{
    "phoneNumber": "5291807314",
    "password": "examplePassword"
}
```
Validation:
```
checks ipn length
```
return "error": "Bad ipn"

**Response body example:**

```json
{
    "refresh_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJNQkEiLCJwaG9uZU51bWJlciI6IiszODA1MDEzOTc4MzAiLCJpcG4iOiIxMjM4NTAwMDAiLCJpc3MiOiJNQkFfSXNzdWVyIiwiZXhwIjoxNjcwOTM2MTYyLCJpYXQiOjE2NjgzNDQxNjJ9.pPACQ94iMxuVhhu48wU7NoH_KjrFnJu3lPC4qK5gzsA",
    "access_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwaG9uZU9ySXBuIjoiKzM4MDUwMTM5NzgzMCIsInN1YiI6Ik1CQSIsImlzcyI6Ik1CQV9Jc3N1ZXIiLCJleHAiOjE2NjgzNDc3NjIsImlhdCI6MTY2ODM0NDE2Mn0.Iwx3fBUbI7zXdc6TgYT68Ngp4VL4opGqc0sXGym-b4s",
    "access_expire_date": "14-11-2022 22:27:30+02:00",
    "refresh_expire_date": "14-12-2022 21:27:30+02:00"
}
```
</details>


## Work with jwt

<details>

**Headers**

    Key: Authorization
    Value: Bearer eyJ0eXAiOiJ141QiLCJhbGciOiJIUzI1NiJ9.eyJwaG9uZU9ySXBuIjoiNTI5MTgwNzMxMSIsInN1YiI6Ik1CQSIsImlzcyI6Ik1CQV9Jc3N1ZXIiLCJleHAiOjE2NjY1MjkzNzQsI4lhdCI6MTY2NjUyNTc3NH0.NkgoKCYJrJXXT23MH0SFeHBTsUJsBOl2DENSY_NRc94

---

## Refresh Token
**Path:** `http://sbbankingapp-env.eba-3teik5g7.eu-central-1.elasticbeanstalk.com/refresh_token`

**Method:** Post

**Format:** JSON

**Raw body example:**

```json
{
    "refreshToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJNQkEiLCJwaG9uZU51bWJlciI6IiszODA1MDAzNTEwMzciLCJpcG4iOiIwMjkxMTExODA5IiwiaXNzIjoiTUJBX0lzc3VlciIsImV4cCI6MTY3MDkzNjAxOSwiaWF0IjoxNjY4MzQ0MDE5fQ.47FW4SGRVQx9bRsvDNsmHFUpuwiP8NhzQdc83QMiCq4"
}
```

**Response body example:**

```json
{
    "refresh_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJNQkEiLCJwaG9uZU51bWJlciI6IiszODA1MDEzOTc4MzAiLCJpcG4iOiIxMjM4NTAwMDAiLCJpc3MiOiJNQkFfSXNzdWVyIiwiZXhwIjoxNjcwOTM2MTYyLCJpYXQiOjE2NjgzNDQxNjJ9.pPACQ94iMxuVhhu48wU7NoH_KjrFnJu3lPC4qK5gzsA",
    "access_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwaG9uZU9ySXBuIjoiKzM4MDUwMTM5NzgzMCIsInN1YiI6Ik1CQSIsImlzcyI6Ik1CQV9Jc3N1ZXIiLCJleHAiOjE2NjgzNDc3NjIsImlhdCI6MTY2ODM0NDE2Mn0.Iwx3fBUbI7zXdc6TgYT68Ngp4VL4opGqc0sXGym-b4s",
    "access_expire_date": "14-11-2022 22:27:30+02:00",
    "refresh_expire_date": "14-12-2022 21:27:30+02:00"
}
```

**Response body example in case when such refresh token is not found in DB:**

```json
{
    "error": "User with such refresh-token was not found"
}
```
</details>

## Log out

<details>

**Path:** `http://sbbankingapp-env.eba-3teik5g7.eu-central-1.elasticbeanstalk.com/refresh_token/logout`

**Method:** Post

**Format:** JSON

**Raw body example:**

```json
{
    "refreshToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJNQkEiLCJwaG9uZU51bWJlciI6IiszODA1MDAzNTEwMzciLCJpcG4iOiIwMjkxMTExODA5IiwiaXNzIjoiTUJBX0lzc3VlciIsImV4cCI6MTY3MDkzNjAxOSwiaWF0IjoxNjY4MzQ0MDE5fQ.47FW4SGRVQx9bRsvDNsmHFUpuwiP8NhzQdc83QMiCq4"
}
```

**Response body example:**

```json
{
    "success": "logout"
}
```
</details>

## Get user info

<details>

**Method:** Get

**Path:** `http://sbbankingapp-env.eba-3teik5g7.eu-central-1.elasticbeanstalk.com/user/info`

**Headers**

    Key: Authorization
    Value: Bearer eyJ0eXAiOiJ141QiLCJhbGciOiJIUzI1NiJ9.eyJwaG9uZU9ySXBuIjoiNTI5MTgwNzMxMSIsInN1YiI6Ik1CQSIsImlzcyI6Ik1CQV9Jc3N1ZXIiLCJleHAiOjE2NjY1MjkzNzQsI4lhdCI6MTY2NjUyNTc3NH0.NkgoKCYJrJXXT23MH0SFeHBTsUJsBOl2DENSY_NRc94

**Response body example:**

```json
{
    "firstName": "Petro",
    "lastName": "Mostavchuk"
}
```
</details>

## Get all cards 

<details>

**Method:** Get

**Path:** `http://sbbankingapp-env.eba-3teik5g7.eu-central-1.elasticbeanstalk.com/user/cards/`

**Headers**

    Key: Authorization
    Value: Bearer eyJ0eXAiOiJ141QiLCJhbGciOiJIUzI1NiJ9.eyJwaG9uZU9ySXBuIjoiNTI5MTgwNzMxMSIsInN1YiI6Ik1CQSIsImlzcyI6Ik1CQV9Jc3N1ZXIiLCJleHAiOjE2NjY1MjkzNzQsI4lhdCI6MTY2NjUyNTc3NH0.NkgoKCYJrJXXT23MH0SFeHBTsUJsBOl2DENSY_NRc94

**Response body examples:**

```json
{
    "ok": [
        {
            "cardNumber": "4152071310000053",
            "creationTime": "2022-11-14T15:15:20.497814+02:00",
            "expirationTime": "2026-11-14T15:15:20.498811+02:00",
            "cvvCode": "087",
            "pinCode": "7468",
            "cardType": {
                "id": 2,
                "name": "Debit"
            },
            "currencyName": "UAH",
            "providerEntity": {
                "id": 1,
                "providerName": "Mastercard",
                "code": "51520713"
            },
            "sum": 0,
            "sumLimit": 0,
            "isBlocked": false
        },
        {
            "cardNumber": "4152071310000061",
            "creationTime": "2022-11-14T15:34:22.858934+02:00",
            "expirationTime": "2026-11-14T15:34:22.858934+02:00",
            "cvvCode": "806",
            "pinCode": "3768",
            "cardType": {
                "id": 2,
                "name": "Debit"
            },
            "currencyName": "UAH",
            "providerEntity": {
                "id": 1,
                "providerName": "Mastercard",
                "code": "51520713"
            },
            "sum": 0,
            "sumLimit": 0,
            "isBlocked": false
        }
    ]
}
```
</details>

## Get card by number

<details>

**Method:** Get

**Path:** `http://sbbankingapp-env.eba-3teik5g7.eu-central-1.elasticbeanstalk.com/user/cards/{cardNumber}`

**Headers**

    Key: Authorization
    Value: Bearer eyJ0eXAiOiJ141QiLCJhbGciOiJIUzI1NiJ9.eyJwaG9uZU9ySXBuIjoiNTI5MTgwNzMxMSIsInN1YiI6Ik1CQSIsImlzcyI6Ik1CQV9Jc3N1ZXIiLCJleHAiOjE2NjY1MjkzNzQsI4lhdCI6MTY2NjUyNTc3NH0.NkgoKCYJrJXXT23MH0SFeHBTsUJsBOl2DENSY_NRc94

**Response body examples:**

```json
{
    "ok": {
        "cardNumber": "4152071310000053",
        "creationTime": "2022-11-14T15:15:20.497814+02:00",
        "expirationTime": "2026-11-14T15:15:20.498811+02:00",
        "cvvCode": "087",
        "pinCode": "7468",
        "cardType": {
            "id": 2,
            "name": "Debit"
        },
        "currencyName": "UAH",
        "providerEntity": {
            "id": 1,
            "providerName": "Mastercard",
            "code": "51520713"
        },
        "sum": 0,
        "sumLimit": 0,
        "isBlocked": false
    }
}
```
</details>



## Create new card

<details>

**Path:** `http://sbbankingapp-env.eba-3teik5g7.eu-central-1.elasticbeanstalk.com/user/cards/new`

**Method:** Post

**Format:** JSON

**Raw body example:**

```json
{
    "provider": "Mastercard",
    "type": "Debit",
    "currency": "UAH"
}
```

**Headers**

    Key: Authorization
    Value: Bearer eyJ0eXAiOiJ141QiLCJhbGciOiJIUzI1NiJ9.eyJwaG9uZU9ySXBuIjoiNTI5MTgwNzMxMSIsInN1YiI6Ik1CQSIsImlzcyI6Ik1CQV9Jc3N1ZXIiLCJleHAiOjE2NjY1MjkzNzQsI4lhdCI6MTY2NjUyNTc3NH0.NkgoKCYJrJXXT23MH0SFeHBTsUJsBOl2DENSY_NRc94

**Response body examples:**

```json
{
    "new card": "4152071310000095"
}
```

```json
{
    "error": "Invalid data"
}
```

</details>

## Change pin code

<details>

**Method:** Get

**Path:** `http://sbbankingapp-env.eba-3teik5g7.eu-central-1.elasticbeanstalk.com/user/cards/change/pin/{cardNumber}`

**Headers**

    Key: Authorization
    Value: Bearer eyJ0eXAiOiJ141QiLCJhbGciOiJIUzI1NiJ9.eyJwaG9uZU9ySXBuIjoiNTI5MTgwNzMxMSIsInN1YiI6Ik1CQSIsImlzcyI6Ik1CQV9Jc3N1ZXIiLCJleHAiOjE2NjY1MjkzNzQsI4lhdCI6MTY2NjUyNTc3NH0.NkgoKCYJrJXXT23MH0SFeHBTsUJsBOl2DENSY_NRc94

**Response body examples:**

```json
{
    "ok": "5964"
}
```

```json
{
    "error": "Wrong number"
}
```

```json
{
    "error": "Wrong owner"
}
```
</details>

## Do transactions

<details>

**Path:** `http://sbbankingapp-env.eba-3teik5g7.eu-central-1.elasticbeanstalk.com/user/transactions/new`

**Method:** Post

**Headers**

    Key: Authorization
    Value: Bearer eyJ0eXAiOiJ141QiLCJhbGciOiJIUzI1NiJ9.eyJwaG9uZU9ySXBuIjoiNTI5MTgwNzMxMSIsInN1YiI6Ik1CQSIsImlzcyI6Ik1CQV9Jc3N1ZXIiLCJleHAiOjE2NjY1MjkzNzQsI4lhdCI6MTY2NjUyNTc3NH0.NkgoKCYJrJXXT23MH0SFeHBTsUJsBOl2DENSY_NRc94

**Format:** JSON

**Raw body example:**

```json
{
    "senderCardNumber": "5168324249821302",
    "receiverCardNumber": "5168758323722993",
    "receiverName": "Igor",
    "sum": 150,
    "purpose": "sending 150$"
}
```

**Response body examples:**

```json
{
    "message": "Success"
}
```

```json
{
    "error": "Sender do not have that card"
}
```

```json
{
    "error": "Receiver card was not found"
}
```

```json
{
    "error": "Limit is lower than specified sum"
}
```

```json
{
    "error": "Not enough money on the card"
}
```

```json
{
    "error": "Transfer data is not valid"
}
```

</details>


## Get all user transactions

<details>

**Path:** `http://sbbankingapp-env.eba-3teik5g7.eu-central-1.elasticbeanstalk.com/user/transactions/all`

**Method:** Get

**Headers**

    Key: Authorization
    Value: Bearer eyJ0eXAiOiJ141QiLCJhbGciOiJIUzI1NiJ9.eyJwaG9uZU9ySXBuIjoiNTI5MTgwNzMxMSIsInN1YiI6Ik1CQSIsImlzcyI6Ik1CQV9Jc3N1ZXIiLCJleHAiOjE2NjY1MjkzNzQsI4lhdCI6MTY2NjUyNTc3NH0.NkgoKCYJrJXXT23MH0SFeHBTsUJsBOl2DENSY_NRc94

**Format:** JSON

**Response body example:**

```json
[
    {
        "id": 1,
        "provider": "Mastercard",
        "currency": "UAH",
        "time": "2023-10-16T00:00:00+03:00",
        "sum": 500,
        "profit": true
    },
    {
        "id": 6,
        "provider": "Mastercard",
        "currency": "EUR",
        "time": "2022-11-15T13:11:10.504181+02:00",
        "sum": 35,
        "profit": false
    }
]
```
</details>
