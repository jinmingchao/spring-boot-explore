package explore.springboot.starter;

public class SpringStarterApplication {
    public static void main(String[] args) {
        String classpath = System.getProperty("java.class.path");
        System.out.println(classpath);
    }
}
