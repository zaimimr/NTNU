<?php
include_once 'topp.php';
?>

  <div class="main">
  <h2>Endring av reiseregninger</h2>

<?php
if (isset($_SESSION['u_id'])) {
//Logge på
require 'logon.inc.php';

// Endre valgt felt
$felt = $_POST['felt'];
$verdi = $_POST['verdi'];
$nr = $_POST['r_nr'];

$sql = $tilkobling->prepare("UPDATE travel SET $felt = ? WHERE r_nr = ?");
$sql->bind_param("ss", $verdi, $nr);
$sql->execute();
$sql->close();

$tilkobling->close();

echo "Reiseregnings nr $nr er oppdatert";
header("Location:travel_all.php");
}
?>
