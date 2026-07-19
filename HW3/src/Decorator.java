interface Outfit {
    String getDescription();
    int getStars();
}

abstract class OutfitDecorator implements Outfit{
    private Outfit outfit;

    public OutfitDecorator(Outfit outfit){
        this.outfit = outfit;
    }

    public String getDescription(){
        return outfit.getDescription();
    };

    public int getStars(){
        return outfit.getStars();
    };
}

class Belt extends OutfitDecorator{

    public Belt(Outfit outfit) {
        super(outfit);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " с кожанным ремнем";
    }

    @Override
    public int getStars() {
        return super.getStars() + 3;
    }
}

class Jeans implements Outfit{

    @Override
    public String getDescription() {
        return "Классические джинсы";
    }

    @Override
    public int getStars() {
        return 1;
    }
}

class Outfits{

    public static void main(String[] args){
        Outfit outfit = new Jeans();
        Outfit outfitDecorated = new Belt(new Jeans());

        System.out.println(outfit.getDescription());
        System.out.println(outfit.getStars());

        System.out.println(outfitDecorated.getDescription());
        System.out.println(outfitDecorated.getStars());
    }
}