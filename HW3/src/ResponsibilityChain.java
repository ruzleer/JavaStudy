enum CorporateRequestType{
    TECH, NETWORK, CASH_UNIT
}

class CorporateRequest{
    private CorporateRequestType type;

    CorporateRequest(CorporateRequestType requestType){
        this.type = requestType;
    }
    public CorporateRequestType getType(){
        return type;
    }
}

interface CorporateSupportHandler{
    void setNextHandler(CorporateSupportHandler nextHandler);
    void handle(CorporateRequest request);
}

class TechSupportHandler implements CorporateSupportHandler {
    private CorporateSupportHandler nextHandler;

    public void setNextHandler(CorporateSupportHandler nextHandler){
        this.nextHandler = nextHandler;
    }

    public void handle(CorporateRequest request){
        if (request.getType() == CorporateRequestType.TECH){
            // Обрабатываем запрос
            System.out.println("Это запрос для тех. поддержки и он был выполнен!");
        } else if (nextHandler != null) {
            nextHandler.handle(request);
        } else{
            System.out.println("Запрос был прерван на тех. поддержке");
        }
    }
}

class NetworkSupportHandler implements CorporateSupportHandler {
    private CorporateSupportHandler nextHandler;

    public void setNextHandler(CorporateSupportHandler nextHandler){
        this.nextHandler = nextHandler;
    }

    public void handle(CorporateRequest request){
        if (request.getType() == CorporateRequestType.NETWORK){
            // Обрабатываем запрос
            System.out.println("Это запрос для сетевой поддержки и он был выполнен!");
        } else if (nextHandler != null) {
            nextHandler.handle(request);
        } else{
            System.out.println("Запрос был прерван на сетевой поддержке");
        }
    }
}

class CashUnitSupportHandler implements CorporateSupportHandler {
    private CorporateSupportHandler nextHandler;

    public void setNextHandler(CorporateSupportHandler nextHandler){
        this.nextHandler = nextHandler;
    }

    public void handle(CorporateRequest request){
        if (request.getType() == CorporateRequestType.CASH_UNIT){
            // Обрабатываем запрос
            System.out.println("Это запрос для кассовой поддержки и он был выполнен!");
        } else if (nextHandler != null) {
            nextHandler.handle(request);
        } else{
            System.out.println("Запрос был прерван на кассовой поддержке");
        }
    }
}

class Corporation {
    public static void main(String[] args){
        TechSupportHandler techSupport = new TechSupportHandler();
        NetworkSupportHandler networkSupport = new NetworkSupportHandler();
        CashUnitSupportHandler cashUnitSupport = new CashUnitSupportHandler();

        techSupport.setNextHandler(networkSupport);
        networkSupport.setNextHandler(cashUnitSupport);

        CorporateRequest cashUnitRequest = new CorporateRequest(CorporateRequestType.CASH_UNIT);
        CorporateRequest TechRequest = new CorporateRequest(CorporateRequestType.TECH);
        CorporateRequest NetworkRequest = new CorporateRequest(CorporateRequestType.NETWORK);

        techSupport.handle(cashUnitRequest);
        techSupport.handle(TechRequest);
        techSupport.handle(NetworkRequest);
    }
}