# My Vault

## Analysis

This problem provides us with an [encryption code](./encrypt.py) and three encrypted messages: [encrypted_friend1.txt](./encrypted_friend1.txt), [encrypted_friend2.txt](./encrypted_friend2.txt), and [encrypted_friend3](./encrypted_friend3.txt). In the problem description, the author states that his encryption message is very secure, specially because he uses a *very clever* format for generating the keys, which consists of the year when he met that particular friend, as well as the country. As an example, he states that if he had met a friend in 2013 in Brazil, he would use the key `2013brazil` to encrypt his messages with said friend.

## Solution

We can easily spot that his method for generating keys is flawed, since there is a very limited number of countries and years. We can use a hardcoded list of countries to solve this problem. We could have an issue where the country is capitalized, but the example shows us that the country name is written in lowercase, so we don't need to worry about that. 

For the years, there is a very limited number of plausible years to be used here, specially because the author states that it was the date they met their friends, so it cannot be a year in the future and will (very likely) not be more than 100 years in the past. Generating all the likely years and combining them with the countries in the list, we can attempt to decrypt the messages one by one.

For friend 1, we will find the key `2005russia`, and the message:

```text
Ossama: Are you ready for the next step in the plan?
Mohammed: Yes, everything is set. But we need to make sure no one knows about these details.
Ossama: Of course, we can't afford for anyone to uncover our identities.
Mohammed: We're at a critical stage, but if we succeed, the reward will be massive.
Ossama: That's what we're hoping for. But we must be cautious at every step, and here is your first part of our plan 0xL4ugh{sad!_
```

For friend 2, we will find the key `2016qatar`, and the message:

```text
Ossama: Do you have any updates on the project?
Khalid: Yes, but I want to remind you to be careful. There are some eyes watching us.
Ossama: Don't worry, the whole team is aware of the situation.
Khalid: But there's something unexpected—there might be leaks from inside the organization.
Ossama: Then we need to change our plans. We can't take any risks, here is the your part _no_easy_challs
Khalid: I'll secure all the channels. Don't worry.
```

Finally, for friend 3, we will find the key `1980turkey`, and the message:

```text
Ossama: Do you remember the secret meeting we had last week?
Ali: Yes, but I need to remind you that any leaks could have severe consequences.
Ossama: We know, that's why I don't allow anyone access to sensitive information, here is your part anymore}
Ali: I hope we have enough time to complete everything before they find out what we're doing.
Ossama: We'll finish everything on time, we just need to work together and stay cautious.
```

Combining the information in the messages, we will get the flag:

```
Friend 1: 0xL4ugh{sad!_
Friend 2: _no_easy_challs
Friend 3: anymore}

FLAG: xL4ugh{sad!_no_easy_challs_anymore}
```

It's worth pointing out that the position of the underscore (`_`) on the part given by friend 2 is incorrect, but given the pattern where each word is separated by an underscore, it's easy to figure out that it must be placed in the end of the part (`no_easy_challs_`).