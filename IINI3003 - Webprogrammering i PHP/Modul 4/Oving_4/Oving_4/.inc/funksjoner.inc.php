<?php
function contactInfo(){
    echo "<p>Kontaktinformasjon: 11223344, epost@oss.no</p>";
}

function footer(){
  echo '<div id="footer">';
  contactInfo();
  echo "</div>";
}



function menyBar(){
  $menu = array("index.php"=>"Home",
    "bestill.php"=>"Order",
    "kontakt.php"=>"Contact",
    "om.php"=>"About"
  );

  echo '<div id="header">';
  foreach ($menu as $path => $value) {
    echo "<a href='$path'>$value</a>";
  }
  login();
  echo '</div>';
}

function login(){
  echo "<div>";
  echo "<form action='passordsjekk.php' method='get'>";
  echo "<input type='text' placeholder='Username' name='username'>";
  echo "<input type='text' placeholder='Password' name='psw'>";
  echo "<button type='submit'>Login</button>";
  echo "</form>";
  echo "</div>";
}

?>
