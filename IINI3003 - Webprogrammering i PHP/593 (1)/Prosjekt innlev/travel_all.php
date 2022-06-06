<?php
include_once 'topp.php';

if (isset($_SESSION['u_id'])) {

echo'    <div class="main">
      <h2>Alle registrerte reisergningr</h2>

<table>
<tr>
  <th>Reise nr</th>
  <th>Reise dato</th>
  <th>Reisemål</th>
  <th>Formål</th>
  <th>Kostnad</th>
  <th>Bet status</th>
  <th>Innbetalt</th>
</tr>';

//Koble til database
require 'logon.inc.php';
//Vise åpne reiseregninger
$sql = $tilkobling->prepare("SELECT COUNT(r_nr) FROM travel");
$sql->execute();
$sql->store_result();
$sql->bind_result($antall);
$sql->fetch();

echo "</br>";
echo "Total antall registrerte reiseregninger " . $antall;
echo "</br>";
echo "</br>";

$sql1 = $tilkobling->prepare(" SELECT t.r_nr, t.r_dato, t.r_mal, t.r_formal, t.r_kost, t.r_stat, sum(p.p_pay)
          FROM travel t LEFT OUTER JOIN payment p
          ON t.r_nr = p.r_nr
          GROUP BY t.r_nr
          ORDER By t.r_nr DESC");
$sql1->execute();
$sql1->store_result();
$sql1->bind_result($tr_nr, $tr_dato, $tr_mal, $tr_formal, $tr_kost, $tr_stat, $innbet);
while ( $sql1->fetch()) {
if ($antall >= $tr_nr) {
  echo "<tr><td>" . $tr_nr . "</td><td>" . $tr_dato . "</td><td>"
      . $tr_mal . "</td><td>" . $tr_formal . "</td><td>". $tr_kost . "</td><td>". $tr_stat . "</td><td>" . $innbet ."</td></tr>";
}
}

echo "</table>";
$tilkobling->close();
}
?>
</table>
</body>
</html>
</div>

<?php
include_once 'bunn.php';
?>
