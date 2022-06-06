<form>
  Bare hardkodet, veldig dårlig <br>
  <select name="tidspunkt">
    <option value='1'>Januar</option>
    <option value='2'>Februar</option>
    <option value='3'>Mars</option>
    <option value='4'>April</option>
    <option value='5'>Mai</option>
    <option value='6'>Juni</option>
    <option value='7'>Juli</option>
    <option value='8'>August</option>
    <option value='9'>September</option>
    <option value='10'>Oktober</option>
    <option value='11'>November</option>
    <option value='12'>Desember</option>
  </select>
</form>
<br>

<div> Dynamisk med array</div>
<?php
//lager en matrise med månedsnumre og navn
  $mnd = array (1 => 'Januar',
    'Februar',
    'Mars',
    'April',
    'Mai',
    'Juni',
    'Juli',
    'August',
    'September',
    'Oktober',
    'November',
    'Desember'
  ); //slutt på matrisen her. Siste element har nøkkelen 12.
?>
<form>
  <select name="tidspunkt">
    <?php
      foreach ($mnd as $maanedsnummer => $maanedsnavn) {
        //lager option-tag med neste månedsnavn.
        //Nummer blir value-attributt
        echo "\t<option value='$maanedsnummer'>$maanedsnavn</option>\n";
      }//foreach - ferdig med å gjennomgå hele matrisen
    ?>
  </select>
</form>
