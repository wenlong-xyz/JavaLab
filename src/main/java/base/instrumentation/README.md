# Java Instrumentation Demo

1. 基本概念请参考
    * [Java Instrumentation](http://javapapers.com/core-java/java-instrumentation/)
    * [java instrument学习总结](http://www.jianshu.com/p/496419cec8eb)
2. 编译执行过程备注
    ```shell
    cd package 根目录
    javac -cp [import的jar文件] [java文件]
    jar -cvfm Agent.jar [manifest文件] [class文件]
    java -javaagent:Agent.jar base.instrumentation.Main
    ```

