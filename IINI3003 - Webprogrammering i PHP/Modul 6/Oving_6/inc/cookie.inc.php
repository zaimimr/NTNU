
<?php
	//Denne filen inkluderes i hver nyhetsfil og oppdaterer en cookie.
	//Riktig kategori er basert p� mottatt kategori fra sp�rrestrengen.
	//Dermed f�r kategori og cookie-variabel samme navn.
  $self =  $_SERVER['PHP_SELF'];
  $name =  isset($_POST['name']) ? $_POST['name'] : false;
  $color = isset($_POST['color']) ? $_POST['color'] : false;
  $size =  isset($_POST['size']) ? $_POST['size'] : false;
  $font = isset($_POST['font']) ? $_POST['font'] : false;

	if($name && $color && $size && $font){
		//Skal uansett lage en cookie
		setcookie("Name", $name, time()+3600);
		setcookie("Font", $font, time()+3600);
		setcookie("Size", $size, time()+3600);
		setcookie("Color", $color, time()+3600);
		echo "Cookie med registrering er oppdatert";
	}
?>
