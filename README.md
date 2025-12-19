# APU CAMPUS KIOSK SYSTEM

Simple terminal-based application for the APU campus drinks and snacks kiosk.
Students and lecturers can register, log in, browse the menu, create and customize orders; admins can manage products, users, and view system stats.

## Run

**From Intellij Idea :**
run `Main.java`.

*From the terminal :*
```bash
# from project root, after compiling classes into src
java -cp src com.AppUI
```


## Features

- User side
    - Register/login as Student or Lecturer
    - View menu
    - Create orders and customize drinks/sandwiches
    - Automatic staff discount for lecturers
    - Balance updates after payment
- Admin side
    - Add, update, remove products; toggle availability
    - View users, pending orders, transaction logs
    - View system statistics


## Tech / Design

- Java console application
- Uses Observer, Strategy, Factory, and Singleton patterns across the kiosk, menu, pricing, and product subsystems.




