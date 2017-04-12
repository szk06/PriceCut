<?php 

	$fullname = $_POST['full_name'];
	$username = $_POST['user_name'];
	$password = $_POST['password'];
	$mobilenumber = $_POST['mobile_number'];
	$email = $_POST['email'];
	$description = $_POST['description'];
	$latitude = $_POST['latitude'];
	$longitude = $_POST['longitude'];
	
	if(isset($fullname) && isset($username)&& isset($password)&& isset($mobilenumber)&& 
	isset($email)&& isset($description)&& isset($latitude)&& isset($longitude)){
		include("connect.php");
		$user_registered = false;
		$country = "Lebanon";
		$seller = true;
		try{
			$pdoQuery = "INSERT INTO `users` (`id`, `name_full`, `username`, `password`, `country`, `mobilenum`, `email`, `is_seller`) VALUES
			(NULL, '$fullname', '$username', '$password', '$country', '$mobilenumber', '$email', '1');";
			$result = $handler->exec($pdoQuery);
			if($result){
				$user_registered = true;	
			}else{
				$user_registered = false;
				print "Error pre registration";
			}
		}catch (PDOException $e){
			echo $pdoQuery . "<br>" . $e->getMessage();
		}
		if($user_registered==true){
			$seller_id = $handler->query("SELECT `id` FROM users WHERE username='$username'");
			foreach ($seller_id as $elem1){
				$display_id = $elem1['id'];
			}
			
			try{
				$pdoQuery = "INSERT INTO `sellers` (`id`, `loca1`, `loca2`, `seller_desc`) VALUES
				($display_id, '$latitude', '$longitude', '$description');";
				$result = $handler->exec($pdoQuery);
				if($result){
					print("Registration ok");	
				}else{
					print("Error in seller");
				}	
			}catch (PDOException $e){
				echo $pdoQuery . "<br>" . $e->getMessage();
			}		
		}
	}else{

		print "Error in Submission";
	}

?>