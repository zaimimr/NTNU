<?php
include_once 'topp.php';

  if (isset($_SESSION['u_id'])) {

echo '<div class="main">
  <h2>Registreringsside for kostnader og innbetalinger</h2>

<form action="travel_add.php" method="post">
  <table cellpadding="10" width="400"
<th><h4> Legg inn reiseregninger</h4></th>
  <tr>
    <td> Dato for reise: </td>
    <td> <input type="date" name="date_r" required></td>
  </tr>
  <tr>
    <td> Reisemål: </td>
    <td> <input type="text" name="reisemal" required></td>
  </tr>
  <tr>
    <td> Utlegg: </td>
    <td> <input type="number" name="kostnad" required></td>
  </tr>
  <tr>
    <td> Formål med reise: </td>
    <td> <textarea rows="4" cols="30" name="formal" required></textarea></td>
  </tr>
  <tr>
    <td><input type="submit" value="Registrer">
    <td><input type="reset" value="Tøm">
  </tr>
</form>

<form action="travel_p.php" method="post">
  <table cellpadding="10" width="400"

  <th><h4> Registrer innbetaling</h4></th>
  <tr>
    <td> Reiseregning: </td>
    <td> <input type="number" name="r_nr_fk" required></td>
  </tr>
  <tr>
    <td> Dato for innbetaling: </td>
    <td> <input type="date" name="p_dato" required></td>
  </tr>
      <td> Utbetalt: </td>
    <td> <input type="number" name="p_pay" required></td>
  </tr>
    <tr>
    <td><input type="submit" value="Registrer betaling">
    <td><input type="reset" value="Tøm">
  </tr>
</form>

</div>';
include_once 'bunn.php';
}
?>
