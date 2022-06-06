<?php
if(isset($_GET['slett'])) {
	//slett innholdet i cookien
	foreach ($_COOKIE as $navn=>$verdi) 
		setcookie($navn, 0, time()-1000);  //Tilbake i tid = sletter
	echo "All informasjon er nå slettet";
}
elseif (!empty($_COOKIE)){ 
	//viser cookie-innholdet
	echo "<pre>";
	print_r($_COOKIE);
	echo "</pre>";	  
	//tilbyr sletting 
	echo "<a href='" . $_SERVER['PHP_SELF'] . "?slett=ja'>
				Slett alt cookie-innhold</a>";
}
else
	echo "Ingenting i cookien, derfor blir standard-bilde vist fram";
?>
<br><a href='portalMedReklame.php'>Til forsiden</a>

