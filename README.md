# Brick_Destroy
HIGHLIGHTS
Maintenance:
- Basic maintenance (renamed variables and methods, encapsulation, organised classes into packages)
- Adhering to SOLID
- Adhereing to MVC
- Conversion to JavaFX
- Applied Factory pattern on creation of Level
- Done some code smelling 

Additions:
- Added new levels
- Added info menu
- Added score storing system
- Permanent score storage
- New mechanic: player can use the sides of the player to slightly change the path of the ball, 
allows for tactical playing (now, it's skill based, instead of luck based)
______________________________________________________________________________________________

Basic maintenance:
- Renamed vars and methods
Example: 
Methods: findImpacts() in Wall renamed to detectCollision() in GameplayController.
variables: ballPoint in PLayer was renamed to playerMidpoint because it represent the midpoint of the player.

- Encapsulation
All the attributes of the classes in the Model were set to private, 

- Classes organised into packages.
Example: 
Brick type objects (i.e. ClayBrick, CementBrick and SteelBrick) organised into package Brick.
Ball type objects organised into package Ball.


SOLID:
The structure of the class and functionality was changed and moved from classes to either new classes or exisitng class.

- Single responsible
Originally - Responsible of Wall: Managing interactions between objects (collision detection), generating Level, holding the state of a level.
Seperated into - GameplayController, LevelFactory and Level

- Open-closed
Abstractions and interfaces were introduced.
Abstract - Ball and Bricks to represent their subtypes.
Interfaces - interface Playable, Manager, Buildable, Controllable for object Player, HighScoreManager, Level and GameplayController.

- Dependency inversion
Interfaces were introduced to help interaction of high level and low level modules.
New interface - Playable for Player, Manager for HighScoreManager, Buildable for Level and Controllable for GameplayController.

Adhering to MVC:
The structure and functionality of the classes in the project was changed so that it adheres to the MVC pattern.
Model - Ball, Brick, Player, HighScoreManager, Level, LevelFactory
Controller - GameplayController and FXML controllers
View - FXML and BrickDestroyMain

Conversion to JavaFX:
Components of Swing were changed to JavaFX components.
Now the game runs on JavaFX instead on Swing.

Factory pattern on Level creation:
Level creation done be LevelFactory
Factory - LevelFactory
Product - Level

Code smelling:
Constant number - set to static final
Moved funcitonalities into methods and their name represents their responsible.
Names are renamed to best represent their function and properties.
Methods were moved from classes to class to reduce dependency.

Additions:

New levels:
- New levels were added with different pattern of bricks, 
and new levels can be introduced while existing level can be changed in LevelFactory.

Info menu:
A menu info was added to display instructions and controls for the player.

Scoring system:
Players earn different worth of score by the type of bricks they destroyed.
Clay Brick -  50 points
Cement Brick - 70 points
Steel Brick - 90 points
List of high scores and the players score is displayed when completing a level or lost all the balls.
If the players has a new high score among the list of high scores, the new record will be placed among the list of high scores,
and the player can type their names. 

Permenant high score storage:
- High scores are permenantly stored as a file when exiting a game.
- HighScoreManager object is used to do manage high score, saving and loading from files.

New mechanic:
- The player can now use the sides of the Player object to slightly change the path of the ball.
This allows the player to play with skills, instead of using luck.
- The closer the ball is to the edge when hitting the player, the larger the change of the path.
- Closer to the left side, and it changes the direction of the ball by counter-clockwise
- Closer to the right side, and it changes the direction of the ball by clockwise.
