<?php
	# GOOGLE RE-CAPTCHA #
	define('GOOGLE_RECAPTCHA_SITE_KEY', 'DOPLNIT');
?>

<!DOCTYPE html>
<html lang="en">
<head>
	
	<!-- METADATA -->
    <meta charset="UTF-8">
	<meta name="author" content="Orim0 & NiX3r">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<!-- RE-CAPTCHA -->
	<script src="https://www.google.com/recaptcha/api.js"></script>
	
	<!-- ICONS -->
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	
	<!-- STYLE -->
    <link rel="stylesheet" href="style.css">
	
    <title>Test Form Webpage</title>
</head>
<body>
    <div class="uvodcon">
        <img class="image" src="assets/topic.jpg">
        <div class="topic">Formulář pro vytvoření hry</div>
    </div>
    <div class="container defFont" style="margin-top: 10px;">
        <hr>
        <br>

        <div class="form">
        <form method="post" action="insert.php">
            <label for="name">Název akce*</label>
            <input type="text" id="name" name="name" required><br>
            <label for="email">Email*</label>
            <input type="email" id="email" name="email" class="input" required><br>
            <label for="thumbnail">URL na úvodní fotku</label>
            <input type="url" id="thumbnail" name="thumbnail"><br>
            <label for="start-date">Začátek akce*</label>
            <input type="datetime-local" id="start-date" name="start-date" required><br>
            <label for="end-date">Konec akce*</label>
            <input type="datetime-local" id="end-date" name="end-date" required><br>
            <label for="repeat">Opakování akce*</label>
            <select name="repeat" id="repeat" class="input" required>
            <option value="" disabled selected>Vybrat...</option>
              <option value="N">žádné</option>
              <option value="W">každý týden</option>
              <option value="M">každý měsíc</option>
              <option value="Y">každý rok</option>
            </select>
            <br>
            <label for="website">Webová stránka</label>
            <input type="url" id="website" name="website"><br>
            <label for="location">Adresa akce*</label>
            <input type="text" id="location" name="location" required><br>
            <label for="price">Vstupné*</label>
            <input type="number" id="price" name="price" required><br>
            <label for="type-action">Typ akce*</label>
            <select name="type-action" id="type-action" class="input" required>
              <option value="" disabled selected>Vybrat...</option>
              <option value="MilSim">MilSim</option>
              <option value="RPG">RPG</option>
              <option value="CQB">CQB</option>
            </select>
            <br>
            <label for="description" style="margin-top: 5px;">Popis akce*</label><br>
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