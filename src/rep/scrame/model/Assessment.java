package rep.scrame.model;


public class Assessment {
    private String name;
    private double weightage;

    public Assessment(String name) {
        this.name = name;
        this.weightage = 0;
    }


    public double getWeightage() {
        return weightage;
    }

    public void setWeightage(double weightage) {
        if (weightage < 0) weightage = 0;
        this.weightage = weightage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
