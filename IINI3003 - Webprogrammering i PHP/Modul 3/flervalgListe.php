<?php
  $interesser = array("TV",
    "Sport",
    "Mat",
    "Kunst",
    "Friluft",
    "Programmering",
    "Fisking",
    "Svømming"
  );
  echo "<form action='flervalgListeSvar.php' method='get'>";
  echo "<p><b>Hva er ditt navn?</b></p>";
  echo "<input type='text' name='name'>";
  echo "<br>";
  echo "<p><b>Velg flere idretter</b></p>";
  echo "<select name='liste[]' multiple>";
  foreach ($interesser as $item) {
    echo "<option value=\"$item\">$item</option>";
  }
  echo "</select>";
  echo "<br>";
  echo "<br>";
  echo "<input type='submit'>";
?>
