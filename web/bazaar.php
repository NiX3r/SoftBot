<?php
	# GOOGLE RE-CAPTCHA #
	define('GOOGLE_RECAPTCHA_SITE_KEY', '6LffBjAhAAAAAIhCPee-TmtUHzQnXrGhsONphLMu');

session_start();

if(!$_SESSION['logged_in']){
  header('Location: error.php');
  exit();
}
extract($_SESSION['userData']);

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
    <link rel="stylesheet" href="/dist/style.css">

    <link rel="apple-touch-icon" sizes="180x180" href="/assets/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="/assets/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="/assets/favicon-16x16.png">
    <link rel="manifest" href="/assets/site.webmanifest">

    <title>Formulář pro vytvoření nabídky/poptávky</title>
</head>
<body>
    <div class="uvodcon">
        <img class="image" src="/assets/bazar.jpg">
        <div class="topic">Formulář pro vytvoření nabídky/poptávky</div>
    </div>
	<a href="dashboard.php" class="back"><i class="fas fa-angle-left"></i> Zpět do menu</a>
    <div class="container defFont" style="margin-top: 10px;">
        <hr>
        <br>

        <div class="form">
        <form method="post" action="process.php">
            <label for="name">Název nabídky/poptávky*</label>
            <input type="text" id="name" name="name" maxlength="256" require /><br>
			
            <label for="type">Typ*</label>
            <select name="type" id="type" class="input" require>
            <option value="" disabled selected>Vybrat...</option>
              <option value="OFFER">nabídka</option>
              <option value="INQUIRY">poptávka</option>
            </select>
			
            <br>
			
            <label for="zip">Poštovní směrovací číslo*</label>
            <input type="number" id="zip" name="zip" require maxlength="11"/><br>
			
            <label class="hoverText" title="V případě, že je cena 'dohodou' napište 0" for="price">Cena*</label>
            <input type="number" id="price" name="price" maxlength="1000000000" require /><br>
			
            <label for="description" style="margin-top: 5px;">Popis nabídky/zakázky*</label><br>
            <textarea class="description" name="description" id="description" cols="30" rows="10" maxlength="1024" require></textarea><br>
			
			<div class="g-recaptcha" data-sitekey="<?php echo(GOOGLE_RECAPTCHA_SITE_KEY) ?>" data-theme="dark"></div>
			
            <p style="font-size: smaller; float: left; clear: left; text-align: left;">
              * povinný údaj <br>
              odesláním tohoto formuláře souhlasíte s uložením Vaší IP <br>
              adresy a všechy výše uvedené informace pro potřeby <br>
              této stránky a Discord bota 🍪
            </p>
			
            <input class="submit" type="submit" name="submit-bazar" value="Odeslat" id="submit" />
          </form>
        </div>

    </div>

</body>
		<?php include('footer.php') ?>
</html>