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

- d



## Commands

- !sb game - commands for game section
  
  - !sb game list - list of all game that start the day of the command perform to same day after month
  
  - !sb game show <id> - show all description of a selected game
  
  - !sb game create <name> - create game with specific name
  
  - !sb game edit <id> [name | start-date | end-date | website | location | description] - edit game with specific ID before it's show time ( only creator of game and programmer can use this command )
  
  - !sb game finish <id> - finish creating a specific game ( only creator of game and programmer can use this command )
  
  - !sb game visible <id> [on | off] - set visible of specific game ( only creator of game and programmer can use this command )

- !sb team - commands for team section

- !sb youtube - commands for youtube section
