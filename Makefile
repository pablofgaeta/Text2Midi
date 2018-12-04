Translate.class: Translate.java
	javac -Xlint Translate.java
do:
	java Translate
clear:
	java Owrite
check:
	./a.out mainOut > mymid.mid
clean:
	rm Translate.class
