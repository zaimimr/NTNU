<?php
  function menuBar(){
    $menu = array( "registerTravel.php"=>"Register Travel",
    "tabel.php"=>"View Travels",
    "statistic.php"=>"Statistics",
    
    );
    echo '<div class="navBar">';
    echo "<a href='index.php' class='navBar__menu--button'>Home</a>";
    if(!empty($_COOKIE['username']) && isset($_COOKIE['username'])) {
      logout();
      foreach ($menu as $path => $value) {
        echo "<a href='$path' class='navBar__menu--button'>$value</a>";
      }
    }else {
      login();
    }

    echo '</div>';
  }

  function login(){
    echo "<div>";
    echo "<form class='navBar__form' action='' method='post'>";
    echo "<input class='navBar__form--input' type='text' placeholder='Username' name='usernameLog' required>";
    echo "<input class='navBar__form--input' type='password' placeholder='Password' name='passwordLog' required>";
    echo "<input class='navBar__form--button' name='in' type='submit' value='Login'></input>";
    echo "<a class='navBar__form--button' href='signUp.php'>Sign Up</a>";
    echo "</form>";
    echo "</div>";
    if(!empty($_POST['usernameLog']) && !empty($_POST['passwordLog']) && isset($_POST['in'])) {
      DBLogIn($_POST['usernameLog'], $_POST['passwordLog']);
    }
  }
  function logout(){
    echo "<div>";
    echo "<form class='navBar__form' action='' method='post'>";
    $user = $_COOKIE['username'];
    echo "<label> $user </label>";
    echo "<input class='navBar__form--button' name='out' type='submit' value='Logout'></input>";
    echo "</form>";
    echo "</div>";
    if(isset($_POST['out'])){
      logOutCookie();
    }
  }
?>