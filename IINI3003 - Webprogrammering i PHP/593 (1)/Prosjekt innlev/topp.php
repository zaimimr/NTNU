<?php
session_start();
 ?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta charset="UTF-8">
  <title> Mine reiseregninger</title>
  <link rel="stylesheet" type="text/css" href="style.css" />
</head>
<body>
  <header>

        <div class="nav">
          <ul>
            <li><a href="index.php">Hjem</a></li>
            <li><a href="travel.php">Registrer reise</a></li>
            <li><a href="travel_change.php">Endre reise</a></li>
            <li><a href="travel_all.php">Vis alle</a></li>
            <li><a href="travel_utr.php">Vis alle pr sted</a></li>
            <li><a href="travel_utr2.php">Vis alle pr Mnd</a></li>
          </ul>
          </div>
        <div class="header">
          <?php
            if (isset($_SESSION['u_id'])) {
              echo'<form action="logout.inc.php" method="POST">
                  <button type="submit" name="submit">Logout</button>
                  </form>';
            } else {
              echo'
                <form action="login.inc.php" method="POST">
                  <input type="text" name="uid" placeholder="Brukernavn">
                  <input type="password" name="pwd" placeholder="Passord">
                  <button type="submit" name="submit">Login</button>
                </form>
                <br>
                <a href="registrer.php">Registrer deg</a>';
            }
           ?>
        </div>

  </header>
