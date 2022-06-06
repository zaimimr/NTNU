<?php
include_once 'topp.php';

if (isset($_SESSION['u_id'])) {

echo    '<div class="main">
      <h2>Korrigering av reiseregninger</h2>

<form action="travel_change.inc.php" method="post">
  <table>
        <h4> Ønsker du å korriger innbetalinger legger du inn en korrigeringstransaksjon i skjema for innbetalinger <h4>
        <h4> Merk korrigerer du innbetalingsstatus og deretter registrerer en innbetaling vil denne bli endret basert på saldo <h4>
  <tr>
    <td> Reiseregningsnr: </td>
    <td> <input type="number" name="r_nr" required></td>
  </tr>
  <tr>
    <td> Velg feltet du ønsker du å endre </td>
      <td>      <select name="felt">
        <Option value ="ingen valgt">
          -- Velg feltet du ønsker å endre -- </option>
          <Option value="r_dato"> Reise dato</option>
          <Option value="r_formal"> Formål</option>
          <Option value="r_mal"> Reisemål</option>
          <Option value="r_kost"> Kostnad</option>
          <Option value="r_stat"> Status</option></td>
          </tr>
          <tr>
            <td> Registrer verdi: </td>
            <td> <input type="text" name="verdi" required></td>
          </tr>
    <tr>
        <td><input type="submit" value="Registrer">
        <td><input type="reset" value="Tøm">
    </tr>
</table>
</form>

</div>';

include_once 'bunn.php';
}
?>
