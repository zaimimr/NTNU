<b>Interesse svar:</b>
<br>
<?php

  echo "$_GET[name] er interessert i ";
  foreach ($_GET['liste'] as $item) {
    echo "<br> $item ";
  }
?>
