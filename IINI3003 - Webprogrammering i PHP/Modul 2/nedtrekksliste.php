<form>
  Mandag: <input type="radio" name="dag" value="mandag"><br>
  Tirsdag: <input type="radio" name="dag" value="tirsdag"><br>
  Onsdag: <input type="radio" name="dag" value="onsdag"><br>
  Torsdag: <input type="radio" name="dag" value="torsdag">
  <p>
  <select name="tidspunkt">
    <option value='01:00'>01:00</option>
    <option value='02:00'>02:00</option>
    <option value='03:00'>03:00</option>
    <option value='04:00'>04:00</option>
    <option value='05:00'>05:00</option>
    <option value='06:00'>06:00</option>
    <option value='07:00'>07:00</option>
    <option value='08:00'>08:00</option>
    <option value='09:00'>09:00</option>
    <option value='10:00'>10:00</option>
    <option value='11:00'>11:00</option>
    <option value='12:00'>12:00</option>
    <option value='13:00'>13:00</option>
    <option value='14:00'>14:00</option>
    <option value='15:00'>15:00</option>
    <option value='16:00'>16:00</option>
    <option value='17:00'>17:00</option>
    <option value='18:00'>18:00</option>
    <option value='19:00'>19:00</option>
    <option value='20:00'>20:00</option>
    <option value='21:00'>21:00</option>
    <option value='22:00'>22:00</option>
    <option value='23:00'>23:00</option>
  </select>
</form>

<form>
  Dynamisk tid med php <br>
  Mandag: <input type="radio" name="dag" value="mandag"><br>
  Tirsdag: <input type="radio" name="dag" value="tirsdag"><br>
  Onsdag: <input type="radio" name="dag" value="onsdag"><br>
  Torsdag: <input type="radio" name="dag" value="torsdag">
  <p>
  <select name="tidspunkt">
    <?php
      for ($i=1; $i<24; $i++) {
        echo "\t<option value='";
        if ($i<10) $nuller = "0";
        else $nuller = "";
        $tid = $nuller . $i . ":00";
        echo "$tid'>$tid</option>\n";
      }
    ?>
  </select>
</form>
<form>
  Med halvtimer <br>
  <select name="tidspunkt">
  <?php
    for ($i=1; $i<24; $i++) {
    //lager starten av en option-tag, og finner ut
    //om det skal være ledende nullere
    echo "\t<option value='";
    if ($i<10) $nuller = "0";
    else $nuller = "";
    //lager tidspunkt med :00 på slutten
    $tid = $nuller . $i . ":00";
    echo "$tid'>$tid</option>\n";
    //lager ny option-tag, nå med tidspunkt :30 på slutten
    echo "\t<option value='";
    $tid = $nuller . $i . ":30";
    echo "$tid'>$tid</option>\n";
    }//for
  ?>
</form>
<br>
<form>
  <select name="tidspunkt">
  <?php
    $dagen_i_dag = date("l"); //liten L, ikke ett-tall
    if ($dagen_i_dag == "Sunday") {
    $start = 9;
    $slutt = 17;
    }
    else {
    $start = 1;
    $slutt = 23;
    }
    for ($i=$start; $i<=$slutt; $i++) {
      //lager starten av en option-tag, og finner ut
      //om det skal være ledende nullere
      echo "\t<option value='";
      if ($i<10) $nuller = "0";
      else $nuller = "";
      //lager tidspunkt med :00 på slutten
      $tid = $nuller . $i . ":00";
      echo "$tid'>$tid</option>\n";
      //lager ny option-tag, nå med tidspunkt :30 på slutten
      echo "\t<option value='";
      $tid = $nuller . $i . ":30";
      echo "$tid'>$tid</option>\n";
    }
  ?>
</form>
