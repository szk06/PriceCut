<?php 
	
		
		include("connect.php");
		$path = 'images/'."sami";
		
		
		
		
			
			$id_sale = 2;
			$pric_ini = 50;
			$price_after = 20;
			$image_name = "luckaku";
			$pdoQuery = "INSERT INTO `sales_example` (`sale_id`,`example_title`, `from_price` ,`to_price`, `image_path`) 
			VALUES (:id_sale, :image_name, :pric_ini , :price_after , :path)";
			$pdoResult = $handler->prepare($pdoQuery);
			$pdoExec = $pdoResult->execute(array(":id_sale"=>$id_sale,":image_name"=>$image_name,":pric_ini"=>$pric_ini,
		":price_after"=>$price_after, ":path"=>$path));
			
			
			if($pdoExec)
			{
				echo "Accepted, User: $myusername registered ";
			}else{
				echo 'Error in regesitering user';
			}		
		
		
		
	
?>