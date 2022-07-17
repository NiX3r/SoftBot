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
  
  - `!sb game list <index>` - list of all teams sorted by alphabetic and counted max to 10
  
  - `!sb game date <date>` - shows all games to the specific date (date format: '21.08.2002' or '21.8.2002')
  
  - `!sb game show <id>` - show all description of a selected game
  
  - `!sb game create <name>` - create game with specific name **PM**
  
  - `!sb game edit <id> [*name | *start-date | *end-date | website | *location | *price | *type | *description] <value>` - edit game with specific ID before its show time. For edit game it must be non visible ( only creator of game and bot admins can use this command ) **PM**
  
  - `!sb game finish <id>` - finish creating a specific game ( only creator of game and bot admins can use this command ) **PM**
  
  - `!sb game visible <id> [on | off]` - set visibility of specific game ( only creator of game and bot admins can use this command ) **PM**
  
  - `!sb game delete <id>` - delete specific game ( only creator of game and bot admins can use this command ) **PM**

- `!sb team` - commands for team section
  
  - `!sb team list <index>` - list of all teams sorted by alphabetic and counted max to 10
  
  - `!sb team show <id>` - show all description of a selected team
  
  - `!sb team create <name>` - create a team with specific name **PM**
  
  - `!sb team edit <id> [*name | *create-date | website | Discord server | *type | *logo ]` - edit team with specific ID before its show time. For edit team it must be non visible ( only creator of team and bot admins can use this command ) **PM**
  
  - `!sb team finish <id>` - finish creating a specific team ( only creator of team and bot admins can use this command ) **PM**
  
  - `!sb team visible <id> [on | off]` - set visibility of team ( only creator of team and bot admins can use this command ) **PM**
  
  - `!sb team delete <id>` - delete specific team ( only creator of team and bot admins can use this command ) **PM**

- `!sb youtube` - commands for youtube section

- `!sb reddit` - command for view random post

- `!sb invite` - shows URL link for invite bot into Discord server

- `!sb credits` - shows bots credentials

- `!sb channel <channel-ping>` - set up channel where every notification will be sended ( only creator of offer and bot admins can use this command )

- `!sb offer` - commands for bazaar offers section
  
  - `!sb offer list <index>` - list of all offers sorted by create date and counted max to 10
  
  - `!sb offer show <id>` - show all description of a selected offer
  
  - `!sb offer create <topic>` - create a offer with specific name **PM**
  
  - `!sb offer edit <id> [*topic | *price | *email | *owners-address | *files | *description ]` - edit offer with specific ID before its show time. For edit offer it must be non visible ( only creator of offer and bot admins can use this command ) **PM**
  
  - `!sb offer finish <id>` - finish creating a specific offer ( only creator of offer and bot admins can use this command ) **PM**
  
  - `!sb offer visible [on | off]` - set visibility of offer ( only creator of offer and bot admins can use this command ) **PM**
  
  - `!sb offer delete <id>` - delete specific offer ( only creator of offer and bot admins can use this command ) **PM**

- `!sb inquiry` - commands for bazaar inquiry section
  
  - `!sb inquiry list ` - list of all inquiries sorted by create date and counted max to 10
  
  - `!sb inquiry show ` - show all description of a selected inquiry
  
  - `!sb inquiry create <topic>` - create a inquiry with specific name **PM**
  
  - `!sb inquiry edit <id> [*topic | *price | *email | *owners-address | *description ]` - edit inquiry with specific ID before its show time. For edit inquiry it must be non visible ( only creator of inquiry and bot admins can use this command ) **PM**
  
  - `!sb inquiry finish ` - finish creating a specific inquiry ( only creator of inquiry and bot admins can use this command ) **PM**
  
  - `!sb inquiry visible [on | off]` - set visibility of inquiry ( only creator of inquiry and bot admins can use this command ) **PM**
  
  - `!sb inquiry delete <id>` - delete specific inquiry ( only creator of inquiry and bot admins can use this command ) **PM**

_`*` stands for necassary input variables_

### Bot admins commands

- `!sba ban <user-dicord-id> [ bazaar | everywhere ] <reason>` - ban user from using bazaar or whole Discord bot

- `!sba unban <user-discord-id> [ bazaar | everywhere ] <reason>` - unban user

- `!sba server` - server commands section
  
  - `!sba server list` - list of all servers bot is in
  
  - `!sba server link <server-id>` - create an invite link for a specify server
  
  - `!sba server disconnect <server-id> <reason>` - disconnect bot from server
  
  - `!sba server ban <server-id> <reason>` - ban server from using bot anyway
  
  - `!sba server unban <server-id> <reason>` - unban server

- `!sba announcement <message>` - send annoucement message to all servers

### Programmer commands

-  `!sbp admin` - admin commands section
  
  - `!sbp admin add <user-discord-id>` - add user as a admin
  
  - `!sbp admin remove <user-discord-id>` - remove user as a admin

## Plan

- [x] Wishes and dreams

- [ ] Games section

- [ ] Programmer commands section

- [ ] Teams section

- [ ] Reddit section

- [ ] Invite & Credits commands

- [ ] Youtube section

- [ ] Bazaar offer section

- [ ] Bazaar inquiry section

- [ ] Admins commands section

- [ ] Hide & seek with a bugs

- [ ] Prelease

- [ ] Release
