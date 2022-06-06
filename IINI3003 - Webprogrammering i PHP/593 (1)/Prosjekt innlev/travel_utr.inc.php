<?php
include_once 'topp.php';

if (isset($_SESSION['u_id'])) {
  echo'  <div class="main">
      <h2>Reiseregninger pr sted</h2>
  <table>
          <tr>
            <th>Reise nr</th>
            <th>Reise dato</th>
            <th>Reisemål</th>
            <th>Formål</th>
            <th>Kostnad</th>
          </tr>';

    require 'logon.inc.php';

  $sql3 = $tilkobling->prepare("SELECT r_nr, r_dato, r_mal, r_formal, r_kost FROM travel WHERE r_mal = ?");
  $sql3->bind_param("s", $_POST['r_mal']);
  $sql3->execute();
  $sql3->store_result();
  $sql3->bind_result($r_nr, $r_dato, $r_mal, $r_formal, $r_kost);

        while ( $sql3->fetch()) {
            echo "<tr><td>" . $r_nr . "</td><td>" . $r_dato . "</td><td>"
            . $r_mal . "</td><td>" . $r_formal . "</td><td>". $r_kost . "</td></tr>";
    }
  echo "<tr> Du har valgt å vise reiser til: " . $r_mal . "</tr>";
  echo "</br>";
  echo "</br>";

$tilkobling->close();
}
?>
</table>
</div>
<?php
include_once 'bunn.php';
?>
