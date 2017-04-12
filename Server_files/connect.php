<?php

	$mysqlhost = "localhost";
	$user= "root";
	$dbname1 = "imagine";
	$sqlpass='';
	$handler = new PDO("mysql:host=$mysqlhost;dbname=$dbname1", $user, $sqlpass);
	
?>