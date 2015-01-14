<?php
    header('Content-type: text/html; charset=utf-8');
    $db = mysql_connect ("localhost","root","sqlPWD(84)") or die(mysql_error());
    mysql_select_db ("dexa_db",$db)or die(mysql_error());
	mysql_set_charset( 'utf8' );
    function hashgen() {
    $alphabet = array("A","B","C","D","E","F","0","1","2","3","4","5","6","7","8","9");
    for ($i = 0; $i < 32; $i++) {
        $n = rand(0, count($alphabet)-1);
        $hash[$i] = $alphabet[$n];
    }
    return implode("", $hash);
}
	function account($num=16,$db){// По умолчанию 16
	
	$nums = array(0,1,2,3,4,5,6,7,8,9,);	
	for ($i = 0; $i < $num; $i++) {
        $n = rand(0, count($nums)-1);
        $account[$i] = $nums[$n];
    }
      $result = mysql_query("SELECT id FROM `Users` WHERE `account`='" . implode("", $account) . "'",$db);
      $myrow = mysql_fetch_array($result);
      while(!empty($myrow['id'])) {
      	for ($i = 0; $i < $num; $i++) {
        $n = rand(0, count($nums)-1);
        $account[$i] = $nums[$n];
    }
           $result = mysql_query("SELECT id FROM `Users` WHERE `account`='" . implode("", $account) . "'",$db);
      $myrow = mysql_fetch_array($result);
      }
      return implode("", $account);

	}
          $answer = array();
    if(isset($_POST['act']) ){  
    if(isset($_POST['login']) && isset($_POST['pass'])) {//Если пришло в посте имя и пасс то обновляем переменные
    	$login	 = trim($_POST['login']);
    	$pass 	 = trim($_POST['pass']);
    }
    switch($_POST['act']){
    default:// В случае поступления неизвесто чего.
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
		 $answer['user'] = json_encode($myrow);
	

	
	
	}
    	 break;
	case "pay":

		 $answer = $_POST;
	break;

    	 
    	 
    
    
    }
    
   	 echo json_encode($answer); 
    
    }
    
    
    ?>
