<html>
<head>
</head>

<body>
<?php
  echo "saved.";
  if(isset($_POST['name']) && isset($_POST['code'])){
    $name=$_POST['name'];
    $code=$_POST['code'];
    
    $file = fopen("./source/$name.c", "w+");
    fwrite($file, $code);
  }
   
  else{
?>
<form method="post" action="<?php echo $_POST['PHP_SELF']?>">
    <label for="name">name</label>
    <input type="text" name="name" id="name">
    <br>
    <label for="code">code</label>
    <br>
    <textarea rows="30" cols="50" name="code" id="code">
    <?php 
        $filename ="./core/template/mookzzibba/mookzzibba_player.c";
        $fcode = fopen($filename,"r");
        echo fread($fcode, filesize($filename));
    ?>  
    </textarea>
    <br>
    <input type="submit" value="submit">
</form>
<?php }?> 

</body>



</html>
