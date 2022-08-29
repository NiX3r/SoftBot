<?php
include('./db.php');

	# GOOGLE RE-CAPTCHA #
	define('GOOGLE_RECAPTCHA_SECRET_KEY', '6LffBjAhAAAAACn4sitAI4-dvGVU_V85luiE7Iz8');

	function validateCaptcha($captcha) {
		$response = file_get_contents("https://www.google.com/recaptcha/api/siteverify?secret=" . GOOGLE_RECAPTCHA_SECRET_KEY ."&response=$captcha");

		if (!json_decode($response)->success) {
			throw new Exception(INVALID_CAPTCHA);
		} else {
			
			return true;
		}
	}
if(isset($_POST['submit']))
{    
	$captcha = $_POST['g-recaptcha-response'];
	$captcha2 = $_POST['g-recaptcha'];
	
	if(validateCaptcha($captcha)){
		
		$currentDate = date('Y-m-d H:i:s');
     	$name = save_replace($_POST['name']);
		$type = $_POST['type'];
		$zip = $_POST['zip'];
		$price = $_POST['price'];
		$description = save_replace($_POST['description']);
		$ip = $_SERVER['REMOTE_ADDR'];

		$sql = "INSERT INTO Bazaar(
				Name,IPAddress,Type,ZIP,Price,Description,Status,CreateDate)
		 		VALUES ('$name', '$ip','$type',$zip, $price, '$description', 'PENDING', '$currentDate')";
		
		if (mysqli_query($conn, $sql)) {
			echo "Úspěšně jste vytvořil/a nový záznam obchodu !";
		} else {
			echo "Error: " . $sql . ":-" . mysqli_error($conn);
		}
			mysqli_close($conn);
		
	} else  {
		echo "Omlouváme se, ale nebyla vyplněna resaptcha ! Budete přesměrováni na formulář za 5 sekund!";
		echo ' <meta http-equiv="refresh" content="5;url=index.php">';
		
	}
}

function save_replace($fvalue) {
	return str_replace("č", "◄cc►",str_replace("ď", "◄cd►", str_replace("'", "◄co►", $fvalue)));
}

?>