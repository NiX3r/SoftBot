# SoftBot

Discord bot for Czech airsoft communites

## Features

- Games notifications
  
  - Games organisators add game and its description (such as start date, end date, name, website, location, and other description) on website *softbot.ncodes.eu/game*
  
  - On every server bot's in it will send a announcement after creating a game and a week before start of a game

- Teams administration
  
  - Team owner could create a team and its description (such as name, type, logo, discord server) on website *softbot.ncodes.eu/team*
  
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

- `!sb help` - shows help menu

- `!sb game` - commands for game section
  
  - `!sb game create` - send create form
  
  - `!sb game list <index>` - list of all teams sorted by ID and counted max to 10
  
  - `!sb game to <date>` - shows all games to the specific date (date format: '21.08.2002' or '21.8.2002')
  
  - `!sb game show <id>` - show all description of a selected game

- `!sb team` - commands for team section
  
  - `!sb team create` - send create form
  
  - `!sb team list <index>` - list of all teams sorted by ID and counted max to 10
  
  - `!sb team show <id>` - show all description of a selected team
  
  - `!sb team invite <id>` create and send invite for a teams server

- `!sb reddit` - command for view random post

- `!sb invite` - shows URL link for invite bot into Discord server

- `!sb credits` - shows bots credentials

- `!sb sponsors` - shows bot's sponsors

- `!sb offer` - commands for bazaar offers section
  
  - `!sb offer create` - send create form
  
  - `!sb offer file <id> <password>` - set files to be shown with offer. Files have to be attached on message
  
  - `!sb offer list <index>` - list of all offers sorted by create date and counted max to 10
  
  - `!sb offer show <id>` - show all description of a selected offer

- `!sb inquiry` - commands for bazaar inquiry section
  
  - `!sb inquiry create` - send create form
  
  - `!sb inquiry file <id> <password>` - set files to be shown with inquiry. Files have to be attached on message
  
  - `!sb inquiry list` - list of all inquiries sorted by create date and counted max to 10
  
  - `!sb inquiry show` - show all description of a selected inquiry

*`*` stands for necassary input variables*

### Server admins commands

- `!sb channel <channel-ping>` - set up channel where every notification will be sended ( only creator of offer and bot admins can use this command )

- `!sb team-role <role-ping>` - set up team role if needed

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

- `!sba pending [game | team | bazaar | shop]` - send oldest game in pending status to approve

### Programmer commands

- `!sbp admin` - admin commands section
  
  - `!sbp admin add <user-discord-ping>` - add user as a admin
  
  - `!sbp admin remove <user-discord-ping>` - remove user as a admin
  
  - `!sbp admin list` - list of all admins

## Develop team

**NiX3r** - backend discord bot developer

**Orim0** - backend website developer & logo designer

**KiJudo** - web UI designer

## Versions

| Version | Release date | Link to changelog                                   |
|:-------:| ------------ | --------------------------------------------------- |
| 1.2.0   | 22.09.2022   | https://github.com/NiX3r/SoftBot/releases/tag/1.2.0 |
