<?php

session_start();

if(!$_SESSION['logged_in']){
  header('Location: error.php');
  exit();
}
extract($_SESSION['userData']);

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
if(isset($_POST['submit-bazar']))
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
				Name,IPAddress,Type,ZIP,Price,Description,Status,CreateDate,DiscordUserID)
		 		VALUES ('$name', '$ip','$type',$zip, $price, '$description', 'PENDING', '$currentDate', '$discord_id')";
		
		if (mysqli_query($conn, $sql)) {
			include("loading2.html");
			echo ' <meta http-equiv="refresh" content="5;url=dashboard.php">';
		} else {
			echo "Error: " . $sql . ":-" . mysqli_error($conn);
		}
			mysqli_close($conn);
		
	} else  {
		echo "Omlouváme se, ale nebyla vyplněna resaptcha ! Budete přesměrováni na formulář za 5 sekund!";
		echo ' <meta http-equiv="refresh" content="5;url=bazaar.php">';
		
	}
}

if(isset($_POST['submit-game']))
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
				Name,IPAddress,Thumbnail,StartDate,EndDate,RepeatDate,Website,Location,Price,Type,Description,Status,CreateDate,DiscordUserID)
		 		VALUES ('$name','$ip','$thumbnail','$start_date','$end_date','$repeat_date','$website','$location',$price,'$type','$description', 'PENDING', '$currentDate', '$discord_id')";
		
		if (mysqli_query($conn, $sql)) {
			include("loading2.html");
			echo ' <meta http-equiv="refresh" content="2;url=dashboard.php">';
		} else {
			echo "Error: " . $sql . ":-" . mysqli_error($conn);
		}
			mysqli_close($conn);
		
	} else  {
		echo "Omlouváme se, ale nebyla vyplněna resaptcha ! Budete přesměrováni na formulář za 5 sekund!";
		echo ' <meta http-equiv="refresh" content="5;url=game.php">';
		
	}
}

if(isset($_POST['submit-shop']))
{    
	$captcha = $_POST['g-recaptcha-response'];
	$captcha2 = $_POST['g-recaptcha'];
	
	if(validateCaptcha($captcha)){
		
		$currentDate = date('Y-m-d H:i:s');
     	$name = save_replace($_POST['name']);
		$voucher = $_POST['voucher'];
		$email = save_replace($_POST['email']);
		$thumbnail = $_POST['thumbnail'];
		$website = $_POST['website'];
		$zip = $_POST['zip'];
		$address = save_replace($_POST['address']);
		$description = save_replace($_POST['description']);
		$ip = $_SERVER['REMOTE_ADDR'];

		$sql = "INSERT INTO Shop(
				Name, Voucher,IPAddress,Thumbnail,Website,Location,ZIP,Description,Status,CreateDate,DiscordUserID)
		 		VALUES ('$name', '$voucher','$ip','$thumbnail','$website','$location',$zip, '$description', 'PENDING', '$currentDate', '$discord_id')";
		
		if (mysqli_query($conn, $sql)) {
			include("loading2.html");
			echo ' <meta http-equiv="refresh" content="2;url=dashboard.php">';
		} else {
			echo "Error: " . $sql . ":-" . mysqli_error($conn);
		}
			mysqli_close($conn);
		
	} else  {
		echo "Omlouváme se, ale nebyla vyplněna resaptcha ! Budete přesměrováni na formulář za 5 sekund!";
		echo ' <meta http-equiv="refresh" content="5;url=shop.php">';
		
	}
}
if(isset($_POST['submit-team']))
{    
	$captcha = $_POST['g-recaptcha-response'];
	$captcha2 = $_POST['g-recaptcha'];
	
	if(validateCaptcha($captcha)){
		
		$currentDate = date('Y-m-d H:i:s');
     	$name = save_replace($_POST['name']);
		$email = $_POST['email'];
		$thumbnail = $_POST['thumbnail'];
		$website = $_POST['website'];
		$type = $_POST['type-action'];
		$description = save_replace($_POST['description']);
		$ip = $_SERVER['REMOTE_ADDR'];
		$discord_server = $_POST['dis-server-id'];
		$discord_user = $_POST['dis-user-id'];

		$sql = "INSERT INTO Team(
				Name,IPAddress,Thumbnail,Website,Type,DiscordServerId,Description,Status,CreateDate,DiscordUserID)
		 		VALUES ('$name','$ip','$thumbnail','$website','$type','$discord_server', '$description', 'PENDING', '$currentDate', '$discord_id')";
		
		if (mysqli_query($conn, $sql)) {
			include("loading2.html");
			echo ' <meta http-equiv="refresh" content="2;url=dashboard.php">';
		} else {
			echo "Error: " . $sql . ":-" . mysqli_error($conn);
		}
			mysqli_close($conn);
		
	} else  {
		echo "Omlouváme se, ale nebyla vyplněna resaptcha ! Budete přesměrováni na formulář za 5 sekund!";
		echo ' <meta http-equiv="refresh" content="5;url=team.php">';
		
	}
}
function save_replace($fvalue) {
	return str_replace("č", "◄cc►",str_replace("ď", "◄cd►", str_replace("'", "◄co►", $fvalue)));
}

?>