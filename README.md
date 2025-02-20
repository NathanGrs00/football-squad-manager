<p align="center">
  <img src="https://github.com/user-attachments/assets/cb4584c7-3c9d-46ff-8f4a-b0acd1ef56d2" width="400" height="400"/>
</p>

# Football Squad Manager Application

This application is a tool designed to manage and organize player selections. You can add, edit, delete and view players and selections.

## Getting Started

These instructions will get you to run the application in a local environment, using the database provided.

### Prerequisites

The things you need before installing the software.

* Java Development Kit version 23.0.2 (JDK 23)
* XAMPP, MAMPP or a different way to run an SQL-file locally.

### Installation

A step by step guide that will tell you how to get the development environment up and running.

```
Download the repository. Alternatively you can clone the repository and open it in your favourite IDE.
```
```
Locate the 'f_squad_manager.sql' file in the project folder. Import this file in localhost/phpmyadmin if you're using XAMPP.
```
```
Locate the .jar file in football-squad-manager-bp2 > out > artifacts > football_squad_manager_bp2_jar
```
```
Run the .jar file to launch the application.
```

## Features

Player Management:

```
Add new players
Edit player details
Delete players
View all players
```
Selection Management:

```
Create new selections
Choose out of a list of formations
Assign players to positions
Modify selections
Clear the selection
```

## Usage
Open the application.

Enter the username 'manager' and the password 'admin213' to log into the manager's account.

![Image](https://github.com/user-attachments/assets/2da8eb1e-8f8b-4928-8b5d-da692e933321)

If you want to login to the physiotherapist account, use 'physio' for the username and 'cure213' for the password.

If you want to login to the assistant manager account, use 'assistant' for the username and 'help213' for the password.

If you click on login, but nothing happens, make sure you imported the SQL file into a new database called 'f_squad_manager'. Also make sure MySQL is running.

![Image](https://github.com/user-attachments/assets/5aef4e8f-b240-49d0-a2e1-0642cc77eca5)

Use the "Players" section to add, edit, delete, and view all players.

![Image](https://github.com/user-attachments/assets/558f5943-9ce0-44a1-9852-6ab0e8ccf8e6)

Use the "New selection" section to create a new selection and specify a formation.

![Image](https://github.com/user-attachments/assets/6e57347f-91d7-47ac-bab7-1e8180b01248)

Assign players to each position within the selected formation.

The application automatically saves changes made to the selection.

## Technologies Used

* JavaFX
* CSS
* SQL queries

## Contributing
Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the repository.

2. Create a new branch:

```

  git checkout -b feature-name

```

3. Make your changes and commit:

```

  git commit -m "Add new feature"

```
4. Push to your fork:
```

  git push origin feature-name

```
5. Open a pull request.

## Roadmap

- [x] Function to add, edit, view and delete player.
- [x] Function to add players in selection.
- [ ] Give suggestion for formation based on the players in the database.
- [ ] Put the players with the highest proficiency first in the list of available players for a spot.
- [ ] Ability to add more formations.
- [ ] Make environments with less permissions:
    - [ ] Assistant-manager.
    - [ ] Physiotherapists.

See the [open issues](https://github.com/NathanGRS00/football-squad-manager-bp2/issues) for a full list of proposed features (and known issues).

## Contact

Nathan - [@LinkedIn](https://www.linkedin.com/in/nathangeers/) - nathangeers2@gmail.com

Project Link: [https://github.com/NathanGrs00/football-squad-manager-bp2](https://github.com/NathanGrs00/football-squad-manager-bp2)

## License

Distributed under the MIT license. See ```LICENSE.md``` for more information.





