2)
a)
String ord;
String[] definisjon;

b)
Ord(){
this.ord = ord;
this.definisjon = String[0];
}

c)
String getOrd(){return this.ord}
String[] getDefinisjon(){return this.definisjon}

d)
boolean toLikeOrd(Object o1){
	if(!(o1 instranceof Ord) return false;

	if(o1 == this) return true;

	Ord o = (Ord)o1;

	if(ord.toLowerCase().equals(o.getOrd.toLowerCase()))) return true;
	return false;
}

e)
String toString(){
String output = this.ord + ":\n";
for(int i = 0; i < definisjon.length; i++){
	output += (i+1) + ". " + definisjon[i] + "\n";
}
return output;

f)
String[] utvidTabell(){
String[] utvid = new String[definisjon.length + 1];
for(int i = 0; i < definisjon.length; i++){
	utvid[i] = definisjon[i];
}
definisjon = utvid;
}

g)
boolean leggTilDefinisjon(String nyDefinisjon){
	String nD = nyDef.toLowerCase();
for(int i = 0; i < definisjon.length; i++){
String def = definisjon.toLowerCase();
if(def.equals(nD)) return false;
}
utvidTabell();
definisjon[definisjon.length - 1] = nyDef;
return true;
}

3)
a)
public Ordbok(String ordbokNavn, int maksord){
if(!(lesOrdbokFraFil("filnavn"))){
	this.ordbokNavn = ordbokNavn;
  this.MAKS_ANTALL_ORD = maksord;
	ordbok = Ord[MAKS_ANTALL_ORD];
	antallReg = 0;
}
}

b)
public boolean regNyttOrd(Ord ord){
for(Ord o : ordbok){
	if(ord == o) return false
}
ordbok[antallReg] = ord;
antallReg++;
return true;
}

c)
public boolean leggTilDefinisjon(String ord, String definisjon){
  //finne ord som matcher String
  for(Ord o : ordbok){
    if(o.getOrd().toLowerCase().equals(ord.toLowerCase())){
      return o.leggTilDefinisjon(definisjon);
    }
  }
  return false;
}

d)
public Ord[] sorter(){
  if(ordbok == null || ordbok.length == 0) return null;

  Ord[] boken = new Ord[antallReg];
  for(int i = 0; i < boken.length; i++){
    boken[i] = new Ord(ordbok[i].getOrd);
  }
//sortering
  for(int i = 0; i < boken.length-1; i++){
    for(int y = i + 1; y < boken.lenth; y++){
      if(boken[i].compareTo(boken[y]) > 0){
        Ord buffer = boken[y];
        boken[y] = boken[i];
        boken[i] = buffer;
      }
    }
  }
  return boken;
}

public Ord getOrd(String sokeString){
  for(Ord o : ordbok){
    if(o.getOrd().equals(sokeString)){
      return o;
    }
  }
  return null;
}

public boolean lesOrdbokFraFil(String filnavn){
  try{
    FileInputStream fis = new FileInputStream(filnavn);
    ObjectInputStream ois = new ObjectInputStream(fis);
    Ord[] ob = ois.readObject();
    ordbok = ob;
    antallReg = 0;
    for(Ord o : ob){
      if(o != null){
        antallReg++
      }
    }
    return true
  }catch(IOException | ClassNotFoundException e){
    e.printStackTrace();
    return false;
  }finally{
    fis.close();
    ois.close();
  }
}


4a)
String sOrd = showInputDialog("Legg til ord");
Ord ord = new Ord(sOrd);
ordbok.regNyttOrd(ord);
