<?php

  function imagePicker(){
    $images = array(1 => 'image1',
      "image2",
      "image3",
      "image4",
      "image5",
      "image6",
      "image7",
      "image8",
      "image9",
      "image10",
   );
    $rand = floor(date("s")/6);
    $image = $images[$rand];
    echo '<img src="$image">';
  }
?>

<div style="position:fixed; width:100%; background-color:gray;">
<?php
imagePicker();
echo date("I dag er det den d. M") . "<br>";
?>
</div>
