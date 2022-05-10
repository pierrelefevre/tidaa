## NB 20

Skriv ett program som löser n-damer problemet och presenterar alla lösningar och antalet lösningar. n-damer-problemet är
som åtta damer problemet där man får ange n där n är antalet damer som ska placeras på ett bräde med n rader och n
kolumner. Observera att programmet ska visa hur alla lösningar ser ut.

## NB 22.1

För att kunna studera att en implementation av ett binärt sökträd faktiskt fungerar korrekt kan det vara bra att kunna
skriva ut hur trädet faktiskt ser ut. Skriv metoden printTree som skriver ut ett träd till standard ut. Den ska skriva
ut trädet med en nivå per rad. För varje nivå skriver den ut barnen till alla noder som fanns i nivån ovanför även om
dessa är tomma (då skriver den null). På så sätt kan man entydigt se strukturen hos trädet. Trädet man får om man
adderar H, B, N, A, E, C, F, D i given ordning skrivs då ut:

H\
B N\
A E null null\
null null C F\
null D null null\
null null

Du får inte lägga till medlemsvariabler i det binära trädet’s klass eller nod-klassen. Tips: Använd en kö för att besöka
noderna enligt bredden först. För att kunna göra radbryt när du byter nivå kan du också köa vilken nivå varje nod
befinner sig på.

## NB 23

Skriv funktionerna numberOfLeaves, numberOfNodes och height till vår implementation från föreläsningen. Obs de ska
bestämma antalet utifrån trädet och inte använda privata medlemsvariabler.

## NB 23.1

Utgå ifrån vårt binära sökträd från föreläsningen. Skriv om sökfunktionen så att den är iterativ istället för rekursiv.
Skriv två funktioner maxRec och maxIt som returnerar det största värdet i ett binärt sökträd. Den ena rekursiv och den
andra iterativ. Alla funktioner ska vara O(logn).