<?php
	//Denne filen inkluderes i hver nyhetsfil og oppdaterer en cookie. 
	//Riktig kategori er basert på mottatt kategori fra spørrestrengen.
	//Dermed får kategori og cookie-variabel samme navn. 
	if(isset($_GET['kategori'])){
		$kat = $_GET['kategori'];
		if (  ! isset($_COOKIE[$kat])  ) $antall = 1;
		else $antall = ++$_COOKIE[$kat];  //øker antallet
		
		//Skal uansett lage en cookie
		setcookie($kat, $antall, time()+3600*24*365);	
		echo "Cookie med kategori $kat er oppdatert med en ekstra";
	}  
?>