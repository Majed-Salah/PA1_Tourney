# Programming Assignment 1 : Majed Salah, Sammy El-Sherif

## PA Description
Implement a system to manage the matches and lineups for the upcoming Fifa Worldcup Qatar 2022.
Your system will rely on a Client/Server architecture allowing admins to load the matches and players
in the lineup for each game and record the matches’ scores. Further, there will be an application for
the general public to query the lineups and matches’ results.

---

## List of incomplete things:
    - Load and save functions implemented but not linked to buttons on JavaFX
    - Not able to name tournament as a whole
    - check for if more than 11 players added to line up not implemented, can add just no count check
    - For user client side:
        - button needs to be clicked to load in choice boxes before anything is in them
        - upcoming matches not implemented
        - Match LineUp for user side not implemented - linked to issue of empty box before submit


## pa1_tourney Package : NOTE IF FULLY FUNCTIONAL OR NOT
This package consists of applications and their controllers so that we can launch an application and control our 


### HelloController
The hello controllers responsibility is to take in the user's input from java jx and sends that data to the serve
in line with the protocol that we have defined.

### UserController
The controller focuses on the general user specifically in this case, providing GUI functionality for the person to view
some of the tournament structure as well as match and team information. They do not however have the ability to edit anything.
---

## Tournament Package : All Classes Fully Functional
The tournament package is centered around the objects that make up a tournament. These objects have a form of hierarchy
to them that starts with players at the bottom since without them, there is no role for other types of participants.

To put it shortly:
Without the players, there are no LineUps.
Without a LineUp, there can't be a team.
Without two teams, there is no reason for a Referee.
Without Referees, there cannot be an official Match.
Without Matches, there cant be a tournament.

All the different types of things I just listed are the general basis of the foundation of classes that make up this
project.

### Tournament Class
The tournament class wraps all the different parts together with functions that leverage all the functions
in the remaining classes below. The main purpose of the functions in this class are to get and set tournament
variables by utilizing the functions within all the objects that are used in a tournament. 

### Match Class
A Match consists of a Date, Time, and Pair of Teams. Matches are correlated with a LineUp, which contains all the
players that are participating in the Match (11/35 in squad to be specific). We also have functions to add players to 
a team, add Referee's to a match, update the match score, and a few other basic getter and setter functions.

### LineUp Class
The LineUp class, which is utilized by the Match Class above, is one of the simplest classes. Its entire functionality
is storing a list of players, a single team, and some functions to get the team and players or add a player. LineUp's
are matched up against each other within a Match so that two teams of 11 can play against each other.

### Team Class
The Team class is what is referenced for a LineUp to be made. A Team instance has a team name, country, and a list of players
but this time it is a list of 35 since it is the teams entire squad. A Squad is every player, including subs and say 1st 
and 2nd string, but when a match is planned there are only 11 selected out of this group to play. In this class you get 
Add a player, which must be done before trying to assign player in a LineUp, and get all the previous attributes I have 
mentioned (country, team name, squad).

### Country Class
The country class only has one variable, country name. It only has one method, other than the overridden toString, and 
that method is gets the country name. This is used by the Match Class to ensure that the country of a referee is not 
the same as the country of one of the teams in a match to ensure fairness. It is also of course used to keep a record of 
the matches as well.

### Player Class
The Player class contains variables relating to a players characteristics like name, age, height and weight. The class 
only has getter functions for the attributes, and that is all that is needed in this class because we are simple keeping
a record of players for a Team and LineUp. 

### Referee Class
The Referee class has variables for a Referee's name and country of origin. As mentioned in the Country class description,
this country variable is used that we can ensure that a Referee isnt from the same country as a team in a match. And the
referee name variable is simply to keep a record of referees.

---



## Server Package : NOTE IF FULLY FUNCTIONAL OR NOT
The Server class utilizes client workers to simultaniously support multiple admin clients at the same time (and user) since
there may be multiple users who want to update or view the tournament structure at the same time. The communication between
the server and client is text-based, with an established protocol between the GUI controller and the server. Strings need
to be passed and objects are determined by identifiers rather than passing the object as a whole due to the limitations of
the text-based protocol.

### ClientWorker

### Server




