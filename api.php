<?php
  
    $db = mysql_connect ("localhost","root","sql_password");
    mysql_select_db ("dexa_db",$db);
    function hashgen() {
    $alphabet = array("A","B","C","D","E","F","0","1","2","3","4","5","6","7","8","9");
    for ($i = 0; $i < 32; $i++) {
        $n = rand(0, count($alphabet)-1);
        $hash[$i] = $alphabet[$n];
    }
    return implode("", $hash);
}
          $answer = array();
    if(isset($_POST['act']) ){  
    if(isset($_POST['login']) && isset($_POST['pass'])) {//Если пришло в посте имя и пасс то обновляем переменные
    	$login	 = trim($_POST['login']);
    	$pass 	 = trim($_POST['pass']);
    }
    switch($_POST['act']){
    default:// В случае поступления немзвесто чего.
    $answer['error']	= "true";
    $answer['content']  = "Не известное действие, возможно версия приложения устарела";
    $answer['type']	="popup";
    break;
    case "registration"://Регистрация

    $cpass 	 = trim($_POST['cpass']);
    $mail 	 = trim($_POST['mail']);
    $phone 	 = trim($_POST['phone']);
    $fullname 	 = trim($_POST['fullname']);
    $sex	 = trim($_POST['sex']);
    if($login != trim(htmlspecialchars($_POST['login'])))
    	{
    		 $answer['error']   = "true";
    		 $answer['content'] = "В логине содержатся не допустимые символы";
    		 $answer['type']    = "login";
    		 break;
    	}
      $result = mysql_query("SELECT id FROM `Users` WHERE `login`='" . mysql_real_escape_string($login) . "'",$db);
      $myrow = mysql_fetch_array($result);
	if (!empty($myrow['id'])) {
		 $answer['error']   = "true";
    		 $answer['content'] = "Такой пользователь уже зарегистрирован";
    		 $answer['type']    = "login";
    		 break;
	}
	 $result = mysql_query("SELECT id FROM `Users` WHERE `mail`='" . mysql_real_escape_string($mail) . "'",$db);
      $myrow = mysql_fetch_array($result);
	if (!empty($myrow['id'])) {
		 $answer['error']   = "true";
    		 $answer['content'] = "Такой E-mail уже зарегистрирован";
    		 $answer['type']    = "mail";
    		 break;
	}
    if(trim(htmlspecialchars($pass)) != trim(htmlspecialchars($cpass))){
    	    	 $answer['error']   = "true";
    		 $answer['content'] = "Введенные пароли не совпадают";
    		 $answer['type']    = "cpass";
    		 break;
    
    }
    $regex = "/^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/";
     if (! preg_match( $regex, $mail ) ) {
    		 $answer['error']   = "true";
    		 $answer['content'] = "Подозрительный E-mail, проверте введеные данные.";
    		 $answer['type']    = "mail";
    		 break;
    
   } 
   $hash = hashgen();
    $result = mysql_query ("INSERT INTO Users (`login`,`pass`,`mail`,`phone`,`fullname`,`sex`,`hash`) VALUES(
    	 '" . mysql_real_escape_string($login) . "',
   	 '" . md5($pass) . "',
   	 '" . mysql_real_escape_string($mail) . "',
   	 '" . mysql_real_escape_string($phone) . "',
   	 '" . mysql_real_escape_string($fullname) . "',
   	 '" . mysql_real_escape_string($sex) . "',
   	 '" . $hash . "')");
    if ($result =='TRUE')
	{
		mail($mail,"Подтверждение регистрации","Тут такая хрень, мыло на которое пришло это сообщение было указанно при регистрации на 		каком-то сервере, для подтверждения ригистрации перейдите по ссылке <a  href=\"http://" . $_SERVER['HTTP_HOST'] . "/api.php?mail=" . 		$mail . "&hash=" . $hash . "\">http://" . $_SERVER['HTTP_HOST'] . "/api.php?mail=" . $mail . "&hash=" . $hash . "</a>","Content-type:text/plain; Charset=windows-1251\r\n");
		 $answer['error']   = "false";
    		 $answer['content'] = "Вы успешно зарегистрированны, на Ваш E-mail отправленно письмо с сылкой для активации аккаунта.";
    		 $answer['type']    = "mail";
    		 break;
	}

	else {
	    $answer['error']	= "true";
    	    $answer['content']  = "Не известная ошибка, возможно версия приложения устарела или не поладки на сервере.";
    	    $answer['type']	="popup";
    	    break;
    	 }
    	 
    	 case "login"://Вход
    	 if(isset($_COOKIE['login']) && isset($_COOKIE['pass'])){
  	$pass = $_COOKIE['pass'];
  	$login = $_COOKIE['login'];
  	}
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
	 setcookie ("login", $login,time()+3600);
	 setcookie ("pass", $pass,time()+3600);
		 $answer['error']   = "false";
    		 $answer['content'] = "Вы успешно авторизованны";
    		 $answer['type']    = "popup";
		 $answer['isUser']  = "true";
	
	
	}
    	 
    	 
    	 
    
    
    }
    
   	 echo json_encode($answer); 
    
    }
    
    
    ?>
