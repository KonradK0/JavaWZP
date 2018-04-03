Transaction generator po zmianach dotycz¹cych DI.
Dodano funkcjê zapisu do pliku XML.

Aby uruchomiæ program nale¿y otworzyæ projekt za pomoc¹ intellij i odpaliæ program z np. takimi parametrami 
-customerIds 1:20 -dateRange "2018-03-08T00:00:00.000-0100";"2018-03-08T23:59:59.999-0100" -itemsFile items.csv -itemsCount 5:15 -itemsQuantity 1:30 -eventsCount 1000 -outDir ./output -format xml


Po zmianach niestety nie dzia³aj¹ testy, których nie zd¹¿y³em jeszcze naprawiæ