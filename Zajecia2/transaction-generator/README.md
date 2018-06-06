Aby odpalić kontener dockera należy:

będąc w katalogu projektu wywołać komendę:

docker build --tag generator .

następnie, aby uruchomić obraz:

docker run -v <sciezka do katalogu z plikami .properties i .csv>:/storage -it generator