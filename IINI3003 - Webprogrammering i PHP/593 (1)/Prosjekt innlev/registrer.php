<?php
include_once 'topp.php';
?>

  <section class="main-container">
    <div class="main-wrapper">
      <h2>Registrering</h2>
      <form class="signup-form" action="registrer.inc.php" method="POST">
        <input type="text" name="first" placeholder="Fornavn">
        <input type="text" name="last" placeholder="Etternavn">
        <input type="text" name="email" placeholder="E-post">
        <input type="text" name="uid" placeholder="Brukernavn">
        <input type="password" name="pwd" placeholder="Passord">
        <button type="submit" name="submit">Registrer deg</button>
    </div>
  </section>

  <?php
  include_once 'bunn.php';
  ?>
