<html>
<head>
</head>

<body>
    <br>
    <?php 
		$A_name = $_POST['rivalA'];
		$B_name = $_POST['rivalB'];
        $filename ="./result/".$A_name."_".$B_name.".result";
        $fcode = fopen($filename,"r");
        echo fread($fcode, filesize($filename));
    ?>  
    <br>
</form>
<?php }?> 

</body>



</html>
