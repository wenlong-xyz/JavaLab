package java8.test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by wenlong on 2016/9/19.
 */
public class StreamTest {
    private static void streamConstructExample(){
        // 1. Individual values;
        Stream stream = Stream.of("a","b","c");

        // 2. Arrays
        String [] strArray = new String[] {"a","b","c"};
        stream = Stream.of(strArray);
        stream = Arrays.stream(strArray);

        // 3. Collections
        List<String> list = Arrays.asList(strArray);
        stream = list.stream();

        // 对于基本数据类型，有
        // IntStream, LongStream, DoubleStream
        IntStream.of(new int[]{1,2,3}).forEach(System.out::println);
        IntStream.range(1,3).forEach(System.out::println);
        IntStream.rangeClosed(1,3).forEach(System.out::println);
    }

    public static void streamOperationExample(){
        // 大写转换
        List<String> wordList = Arrays.asList("aaa","bbb","ccc");
        List<String> output = wordList.stream().map(String::toUpperCase).collect(Collectors.toList());
        output.forEach(System.out::println);

        // 平方数
        List<Integer> nums = Arrays.asList(1,2,3,4);
        List<Integer> squareNusm = nums.stream().map(n -> n * n).collect(Collectors.toList());
        squareNusm.forEach(System.out::println);


        // 一对多
        Stream<List<Integer>> inputStream = Stream.of(
                Arrays.asList(1),
                Arrays.asList(2,3),
                Arrays.asList(4,5,6)
        );
        Stream<Integer> outputStream = inputStream.flatMap((childList) -> childList.stream());
        List<Integer> result = outputStream.collect(Collectors.toList());
        result.forEach(System.out::println);

        // filter
        Integer[] sixNums = {1,2,3,4,5,6};
        Integer[] evens= Stream.of(sixNums).filter(n -> n % 2 == 0).toArray(Integer[]::new);

        // reduce
        String contact = Stream.of("A","B","C","D").reduce("",String::concat);
    }

    public static void supplierExample(){
        Random seed = new Random();
        Supplier<Integer> random = seed::nextInt;
        Stream.generate(random).limit(10).forEach(System.out::println);
    }
    public static void main(String[] args) {
//        streamConstructExample();
//        streamOperationExample();
        supplierExample();

    }
}
