<html>
<head>
  <link rel='stylesheet' href='../css/main.css'/>
</head>
<body>
  <?php
    $menu = array("Landing.php"=>"Register",
      "myPage.php"=>"My Page",
      "random.php"=>"Random",
    );

    echo '<div id="header">';
    foreach ($menu as $path => $value) {
      echo "<a href='$path'>$value</a>";
    }
    echo '</div>';
  ?>
