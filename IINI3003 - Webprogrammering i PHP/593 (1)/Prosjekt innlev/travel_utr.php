<?php
include_once 'topp.php';

  if (isset($_SESSION['u_id'])) {
echo'    <div class="main">
      <h2>Reiseregninger pr sted</h2>

<form action="travel_utr.inc.php" method="post">
  <table>
<tr>
  <td> Velg reisemål: <td>
      <select name="r_mal" >
        <Option value ="ingen valgt" selected>
        -- Velg reisemål -- </option>';

//Vising av regninger pr reisemål
  require 'logon.inc.php';
  $sql2 = $tilkobling->prepare("SELECT DISTINCT r_mal FROM travel");
  $sql2->execute();
  $sql2->store_result();
  $sql2->bind_result($r_mal);

        while ( $sql2->fetch()) {
              	echo "<option value= '$r_mal'>$r_mal</option>";
  		}
    $tilkobling->close();  
echo'<td><input type="submit" value="Velg"></td>
    <tr>
      <th>Reise nr</th>
      <th>Reise dato</th>
      <th>Reisemål</th>
      <th>Formål</th>
      <th>Kostnad</th>
    </tr>
</table>
</form>
</div>';
}
include_once 'bunn.php';
?>
