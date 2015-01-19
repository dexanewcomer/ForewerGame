<?php
if(isset($_POST['login']) && isset($_POST['pass'])) {
    	$login	 = trim($_POST['login']);
    	$pass 	 = trim($_POST['pass']);

    	 $result = mysql_query("SELECT * FROM Users WHERE login='" . mysql_real_escape_string($login) . "' AND pass='" . mysql_real_escape_string($pass) . "'",$db);
	 $myrow = mysql_fetch_array($result);
	if (empty($myrow['id']))
	{
			$answer['error']	= "true";
    	    $answer['pass']  = "false";
	}else{
	$answer['isUser']  = "true";
	$answer['user'] = json_encode($myrow);
	$lot = mysql_real_escape_string(stripslashes($_POST['lot']));
	$result = mysql_query("SELECT * FROM lots WHERE name='" . $lot . "'",$db);
	$myrow = mysql_fetch_array($result);
	if (empty($myrow['id']))
	{
	$answer['error']	= "true";
    	    $answer['content']  = "Лот не найден или был удален Администратором.";
    	    $answer['type']	="popup";
    	    break;
	}
	$answer['price']  = $myrow['price'];

		}

	}
	?>
