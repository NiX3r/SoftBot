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
	<script src="https://kit.fontawesome.com/f307a5f3a4.js" crossorigin="anonymous"></script>
	
	<!-- STYLE -->
    <link rel="stylesheet" href="../dist/style.css">

    <link rel="apple-touch-icon" sizes="180x180" href="../assets/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="../assets/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="../assets/favicon-16x16.png">
    <link rel="manifest" href="../assets/site.webmanifest">

    <title>Formulář pro vytvoření hry</title>
</head>
<body>
    <div class="uvodcon">
        <img class="image" src="../assets/game.jpg">
        <div class="topic">Formulář pro vytvoření hry</div>
    </div>
	<a href="dashboard.php" class="back"><i class="fas fa-angle-left"></i> Zpět do menu</a>
    <div class="container defFont" style="margin-top: 10px;">
        <hr>
        <br>

        <div class="form">
        <form method="post" action="process.php">
			
            <label for="name">Název akce*</label>
            <input type="text" id="name" name="name" maxlength="64" required><br>
			
            <label class="hoverText" title="Slouží pro zobrazení úvodního obrázku na Discord. Vložte permanentní link, aby nedocházelo k zbytečných výpadkům." for="thumbnail">URL na úvodní fotku</label>
            <input type="url" id="thumbnail" maxlength="2048" name="thumbnail"><br>
			
            <label for="start-date">Začátek akce*</label>
            <input type="datetime-local" id="start-date" name="start-date" required><br>
			
            <label for="end-date">Konec akce*</label>
            <input type="datetime-local" id="end-date" name="end-date" required><br>
			
            <label class="hoverText" title="Slouží k opakování akce. Akce bude zobrazována s daty, kterými vložíte zde jako 'Začátek akce' a 'Konec akce', ale v kalendáři bude podle zde vybraného opakování" for="repeat">Opakování akce*</label>
            <select name="repeat" id="repeat" class="input" required>
				
            	<option value="" disabled selected>Vybrat...</option>
              	<option value="N">žádné</option>
              	<option value="W">každý týden</option>
              	<option value="M">každý měsíc</option>
              	<option value="Y">každý rok</option>
            </select>
			
            <br>
			
            <label for="website">Webová stránka</label>
            <input type="url" id="website" maxlength="256" name="website"><br>
			
            <label for="location">Adresa akce*</label>
            <input type="text" id="location" name="location" maxlength="256" required><br>
			
            <label class="hoverText" title="Zadané vstupné pište v českých korunách. Do budoucna přibude rozšíření o nastavení měny." for="price">Vstupné*</label>
            <input type="number" id="price" name="price" maxlength="1000000000" required><br>
			
            <label for="type-action">Typ akce*</label>
            <select name="type-action" id="type-action" class="input" required>
              <option value="" disabled selected>Vybrat...</option>
              <option value="MilSim">MilSim</option>
              <option value="RPG">RPG</option>
              <option value="CQB">CQB</option>
              <option value="PB">PláckoBitka</option>
            </select>
			
            <br>
			
            <label for="description" style="margin-top: 5px;">Popis akce*</label><br>
            <textarea class="description" name="description" id="description" cols="30" rows="10" maxlength="1024" required></textarea><br>
			
			<div class="g-recaptcha" data-sitekey="<?php echo(GOOGLE_RECAPTCHA_SITE_KEY) ?>" data-theme="dark"></div>
			
            <p style="font-size: smaller; float: left; clear: left; text-align: left;">
              * povinný údaj <br>
              odesláním tohoto formuláře souhlasíte s uložením Vaší IP <br>
              adresy a všechy výše uvedené informace pro potřeby <br>
              této stránky a Discord bota 🍪
            </p>
			
            <input class="submit" type="submit" name="submit-game" value="Odeslat" id="submit">
          </form>
        </div>

    </div>
</body>
	<?php include('footer.php') ?>
</html>