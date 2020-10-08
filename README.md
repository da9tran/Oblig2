# Obligatorisk oppgave 2 i Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i Algoritmer og Datastrukturer. 

# Krav til innlevering

Se oblig-tekst for alle krav. Oppgaver som ikke oppfyller følgende vil ikke få godkjent:

* Git er brukt til å dokumentere arbeid (minst 2 commits per oppgave, beskrivende commit-meldinger)	
* Ingen debug-utskrifter
* Alle testene som kreves fungerer (også spesialtilfeller)
* Readme-filen her er fyllt ut som beskrevet

# Arbeidsfordeling

Oppgaven er levert av følgende student:
* Dani Tran, S346177, s346177@oslomet.no


Vi har brukt git til å dokumentere arbeidet vårt. Vi har 16 commits totalt, og hver logg-melding beskriver det vi har gjort av endringer.

Oppgaver jeg har gjort:
* Oppgave 1, 2, 3, 4, 5, 6 og 8.

# Beskrivelse av oppgaveløsning (maks 5 linjer per oppgave)

* Oppgave 1: Løste ved å implementere en metode for int antall() og boolean tom() som er standardkonstruktøren. 
Deretter lager jeg en metode som fjerner den midlertidige noden, altså konstruktøren public DobbeltLenketListe(T[] a).
Til slutt implimenterer jeg int antall() og boolean tom() i gitt programbit som gir utskrift: 0 true. Og
tester konstruktøren ved hjelp programbit, og får utskrift 3 false.

* Oppgave 2: a) Implimenterte en metode for metoden String omvendtString() som returnerer en tegnstreng
med listens verdier. Og implementerte en metode for metoden String omvendtString(), som returnerer tegnstreng på samme
fom som den toString() gir, men verdiene skal komme i omvendt rekkefølge. Til slutt kjørte jeg følgende
programbit for oppgave 2 a. b) Implementerte en metode for metoden boolean leggInn(T verdi) og bruker en 
requireNonNull-metode fra klassen Objects. Antallet økes etter en 
innlegging. Dette sjekket jeg via programbiten gitt på oppgave 2b.

* Oppgave 3: a) Først implementerte jeg den private hjelpemetoden Node<T> finnNode(int indeks), som skal 
returnere noden med den gitte indeksen/posisjonen. Også implimenterte jeg metoden public T hent(int indeks)
ved å bruke finnNode(), hvor jeg brukte metoden indeksKontroll() som arves fra Liste. Byttet også tablengde med antall.
Så lagde jeg metoden T oppdater(int indeks, T nyverdi), som erstattet verdien som erstatter verdien på plass indeks med 
nyverdi og returnerer det som lå der fra før. b) Først implementerte en privat metode kalt fratilKontroll, som sjekkes
om indeksene fra og til er lovlige, så lagde jeg ferdig metoden Liste<T> subliste(int fra, int til) som skal 
returnere en liste som inneholder verdiene fra intervallet fra:til > i "vår" liste. Til slutt sjekket jeg følgende
programbit på main metoden,og det ble kjørt og skrevet ut. 


