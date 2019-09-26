# Text2Midi Translator
Code Repository for "Audible Cosmos" Altered Book Project

## Contributors
Author: Pablo Gaeta

Source code for Ascii to Midi conversion: Alex Selby from www.archduke.org

### About the Code...
This code works by taking text from the input.txt file and converting it into an ascii midi representation
and usable midi file. Each letter from the input is converted into a note on the Western tone system 
(C, C#, D, D#, E, F, F#, G, G#, A, A#, B, C). Harmony is created by semitone shifts determined by a random
number generator (This leaves the chance of the cosmos to form the piece). The input text that I used comes from
Carl Sagan's _Cosmos_. The user can change the text file to any piece of ascii text they wish to create a new piece.

### Files
1. Makefile        - Compiles Translate class (Find out more in documentation)
2. Translate.java  - Main method writes the translation to mainOut
3. asc2mid.c       - Source code for ascii to midi conversion (Should be compiled with gcc, creating a.out)
4. Owrite.class    - Overwrite class that restores the mainOut file to its default state
5. input.txt       - input text for this project (Can be changed to anything by user)
6. mainOut         - Contains ascii representation of the midi data. Written to automatically by Translate
7. README.md       - Info on how to get started

Note: mymid.mid will be created as the output midi file to be used

### Getting Started
This will compile the source code and create the midi file that I created for the project.

```shell
$ git clone https://github.com/pablofgaeta/Text2Midi.git
$ cd Text2Midi
$ gcc asc2mid.c
$ make
$ make do
$ make check
```
