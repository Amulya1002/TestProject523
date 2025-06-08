package framework;

//Customized Library to handle the exceptions
public class GenericExceptions extends RuntimeException {

    public GenericExceptions(String message)
    {
        super(message);
    }
}
