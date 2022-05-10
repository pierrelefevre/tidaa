## NB 32

Skriv en klass som använder en grannlista (förbindelselista, adjacency list) för att representera en viktad graf.

Konstruktorn tar ett filnamn på en textfil och skapar utifrån denna en grannlista. Du väljer själv hur du organiserar
textfilen för att representera grafen. Det ska dock vara relativt enkelt att skriva en textfil för att representera en
godtycklig graf.

Skriv toString som skriver ut en nod per rad följt av noderna som denne har bågar till med vikter.

Skriv en metod som returnera närmsta vägen mellan två noder. Den ska använda Dijkstra.

Skapa en textfil som representerar grafen från föreläsningen och testkör.

## NB 33

Skriv en static metod som hittar det minsta uppspända trädet för en graf representerad med en grannmatris (
förbindelsematris, adjacency matrix) med hjälp av Prims algoritm. Funktionen skall ta en grannmatris som input och
returnera en int-array p, där p[v] anger den nod som noden v anslöts till enligt algoritmen. Använd samma knep som vi
använde för Dijkstra så att din algoritm blir O(n 2
), där n är antalet noder. Tänk noga efter vad du behöver ändra.

Utgå ifrån filerna som finns på Canvas. Du kommer då att kunna se grafiskt vilket träd du hittar. Det är bra om du
försöker förstå resten av koden. I alla fall bör du förstå hur p används för att få kanter
(edges). (Tips har du svårt att få Java-Fx att fungera kan du använda Java 1.8)

## NB 33.2

Gör om uppgift nb33 men nu för en grannlista. Den ska då bli effektivare än vår lösning med matris om inte alla noder
har bågar till varje annan nod. Obs här får du då skriva en egen main och klara dig utan grafik. Skriv ut resultatet i
main på formatet:

Node A var startnod \
Nod F anslöts till A\
Nod D anslöts till F\
Nod B anslöts till A\
Nod C anslöts till B\
Nod H anslöts till C\
Nod E anslöts till C\
Nod G anslöts till F\
TotalVikt: 15

(obs att det kan finnas fler korrekta lösningar med avseende på hur man ansluter noderna)