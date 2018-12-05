# Text2Midi Translator
Code Repository for "Audible Cosmos" Altered Book Project

## Contributors
Author: Pablo Gaeta

Source code for Ascii to Midi conversion: Alex Selby from www.archduke.org

### About the Code...
This code works by taking text from the input.txt file and converting it into an ascii midi representation
and usable midi file. Each letter from the input is converted into a note on the western classical scale 
(C, C#, D, D#, E, F, F#, G, G#, A, A#, B, C). Harmony is created by semitone shifts determined by a random
number generator (This leaves the chance of the cosmos to form the piece).


### Files
1. Makefile        - Compiles Translate class (Find out more in documentation)
2. Translate.java  - Main method writes the translation to mainOut
2. asc2mid.c       - Source code for ascii to midi conversion (Should be compiled with gcc, creating a.out)
3. Owrite.class    - Overwrite class that restores the mainOut file to its default state
4. input.txt       - input text for this project (Can be changed to anything by user)
5. mainOut         - Contains ascii representation of the midi data. Written to automatically by Translate
6. README.md       - Info on how to get started

Note: mymid.mid will be created as the output midi file to be used

### Getting Started
This will compile the source code and create the midi file that I created for the project.

To alter

```shell
$ git clone https://github.com/pablofgaeta/Text2Midi.git
$ cd Text2Midi
$ gcc asc2mid.c
$ make
$ make do
$ make check
```
