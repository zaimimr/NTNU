<?php
  $color = $_COOKIE["Color"];
  $name = $_COOKIE["Name"];
  $font = $_COOKIE["Font"];
  $size = $_COOKIE["Size"];

  echo "<style>";
  echo "body, h2{";
  echo "color: $color;";
  echo "font-size: $size" . "px;";
  echo "font-family: $font;";
  echo "} </style>";
?>
