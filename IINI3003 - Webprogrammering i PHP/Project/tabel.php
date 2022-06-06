<head>
<?php
  include "./inc/top.inc.php";
  if(isset($_POST['updateTable'])) {
    updateTable($_POST['tid'],$_POST['date'],$_POST['dest'],$_POST['about'],$_POST['cost'],$_POST['pay']);
  }
  if(isset($_POST['deleteTable'])) {
    deleteTable($_POST['tid'],$_POST['date'],$_POST['dest'],$_POST['about'],$_POST['cost'],$_POST['pay']);
  } 
?>
</head>
<body>
  <div class='sections'>
    <div class='cards'> 
      <form method='post' action=''>
        <input class="signUp__form--input" type="text" name="search" placeholder="Enter search destination">
        <input class="signUp__form--button" name="searching" type="submit" value="submit">
      </form>
      <?php
      if(!empty($_POST['search'])){
        getTable($_POST['search']);
      }else{
        getTable();
      }
        
      ?>
    </div>
  </div>
</body>
