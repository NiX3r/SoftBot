<?php

$discord_url = "https://ptb.discord.com/api/oauth2/authorize?client_id=995384315464667186&redirect_uri=https%3A%2F%2Fsoftbot.ncodes.eu%2Fprocess-oauth.php&response_type=code&scope=identify%20guilds";
header("Location: $discord_url");
exit();

?>