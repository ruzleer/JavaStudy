import java.util.Date;

public final class ImmutableClass {

    private final String name;
    private final Date myDate;


    public ImmutableClass(String name, Date myDate){
        this.name = name;
        this.myDate = new Date(myDate.getTime());
    }

    public String getName(){
        return name;
    }

    public Date getMyDate(){
        return new Date(myDate.getTime());
    }

    public static void main(String... args) {
        ImmutableClass obj = new ImmutableClass("MyClass", new Date());
        System.out.println(obj.getName());
        System.out.println(obj.getMyDate());

        Date other = obj.getMyDate();
        other.setTime(8000);
        System.out.println(obj.getMyDate());
        System.out.println(other);
    }
}
