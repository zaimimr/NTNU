<form action='' method='post' enctype='multipart/form-data'>
  <input type='file' name='file1' size='50'><p>
  <input type='file' name='file2' size='50'><p>
  <input type="checkbox" name="check" value="true">Remove duplicates<p>
  <input type='submit' name='submit'>
</form>


<?php
  if(isset($_FILES['file1']['name']) && isset($_FILES['file2']['name'])){
    if($_FILES['file1']['name'] != null && $_FILES['file2']['name'] != null){
      $array1 = readFromFile($_FILES['file1']['name']);
      $array2 = readFromFile($_FILES['file2']['name']);
      if(isset($_POST['check'])){
        $array1 = array_unique($array1);
        $array2 = array_unique($array2);
      }
      displayData($array1, $array2);
    }
  }

  function displayData($file1, $file2){
      echo "<h2>ReadFromFile:</h2> " . implode(" ",$file1) . "<br>";
      echo "<h2>ReadFromFile2:</h2> " . implode(" ",$file2) . "<br>";
      echo "<h2>Intersection:</h2> " . implode(" ",intersection($file1, $file2)) . "<br>";
      echo "<h2>Minus:</h2> " . implode(" ",minus($file1, $file2)) . "<br>";
      echo "<h2>Sum:</h2> " . implode(" ",sum($file1, $file2)) . "<br>";
      echo "<h2>NotIntersection:</h2> " . implode(" ",notIntersection($file1, $file2)) . "<br>";
  }

  function readFromFile($file){
    return file($file);
  }
  function intersection($file1, $file2){
    return array_intersect($file1,$file2);
  }
  function minus($file1, $file2){
    return array_diff($file1, intersection($file1, $file2));
  }
  function sum($file1, $file2){
    return array_unique(array_merge($file1, $file2));
  }
  function notIntersection($file1, $file2){
    return array_diff(sum($file1, $file2), intersection($file1, $file2));
  }
?>
