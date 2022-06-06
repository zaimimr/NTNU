<?php
  echo "<b>Øving 2</b> <br>";
  echo "<b>Oppgave 1)</b> <br>";
  echo "<table border ='1'>";
  for($x=0; $x<10; $x++){
    echo "<tr>";
    if($x != 0){
      echo "<td><b> $x </b></td>";
    }else{
      echo"<th> Gangetabell </th>";
    }
    for ($y=1; $y<10; $y++){
      if($x == 0){
        echo "<th>$y</th>";
      }else{
        echo "<td>";
        echo "$y x $x = <b>" . $x*$y . "<b>";
        echo "</td>";
      }
    }
    echo "</tr>";
  }
  echo "</table>";
?>

<?php
  echo "<br><b>Oppgave 2)</b> <br>";
  $ing = array (1 => 'Lok',
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
  echo "<form>";
  foreach($ing as $ingNr => $ingNavn){
    echo "<input type='checkbox' name='ing' value=" .
    $ingNr . "> - " . $ingNavn . "<br>";
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
  echo "<form> \n <select name='plukkopp'>";
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
  echo"</select></form>";
?>

<?php
  echo "<br><b>Oppgave 3)</b> <br>";
  $nrDay = array(1 => 'first',
    'second',
    'third',
    'fourth',
    'fifth',
    'sixth',
    'seventh',
    'eight',
    'ninth',
    'tenth',
    'eleventh',
    'twelfth');
  for ($teller=1; $teller<=12; $teller++){
   echo "<p>On the $nrDay[$teller] day of Christmas<br />";
   echo "my true love sent to me: <br />";
   switch($teller) {
     case 12:
       echo "12 Drummers Drumming<br />";
     case 11:
       echo "11 Pipers Piping<br />";
     case 10:
       echo "10 Lords a Leaping<br />";
     case 9:
       echo "9 Ladies Dancing<br />";
     case 8:
       echo "8 Maids a Milking<br />";
     case 7:
       echo "7 Swans a Swimming<br />";
     case 6:
       echo "6 Geese a Laying<br />";
     case 5:
       echo "5 Golden Rings<br />";
     case 4:
       echo "4 Calling Birds<br />";
     case 3:
       echo "3 French Hens<br />";
     case 2:
       echo "2 Turtle Doves<br /> And ";
     case 1:
       echo "a Partridge in a Pear Tree<br />";
   }
   echo "</p>";
   echo "<hr width=40 align=left />";
  }
?>

<?php
  echo "<br><b>Oppgave 4)</b> <br>";
  for($x=1; $x<10; $x++){
    for($y=1; $y<10; $y++){
      if($y<=$x) echo "$y ";
    }
    echo"<br>";
  }
?>

<?php
  echo "<br><b>Oppgave 5)</b> <br>";
  $hexNr = array('00',
    '33',
    '66',
    '99',
    'CC',
    'FF'
  );
  echo "<table  border=1 width=200 >";
  for ($x=0; $x < 6; $x++) {
    echo "<tr>";
    for ($y=0; $y < 6; $y++) {
      echo "<tr>";
      for ($z=0; $z < 6; $z++) {
        if($x < 3) echo
        "<td bgcolor='#$hexNr[$x]$hexNr[$y]$hexNr[$z]'>
          <font color='white'>
            $hexNr[$x]$hexNr[$y]$hexNr[$z]
          </font>
        </td>";
        else echo
        "<td bgcolor='#$hexNr[$x]$hexNr[$y]$hexNr[$z]'>
          <font color='black'>
            $hexNr[$x]$hexNr[$y]$hexNr[$z]
          </font>
        </td>";
      }
      echo "</tr>";
    }
    echo "</tr>";
  }
  echo "</table>";
?>
