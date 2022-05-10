## Uppgift 7 (Föreläsning 6)

Skriv ett program som hittar alla lösningar till pusslet ovan. För att lösa det har man 8 likadana pusselbitar som kan
vridas så att man får de fyra varianterna ovan. Den grå rutan skall inte täckas. Lägg sedan till så att användaren får
möjlighet att ange vilken ruta som ska vara grå och programmet ska då beräkna antalet möjliga lösningar. I exemplet ovan
har användaren då angett 1,3. Programmet skall också skriva ut lösningarna. En bra backtracking algoritm kommer att
kunna lösa pusslet effektivt genom att tidigt kunna avfärda felaktiga dellösningar. Eftersträva att få till en sådan.

Nicklas tips:

Lös med backtracking, tips!
Se bitarna som att de har en fäst punkt. iterera över raderna Vi kommer inte kunna lämna rader ovanför oss tomma ->
alltså kan vi backtracka Gå aldrig förbi en ruta som är tom

Enklat är att hård koda att stoppa in en ruta en funktion somn kollar ifall det går en funktion som sätter in en bit en
funktion som tar bort en bit Numrera bitarna så man fattar vilken bit som finns

**KOM IHÅG ATT HITTA ALLA LÖSNINGARNA!**

## Uppgift 8 (Föreläsning 7)

Vi jobbar här med det binära sökträdet från föreläsningen. Skriv en metod getNextLarger som returnerar det element i
trädet som är närmast större än det inskickade värdet. Om trädet innehåller 1, 2, 5, 8, 12 (balanserat) och vi skickar
in 2, 3 eller 4 så returnerar metoden 5. Skickar vi in 5, 6 eller 7 returnerar den 8. Skickar vi in 12 eller högre ska
den returnera null. Detsamma gäller om trädet är tomt. Denna metod skall vara O(logn) om trädet är balanserat. Kom ihåg
att testa alla möjliga fall. Det är ganska många.

## Uppgift 9 (Föreläsning 10)

Implementera en radix-sort som använder counting sort. Den ska sortera heltal av typen int. Skriv ett testprogram som
slumpar fram en miljon positiva heltal och sedan låter radixsort sortera dessa. Programmet ska sedan gå igenom och
kontrollera att sorteringen har fungerat. Mät gärna tiden för sorteringen.