# SoftBot

 Discord bot for Czech airsoft communites

## Features

- Games notifications
  
  - Games organisators add game and its description (such as start date, end date, name, website, location, and other description)
  
  - On every server bot's in it will send a announcement after creating a game and a week before start of a game

- Teams administration
  
  - Team owner could create a team and its description (such as name, type, logo, discord server)
  
  - Team administrators could add and remove members of a team
  
  - Discord users could see list of teams and join their Discord server

- Youtubers statistics
  
  - Server administrators can setup youtube channels to send notification about new videos
  
  - Every X minutes bot check every single Youtube channel for a new video to send to Discord servers announcement

- Reddit posts
  
  - Users can view a posts on r/airsoft reddit
  
  - Posts are save into cache which is refresh every single day

- *and more next time*

## Commands

- !sb game - commands for game section
  
  - !sb game list - list of all game that start the day of the command perform to same day after month
  
  - !sb game show <id> - show all description of a selected game
  
  - !sb game create <name> - create game with specific name
  
  - !sb game edit <id> [^name | ^start-date | ^end-date | website | ^location | ^price | ^type | ^description] <value> - edit game with specific ID before its show time ( only creator of game and programmer can use this command )
  
  - !sb game finish <id> - finish creating a specific game ( only creator of game and programmer can use this command )
  
  - !sb game visible <id> [on | off] - set visibility of specific game ( only creator of game and programmer can use this command )

- !sb team - commands for team section
  
  - !sb team list <index> - list of all teams sorted by alphabetic and counted max to 10
  
  - !sb team show <id> - show all description of a selected team
  
  - !sb team create <name> - create a team with specific name
  
  - !sb team edit <id> [^name | ^create-date | website | Discord server | ^type | ^logo ] - edit team with specific ID before its show time ( only creator of game and programmer can use this command )
  
  - !sb team finish <id> - finish creating a specific team ( only creator of game and programmer can use this command )
  
  - !sb team visible <id> [on | off] - set visibility of team ( only creator of game and programmer can use this command )

- !sb youtube - commands for youtube section

- !sb reddit - command for view 

_^ stands for necassary data_
