<?php
  $ImageList = array('Lok' => 'lok.jpg',
    'Sopp'=> 'sopp.jpg',
    'Gulrot'=> 'gulrot.jpg',
    'Skinke'=> 'skinke.jpg',
    'Pepperoni'=> 'pepperoni.jpg',
    'Ost'=> 'ost.jpg',
    'Saus'=> 'saus.jpg',
    'Biff'=> 'biff.jpg',
    'Kylling'=> 'kylling.jpg',
    'Ananas'=> 'ananas.jpg',
    'Pizza'=> 'pizza.jpg');
?>
<?php
  echo "<b> Ordre bekreftelse for pizza bestilling<b>";
  echo "<br>";
  echo "Du har bestillt pizza med: <br>";
  echo "-----<br>";
  if(!empty($_POST['ing'])) {
        foreach($_POST['ing'] as $value){
            echo "$value <br>";
            echo "<img src='assets/$ImageList[$value]' alt='$value' style='width:200px;height:200px;'>";
            echo "<br>";
        }
  }
  echo "-----<br>";
  echo "Levering er bestillt og kan hentes kl " . $_POST['plukkopp'];
?>
