/*
 * Pablo Gaeta
 * Dec 4, 2018
 * Translate.java
 * 
 * Description:
 *  Writes an ascii representation of midi data to a file, mainOut, from an input text file, input.txt.
 * 
 *  Methods:
 *  main            - Runs newSong method and records run time
 *  readFile        - Reads the specified file and returns it as a string
 *  append2midifile - Adds a new line to the plain text file, mainOut, with midi instructions
 *  newSong         - Algorithm to generate new midi information
 *  findNoteIndex   - Finds and returns the index of the given note in the "dict" array
 *  checkNote       - Checks what note a certain char should yield and returns that String
 */

import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Translate
{
    // CLASS VARIABLES
    
    // // Constants
    private static final String MIDI_STRING1 = "BA 1 CR ";
    private static final String MIDI_STRING2 = " TR 0 CH 1 NT ";
    private static final String MIDI_STRING3 = " 2";
    private static final String [] dict = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
   
    // // Variables
    private static int midiTimeCounter = 0;
    private static int midiNoteCounter = 0;
    private static Boolean first = true;
    private static String prevNote = "";
    

    public static void main(String [] args) {

        long startTime = System.nanoTime();

        //Run newSong with text from input.txt
        String str = readFile("input.txt");
        newSong(str);

        long endTime = System.nanoTime();

        //Calculate time it took for the program to run
        long decSec = (endTime - startTime) / (100000000);
        System.out.println("Program Took About "+ (decSec/10) + "." + (decSec%10) + " secs"); 
    }

    // Return string of the input text from the file, fileName
    // Note: If nothing can be read from the file an empty string is returned
    public static String readFile(String fileName) {
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            System.out.println("readFile: File not found. Returning empty string.\n");
        }
        return content;
    }

    //Appends a new note to the file, mainOut, from the given string str.
    public static void append2midifile(String str) throws IOException{

        //Case if empty string is passed. Don't do anything
        if(str.equals("")){
            return;
        }

        //Open buffered writer to write to the file, mainOut
        BufferedWriter writer = new BufferedWriter(new FileWriter("mainOut",true));

        //Case if this is the first note to write. Must turn velocity on
        if(first==true) {
            first = false;
            writer.append('\n' + MIDI_STRING1 + midiTimeCounter + MIDI_STRING2 + str + MIDI_STRING3 + " von=100\n");
        }

        //Case if it's any other note
        else {
            //Case if the note equals previous note. Plays an octave up, so there is no overlap
            if(str.equals(prevNote)) {
                writer.append(MIDI_STRING1 + midiTimeCounter + MIDI_STRING2 + str + "\'" + MIDI_STRING3 + '\n');
            }
            //Case if it's a unique note
            else{
                writer.append(MIDI_STRING1 + midiTimeCounter + MIDI_STRING2 + str + MIDI_STRING3 + '\n');
            }
        }

        //Close the writer
        writer.close();

        //Increment the note counter
        midiNoteCounter++;

        //Increment time counter every other note, so there can be two notes played at the same time
        if(midiNoteCounter % 2 == 0) {
            midiTimeCounter ++;
        }
    }

    // Makes a new complete midi song from the input string str.
    // Note: Argument should be the complete string from the given text file
    public static void newSong(String str) {

        // Make the string pretty
        str = str.toLowerCase();               // All lower case characters
        str = str.replaceAll("[^A-Za-z]+",""); // Keep only alphabetic characters
        char arr[] = str.toCharArray();        // Put each character into a char array so it can be traversed

        // Traverse each character in the string
        // Note: In each instance of this loop, two notes will be appended to be played at the same time in the midi track
        for(char c: arr) {
            // Try to append midi file. Catch IOException
            try {
                // Save what note this character corresponds to
                String tmp = checkNote(c);
                // Append the note
                append2midifile(tmp);
                // Save this note as prevNote
                prevNote = tmp;

                // Save which note the previous note is
                int noteIndex = findNoteIndex(tmp);

                // Random cases to choose what interval should be played for harmony
                if(Math.random()<.2) {
                    // Appends a note 7 semitones up from the first
                    append2midifile(dict[(noteIndex+7)%12]);
                    // Saves this note as prevNote
                    prevNote = dict[(noteIndex+7)%12];
                }
                else if (Math.random()<.4){
                    // Appends a note 10 semitones up from the first
                    append2midifile(dict[(noteIndex+10)%12]);
                    // Saves this note as prevNote
                    prevNote = dict[(noteIndex+10)%12];
                }
                else if (Math.random()<.6) {
                    // Appends a note 5 semitones up from the first
                    append2midifile(dict[(noteIndex+5)%12]);
                    // Saves this note as prevNote
                    prevNote = dict[(noteIndex+5)%12];
                }
                else if (Math.random()<.8) {
                    // Appends a note 6 semitones up from the first
                    append2midifile(dict[(noteIndex+6)%12]);
                    // Saves this note as prevNote
                    prevNote = dict[(noteIndex+6)%12];
                }
                else if (Math.random()<1) {
                    // Appends a note 4 semitones up from the first
                    append2midifile(dict[(noteIndex+4)%12]);
                    // Saves this note as prevNote
                    prevNote = dict[(noteIndex+4)%12];
                }
                
            }
            catch (IOException io) {
                System.out.println("Couldn't write to file.");
            }
        }
    }

    // Find the index of the given argument note in the "dict" array
    // Precondition: "note" must be a valid note string that exists in dict or 0 will be returned
    public static int findNoteIndex(String note){
        //Traverse the entire dict array
        for(int i = 0; i < dict.length; i++) {
            //Check if the given note matches the note in dict
            if(note.equals(dict[i]))
                return i;
        }
        return 0;
    }

    // Returns a String representing a note in the western scale from a given alphabetic character
    // Precondition: Entered char c must be a valid alphabetic char or an empty string will be returned
    public static String checkNote(char c){
        if(c=='a' || c=='m' || c=='n' || c=='z') {
            return "C";
        }
        if(c=='b' || c=='o') {
            return "C#";
        }
        if(c=='c' || c=='p') {
            return "D";
        }
        if(c=='d' || c=='q') {
            return "C#";
        }
        if(c=='e' || c=='r') {
            return "E";
        }
        if(c=='f' || c=='s') {
            return "F";
        }
        if(c=='g' || c=='t') {
            return "F#";
        }
        if(c=='h' || c=='u') {
            return "G";
        }
        if(c=='i' || c=='v') {
            return "G#";
        }
        if(c=='j' || c=='w') {
            return "A";
        }
        if(c=='k' || c=='x') {
            return "A#";
        }
        if(c=='l' || c=='y') {
            return "B";
        }
        return "";
    }
}
