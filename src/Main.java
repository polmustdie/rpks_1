import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        //FileReader reader = null;
        StringBuilder buf = new StringBuilder();
        //Scanner scanner = null;
        try (FileReader reader = new FileReader("src/brackets.txt");
        Scanner scanner = new Scanner(reader))
        {
            //reader = new FileReader("src/brackets.txt");
            //scanner = new Scanner(reader);
            while (scanner.hasNext()) {
                buf.append(scanner.next()); // copying brackets.txt to buffer
                //System.out.println(buf);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e); //close reader
//        } finally {
//            assert reader != null;
//            reader.close();
//            assert scanner != null;
//            scanner.close();
//        }
        }
        if (buf.length() == 0) throw new Exception("Bracket template is empty");
        ArrayList<String> leftBracketList = new ArrayList<>();
        ArrayList<String> rightBracketList = new ArrayList<>();
        ArrayList<String> queue = new ArrayList<>();
        ArrayList<String> queueIndex = new ArrayList<>();
        StringBuilder bufferString = new StringBuilder(buf);

        //while (bufferString.indexOf("left") != -1) { //toString
        while (bufferString.toString().contains("left")) {
            leftBracketList.add(String.valueOf(bufferString.charAt(bufferString.indexOf("left") + 7)));
            rightBracketList.add(String.valueOf(bufferString.charAt(bufferString.indexOf("right") + 8)));
            bufferString.delete(0, bufferString.indexOf("right") + 8);
            //System.out.println(leftBracketList);
        }
//        System.out.println(leftBracketList);
//        System.out.println(rightBracketList);
//        scanner.close();
//        reader.close();


        try (FileReader readerText = new FileReader("src/test.txt");
             Scanner scanText = new Scanner(readerText);) {
            buf = new StringBuilder();
            while (scanText.hasNext()) {
                buf.append(scanText.next());
            }
            if (buf.isEmpty()) throw new Exception("Text is empty");
            //StringBuilder SBBuffer = new StringBuilder(buf); //test.txt
            //System.out.println(SBBuffer);

            for (int i = 0; i < buf.length(); i++) {
                //System.out.println(queue);

                if ((rightBracketList.contains((String.valueOf(buf.charAt(i))))) && (!leftBracketList.contains(String.valueOf(buf.charAt(i))))) {
                    if (queue.isEmpty()) {
                        queueIndex.add(String.valueOf(i));
                    } else if (leftBracketList.indexOf(queue.get(queue.size() - 1)) == rightBracketList.indexOf(String.valueOf(buf.charAt(i)))) {
                        queue.remove(queue.size() - 1);
                    } else {
                        queueIndex.add(String.valueOf(i));
                    }
                } else if ((rightBracketList.contains(String.valueOf(buf.charAt(i)))) && (leftBracketList.contains(String.valueOf(buf.charAt(i))))) {
                    if (queue.isEmpty()) {
                        queue.add(String.valueOf(buf.charAt(i)));
                    } else if ((leftBracketList.indexOf(queue.get(queue.size() - 1))) == (rightBracketList.indexOf(String.valueOf(buf.charAt(i))))) {
                        queue.remove(queue.size() - 1);
                    } else {
                        queue.add(String.valueOf(buf.charAt(i)));
                    }
                } else if ((leftBracketList.contains(String.valueOf(buf.charAt(i))))) {
                    queue.add(String.valueOf(buf.charAt(i)));
                }
            }
            if (queueIndex.isEmpty())
                System.out.println("No mistakes found");
            else
                System.out.println("Found mistakes on position(s): " + queueIndex);
        } catch (Exception e) {
            throw new Exception("Error while parsing template");
        }
    }
}

