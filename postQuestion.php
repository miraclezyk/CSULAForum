<?php
	$con=mysqli_connect("sql110.byethost3.com","b3_14937755","hithere","b3_14937755_ecstforum");
	//$con=mysqli_connect("localhost","root","","csulaforum");
	if (mysqli_connect_errno($con))
	{
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}
	if (isset($_POST['question']) && isset($_POST['courseid']) && isset($_POST['userid'])) {
    $question = $_POST['question'];
    $courseid = $_POST['courseid'];
	$userid = $_POST['userid'];
	
    $sql="INSERT INTO questions(question,courseid,userid) VALUES('$question', '$courseid', '$userid')";
	if(mysqli_query($con,$sql)){
		echo 1;
	}
	else{
		echo 0;
	}
	} else {
		echo "Required field(s) is missing";
	}
	mysqli_close($con);
?>
