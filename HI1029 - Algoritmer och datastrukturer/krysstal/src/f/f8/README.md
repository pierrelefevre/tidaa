## NB 24

Skriv en toString-metod och en remove-metod till vår hashtabell med länkning från föreläsningen. Skriv toString-metoden
med fokus på att den ska kunna hjälpa till vid testning av klassen. Den bör alltså skriva ut tabellen så att man kan se
var i tabellen de olika värdena är lagrade. Skriv som alltid en main som testar din metod. På canvas hittar filer som
utgår ifrån att du använder vår enkellänkade lista. Den bör anpassas så att den minimerar minnesbehovet hos varje
instans och därmed inte ha någon variabel size. För att det ska fungera behöver du ha implementerat remove i iteratorn.
Har du svårt att få till det med din enkellänkade lista kan du använda javas dubbellänkade lista.

## NB 25

Lägg också till att den dubblar storleken på tabellen när antalet entries (key, value –par) överstiger 75% av tabellens
storlek. Kom ihåg att du måste sätta in alla entries igen eftersom de kommer att få ett annat index. Skriv en main som
testar din metod.

## NB 26

För att göra denna behöver du ha gjort NB24. Skriv nu ordentlig testkod för vår hashtabell. Om ni gjort NB 25 ska ni
testa även denna. Fundera på vad den behöver testa. Inspiration kan du hämta på sid 394 i boken.

## NB 26.1

Skriv en funktion som tar en array av strängar och returnerar antalet gånger som den vanligaste strängen förekommer i
arrayen. Din lösning ska vara O(n). Tips: enklast är att använda sig av en HashMap där value är antalet gånger ordet
förekommer.

## NB 26.2

Skriv en funktion som tar en array av strängar och returnerar antalet unika strängar. Din lösning ska vara O(n). Tips:
kanske ett hashset?

## NB 26.3

Implementera en egen hashtabell som använder öppen adressering. Den ska minimum ha put, get och remove och göra rehash
vid behov. Börja själv och använd boken som inspiration om du kör fast.