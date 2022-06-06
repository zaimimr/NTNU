<head>
<?php
  include "./inc/top.inc.php";
?>
</head>
<body>
  <div class='sections'>
    <div class='cards'> 
      <h1>Statistics</h1>
      <?php
      statistic();
      $months = array(
        'January' => "1",
        'February' => "2",
        'March' => "3",
        'April' => "4",
        'May' => "5",
        'June' => "6",
        'July' => "7",
        'August' => "8",
        'September' => "9",
        'October' => "10",
        'November' => "11",
        'December' => "12",
      );
      foreach ($months as $key => $value) {
        echo "<p>$key: " . getCostPerMonth($value) . "</p>";
      }
      ?>       
    </div>
  </div>
</body>
