Transaction generator po zmianach dotycz�cych DI.
Dodano funkcj� zapisu do pliku XML.

Aby uruchomi� program nale�y otworzy� projekt za pomoc� intellij i odpali� program z np. takimi parametrami 
-customerIds 1:20 -dateRange "2018-03-08T00:00:00.000-0100";"2018-03-08T23:59:59.999-0100" -itemsFile items.csv -itemsCount 5:15 -itemsQuantity 1:30 -eventsCount 1000 -outDir ./output -format xml


Po zmianach niestety nie dzia�aj� testy, kt�rych nie zd��y�em jeszcze naprawi�