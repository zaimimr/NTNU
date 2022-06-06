<?php
if (isset($_POST['submit']) ){

    require 'logon.inc.php';

    $first = mysqli_real_escape_string($tilkobling, $_POST['first']);
    $last = mysqli_real_escape_string($tilkobling, $_POST['last']);
    $email = mysqli_real_escape_string($tilkobling, $_POST['email']);
    $uid = mysqli_real_escape_string($tilkobling, $_POST['uid']);
    $pwd = mysqli_real_escape_string($tilkobling, $_POST['pwd']);

//Feil behandling
  if(empty($first) || empty($last) || empty($email) || empty($uid) || empty($pwd)) {
    header("Location:registrer.php?signup=empty");
    exit();
    } else {
      // Sjekker om det er lovlige tegn
      if (!preg_match("/^[a-zA-Z]*$/", $first) || !preg_match("/^[a-zA-Z]*$/", $last)) {
        header("Location:registrer.php?signup=invalid");
        exit();
      }else{
        if(!filter_var($email, FILTER_VALIDATE_EMAIL)) {
          header("Location:registrer.php?signup=epost");
          exit();
        } else {
          $sql = "SELECT * FROM users WHERE user_uid='$uid'";
          $resultat = $tilkobling->query($sql);
          //$resultat = mysql_query($tilkobling, $sql);
          $resultatsjekk = mysqli_num_rows($resultat);

          if($resultatsjekk > 0){
            header("Location:registrer.php?signup=ikkeledig");
            exit();
          }  else {
            // Hashing Passord
            $hashpwd = password_hash($pwd, PASSWORD_DEFAULT);
            $sql1 = "INSERT INTO users (user_first, user_last, user_email, user_uid, user_pwd) VALUES ('$first',   '$last', '$email', '$uid', '$hashpwd');";
            $resultat1 = $tilkobling->query($sql1);
            header("Location:registrer.php?signup=success");
            exit();
          }
        }
      }
    }

}else {
  header("location:registrer.php");
  exit();
}

?>
