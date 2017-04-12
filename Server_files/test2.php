<?php 
	
	header('Content-type : bitmap; charset=utf-8');
	
	
	
	$dis_title = "sami";
	$dis_description = "jdsnv ifdjsv ds dcdslknv";
	$dis_category = "jfdnalnfds dfk";
	$dis_percent = 20;
	$dis_proname = "sami12";
	$dis_fromprice = "15";
	$dis_afterprice = "12";
	$path = "hello";
	//Posted Values from the android application
	
	
		
			include("connect.php");
			$id_sale = 2;
			$price_ini = intval($dis_fromprice);
			$price_after= intval($disafterprice);
		
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
				
		
		
	
?>