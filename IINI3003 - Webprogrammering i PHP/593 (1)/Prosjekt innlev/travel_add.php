<?php
//Logge på
require 'logon.inc.php';

//Hente data

$date_r = $_POST['date_r'];
$reisemal = $_POST['reisemal'];
$kostnad = $_POST['kostnad'];
$formal = $_POST['formal'];
$status = 'Nei';

$sql = $tilkobling->prepare("INSERT INTO travel (r_nr, r_dato, r_formal, r_mal, r_kost, r_stat)
                             VALUES (?,?,?,?,?,?)");
$sql->bind_param("ssssss", $r_nr, $date_r, $formal, $reisemal, $kostnad, $status );
$sql->execute();
$sql->close();
$tilkobling->close();
header("location:travel_all.php");
?>
