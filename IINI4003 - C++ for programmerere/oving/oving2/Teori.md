## Oppgave 2
``` c++
char *line = nullptr; // eller char *line = 0
strcpy(line, "Dette er en tekst");
```

Vi starter med å definere en null pointer. Deretter prøver vi å kopiere stringen "Dette er en tekst" til line pointeren.
 Problemet med dette er at dette ikke vil fungere siden den vil copiere teksten til den adressen pekeren peker til.
 Problemet er at `*line` peker til `0`, som ikke er en gyldig adresse for å lagre string.
 
 ## Oppgave 3
 ``` c++
char text[5];
char *pointer = text;
char search_for = 'e';
cin >> text;
while (*pointer != search_for) {
    *pointer = search_for;
    pointer++;
}
```
Programmet vil lese inn en string og loope gjennom string adressene til den finner `'e'`. Problemet oppstår når vi ikke
sender inn en string som innhodler en `'e'`. Da vil programmet fortsette å inkrementere pointeren gjennom hele minnet
finner `'e'`. Dette kan være veldig farlig.