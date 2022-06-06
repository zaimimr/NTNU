<?php
  include 'Cookie.inc.php';
  function db($dbName = "zuimran"){
    $host = "mysql.stud.iie.ntnu.no";
    $user = "zuimran";
    $password = "xaXIMlNC";
    $db = new mysqli($host, $user, $password, $dbName);
    if ($db->connect_error) {
      die("Connection failed: " . $db->connect_error);
    }
    return $db;
  }
  function DBCreateTables(){
    $sql = "CREATE TABLE IF NOT EXISTS users (
      userID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
      userName VARCHAR( 20 ) NOT NULL,
      password VARCHAR( 30 ) NOT NULL
    )";
    $db = db();
    $res = $db->query($sql);
    $db -> close();
  }
  function DBSignUp($username, $password){
    DBCreateTables();
    $db = db();
    $sql = "INSERT INTO users VALUES(DEFAULT, ?, ?)";
    $stmt = $db->prepare($sql);
    if ($stmt && $stmt -> bind_param('ss', $username, $password) && $stmt -> execute()) {
      echo "<p>Registration complete</p>";
    }else{
      echo "<p>Registration failed</p>";
    }
    $db -> close();
  }
  function DBLogIn($username, $password){
    $db = db();
    $sql = "SELECT password FROM users WHERE userName = ?";
    $stmt = $db->prepare($sql);
    if ($stmt && $stmt -> bind_param('s', $username) && $stmt -> execute()
      && $stmt -> store_result() && $stmt -> bind_result($DBPassword)) {
      while ($stmt -> fetch()) {
        if ($DBPassword === $password) {
          logInCookie($username);
        }
      }
    } else {
      echo 'Prepared Statement Error';
    }
    $db -> close();
  }
  function createTravelDB(){
    $sql = "CREATE TABLE IF NOT EXISTS reise(
      travelID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
      date DATE NOT NULL,
      destination VARCHAR(20) NOT NULL,
      description VARCHAR(50) NOT NULL,
      cost INT NOT NULL,
      payed BOOLEAN NOT NULL);";
      $db = db();
      $res = $db->query($sql);
      $db -> close();
  }
  function newTravel($date, $destination, $description, $cost){
    createTravelDB();
    $db = db();
    $sql = "INSERT INTO reise VALUE (DEFAULT, ?, ?, ?, ?, false)";
    $stmt = $db->prepare($sql);
    if ($stmt && $stmt -> bind_param('sssi',$date, $destination, $description, $cost) && $stmt -> execute()) {
      echo "<p>Registration complete</p>";
    }else{
      echo "<p>Registration failed</p>";
    }
    $db -> close();
  }
  function getTable($destination = '%'){
    $db = db();
    $sql = "SELECT * FROM reise WHERE destination LIKE ?";
    $stmt = $db->prepare($sql);
    if ($stmt && $stmt -> bind_param('s', $destination) && $stmt -> execute()) {
      echo '<table>';
      echo '<tr>';
      echo '<th>Date</th>';
      echo '<th>Destination</th>';
      echo '<th>Description</th>';
      echo '<th>Cost</th>';
      echo '<th>Payed</th>';
      echo '<th>Update</th></tr>';
      if ($stmt && $stmt -> execute()
        && $stmt -> store_result() && $stmt -> bind_result($travelID, $date, $destination, $description, $cost, $payed)) {
        while($stmt -> fetch()) {
          echo '<tr><form action="" method="post">';
          echo "<input hidden name='tid' type='number' value='$travelID' required>";
          echo "<td><input class='table__input' name='date' type='date' value='$date' required></td>";
          echo "<td><input class='table__input' name='dest' type='string' value='$destination' required></td>";
          echo "<td><input class='table__input' name='about' type='string' value='$description' required></td>";
          if($payed == 0){
            echo "<td style='background-color:red'>";
          }else{
            echo "<td style='background-color:green'>";
          }
          echo "<input class='table__input' name='cost' type='number' min='0' value='$cost' required></td>";
          echo "<td><input class='table__input' name='pay' type='number' min='0' max='1' value='$payed' required></td>";
          echo "<td><input class='table__input--submit' name='updateTable' type='submit' value='Update'>";
          echo "<input class='table__input--submit' name='deleteTable' type='submit' value='Delete'></td>";
          echo '</form></tr>';
        }
      } else {
        echo 'Prepared Statement Error';
      }
      echo '</table>';
      $db -> close();
    }
  }
  function updateTable($travelID, $date, $destination, $description, $cost, $payed){
    $db = db();
    $sql = "UPDATE reise SET date = ?, destination = ?, description = ?, cost = ?, payed = ? WHERE travelID = $travelID";
    $stmt = $db->prepare($sql);
    if ($stmt && $stmt -> bind_param('sssii', $date, $destination, $description, $cost, $payed) && $stmt -> execute()) {
    }else{
      echo "<p>Update failed</p>";
    }
    $db -> close();
  }
  function deleteTable($travelID){
    $db = db();
    $sql = "DELETE FROM reise WHERE travelID = $travelID";
    $res = $db->query($sql);
    $db -> close();
  }
  function statistic(){
    $db = db();
    $sql = "SELECT cost, payed FROM reise";
    $stmt = $db->prepare($sql);
    if ($stmt && $stmt -> execute()
      && $stmt -> store_result() && $stmt -> bind_result($costPay, $payed)) {
      $total = 0;
      $count = 0;
      $totalNotPay = 0;
      $totalPay = 0;
      while ($stmt -> fetch()) {
        if($payed === 1){
          $totalPay += $costPay;
        }else{
          $totalNotPay +=$costPay;
        }
        $total += $costPay;
        $count += 1;
      }
      echo "<p>Total cost: $total</p>";
      echo "<p>Average cost:" . round($total/$count) . "</p>";
      echo "<p>Total cost payed: $totalPay</p>";
      echo "<p>Total cost not payed: $totalNotPay</p>";
    } else {
      echo 'Prepared Statement Error';
    }
    $db -> close();
  }
  function getCostPerMonth($monthID){
    $db = db();
    $sql = "SELECT cost FROM reise WHERE MONTH(date) = $monthID";
    $stmt = $db->prepare($sql);
    if ($stmt && $stmt -> execute()
      && $stmt -> store_result() && $stmt -> bind_result($cost)) {
      $total = 0;
      while ($stmt -> fetch()) {
        $total += $cost;
      }
      $db -> close();
      return $total;
    } else {
      echo 'Prepared Statement Error';
    }
    $db -> close();
  }
?>
