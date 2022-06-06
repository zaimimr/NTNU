<?php
function selectFont(){
  $interesser = array("Arial",
    "Times New Roman",
    "Callibri",
  );
  echo "<div>";
  echo "<label>Font </label>";
  echo "<select name='font'>";
  foreach ($interesser as $item) {
    echo "<option value=\"$item\">$item</option>";
  }
  echo "</select>";
  echo "</div>";
}

function selectColor(){
  $interesser = array("RED",
    "GREEN",
    "BLUE",
  );
  echo "<div>";
  echo "<label>Color </label>";
  echo "<select name='color'>";
  foreach ($interesser as $item) {
    echo "<option value=\"$item\">$item</option>";
  }
  echo "</select>";
  echo "</div>";
}


?>
