<?php
	$con=mysqli_connect("localhost","root","","csulaforum");
	if (mysqli_connect_errno($con))
	{
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}
	$course = $_POST['coursename'];
	//$m = ltrim($major);
	//echo strlen($major);
	$q = mysqli_query($con,"select question,userid from questions where courseid=(select courseid from course where course_name='$course')");
	$sql = mysqli_query($con,"select courseid from course where course_name='$course'");
	try
	{
		while($row=mysqli_fetch_array($sql)){
			echo $row['courseid'].";";
		}		

		while($r1=mysqli_fetch_array($q)){
			echo $r1[0].":";
			$qq = mysqli_query($con,"select fullname from user where userid=$r1[1]");
			$r2=mysqli_fetch_array($qq);
			echo $r2['fullname'].",";
		}
	}
	catch(Exception $e)
	{
		echo 'Caught exception: ',  $e->getMessage(), "\n";
	}
	mysqli_close($con);
?>
