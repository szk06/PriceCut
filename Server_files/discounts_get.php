<?php

	function haversineGreatCircleDistance($latitudeFrom, $longitudeFrom, $latitudeTo, $longitudeTo, $earthRadius = 6371000)
	{
	  // convert from degrees to radians
	  $latFrom = deg2rad($latitudeFrom);
	  $lonFrom = deg2rad($longitudeFrom);
	  $latTo = deg2rad($latitudeTo);
	  $lonTo = deg2rad($longitudeTo);

	  $latDelta = $latTo - $latFrom;
	  $lonDelta = $lonTo - $lonFrom;

	  $angle = 2 * asin(sqrt(pow(sin($latDelta / 2), 2) +
		cos($latFrom) * cos($latTo) * pow(sin($lonDelta / 2), 2)));
	  return $angle * $earthRadius;
	}
	
	
	$user_name = $_POST["user_name"];
	$latitude = $_POST["latitude"];
	$longitude = $_POST["longitude"];
	$distance_thresh= $_POST["distance_thresh"];
	
	
	if(isset($user_name) && isset($latitude)&& isset($longitude) ){
	
		include("connect2.php");
		$json_array1 = array();
		$sql = "SELECT *  FROM `discounts`";
		$result = mysqli_query($connect,$sql);
		
		while($row = mysqli_fetch_assoc($result)){
			
			
			$my_arr = array();
			$sel_id = $row['seller_id'];
			
			$path = $row['image_path'];
			$type = pathinfo($path, PATHINFO_EXTENSION);
			$data = file_get_contents($path);
			$base64 = 'data:image/' . $type . ';base64,' . base64_encode($data);
			
			$query = "SELECT * FROM `sellers` WHERE id='$sel_id'";
			$result2 = mysqli_query($connect,$query);
			$row2 =mysqli_fetch_assoc($result2);
			
			
			$mycat_id = $row['cat_id'];
			
			$query2 = "SELECT * FROM `categories` WHERE id = '$mycat_id'";
			$result3 = mysqli_query($connect,$query2);
			$row3 =mysqli_fetch_assoc($result3);
			
			$sale_cat = $row3['cat_name'];
			
			$query4 = "SELECT * FROM `users` WHERE id ='$sel_id'";
			$result4 = mysqli_query($connect,$query4);
			$row4 =mysqli_fetch_assoc($result4);
			
			$seller_name = $row4['username'];
			$seller_latitude = $row2['loca1'];
			$seller_longitude = $row2['loca2'];
			$seller_distance = haversineGreatCircleDistance($seller_latitude,$seller_longitude
			,$longitude,$latitude)/100;
			$seller_distance = round($seller_distance, 3);
			
			$my_arr["dis_id"] = $row['id'];
			$my_arr["sale_title"] = $row['sale_title'];
			$my_arr["sale_desc"] = $row['sale_desc'];
			$my_arr["sale_rating"]= $row['rating'];
			$my_arr["sale_cat"]= $sale_cat;
			$my_arr["seller_name"] = $seller_name;
			$my_arr["sale_perc"] = $row['sale_perc'];
			$my_arr["image_path"] = $row['image_path'];
			$my_arr["latitude"] = $seller_latitude;
			$my_arr["distance"]= $seller_distance;
			$my_arr["longitude"] = $seller_longitude;
			$my_arr["path"]= $path;
			
			$json_array1[] = $my_arr;
		}		
		$output = json_encode(array('discounts' => $json_array1));
		print $output;	
	}else{
		
		print "Not set";
	}
	
	

?>