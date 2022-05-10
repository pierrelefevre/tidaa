## Uppgift 4 (Föreläsning 4)

Du ska simulera en liten flygplats. Denna har endast en landningsbana. Simuleringen skall ske i tidssteg om 5 minuter.
Det tar 20 minuter att genomföra en landning och lika lång tid att genomföra en start. Plan som behöver landa
prioriteras alltid före plan som vill starta (dock får ett plan som påbörjat en start alltid fullfölja). Varje
femminuters intervall är det 5 % sannolikhet att ett plan begär att få landa och 5 % sannolikhet att ett plan begär att
få starta. Kör en simulering under 10 år och ta reda på medelväntetiden för plan som ska landa och medelväntetiden för
plan som ska starta. Ta också reda på maximala väntetiden för ett plan som ska starta och för ett plan som ska landa.
För statistikens skull räknar vi med att ett plan som dyker upp under ett intervall dyker upp precis i intervallets
slut.\
Tips: Tanken är då att man använder två köer: ett för plan som ska landa och ett för plan som ska tarta.\
Typisk medelväntetid för landning: 3,8 min och för start 7,9 min.

## Uppgift 5 (Föreläsning 5)

En robot ska flytta om 5 paket så att de ligger i bokstavsordning. Den har två funktioner:\
• byta plats på de två paketen längst till vänster (b)\
• lägga paketet längst till höger längst till vänster (s)

Skriv ett program som läser in en godtycklig ordning (men alltid samma bokstäver, A, B, C, D, och E)
och tar reda på minsta antal drag roboten behöver och tillhörande dragordning. Ex: Ordning: BECAD Tar 7 steg: bsssbsb.
Lös problemet med en enkel rekursiv lösning (djupet först). Sätt maxdjupet till 15.

## Uppgift 6 (Föreläsning 6)

Skriv ett nytt program som löser problemet ovan men nu med djupet först såsom vi gjorde med hissen på föreläsningen.
Ditt program skall alltså inte behöva något maxdjup utan första lösningen det hittar är också garanterat den (eller en
av de) kortaste.