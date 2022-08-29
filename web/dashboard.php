<?php
session_start();

if(!$_SESSION['logged_in']){
  header('Location: error.php');
  exit();
}
extract($_SESSION['userData']);

$avatar_url = "https://cdn.discordapp.com/avatars/$discord_id/$avatar.png";


?>
<!doctype html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<script src="https://kit.fontawesome.com/f307a5f3a4.js" crossorigin="anonymous"></script>
  <link href="../dist/output.css" rel="stylesheet">
	    <link rel="apple-touch-icon" sizes="180x180" href="../assets/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="../assets/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="../assets/favicon-16x16.png">
    <link rel="manifest" href="../assets/site.webmanifest">
</head>
<body>
    <div class="flex items-center justify-center h-screen flex-col" style="background: linear-gradient(rgba(0, 0, 0, 0.7), rgba(0, 0, 0, 0.7)), url('../assets/bg.jfif'); background-position: 55% 25%;">
      <div class="text-white text-3xl">Vítej v panelu SoftBota, </div>
      <div class="flex items-center mt-5">
        <img class="rounded-full w-12 h-12 mr-3" src="<?php echo $avatar_url?>" />
        <span class="text-4xl text-white font-semibold"><?php echo $name;?></span>
      </div>
		
		    <a href="bazaar.php" class="flex items-center rcorners1  mt-5">
        	<span class="text-white font-semibold"><i class="fas fa-cart-plus"></i> Bazar</span>
      	</a>
		    <a href="game.php" class="flex items-center rcorners1 mt-4">
        	<span class="text-3xl text-white font-semibold"><i class="fas fa-gamepad"></i> Hra</span>
      	</a>
		    <a href="shop.php" class="flex items-center rcorners1 mt-4">
        	<span class="text-3xl text-white font-semibold"><i class="fas fa-shopping-basket"></i> Obchod</span>
      	</a>
		    <a href="team.php" class="flex items-center rcorners1 mt-4">
        	<span class="text-3xl text-white font-semibold"><i class="fas fa-users"></i> Team</span>
      	</a>
        <a href="https://github.com/NiX3r/SoftBot/wiki" class="flex items-center rcorners1 mt-4">
        	<span class="text-3xl text-white font-semibold"><i class="fa fa-wikipedia-w"></i> Wiki</span>
      	</a>
        <a href="https://discord.com/oauth2/authorize?client_id=995384315464667186&scope=bot&permissions=0515396586561" class="flex items-center rcorners1 mt-4">
        	<span class="text-3xl text-white font-semibold"><i class="fa fa-envelope-o"></i> Pozvánka</span>
      	</a>
		
		<a href="logout.php" class="mt-5 text-white"><i class="fas fa-sign-out-alt"></i> Odhlásit se</a>
    </div>

</body>
</html>