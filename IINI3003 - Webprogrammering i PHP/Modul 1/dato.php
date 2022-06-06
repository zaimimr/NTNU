<html><body>
<?php
 echo "Dagens dato er... ";
 echo date("d.m.Y");
 echo "<br>";

 $navn = "Kari Olsen";
 $alder = 23;
 echo "Alder til " .
 $navn .
 " er " .
 $alder .
 " år";
 echo "<br>";

 echo "<a href='lenke.html'>Her er en lenke 1</a>"; //tolker variabler
 echo "<br>";

 echo '<a href="lenke.html">Her er en lenke 2</a>'; //ingen tolkning
 echo '<br>';

 $tall = 24;
 echo $tall ++;
 echo $tall;
 echo $tall /= 2;
 echo $tall;
 echo "<br>";


?>
</body></html>
