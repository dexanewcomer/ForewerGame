 <?php
 

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
   $account = account(16,$db);//Вместо 16 можно указать другое число, это количество цифер в номере счета.
    $result = mysql_query ("INSERT INTO Users (`login`,`pass`,`mail`,`phone`,`fullname`,`sex`,`account`,`hash`) VALUES(
    	 '" . mysql_real_escape_string($login) . "',
   	 '" . md5($pass) . "',
   	 '" . mysql_real_escape_string($mail) . "',
   	 '" . mysql_real_escape_string($phone) . "',
   	 '" . mysql_real_escape_string($fullname) . "',
   	 '" . mysql_real_escape_string($sex) . "',
   	 '" . mysql_real_escape_string($account) . "',
   	 '" . $hash . "')");
    if ($result =='TRUE')
	{
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
    	 
    	
?>

