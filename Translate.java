import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Translate
{
    private static final String MIDI_STRING1 = "BA 1 CR ";
    private static final String MIDI_STRING2 = " TR 0 CH 1 NT ";
    private static String MIDI_STRING3 = " 2";
    private static int midiTimeCounter = 0;
    private static int midiNoteCounter = 0;
    private static Boolean first = true;
    private static String prevNote = "";
    public final static String [] dict = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

    public static void main(String [] args) {

        long startTime = System.nanoTime();

        String str = readFile("input.txt");
        System.out.println(str);
        // inverseCtrPt(str);
        // chords(str);
        newSong(str);

        long endTime = System.nanoTime();
        long decSec = (endTime - startTime) / (100000000);
        System.out.println("Program Took About "+ (decSec/10) + "." + (decSec%10) + " secs"); 
    }

    public static String readFile(String fileName) {
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            System.out.println("readFile: File not found. Returning empty string.\n");
        }
        return content;
    }

    public static void chords(String str){
        LinkedList <String> chordStrings = new LinkedList<>();
        str = str.toLowerCase();
        StringTokenizer tokens = new StringTokenizer(str);
        while(tokens.hasMoreTokens()){
            // System.out.print(tokens.nextToken()+"\n");
            chordStrings.add(tokens.nextToken());
        }
        for(String toke: chordStrings){
            LinkedList <String> tokenChord = new LinkedList<>();
            char arr[] = toke.toCharArray();
            for(char note: arr){
                tokenChord.add(checkNote(note));
            }
            for(String note: tokenChord) {
                try {
                    appendchords(note);
                }
                catch (IOException io) {
                    System.out.println("Couldn't append chord\n");
                }
            }
            midiTimeCounter+= 2;
            System.out.println(tokenChord);
        }
        System.out.println("Linked list of tokens: " + chordStrings);
    }

    public static void appendchords(String str) throws IOException {
        if(str.equals("")){
            return;
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter("mainOut",true));
        // char arr[] = str.toCharArray();
        if(first==true) {
            first = false;
            writer.append('\n' + MIDI_STRING1 + midiTimeCounter + MIDI_STRING2 + str + MIDI_STRING3 + " von=100\n");
            
        }
        else {
            if(str.equals(prevNote)) {
                writer.append(MIDI_STRING1 + midiTimeCounter + MIDI_STRING2 + str + "\'" + MIDI_STRING3 + '\n');
            }
            else{
                writer.append(MIDI_STRING1 + midiTimeCounter + MIDI_STRING2 + str + MIDI_STRING3 + '\n');
            }
        }
        writer.close();
    }
    
    public static void inverseCtrPt(String str){
        str = str.toLowerCase();
        str = str.replaceAll("\\s+","");
        char arr[] = str.toCharArray();
        for(char c: arr) {
            int newCharNum = 122-(((int) c)-97);
            char d = (char) newCharNum;
            try {
                String tmp = checkNote(c);
                append2midifile(tmp);
                prevNote = tmp;
                tmp = checkNote(d);
                append2midifile(tmp);
                prevNote = tmp;
            }
            catch (IOException io) {
                System.out.println("Couldn't write to file.");
            }
            prevNote=checkNote(c);
        }
    }

    public static void append2midifile(String str) throws IOException{
        if(str.equals("")){
            return;
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter("mainOut",true));
        if(first==true) {
            first = false;
            writer.append('\n' + MIDI_STRING1 + midiTimeCounter + MIDI_STRING2 + str + MIDI_STRING3 + " von=100\n");
        }
        else {
            if(str.equals(prevNote)) {
                writer.append(MIDI_STRING1 + midiTimeCounter + MIDI_STRING2 + str + "\'" + MIDI_STRING3 + '\n');
            }
            else{
                writer.append(MIDI_STRING1 + midiTimeCounter + MIDI_STRING2 + str + MIDI_STRING3 + '\n');
            }
        }
        writer.close();
        midiNoteCounter++;
        if(midiNoteCounter % 2 == 0) {
            midiTimeCounter ++;
        }
    }

    public static void newSong(String str) {
        str = str.toLowerCase();
        str = str.replaceAll("[^A-Za-z]+","");
        System.out.println(str);
        char arr[] = str.toCharArray();
        for(char c: arr) {
            try {
                String tmp = checkNote(c);
                append2midifile(tmp);
                prevNote = tmp;
                int noteIndex = findNoteIndex(tmp);
                if(Math.random()<.2) {
                    append2midifile(dict[(noteIndex+7)%12]);
                    prevNote = dict[(noteIndex+7)%12];
                }
                else if (Math.random()<.4){
                    append2midifile(dict[(noteIndex+10)%12]);
                    prevNote = dict[(noteIndex+10)%12];
                }
                else if (Math.random()<.6) {
                    append2midifile(dict[(noteIndex+5)%12]);
                    prevNote = dict[(noteIndex+5)%12];
                }
                else if (Math.random()<.8) {
                    append2midifile(dict[(noteIndex+6)%12]);
                    prevNote = dict[(noteIndex+6)%12];
                }
                else if (Math.random()<1) {
                    append2midifile(dict[(noteIndex+4)%12]);
                    prevNote = dict[(noteIndex+4)%12];
                }
                
            }
            catch (IOException io) {
                System.out.println("Couldn't write to file.");
            }
            prevNote=checkNote(c);
        }
    }

    public static int findNoteIndex(String note){
        for(int i = 0; i < dict.length; i++) {
            if(note.equals(dict[i]))
                return i;
        }
        return 0;
    }

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
