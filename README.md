# NetworkAnalyzer

Dla administratorów różnego rodzaju sieci (energetycznych, wodociągowych itp.) nasza aplikacja Network Analyzer umożliwi znajdowanie ekonomicznych połączeń pomiędzy węzłami sieci oraz dostarczy informacji o samej sieci.

Opis struktury sieci:

• Node = każdy węzeł opisany jest poprzez:

	o id = unikalny identyfikator
   
   	o name = opcjonalny tekst opisujący węzeł
   
   	o type = entry, exit, regular
   
   	o outgoing = lista połączeń wychodzących z tego węzła
   
   	o incoming = lista połączeń wchodzących z innych węzłów
   
• Connection = powiązanie węzłów

	o Połączenie dwóch węzłów:
   
      • from = z jakiego węzła
	  
      • to = do jakiego węzła
	  
      • value = wartość, która w zależności od dziedziny biznesowej może oznaczać np. dystans, przepustowość, itd.


![Build status](https://travis-ci.org/MarcinKrz1/NetworkAnalyzer.svg?branch=master)
