Aby odpalić kontener dockera należy:

będąc w katalogu projektu wywołać komendę:

docker build --tag generator .

następnie, aby uruchomić obraz:

docker run -v <sciezka do katalogu z plikami .properties i .csv>:/storage -it generator


Broker:

Należy stworzyć shadowjara, a potem odpalić program komendą:

java -jar ./build/libs/Transaction_generator.jar --itemsFile=<ściezka_do_itemfile> --customerIds=1:1 --dateRange="2018-03-08T00:00:00.000-0100":"2018-03-08T23:59:59.999-0100" --itemsCount=1:2 --itemsQuantity=1:2 --outDir=./output --eventsCount=1 --format=xml --broker=tcp://localhost:61616 --queue=transactions-queue --topic=transaction-topics
