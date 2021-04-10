package text_censore;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            List<String> myText = Arrays.asList(new String(Files.readAllBytes(Paths.get("text.txt"))).split("\\."));
            List<String> blackWords = Files.readAllLines(Paths.get("black_list.txt"));
            List<String> wrongSentences = new ArrayList<>();
            for (String blackWord: blackWords){
                for (String sentence : myText){
                    if (sentence.toLowerCase().contains(blackWord.toLowerCase()) &&
                            !wrongSentences.contains(sentence)){
                        wrongSentences.add(sentence);
                    }
                }
            }

            if (wrongSentences.size() == 0){
                System.out.println("Файл прошел проверку на цензуру:");
            }else {
                System.out.println("Количество предложений не прошедших цензуру " + wrongSentences.size());
                System.out.println();
                for (String wrongSentence: wrongSentences){
                    System.out.println("ПРЕДЛОЖЕНИЕ | " + wrongSentence.trim());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
