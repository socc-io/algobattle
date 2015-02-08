<!DOCTYPE html>

<html>
	<head>
	  <link rel="stylesheet" type="text/css" href="./style.css" />
	  <title>ALGO-BATTLE</title>
	</head>
	
	<body>
	  <div id="console">
		Welcome to AlGO-BATTLE v0.0.1(2015.02.01) <br>
		<br>
		<pre>       ;     ::       :111:    ;11;             :;;;;      :   ;;;;;;;;;;;;;;;;  ;       ;;;;;:</pre>   
		<pre>      1&$    00      $&0$08  '0&$$&0'          2&0088;    0&|  :888&88$$88&888: ;&:     ;&88881</pre>
		<pre>      08&:   00     $&;      08:  :&$          2&   80   ;&&$     1&'    '&1    ;&:     ;&;    </pre>
		<pre>     ;&;&2   00    '&2      :&1    1&'         2&   8$   $$1&:    1&     '&1    ;&:     ;&;    </pre>
		<pre>     $$ 28   00    :&1  ''' ;&|    |&;  1111'  2&$$88'  '&; &2    1&     '&1    ;&:     ;&8002 </pre>
		<pre>    :&1:1&|  00    :&1  08&|:&|    |&;  2222:  2&|;18$  1&;:08    1&     '&1    ;&:     ;&1||; </pre> 
		<pre>    1&000&0  00    '&2   :&|'&1    2&'         28   1&: 88000&1   1&     '&1    ;&:     ;&:    </pre>
		<pre>    82   1&: 00     2&|  :&| $&;  |&2          2&' '$& ;&:   00   1&     '&1    ;&;     ;&;    </pre>
		<pre>   |&:    82 $&8880  2&888&;  $&00&2           2&8888| $$    1&:  1&     '&1    ;&8888; :&88882</pre>
		<pre>    '     '' '::::'   ';;:     ';;'             :::'   '      '    '      '      :::::   :::::'</pre>
		<br>
		<pre> * Documentation: <a href="https://github.com/becxer/algobattle">https://github.com/becxer/algobattle</a></pre>
		<br>

	  <?php
			$path = "./source";
			$files = scandir($path);
		
			echo count($files)-2 . " sources are updates.<br>";
	  ?>

	  <script language="JavaScript" type="text/JavaScript">
		now = new Date();
		localtime = now.toString();
		document.write("<br>Local time : " + localtime + "<br>");
	  </script>
	  
	  <br>
 	  <a href="#" onclick="show('commit'); return false;" />Commit Source Code</a><br>
	  <a href="#" onclick="show('battle'); return false;" />Match Battle </a><br>
	  </div>


<!-- COMMIT Page -->
<?php
  if(isset($_POST['name']) && isset($_POST['code'])){
	$name=$_POST['name'];
	$code=$_POST['code'];
	
	$file = fopen("./source/$name.c", "w+");
	fwrite($file, $code);

	echo "<script> document.location.pathname='/algobattle/';</script>";
  }
   
  else{
?>
	  <div id="commit" style="display:none;">
	  	<form method="post" action="<?php echo $_POST['PHP_SELF']?>">
	  		<br>
			<label for="name">name</label>
			<input type="text" name="name" id="name">
			<br>
			<label for="code">code</label>
			<br>
			<textarea rows="35" cols="110" name="code" id="code">
	<?php 
        $filename ="./core/template/mookzzibba/mookzzibba_player.c";
        $fcode = fopen($filename,"r");
        echo fread($fcode, filesize($filename));
    ?>
</textarea>
			<br>
			<input type="submit" value="submit">
		</form>
	  </div>
<?php }?>


<!--Match Battle -->
<?php
if(isset($_POST['rivalA']) && isset($_POST['rivalB'])){
  if(!empty($_POST['rivalA']) && !empty($_POST['rivalB'])){
	$rivalA=$_POST['rivalA'];
	$rivalB=$_POST['rivalB'];
	
	$res = exec("node ./core/algobattle.js $rivalA $rivalB");
	$filename ="./result/".$rivalA."_".$rivalB.".result";
    $fcode = fopen($filename,"r");
    echo "<pre>".fread($fcode, filesize($filename))."</pre>";

  }	
  else{
	echo '<script>alert("파일명을 입력해주세요.");</script>';
	//echo "<script> document.location.href='/algobattle/lobby.php'; </script>";
  }
}
else{
	echo "<div id='battle' style='display:none'>";
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
	<br>
	<form method="post" action="<?php echo $_POST['PHP_SELF'];?>">
	  <label for="rivalA">A: </label>
	  <input type="text" name="rivalA" id="rivalA">
	  <br>
	  <label for="rivalB">B: </label>
	  <input type="text" name="rivalB" id="rivalB">
	  <br><br>
	  <input type="submit" value="submit">
	</form>
</div>
<?php }?>
	</body>
</html>


<script type="text/javascript">
function show(idMyDiv){
     var objDiv = document.getElementById(idMyDiv);

     if(objDiv.style.display=="block") objDiv.style.display = "none";
     else objDiv.style.display = "block"; 
}
</script>
