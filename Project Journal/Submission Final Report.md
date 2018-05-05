# Submission Final Report

## The Topic of your team's project
Broken Brick

It’s a brick breaking game with multiple different ball powers. Endless random generated stages, countless different featured balls. Waiting for you to explore.

## The names of each team member
- Jiaqi Qin
- Hunter Hsieh
- Minglei Lu
- Tsung-Min Huang
- JuYun Cheng

## A summary of areas of contributions (for each team member)

### Jiaqi Qin

#### Contributions
As a member in the team, I made following contributions to the project:
1. Design the architecture of the game, and migrate the initial Greenfoot project into IntelliJ.
2. Design and implement Dynamic Configuration module, load Properties / JSON / XML configuration file at runtime. Multiple data types are supported in the module, including primitive types and collections.
3. Develop automatical generation of unlimited game scene, with multiple types of bricks, in different level of difficulty. Implement scene switcher when completing a level.
4. Develop Progress Manager, save game progress into local storage and load previous checkpoints to resume game.
5. Optimize memory consumption and facilitate runtime performance by reuse data object via pooling.

#### Design Patterns

1. Factory Method Pattern

For loading and saving configuration file, I applied Apache Commons 2.0 framework to process JSON or Properties file. To make it more flexible and extendible, I define an internface ```Config``` to contract the general Get and Put method. ```ConfigFactory``` determine the requested file name and fetch differnt configuration managers, in this case, ```JsonConfig``` and ```PropertiesConfig```. Each configuration manager extends  ```AbstractConfig```, which is an abstract class, defining general functions in all configuration manager.

2. Momento

I applied Momento to save and restore game progress. ```GameState``` abstracts all game progress. ```GameCheckPoint``` is a momento, containing a ```GameState``` object with a certain timestamp. ```GameProgressManager``` is used to save and load a certain ```GameCheckPoint```. When persisting game progress into local file system, Jackson framework is used to convert JSON and Java object mutually.


### Hunter Hsieh


#### Contributions

I am responsible for the logic of user interface objects, and support settings from dynamic configuration file, the following are the list of my contributions:
Speed up ball power and power square
Speed down ball power and power square
Fireball power and power square
Pyroblast power and power square
Multiball power and power square
Golden egg power and power square
Wall collision
Stick function
Dynamic configuration integration

These functionalities are the spirit of our game. Whenever the ball hit the wall or the stick, the ball should do a reflection. Moreover, when the ball hit a brick, it may randomly generate a power square which will slowly fall down to the bottom of the screen. If the stick touches the power square, then the ball will transformed into its power mode. Here, different power square represent different ball ability. Take fireball power as an example, the fireball ability can break every kind of brick, no matter how many hits should the brick get before it break. An interesting power is the golden egg power. It will turn every power square into multiball power square, which will eventually help you create hundreds of ball and you will complete the stage easily. Of course, the golden egg won’t appear so frequently, only you are lucky enough to get it.

Another part which I am in charge of is to support dynamic configuration settings. I should make sure that all configurations are written in the way that the user can modified easily in a configuration file.

#### Design Patterns

In my implementation, I used three design patterns in two different functionalities. The functionalities and design patterns are listed below:

Ball power: decorator & strategy

For the ball power, attributes of the power should be added onto the normal ball because it still need the basic ball moving and reflecting functions. Therefore, decorator pattern is used here to do the trick. On the other hand, some power should have different strategies for the basic moving and bouncing functions. For example, the fireball won’t reflect after hitting a brick, instead, it will break through the brick directly. Hence, I used a strategy pattern here.

Power square: simple factory

For power square, this game will contains several similar but different power squares. It is not a good way to use a pile of if conditions for creating a specific power square. This type of problem lead me to the simple factory pattern. By using this pattern, it easy to just pass an enumeration to the factory and get the power object that I expected.

### Minglei Lu

#### Contributions
I'm in charge of two main use cases. The first one is a game feature and it enables the ball UI to change when score changes (reaches a certain level). The second use case is to re-design main menu and any other world views that needs to display buttons for any redirection features. 

The first feature enables the ball UI to change to a series of interesting ball images such as baseball, football, pokemon ball, Captain American's shield and more. The ball will change to these images when a player reaches 150/300/400/.etc points. It encourages player to reach higher score and keeps player attracted and surprised to see what's the next new image for their wrecking ball.

The second feature is a technical user story. It enables a new functionality -- buttons. Button will take some actions as player press them. We have 7 button objects in total in this project, they all do very different actions. For example, the START button starts the game and exit the main menu. The SAVE button saves the current state of the whole game in order to load the game later if the player decides to. As we can see, buttons fulfill very distinguished functionality and player only needs to simply click them then they execute a series of commands. So our team decide to implement it as command design pattern.


#### Design Patterns
In the part of the system I’m responsible for, I decide to implement two design patterns to fulfill my use cases. They are Observer and command design patterns.

Observer: 
Observer pattern is for the class called ```ContextController``` in our system. It modifies every single ball in our game. Since I need to change all the balls’ UI image when the score reaches a certain point, I decided to make this class an Observer for the class ```ScoreBoard```.  Everytime when score changes, it will notify all of its observers (there are other classes are observers of scoreBoard too.) The ```ContextController``` will be notified and it will update the current default ball UI image. Thus, every ball created after will have a new image and every existing ball will change to new image immediately.

Command: 
Command pattern is used to implement the whole functionality of buttons. Then the whole button functionality is able to be encapsulated as an interface for the client side to customize whatever events they want to be executed when a button is clicked. Without the command design pattern, when client side needs a certain button, they will have to create a new object of ```MenuButtons``` and implement all the executable functions in the existing codes, which is not convenient. In total, the command design pattern enables client side to customize their own buttons. 

### Tsung-Min Huang

#### Contributions

Encapsulate Greenfoot mouse event to abstract controller.  Implement two concrete controllers: ```MouseController``` and ```KeyBoardController``` extend from the abstract controller. Wrap the low-level controller event such as mouse movement and keyboard input convert to high-level controller event. This implementation loose couple user input event from Greenfoot mosue and keyboard input can support different type user input.

#### Design Patterns

Using Observer pattern to implement the controller. Define two interfaces ```ControllerSubject``` and ```ControllerObserver``` interface for subject and observer object. To support new controller just need implement ```ControllerSubject``` and attach ```ControllerObserver``` to it. Decouple the controller from Greenfoot mouse and keyboard event.

### JuYun Cheng

#### Contributions

I was responsible for implementing two features in this game, music player which can control the background music and highscore board that can show the top 10 scores in history.  
First, Music Player can control the background music and turn up/down the volume. Gamer can press the button on the left of the game screen to play/stop the music while playing the game. The volume buttons will show the latest value of volume when gamer press it. 
 
Second, HighScore Board is designed for showing the high scores while gamer press the button ”scores” in the main page, and it will dynamically show the latest top 10 scores. At the same time, high score board saves those scores in a local file. When the game starts, it will read the same file to fetch the previous scores. Previous scores will store in an array and compete with the current scores while the game is over. 

#### Design Patterns

Observer pattern: 
Both music player and highscore board are implemented with Observer pattern. Since Music player needs to track the volume each time when it changes and highscore board needs to track the score each time when the score increases. Therefore, scoreboard that record the current scores, and the highscore board are all in the ```score observer``` list. While gamer presses the volume button, the volume will increase or decrease the quantity of volume by 5. After volume changes, it will notify "volume up" class and "volume down" class, and it will show the value of volume on the screen. Therefore, music player, volume up and volume down classes are all in the ```volume observer``` list.

State pattern:
 In the beginning of game, the music player is in the ```play state```, so it plays the background music and loop it. If gamers press the music player button, it will change to the ```stop state``` and the background music will stop immediately. Hence, music player will switch between these two states.

## Links
- Link to your team's GitHub Repo

[https://github.com/nguyensjsu/team202-loser-land-inc](https://github.com/nguyensjsu/team202-loser-land-inc)

- GitHub Account for transfer: ```jiaqiqin```

[https://github.com/jiaqiqin/](https://github.com/jiaqiqin/)

- Link to your team's Project Board (on GitHub)

[https://github.com/nguyensjsu/team202-loser-land-inc/projects/1](https://github.com/nguyensjsu/team202-loser-land-inc/projects/1)

- Link to your team's Project Journal (on GitHub)

[https://github.com/nguyensjsu/team202-loser-land-inc/tree/master/Project%20Journal](https://github.com/nguyensjsu/team202-loser-land-inc/tree/master/Project%20Journal)

- Link to your team's Google Sprint Task Sheet

[https://docs.google.com/spreadsheets/d/1ETcQLdThsi7fDahYWJlDC2GyPorusU5DN5hWJB72dHU/edit?usp=sharing](https://docs.google.com/spreadsheets/d/1ETcQLdThsi7fDahYWJlDC2GyPorusU5DN5hWJB72dHU/edit?usp=sharing)

- Link to your team's Ad Video on YouTube







