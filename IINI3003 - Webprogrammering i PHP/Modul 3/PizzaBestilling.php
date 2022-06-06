<?php
  echo "<br><b>Oppgave 2)</b> <br>";
  $ing = array ('Lok',
  'Sopp',
  'Gulrot',
  'Skinke',
  'Pepperoni',
  'Ost',
  'Saus',
  'Biff',
  'Kylling',
  'Ananas',
  'Pizza'
  );
  echo "Ingredienser:";
  echo "<form method='post' action='PizzaOrdre.php'>";
  foreach($ing as $ingNavn){
    echo "<input type='checkbox' name='ing[]' value='" .
    $ingNavn . "'> " . $ingNavn . "<br>";
  }
  echo "<br>";
  echo "Plukketid: ";
  $dagen_i_dag = date("l"); //liten L, ikke ett-tall

  if ($dagen_i_dag == "Sunday") {
    $start = 0;
    $slutt = 0;
  }elseif($dagen_i_dag == "Saturday"){
    $start = 10;
    $slutt = 18;
  } else {
    $start = 7;
    $slutt = 21;
  }
  echo "<select name='plukkopp'>";
  if($start == 0 && $slutt == 0) {
   echo "<option value='0'>Stengt idag</option>";
  }
  for($i = $start; $i <$slutt; $i++){
    for($j = 0; $j < 4; $j++){
      echo "\t<option value='";
      if($i < 10) $nuller = "0";
      else $nuller = "";
      $min = ":" . $j*15;
      if($min == ":0") $tid = $nuller . $i . $min . "0";
      else $tid = $nuller . $i . $min;
      echo "$tid'>$tid</option>\n";
    }
  }
  echo "";
  echo"</select><br><input type='submit'></form>";
?>
