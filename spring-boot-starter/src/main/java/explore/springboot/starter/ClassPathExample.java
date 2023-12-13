package explore.springboot.starter;

public class ClassPathExample {

    public static void main(String[] args) {
        String classpath = System.getProperty("java.class.path");
        System.out.println(classpath);
    }
}
