interface ProductInfo{
    void getPrice();
    void getState();
}

class GoodInfo implements ProductInfo{
    @Override
    public void getPrice() {
        System.out.println("Товар имеет цену: 300 рублей.");
    }

    @Override
    public void getState() {
        System.out.println("Товар имеет статус: Нет на остатках.");
    }
}

class GoodInfoProxy implements ProductInfo{

    private ProductInfo productInfo = new GoodInfo();

    @Override
    public void getPrice() {
        productInfo.getPrice();
    }

    @Override
    public void getState() {
        productInfo.getState();
    }
}

class DisplayProductInfo{
    private ProductInfo info = new GoodInfoProxy();

    public void printInfo(){
        info.getPrice();
        info.getState();
    }

    public static void main(String[] args){
        DisplayProductInfo displayInfo = new DisplayProductInfo();
        displayInfo.printInfo();
    }
}
