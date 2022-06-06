<?php
$utgpunkt = "Det er en fin dag i dag";
echo "$utgpunkt <p>";
//lager en hashet streng
$hashet_streng = md5($utgpunkt);
$strengLengde = strlen($hashet_streng);
echo "Etter md5 har vi fått denne strengen ";
echo " <b>$hashet_streng</b> som er en <b>$strengLengde</b>";
echo " tegn lang streng av heksadesimale tall<br>";
$passordLengde = 8;
//setter startverdi til et tilfeldig sted i strengen, men ikke
//for nær slutten for å sikre at det blir 8 tegn i passordet
$start = rand(0, ($strengLengde - $passordLengde - 1));
//lager selve passordet
$passordet = substr($hashet_streng, $start, $passordLengde);
echo "Passordet som foreslås er:<b> $passordet</b>";
?>
