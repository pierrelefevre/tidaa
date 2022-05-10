package f.f3;

public class NB7 {
    public static void main(String[] args) {
        LinkedStack<String> s = new LinkedStack<>();

        s.push("Fizz");
        s.push("Buzz");
        System.out.println(s.push("FizzBuzz"));
        System.out.println(s.peek());
        System.out.println(s.pop());
        s.push("FooBar");
        System.out.println(s.size());
        System.out.println(s.peek(1));
        System.out.println(s.pop());
        System.out.println(s.pop());
        System.out.println(s.empty());
        System.out.println(s.flush());

        System.out.println(s.empty());
    }
}
