<?php
		
				include("connect2.php");
		$json_array1 = array();
		$sql = "SELECT *  FROM `discounts`";
		$result = mysqli_query($connect,$sql);
		$count =0;
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
			
			
			$my_arr["dis_id"] = $row['id'];
			$my_arr["sale_title"] = $row['sale_title'];
			$my_arr["sale_desc"] = $row['sale_desc'];
			$my_arr["sale_rating"]= $row['rating'];
			$my_arr["sale_cat"]= $sale_cat;
			$my_arr["seller_name"] = $seller_name;
			$my_arr["sale_perc"] = $row['sale_perc'];
			$my_arr["image_path"] = $row['image_path'];
			$my_arr["latitude"] = $seller_latitude;
			$my_arr["longitude"] = $seller_longitude;
			$my_arr["image_64"]= $base64;
			
			
			$json_array1[] = $my_arr;
			$count ++;
		}		
		$output = json_encode(array('discounts' => $json_array1));
		//print $output;	
		?>