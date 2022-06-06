<?php
include_once 'topp.php';
?>

  <div class="main">
  <h2>Reiseregninger pr sted</h2>
<?php
  if (isset($_SESSION['u_id'])) {
//Logge på
require 'logon.inc.php';

$r_nr_fk = $_POST['r_nr_fk'];
$p_dato = $_POST['p_dato'];
$p_pay = $_POST['p_pay'];

// Registrere betaling
$sql = $tilkobling->prepare("INSERT INTO payment (p_nr, p_dato, p_pay, r_nr)
                             VALUES (?,?,?,?)");
$sql->bind_param("isss", $p_nr, $p_dato, $p_pay, $r_nr_fk);
$sql->execute();
$sql->close();


$sql1 = $tilkobling->prepare("SELECT sum(p_pay) AS sum FROM payment WHERE r_nr =?");
$sql1->bind_param("s", $r_nr_fk);
$sql1->execute();
$sql1->store_result();
$sql1->bind_result($sum);
$sql1->fetch();
$sql1->close();

// Hente kostnaden ssom hører til innbetalingen
$sql2 = $tilkobling->prepare("SELECT r_kost FROM travel WHERE r_nr = ?");
$sql2->bind_param("i", $r_nr_fk);
$sql2->execute();
$sql2->store_result();
$sql2->bind_result($kostnad);
$sql2->fetch();
$sql2->close();

if ($sum == '0') {
  $r_stat = 'Nei';
}elseif ($sum == $kostnad) {
$r_stat = 'Ja';
}elseif ($sum > $kostnad) {
$r_stat = 'Forskudd';
} else {
  $r_stat = 'Delvis';
}

echo "Reiseregningsnr ";
echo $r_nr_fk . " er oppdatert.";
echo "</br>";
echo "På denne reiseregningen er det totalt registrert kr. ";
echo $sum . " som innbetalt.";
echo "</br>";
echo "Totalt utlegg på denne reiseregningen er kr. ";
echo $kostnad;
echo "</br>";
echo "Betalingsstatus ";
echo $r_stat;
echo "</br>";
echo "Restbeløp ";
echo $kostnad - $sum;
echo "</br>";


//Oppdatere status

$sql3 = $tilkobling->prepare("UPDATE travel SET r_stat=? WHERE r_nr=?");
$sql3->bind_param("ss", $r_stat, $r_nr_fk);
$sql3->execute();
$sql3->close();

$tilkobling->close();
}
?>

</div>

<?php
include_once 'bunn.php';
?>
