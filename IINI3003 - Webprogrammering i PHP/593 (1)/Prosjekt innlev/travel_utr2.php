<?php
include_once 'topp.php';

if (isset($_SESSION['u_id'])) {

echo'    <div class="main">
      <h2>Reiseregninger pr mnd</h2>

<form action="travel_utr2.inc.php" method="post">
  <table>
</tr>

  <td> Hvilken mnd ønsker du å vise reiser for <td>
      <select name="mnd">
      <Option value ="ingen valgt">
        -- Velg mnd -- </option>
      <Option value ="1"> Januar </option>
      <Option value ="2"> Februar </option>
      <Option value ="3"> Mars </option>
      <Option value ="4"> April </option>
      <Option value ="5"> Mai </option>
      <Option value ="6"> Juni </option>
      <Option value ="7"> Juli </option>
      <Option value ="8"> August </option>
      <Option value ="9"> September </option>
      <Option value ="10"> Oktober </option>
      <Option value ="11"> November </option>
      <Option value ="12"> Desember </option>
      <td><input type="submit" value="Velg"></td>
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
include_once 'bunn.php';
}
?>
