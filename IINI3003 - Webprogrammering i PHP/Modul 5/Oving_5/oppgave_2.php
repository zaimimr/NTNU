<?php
function enkelDatoBehandler(){
  $time = date("H");
  if($time < "12" && $time > "06"){
    return "God morgen";
  }elseif ($time >= "12" && $time < "18") {
    return "God ettermiddag";
  }else{
    return "God natt";
  }
}

function greenSunday(){
  if(date("l") == "Sunday"){
    echo '<div style="color:green;">';
    echo enkelDatoBehandler();
    echo "</div>";
  }else{
    echo enkelDatoBehandler();
  }
}
greenSunday();
?>
