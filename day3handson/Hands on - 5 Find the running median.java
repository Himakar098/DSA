import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {

    /*
     * Complete the 'runningMedian' function below.
     *
     * The function is expected to return a DOUBLE_ARRAY.
     * The function accepts INTEGER_ARRAY a as parameter.
     */

    public static List<Double> runningMedian(List<Integer> a) {
    // Write your code here
        PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>();
        PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>((o1, o2) -> Integer.compare(o2, o1));
        List<Double> result = new ArrayList<>();
        for(int i=0; i<a.size(); i++){
            if(!minHeap.isEmpty() && minHeap.peek() <= a.get(i)){
                minHeap.add(a.get(i));
            }else{
                maxHeap.add(a.get(i));
            }
            
            if(maxHeap.size() > minHeap.size()){
                minHeap.add(maxHeap.poll());
            }else if(maxHeap.size() < minHeap.size()){
                maxHeap.add(minHeap.poll());
            }
            
            
            if(minHeap.size() == maxHeap.size()){
                result.add((minHeap.peek()+maxHeap.peek())/2.0);
            }else if(minHeap.size() > maxHeap.size()){
                result.add((double) minHeap.peek());
            }else{
                result.add((double) maxHeap.peek());
            }
        }
        return result;

    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int aCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> a = IntStream.range(0, aCount).mapToObj(i -> {
            try {
                return bufferedReader.readLine().replaceAll("\\s+$", "");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        })
            .map(String::trim)
            .map(Integer::parseInt)
            .collect(toList());

        List<Double> result = Result.runningMedian(a);

        bufferedWriter.write(
            result.stream()
                .map(Object::toString)
                .collect(joining("\n"))
            + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();
    }
}
