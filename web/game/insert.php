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
     	$name = str_replace("č", "◄cc►",str_replace("ď", "◄cd►", $_POST['name']));
		$thumbnail = $_POST['thumbnail'];
		$start_date = $_POST['start-date'];
		$end_date = $_POST['end-date'];
		$repeat_date = $_POST['repeat'];
		$website = $_POST['website'];
		$location = save_replace($_POST['location']);
		$price = $_POST['price'];
		$type = $_POST['type-action'];
		$description = save_replace($_POST['description']);
		$ip = $_SERVER['REMOTE_ADDR'];

		$sql = "INSERT INTO Game(
				Name,IPAddress,Thumbnail,StartDate,EndDate,RepeatDate,Website,Location,Price,Type,Description,Status,CreateDate)
		 		VALUES ('$name','$ip','$thumbnail','$start_date','$end_date','$repeat_date','$website','$location',$price,'$type','$description', 'PENDING', '$currentDate')";
		
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