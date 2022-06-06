
<?php
echo "<h1>Favoritt film</h1>";
  $film = $_GET['t'];
  $besk = $_GET['ta'];
  echo "Din favorittfilm er \"$film\".";
  echo "<br>";
  echo "Og du har valgt $film som din favorittfilm fordi";
  echo "<br>";
  echo $besk;
?>
