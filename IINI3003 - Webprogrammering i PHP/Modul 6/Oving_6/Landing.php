<!DOCTYPE html>
<html>
<head>
  <title>Oving_6</title>
  <link rel="stylesheet" href="css/main.css">
  <?php
    include "inc/topBar.inc.php";
    include "inc/functions.inc.php";
    include "inc/cookie.inc.php";
    include "css/css.php";
  ?>
</head>
<body align = "center">
  <div class="header">
  	<h2>Register user</h2>
  </div>

  <form method="post" action="<?php echo( $self ); ?>">
    <div class="input-group">
      <label>Name</label>
      <input type="text" name="name" value="">
    </div>
  	<?php selectFont(); ?>
    <div class="input-group">
      <label>FontSize</label>
      <input type="number" name="size" value="13">
    </div>
  	<?php selectColor(); ?>
  	<div class="input-group">
  	  <button type="submit" class="btn" name="reg_user">Register</button>
  	</div>
    <a href="inc/writeCookie.inc.php">Vis info<a>
  </form>
</body>
</html>
