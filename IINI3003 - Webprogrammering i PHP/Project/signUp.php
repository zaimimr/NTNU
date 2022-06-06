<head>
<?php
  include './inc/top.inc.php';
?>
</head>
<body>
  <div class='sections'>
    <div class='cards'>
    <form action="" method="post">
      <label>Username</label>
      <input class="signUp__form--input" type="text" name="username" placeholder="Your username" required>
      <label>Password</label>
      <input class="signUp__form--input" type="password" name="password" placeholder="Your password" required>
      <label>Re-enter Password</label>
      <input class="signUp__form--input" type="password" name="repassword" placeholder="Re-enter password" required>
      <input class="signUp__form--button" type="submit" value="submit">
    </form>
    </div>
  </div>
  <?php
    if(!empty($_POST['username']) && !empty($_POST['password']) && !empty($_POST['repassword'])) {
      if($_POST['password'] == $_POST['repassword']) {
        DBSignUp($_POST['username'], $_POST['password']);
      }
    }
  ?>
</body>