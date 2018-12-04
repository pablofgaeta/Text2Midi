Translate.class: Translate.java
	javac -Xlint Translate.java
do:
	java Translate
clear:
	java Owrite
check:
	./Ascii2midi mainOut > mymid.mid
clean:
	rm Translate.class