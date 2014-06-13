<?php
	$con=mysqli_connect("sql110.byethost3.com","b3_14937755","hithere","b3_14937755_ecstforum");
	//$con=mysqli_connect("localhost","root","","csulaforum");
	if (mysqli_connect_errno($con))
	{
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}
	if (isset($_POST['questionid']) && isset($_POST['userid']) && isset($_POST['courseid']) && isset($_POST['reply'])) {
    $questionid = $_POST['questionid'];
    $userid = $_POST['userid'];
	$courseid = $_POST['courseid'];
	$reply = $_POST['reply'];
	
    $sql="INSERT INTO replies(question_id,user_id,course_id,reply) VALUES('$questionid', '$userid', '$courseid', '$reply')";
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
