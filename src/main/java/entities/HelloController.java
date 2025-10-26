package entities;

import annotation.Route;

@Route("/hello")
public class HelloController {

    @Route("/say")
    public void sayHello() {
        System.out.println("Hello World!");
    }

    @Route("/bye")
    public void sayBye() {
        System.out.println("Bye!");
    }
}
