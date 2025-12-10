public class Calling {
    public static void main(String[] args) {
        int result = Calculator.sub(3, 5);
        System.out.println("Kết quả" + result);
        String result2 = QuadraticEquation.solve(1, 2, 4);
        System.out.println(result2);
    }
}
