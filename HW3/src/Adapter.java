interface Printer{
    void print();
    void scan();
}

class USSRPrinter{
    public void printDocument(){
        System.out.println("Товарищ, документ в печати!");
    }
}

class PrinterAdapter implements Printer{

    private USSRPrinter ussrPrinter;

    public PrinterAdapter(USSRPrinter ussrPrinter){
        this.ussrPrinter = ussrPrinter;
    }

    public void print() {
        System.out.println("Товарищ, у адаптированного принтера документ в печати!");
    }

    public void scan() {
        throw new UnsupportedOperationException("Товарищ, операция сканирования провалена.");
    }

}

class PrintAtWork{
    public static void main(String[] args){

        USSRPrinter ussrPrinter = new USSRPrinter();
        PrinterAdapter adaptedPrinter = new PrinterAdapter(ussrPrinter);

        adaptedPrinter.print();
        adaptedPrinter.scan();
    }
}