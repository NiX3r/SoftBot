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

    <title>Formulář pro vytvoření obchodu</title>
</head>
<body>
    <div class="uvodcon">
        <img class="image" src="../assets/shop.jpg">
        <div class="topic">Formulář pro vytvoření obchodu</div>
    </div>
	<a href="dashboard.php" class="back"><i class="fas fa-angle-left"></i> Zpět do menu</a>
    <div class="container defFont" style="margin-top: 10px;">
        <hr>
        <br>

        <div class="form">
        <form method="post" action="process.php">
            <label for="name">Název obchodu*</label>
            <input type="text" id="name" name="name" maxlength="128" required><br>
            <label for="voucher">Slevový kód</label>
            <input type="text" id="voucher" maxlength="64" name="voucher"><br>
            <label class="hoverText" title="Slouží pro zobrazení úvodního obrázku na Discord. Vložte permanentní link, aby nedocházelo k zbytečných výpadkům." for="thumbnail">URL na úvodní fotku/logo</label>
            <input type="url" id="thumbnail" name="thumbnail" maxlength="2048"><br>
            <label for="website">Webová stránka*</label>
            <input type="url" id="website" name="website" maxlength="256" require><br>
            <label for="address">Adresa</label>
            <input type="text" id="address" maxlength="256" name="address"><br>
            <label for="zip">Poštovní směrovací číslo</label>
            <input type="number" id="zip" maxlength="11" name="zip"><br>
            <label for="description" style="margin-top: 5px;">Popis obchodu</label><br>
            <textarea class="description" name="description" id="description" cols="30" rows="10"></textarea><br>
			
			<div class="g-recaptcha" data-sitekey="<?php echo(GOOGLE_RECAPTCHA_SITE_KEY) ?>" data-theme="dark"></div>
			
            <p style="font-size: smaller; float: left; clear: left; text-align: left;">
              * povinný údaj <br>
              odesláním tohoto formuláře souhlasíte s uložením Vaší IP <br>
              adresy a všechy výše uvedené informace pro potřeby <br>
              této stránky a Discord bota 🍪
            </p>
			
            <input class="submit" type="submit" name="submit-shop" value="Odeslat" id="submit">
          </form>
        </div>

    </div>

</body>
		<?php include('footer.php') ?>
</html>