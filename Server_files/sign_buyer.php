<?php 
	$fullname = $_POST['full_name'];

	$username = $_POST['user_name'];

	$password = $_POST['password'];

	$mobilenumber = $_POST['mobile_number'];

	$email = $_POST['email'];
	
	/*
	echo $full_name."<br>";
	echo $username."<br>";
	echo $password."<br>";
	echo $mobilenumber."<br>";
	echo $email."<br>";
	*/

	if(isset($fullname) && isset($username)&& isset($password)&& isset($mobilenumber)&& isset($email)){
		
		include("connect.php");
		$country = "Lebanon";
		$seller = false;
		try{
			$pdoQuery = "INSERT INTO `users` (`id`, `name_full`, `username`, `password`, `country`, `mobilenum`, `email`, `is_seller`) VALUES
			(NULL, '$fullname', '$username', '$password', '$country', '$mobilenumber', '$email', '0');";
		
			$result = $handler->exec($pdoQuery);
			
			if($result){
				echo "New record created successfully";
				
			}else{
				
				echo "error";
			}
				
		}
		catch(PDOException $e)
		{
			echo $pdoQuery . "<br>" . $e->getMessage();
		}
	
		
	}else{
		
		print "Error in Submission";
	}

?>