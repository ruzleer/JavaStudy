class Chair{
    private float price;
    private String texture;
    private boolean isNew; //optional
    private int rating; //optional

    public Chair( ChairBuilder chairBuilder){
        price = chairBuilder.price;
        texture = chairBuilder.texture;
        isNew = chairBuilder.isNew;
        rating = chairBuilder.rating;
    }

    public boolean isNew() {
        return isNew;
    }

    public float getPrice() {
        return price;
    }

    public int getRating() {
        return rating;
    }

    public String getTexture() {
        return texture;
    }

    public static class ChairBuilder{
        private float price;
        private String texture;
        private boolean isNew;
        private int rating;

        public ChairBuilder(float price, String texture){
            this.price = price;
            this.texture = texture;
        }

        public ChairBuilder setIsNew(boolean isNew) {
            this.isNew = isNew;
            return this;
        }

        public ChairBuilder setRating(int rating){
            this.rating = rating;
            return this;
        }

        public Chair build(){
            return new Chair(this);
        }
    }
}

class ChairFabric{
    public static void main(String[] args){
        Chair chair = new Chair.ChairBuilder(100, "wood").setIsNew(true).setRating(10).build();

        System.out.println(chair.getRating());
    }
}