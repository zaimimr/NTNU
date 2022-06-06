<?php
  function logInCookie($username) {
    setcookie('username', $username, time()+3600);
    header("Refresh:0; url=index.php");
  }

  function logOutCookie() {
    setcookie('username', "rip", time()-10);
    unset($_COOKIE['username']);
    header("Refresh:0; url=index.php");
  }
?>
