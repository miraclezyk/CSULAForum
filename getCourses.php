<?php

	$con=mysqli_connect("localhost","root","","csulaforum");
	if (mysqli_connect_errno($con))
	{
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}
	$major = $_POST['major'];
	$m = ltrim($major);
	//echo strlen($major);
	$q = mysqli_query($con,"select course_name from course where major='$m'");
	try
	{
		while($r1=mysqli_fetch_array($q)){
			echo $r1['course_name'].",";
		}
	}
	catch(Exception $e)
	{
		echo 'Caught exception: ',  $e->getMessage(), "\n";
	}
	mysqli_close($con);
?>
