<?php
$db="";
$host = "mysql-ait.stud.idi.ntnu.no";
$bruker = "zuimran";
$passord = "xaXIMlNC";
$tilkobling = new mysqli($host, $bruker, $passord, $db);
  if ($tilkobling-> connect_error) {
    die("Tilkoblingen til databasen feilet:". $tilkobling-> connect_error);
  }
?>
