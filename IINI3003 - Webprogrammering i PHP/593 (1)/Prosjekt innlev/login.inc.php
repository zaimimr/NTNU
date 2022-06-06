<?php
session_start();
if (isset($_POST['submit'])) {

  require 'logon.inc.php';

  //Logge på
  $uid = mysqli_real_escape_string($tilkobling, $_POST['uid']);
  $pwd = mysqli_real_escape_string($tilkobling, $_POST['pwd']);

  //Feil behandling
    if (empty($uid) || empty($pwd)) {
      header("location:index.php?login=empty");
      exit();
    } else {
        $sql = "SELECT * FROM users WHERE user_uid ='$uid'";
        $resultat = $tilkobling->query($sql);
        $resultatsjekk = mysqli_num_rows($resultat);
        if ($resultatsjekk < 1 ) {
            header("location:index.php?login=error1");
            exit();
        } else {
          if ($rad = mysqli_fetch_assoc($resultat)) {
            $hashedPwdCheck = password_verify($pwd, $rad['user_pwd']);
            if ( $hashedPwdCheck == false) {
              header("location:index.php?login=error2");
              exit();
              } elseif ($hashedPwdCheck == true) {
                $_SESSION['u_id'] = $rad['user_id'];
                $_SESSION['u_first'] = $rad['user_first'];
                $_SESSION['u_last'] = $rad['user_last'];
                $_SESSION['u_email'] = $rad['user_email'];
                $_SESSION['u_uid'] = $rad['user_uid'];
                header("location:index.php?login=success");
                exit();
            }
          }
        }
    }
  } else {
    header("location:index.php?login=error3");
    exit();
  }
?>
