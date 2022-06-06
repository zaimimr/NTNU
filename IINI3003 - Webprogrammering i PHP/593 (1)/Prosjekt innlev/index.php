<?php
include_once 'topp.php';
?>
    <div class="main">
      <h2>Hovedside for reiseregninger</h2>

      <?php
      if (isset($_SESSION['u_id'])) {

      echo"<h4> Detter er de siste registrerte regningene </h4>";
      echo "<table cellpadding='3' width='500'";
      echo "<tr>";
      echo "<th>Reise nr</th>";
      echo "<th>Reise dato</th>";
      echo "<th>Reisemål</th>";
      echo "<th>Formål</th>";
      echo "<th>Kostnad</th>";
      echo "</tr>";

      //Koble til database
      require 'logon.inc.php';
      //Vise åpne reiseregninger

      $sql = $tilkobling->prepare("SELECT COUNT(r_nr) FROM travel");
      $sql->execute();
      $sql->store_result();
      $sql->bind_result($antall);
      $sql->fetch();


      $sql1 = $tilkobling->prepare("SELECT r_nr, r_dato, r_mal, r_formal, r_kost FROM travel ORDER BY r_nr DESC");
      $sql1->execute();
      $sql1->store_result();
      $sql1->bind_result($r_nr, $r_dato, $r_mal, $r_formal, $r_kost);
      while ($sql1->fetch()) {
      if ($antall - 5 < $r_nr) {
        echo "<tr><td>" . $r_nr . "</td><td>" . $r_dato . "</td><td>"
            . $r_mal . "</td><td>" . $r_formal . "</td><td>". $r_kost . "</td></tr>";
      }
      }

      echo "</table>";

      $sql4 = $tilkobling->prepare("SELECT SUM(r_kost), AVG(r_kost) FROM travel");
      $sql4->execute();
      $sql4->store_result();
      $sql4->bind_result($kostnad, $snitt);
      $sql4->fetch();
                echo "</br>";
                echo "</br>";
                echo "Gjennomsnitts kostnaden for alle reiser er: " . round($snitt,2);
                echo "</br>";
                echo "</br>";
                echo "Total kostnad for alle reiser er " . round($kostnad,2);

        $sql5 = $tilkobling->prepare("SELECT SUM(p_pay) FROM payment");
        $sql5->execute();
        $sql5->store_result();
        $sql5->bind_result($utbet);
        $sql5->fetch();
                echo "</br>";
                echo "</br>";
                echo "Av totale kostnder er kr. " . round($utbet,2) . " utbetalt.";
                echo "</br>";
                echo "</br>";
                echo "Restbeløp til utbetaling er kr ";
                echo  round($kostnad - $utbet,2);
                $tilkobling->close();
              }
      ?>
    </div>

<?php
include_once 'bunn.php';
?>
