## NB34

Lös växlingsproblemet med en girig algoritm. Skriv en metod som tar växlingssumman och en intarray med de olika
tillgängliga valutorna I fallande ordning och returnerar en int-array med antalet av varje valuta. Ex. Man vill veta
växeln för 789 kr I vår valuta. Man anropar då:
change(789,new int[] {1000,500,100,50,20,10,5,1}) som då returnerar: {0,1,2,1,1,1,1,4}.

## NB35

Lös det obegränsade kappsäcksproblemet med en girig algoritm. Skriv ett primitivt användargränssnitt (ok med
textbaserat) där man får ange storlek på kappsäcken och varornas vikt och värde. Programmet ska sedan presentera vilket
totalt värde man fick med sig i kappsäcken och hur många av varje vara man packade.

## NB36

Skriv en schemaläggningsfunktion enligt föreläsningen.

En girig algoritm:\
V – mängden av alla aktiviteter\
Medans det finns aktiviteter i V\
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Schemalägg den aktivitet i V som slutar först.\
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tag bort vald aktivitet ur V\
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tag bort alla aktiviteter ur V som överlappar med vald aktivitet\

Är lösningen den optimala lösningen? Om inte kan du hitta ett motexempel?

## NB38

Skriv en algoritm som löser handelseresande-problemet med en girig algoritm. Den ska spara både faktisk väg och
sträckans längd. Utgå ifrån skalet i Canvas så att vi får en grafisk representation. Använd helst ett BitSet för att
representera vilka städer vi besökt. Du behöver bara skriva själva algoritmen i TravelingSalesMan-klassen. (Tips har du
svårt att få Java-Fx att fungera kan du använda Java 1.8)

## NB39

Vid en fabrik finns ni stycken aktiviteter som behöver utföras under en dag. Varje aktivitet har en starttid si och en
sluttid mi. Under denna tid måste en person jobba med aktiviteten. Designa en girig algoritm som beräknar minsta antalet
personer som krävs för att lösa uppgiften. Implementera sedan algoritmen i ett program som läser data från en fil för
att sedan presentera minsta antalet som krävs. Designa ett antal kniviga fall och kontrollera att algoritmen verkligen
når det minimala antalet personer.

## NB40

Givet n punkter på den reella tallinjen. Hitta det minsta antal intervall med längden 2,0 som tillsammans täcker alla
punkter. Designa och implementera en girig algoritm. Låt programmet slumpa fram ett antal punkter. Försök verifiera att
din algoritm fungerar med ett resonemang. Skriv även ner ditt resonemang i din redovisning.
