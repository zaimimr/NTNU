<!DOCTYPE html>
<html>
<head>
  <title>Mypage</title>
  <link rel="stylesheet" href="css/main.css">
  <?php
    include "inc/functions.inc.php";
    include "inc/cookie.inc.php";
    include "inc/topBar.inc.php";
    include "css/css.php";
  ?>
</head>
<body align = "center">
  <div class="header">
  	<h2>My Page</h2>
  </div>
  <div>
    <p><?php echo $_COOKIE["Name"]; ?>  er en student som liker fargen <?php echo $_COOKIE["Color"]; ?></p>
  </div>

</body>
</html>
