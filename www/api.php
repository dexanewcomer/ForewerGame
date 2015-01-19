<?php
    header('Content-type: text/html; charset=utf-8');
    ini_set ("default_charset", "utf-8");
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
	function account($num=16,$db){// По умолчанию 16 цефр

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
    	include "include/registration.php";
    break;
    	 case "login"://Вход
		 include "include/login.php";
   break;
		case "withdrawal":
		include "include/withdrawal.php";
	break;
		case "pay":
		include "include/payments.php";
	break;
		case "price":
		include "include/price.php";
	break;
		case "priceall":
		include "include/allprice.php";
	break;






    }

   	 echo json_encode($answer);

    }


    ?>
