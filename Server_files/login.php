<?php 
	$username = $_POST['user_name'];
	$password = $_POST['password'];
	if(isset($username) && isset ($password)){
		include("connect.php");
		$row = $handler -> query("SELECT * FROM `users` WHERE `username`='$username'");
		foreach ($row as $elm){
			$data_pass = $elm['password'];
			if($data_pass==$password){
				$type = $elm['is_seller'];
				print $type;
			}
			else{
				print "Wrong Credentials";
			}
		}
	}else{
		print "error in submission";
	}
?>