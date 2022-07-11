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

- Bazaar
  
  - Users can create inquiry or offer of a airsoft stuff
  
  - Users can list it through every Discord server
  
  - Users can report another user from using bazaar. Onyl bot admins can give user ban from using bazaar 

- *and more will be added*

## Commands

### Users commands

- `!sb game` - commands for game section
  
  - `!sb game list` - list of all game that start the day of the command perform to same day after month
  
  - `!sb game show <id>` - show all description of a selected game
  
  - `!sb game create <name>` - create game with specific name
  
  - `!sb game edit <id> [*name | *start-date | *end-date | website | *location | *price | *type | *description] <value>` - edit game with specific ID before its show time. For edit game it must be non visible ( only creator of game and bot admins can use this command )
  
  - `!sb game finish <id>` - finish creating a specific game ( only creator of game and bot admins can use this command )
  
  - `!sb game visible <id> [on | off]` - set visibility of specific game ( only creator of game and bot admins can use this command )
  
  - `!sb game delete <id>` - delete specific game ( only creator of game and bot admins can use this command )

- `!sb team` - commands for team section
  
  - `!sb team list <index>` - list of all teams sorted by alphabetic and counted max to 10
  
  - `!sb team show <id>` - show all description of a selected team
  
  - `!sb team create <name>` - create a team with specific name
  
  - `!sb team edit <id> [*name | *create-date | website | Discord server | *type | *logo ]` - edit team with specific ID before its show time. For edit team it must be non visible ( only creator of team and bot admins can use this command )
  
  - `!sb team finish <id>` - finish creating a specific team ( only creator of team and bot admins can use this command )
  
  - `!sb team visible <id> [on | off]` - set visibility of team ( only creator of team and bot admins can use this command )
  
  - `!sb team delete <id>` - delete specific team ( only creator of team and bot admins can use this command )

- `!sb youtube` - commands for youtube section

- `!sb reddit` - command for view 

- `!sb offer` - commands for bazaar offers section
  
  - `!sb offer list <index>` - list of all offers sorted by create date and counted max to 10
  
  - `!sb offer show <id>` - show all description of a selected offer
  
  - `!sb offer create ` - create a offer with specific name
  
  - `!sb offer edit <id> [*topic | *price | *email | *owners-address | *files | *description ]` - edit offer with specific ID before its show time. For edit offer it must be non visible ( only creator of offer and bot admins can use this command )
  
  - `!sb offer finish <id>` - finish creating a specific offer ( only creator of offer and bot admins can use this command )
  
  - `!sb offer visible [on | off]` - set visibility of offer ( only creator of offer and bot admins can use this command )
  
  - `!sb offer delete <id>` - delete specific offer ( only creator of offer and bot admins can use this command )

- `!sb inquiry` - commands for bazaar inquiry section
  
  - `!sb inquiry list ` - list of all inquiries sorted by create date and counted max to 10
  
  - `!sb inquiry show ` - show all description of a selected inquiry
  
  - `!sb inquiry create` - create a inquiry with specific name
  
  - `!sb inquiry edit <id> [*topic | *price | *email | *owners-address | *description ]` - edit inquiry with specific ID before its show time. For edit inquiry it must be non visible ( only creator of inquiry and bot admins can use this command )
  
  - `!sb inquiry finish ` - finish creating a specific inquiry ( only creator of inquiry and bot admins can use this command )
  
  - `!sb inquiry visible [on | off]` - set visibility of inquiry ( only creator of inquiry and bot admins can use this command )
  
  - `!sb inquiry delete <id>` - delete specific inquiry ( only creator of inquiry and bot admins can use this command )

_`*` stands for necassary input variables_

## Plan

- [x] Wishes and dreams

- [ ] Games section

- [ ] Teams section

- [ ] Reddit section

- [ ] Youtube section

- [ ] Bazaar offer section

- [ ] Bazaar inquiry section

- [ ] Hide & seek with a bugs

- [ ] Prelease

- [ ] Release
