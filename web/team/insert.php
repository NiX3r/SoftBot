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
		$password = hash('sha256', 'soft' + $_POST['password'] + 'bot');
		$email = $_POST['email'];
		$thumbnail = $_POST['thumbnail'];
		$website = $_POST['website'];
		$type = $_POST['type-action'];
		$description = save_replace($_POST['description']);
		$ip = $_SERVER['REMOTE_ADDR'];
		$discord_server = $_POST['dis-server-id'];
		$discord_user = $_POST['dis-user-id'];

		$sql = "INSERT INTO Team(
				Name,IPAddress,Thumbnail,Website,Type,DiscordServerId,Description,Status,CreateDate)
		 		VALUES ('$name','$ip','$thumbnail','$website','$type','$discord_server', '$description', 'PENDING', '$currentDate')";
		
		if (mysqli_query($conn, $sql)) {
			echo "Úspěšně jste vytvořil/a novou akci !";
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