<html>
<?php
  $interesser = array("",
    "Fotball",
    "Sykle",
    "Ishockey",
    "Lese",
    "Spille",
    "Programmere",
    "Spise"
  );
?>
<body>
  <b>Interessene dine</b>
  <form action="Interesse.php" method="get">
    Hva er ditt navn?
    <input type="text" name="name">
    <br>
    Velg den Interessen du vil foretrekke
    <select class="" name="list">
      <?php
      foreach ($interesser as $name) {
        echo "<option value=\"$name\">$name</option>";
      } ?>
    </select>
    <input type="submit">
  </form>
