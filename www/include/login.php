<?php

  	    if(isset($_POST['login']) && isset($_POST['pass'])) {//Если пришло в посте имя и пасс то обновляем переменные
    	$login	 = trim($_POST['login']);
    	$pass 	 = md5(trim($_POST['pass']));
  	  }
    	 $result = mysql_query("SELECT * FROM Users WHERE login='" . mysql_real_escape_string($login) . "' AND pass='" . mysql_real_escape_string($pass) . "'",$db);
	 $myrow = mysql_fetch_array($result);
	if (empty($myrow['id']))
	{
			$answer['error']	= "true";
    	    $answer['content']  = "Пароль или имя пользователя введены не верно.";
    	    $answer['type']	="pass";
    	    break;
	}else{
	 $_SESSION['login'] =  $login;
	 $_SESSION['pass'] = $pass;
	 $answer['error']   = "false";
     $answer['content'] = "Вы успешно авторизованны";
     $answer['type']    = "popup";
	 $answer['isUser']  = "true";
	 $answer['user'] = json_encode($myrow);
	}
	?>
