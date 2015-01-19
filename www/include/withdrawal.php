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
		$summ =  mysql_real_escape_string(stripslashes($_POST['summ']));
		$comment =  mysql_real_escape_string(stripslashes($_POST['comment']));
	
		
		$result = mysql_query ("INSERT INTO `withdrawal` (`user_id`,`date`,`summ`,`comment`) VALUES (
   	 '" . $user_id . "',
   	 '" . time() . "',
   	 '" . $summ . "',
   	 '" . $comment . "')") or die(mysql_error());
    if ($result == 'TRUE')
	{
		
	 $answer['error']   = "false";
     $answer['content'] = "Ваш запрос принят и будет обработан в кратчайшии сроки.";
     $answer['post'] = $_POST;

			}
	
		}
	
	}
	?>

