<head>
<?php
  include "./inc/top.inc.php";
?>
</head>
<body>
  <div class='sections'>
    <div class='cards'>
      <form action="" method="post">
        <label>Date</label>
        <input class="signUp__form--input" type="date" value="<?php echo date("Y-m-d") ?>" name='date' required>
        <label>Travel Destination</label>
        <input class="signUp__form--input" type="text" name="dest" placeholder="Destination" required>
        <label>About</label>
        <input class="signUp__form--input" type="text" name="about" placeholder="About" required>
        <label>Payment</label>
        <input class="signUp__form--input" type="number" name="pay" placeholder="Payment" required>
        <input class="signUp__form--button" name="travel" type="submit" value="submit">
      </form>
    </div>
  </div>
  <?php
    if(isset($_POST['travel'])) {
      newTravel($_POST['date'],$_POST['dest'],$_POST['about'],$_POST['pay']);
    }
  ?>
</body>
