<?php
	# GOOGLE RE-CAPTCHA #
	define('GOOGLE_RECAPTCHA_SITE_KEY', '6LffBjAhAAAAAIhCPee-TmtUHzQnXrGhsONphLMu');
?>

<!DOCTYPE html>
<html lang="en">
<head>
	
	<!-- METADATA -->
    <meta charset="UTF-8">
	<meta name="author" content="Orim0 & NiX3r & KiJudo">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<!-- RE-CAPTCHA -->
	<script src="https://www.google.com/recaptcha/api.js"></script>
	
	<!-- ICONS -->
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	
	<!-- STYLE -->
    <link rel="stylesheet" href="style.css">

    <link rel="apple-touch-icon" sizes="180x180" href="assets/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="assets/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="assets/favicon-16x16.png">
    <link rel="manifest" href="assets/site.webmanifest">

    <title>Formulář pro vytvoření týmu</title>
</head>
<body>
    <div class="uvodcon">
        <img class="image" src="assets/topic.jpg">
        <div class="topic">Formulář pro vytvoření týmu</div>
    </div>
    <div class="container defFont" style="margin-top: 10px;">
        <hr>
        <br>

        <div class="form">
        <form method="post" action="insert.php">
            <label for="name">Název týmu*</label>
            <input type="text" id="name" name="name" required><br>
            <label class="hoverText" title="Slouží k udelění zablokování editace záznamu komukoliv" for="name">Heslo pro editaci*</label>
            <input type="password" id="password" name="password" required><br>
            <label class="hoverText" title="Email slouží pro případnou komunikaci s administrátory SoftBota, který se nezobrazuje veřejně" for="email">Email*</label>
            <input type="email" id="email" name="email" class="input" required><br>
            <label class="hoverText" title="Slouží pro zobrazení úvodního obrázku na Discord. Vložte permanentní link, aby nedocházelo k zbytečných výpadkům." for="thumbnail">URL na úvodní fotku/logo</label>
            <input type="url" id="thumbnail" name="thumbnail"><br>
            <label for="website">Webová stránka</label>
            <input type="url" id="website" name="website"><br>
            <label for="type-action">Typ týmu*</label>
            <select name="type-action" id="type-action" class="input" required>
              <option value="" disabled selected>Vybrat...</option>
              <option value="MilSim">MilSim</option>
              <option value="CQB">CQB</option>
              <option value="CQB&MS">CQB i Milsim</option>
              <option value="None">žádný</option>
            </select>
            <br>
            <label class="hoverText" title="V případě nejasností se obraťte na WIKI (www.softbot.ncodes.eu/wiki)" for="dis-server-id">Discord server ID</label>
            <input type="number" id="dis-server-id" name="dis-server-id"><br>
            <label class="hoverText" title="V případě nejasností se obraťte na WIKI (www.softbot.ncodes.eu/wiki)" for="dis-user-id">Vaše Discord ID účtu</label>
            <input type="number" id="dis-user-id" name="dis-user-id"><br>
            <label for="description" style="margin-top: 5px;">Popis týmu*</label><br>
            <textarea class="description" name="description" id="description" cols="30" rows="10" required></textarea><br>
			
			<div class="g-recaptcha" data-sitekey="<?php echo(GOOGLE_RECAPTCHA_SITE_KEY) ?>" data-theme="dark"></div>
			
            <p style="font-size: smaller; float: left; clear: left; text-align: left;">
              * povinný údaj <br>
              odesláním tohoto formuláře souhlasíte s uložením Vaší IP <br>
              adresy a všechy výše uvedené informace pro potřeby <br>
              této stránky a Discord bota 🍪
            </p>
			
            <input class="submit" type="submit" name="submit" value="Odeslat" id="submit">
          </form>
        </div>

    </div>

</body>
</html>