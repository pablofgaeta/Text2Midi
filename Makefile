# Pablo Gaeta
# Dec 4, 2018
# Makefile
# 
# Description:
# Compiles java source code for the ascii to midi translation.
#
# How to use (terminal commands):
# "make do"    - runs the main method of the class, Translate, that writes midi 
#                information to the file "mainOut" using text from "input.txt"
#
# "make clear" - Restores "mainOut" to its original state so it can be written to again
#
# "make check" - Converts the "mainOut" file into a usable midi file
#
# "make clean" - removes the compiled Translate class


# Compiles Translate class. Done automatically when calling "make"
Translate.class: Translate.java
	javac -Xlint Translate.java

# PHONY TARGETS
do:
	java Translate
clear:
	java Owrite
check:
	./Ascii2midi mainOut > mymid.mid
clean:
	rm Translate.class
