interface FertilityStrategy{
    void getChild(String motherName);
}

class SurrogateMethod implements FertilityStrategy{

    public void getChild(String motherName) {
        System.out.println(motherName + " выбрала Суррогатное материнство");
    }
}

class EcoMethod implements FertilityStrategy{

    public void getChild(String motherName) {
        System.out.println(motherName +" выбрала ЭКО");
    }
}

class NaturalMethod implements FertilityStrategy{

    public void getChild(String motherName) {
        System.out.println(motherName +" выбрала Естественное зачатие");
    }
}

class GodGivenMethod implements FertilityStrategy{

    public void getChild(String motherName) {
        System.out.println(motherName +" выбрала Непорочное зачатие");
    }
}

class FemalePatient {

    private FertilityStrategy fertilityStrategy;
    private String name;

    public void setFertilityStrategy(FertilityStrategy strategy) {
        this.fertilityStrategy = strategy;
    }

    public void haveChild(){
        fertilityStrategy.getChild(name);
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}

class FertilityClinic{

    public static void main(String[] args){

        FemalePatient LeraRanetka = new FemalePatient();
        LeraRanetka.setName("Lera Ranetka");
        LeraRanetka.setFertilityStrategy(new GodGivenMethod());
        LeraRanetka.haveChild();

        FemalePatient AnnaRanetka = new FemalePatient();
        AnnaRanetka.setName("Anya Ranetka");
        AnnaRanetka.setFertilityStrategy(new EcoMethod());
        AnnaRanetka.haveChild();

        FemalePatient NatashaRanetka = new FemalePatient();
        NatashaRanetka.setName("Natasha Ranetka");
        NatashaRanetka.setFertilityStrategy(new NaturalMethod());
        NatashaRanetka.haveChild();

        FemalePatient JennieRanetka = new FemalePatient();
        JennieRanetka.setName("Jennie Ranetka");
        JennieRanetka.setFertilityStrategy(new SurrogateMethod());
        JennieRanetka.haveChild();

    }
}