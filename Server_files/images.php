<?php 
	
	header('Content-type : bitmap; charset=utf-8');
	
	
	
	//Posted Values from the android application
	
	$enc_string = $_POST["encoded_string"];
	$dis_title = $_POST["title"];
	$dis_description = $_POST["description"];
	$dis_category = $_POST["category"];
	$dis_percent = $_POST["percent"];
	$dis_proname = $_POST["proname"];
	$dis_fromprice = $_POST["fromprice"];
	$dis_afterprice = $_POST["afterprice"];
	$seller_name = $_POST["seller_name"];
	
	
	
	if(isset($enc_string) && isset($dis_title) && isset($dis_description) && isset($dis_category) && isset($dis_percent)
&& isset($dis_proname) && isset($dis_fromprice) && isset($dis_afterprice) && isset($seller_name) ) {
	
		$decoded_string = base64_decode($enc_string);
		$image_name = $dis_proname.'.jpg';
		$path = 'images/'.$image_name;
		
		$file = fopen($path, 'wb');
		
		$is_written = fwrite($file, $decoded_string);
		fclose($file);
		
		if($is_written > 0) {
			include("connect.php");
			$id_sale = 2;
			$price_ini = intval($dis_fromprice);
			$price_after= intval($disafterprice);
			$dis_percent = intval($dis_percent);
			$id_seller =1;
			$id_cat =1;
			$pdoQuery = "INSERT INTO `discounts` (`seller_id`,`cat_id`, `sale_title` ,`sale_desc`, `sale_perc`,`item_name`,`from_price` ,`to_price`, `image_path`) 
			VALUES (:id_seller, :id_cat, :dis_title , :dis_description , :dis_percent,:dis_proname, :price_ini , :price_after , :path )";
			
			$pdoResult = $handler->prepare($pdoQuery);
			$pdoExec = $pdoResult->execute(array(":id_seller"=>$id_seller,":id_cat"=>$id_cat,":dis_title"=>$dis_title,
			":dis_description"=>$dis_description, ":dis_percent"=>$dis_percent,":dis_proname"=>$dis_proname,":price_ini"=>$price_ini,
			":price_after"=>$price_after, ":path"=>$path));
			
			if($pdoExec)
			{
				echo 'Accepted, discount added ';
			}else{
				echo 'Error in regesitering Sale';
			}
			
		}else{
			print "error saving image on server";
		}
		
	}else{
		print "not set";
	}
?>