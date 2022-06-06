<?php
include_once 'topp.php';

if (isset($_SESSION['u_id'])) {

echo'    <div class="main">
      <h2>Reiseregninger pr mnd</h2>
<table>
<tr>
  <th>Reise nr</th>
  <th>Reise dato</th>
  <th>Reisemål</th>
  <th>Formål</th>
  <th>Kostnad</th>
</tr>';

require 'logon.inc.php';
//Vising av regninger pr Mnd

$mnd = $_POST['mnd'];

//Printe pr mnd

$sql1 = $tilkobling->prepare("SELECT r_nr, r_dato, r_mal, r_formal, r_kost FROM travel WHERE MONTH(r_dato) = ?");
$sql1->bind_param("s", $mnd);
$sql1->execute();
$sql1->store_result();
$sql1->bind_result($r_nr, $r_dato, $r_mal, $r_formal, $r_kost);

      while ( $sql1->fetch()) {
          echo "<tr><td>" . $r_nr . "</td><td>" . $r_dato . "</td><td>"
          . $r_mal . "</td><td>" . $r_formal . "</td><td>". $r_kost . "</td></tr>";
  }

  $sql2 = $tilkobling->prepare("SELECT SUM(r_kost) FROM travel WHERE MONTH(r_dato) = ?");
  $sql2->bind_param("s", $mnd);
  $sql2->execute();
  $sql2->store_result();
  $sql2->bind_result($mndkost);
  $sql2->fetch();

echo "<tr> Du har valgt reiser i mnd: " . $mnd . "</tr>";
echo "</br>";
echo "</br>";
echo "<tr> Kostnad denne mnd er: " . $mndkost . "</tr>";



$tilkobling->close();
}
?>
</table>
</div>
<?php
include_once 'bunn.php';
?>
