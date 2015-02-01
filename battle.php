<html>
<head>
</head>
<meta charset="utf-8">
<body>
<?php
if(isset($_POST['rivalA']) && isset($_POST['rivalB'])){
  if(!empty($_POST['rivalA']) && !empty($_POST['rivalB'])){
	$rivalA=$_POST['rivalA'];
	$rivalB=$_POST['rivalB'];
	
	$res = exec("node ./core/algobattle.js $rivalA $rivalB");
	echo("".$res);

  }	
  else{
	echo '<script>alert("파일명을 입력해주세요.");</script>';
	//echo "<script> document.location.href='/algobattle/lobby.php'; </script>";
  }
}
else{
		
	if($dirname="./source"){
		$data_list = @opendir($dirname);
		echo '<ul>';
		while(($filename = readdir($data_list))!== false){
			if($filename!="." && $filename !=".."){
				echo '<li>';
				echo $filename;
				echo '</li>';
			}	
		}
		echo '</ul>';
		
	}
	else{
		die("Error : directory.");	
	}
	?>
	<form method="post" action="<?php echo $_POST['PHP_SELF'];?>">
	  <label for="rivalA">A: </label>
	  <input type="text" name="rivalA" id="rivalA">
	  <br>
	  <label for="rivalB">B: </label>
	  <input type="text" name="rivalB" id="rivalB">
	  <br><br>
	  <input type="submit" value="submit">
	</form>
<?php }?>
</body>
</html>
