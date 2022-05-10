## NB41

Skriv ett program som slumpar n stycken heltal mellan -1000 och 1000. Användaren anger n. Programmet beräknar sedan den
maximala delsekvenssumman med hjälp av ett funktionsanrop av en funktion som använder en söndra och härska algoritm. Den
skriver sedan ut vad summan blev och hur många anrop till funktionen som behövdes.

## NB42

Skriv en funktion som tar en array med punkter (använd en klass för att representera en 2D-punkt med float för
koordinaterna) och returnerar det kortaste avståndet mellan två punkter. Funktionen ska använda en effektiv söndra och
härska algoritm enligt föreläsningen (dvs inte jämföra onödiga punkter över mittlinjen). Skriv också en funktion som
löser problemet med genom att jämföra varje punkt med varje annan punkt och som då blir O(n2
). Skriv nu en main som slumpar fram n punkter som ligger i intervallet: -1<x<1 och -1<y<1 med lika stor sannolikhet för
alla värden. Anropa dina funktioner med de framslumpade punkterna och kontrollera att du får samma resultat.

## NB43

Skriv ett program som löser det generella växlingsproblemet. Användaren får först ange hur många olika valutor det finns
och sedan valören på dessa. Därefter får denne ange vilket belopp som skall växlas och programmet svarar med minsta
antalet mynt och sedlar som behövs. Lägg till att programmet också skriver ut växeln.

## NB44

Skriv en funktion som löser Skyline-problemet. Skriv sedan ett program som slumpar fram n st hus (n anges av användaren)
och ritar upp dessa och deras skyline.

## NB45

Av n element skall vi ta reda på om det finns mer än n/2 av något och i så fall vilket. Utgå från att elementen kan
testas för likhet men inte rangordnas (equal men inte compare). Om du vill kan du skriva metoden för en array av heltal
men kom i så fall ihåg att du inte får utnyttja att de går att sortera. Designa en söndra och härska algoritm som hittar
ett sådant elementet i en array om ett sådant finns. Skriv sedan en funktion och en main med några hårdkodade exempel.
Sträva efter att få algoritmen så effektiv som möjligt (O(nlogn) blir resultatet av en bra söndra och härska). Man kan
hitta en lösning som är O(n) men då inte med söndra och härska.