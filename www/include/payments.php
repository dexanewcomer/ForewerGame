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
		$user_id = $myrow['id'];
		$transaction = mysql_real_escape_string(stripslashes($_POST['transaction']));
		$summ =  mysql_real_escape_string(stripslashes($_POST['summ']));
		$comment =  mysql_real_escape_string(stripslashes($_POST['comment']));
	
		
		$result = mysql_query ("INSERT INTO `payments` (`user_id`,`date`,`transaction`,`summ`,`comment`) VALUES (
   	 '" . $user_id . "',
   	 '" . time() . "',
   	 '" . $transaction . "',
   	 '" . $summ . "',
   	 '" . $comment . "')") or die(mysql_error());
    if ($result == 'TRUE')
	{
		
	 $answer['error']   = "false";
     $answer['content'] = "Ваш платёж принят и будет обработан в кратчайшии сроки.";
     $answer['post'] = $_POST;

	}
	

		}
	
	}
	?>
