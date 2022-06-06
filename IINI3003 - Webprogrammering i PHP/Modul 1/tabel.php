<head>
<meta http-equiv="refresh" content="3" >
</head>

<?php
  $navn = "Nikoli";
  $alder = 18;
?>

<table border="1">
  <tr>
    <th><?php echo "Navn";?></th> <th><?php echo "Alder";?></th>
  </tr>
  <tr>
    <td><?php echo $navn; ?></td> <td><?php echo $alder; ?></td>
  </tr>
</table>

<?php
echo "<ul>";
echo "<li>Navnet er $navn</li>";
echo "<li>Alderen er $alder</li>";
echo "</ul>"; //avslutter punktmerket liste
?>

<?php
echo "<ol>";
echo "<li>Navnet er $navn</li>";
echo "<li>Alderen er $alder</li>";
echo "</ol>"; //avslutter punktmerket liste
?>
