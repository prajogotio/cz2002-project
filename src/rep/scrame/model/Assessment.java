package rep.scrame.model;

/**
 * 
 * The data structure for an assessment. Course works and exams are represented as assessments
 * in this system. An assessment has a name and a weightage.
 * 
 */
public class Assessment {
	/**
	 * The name of the assessment. This will be shown in the course grading policy.
	 */
    private String name;
    
    /**
     * The weightage of this assessment. Weightage can take value between 0 to 100.
     */
    private double weightage;

    /**
     * Assessment class constructor. Weightage is initialized to 0.
     * @param name The name of the assessment.
     */
    public Assessment(String name) {
        this.name = name;
        this.weightage = 0;
    }

    /**
     * Retrieves the assessment's weightage.
     * @return The weightage of this assessment.
     */
    public double getWeightage() {
        return weightage;
    }
    
    /**
     * Sets the weightage of the assessment. It performs a validation check to make sure
     * that the weightage provided is in the valid range of 0 to 100.
     * @param weightage The desired weightage of the assessment.
     */
    public void setWeightage(double weightage) {
        if (weightage < 0) weightage = 0;
        if (weightage > 100) weightage = 100;
        this.weightage = weightage;
    }

    /**
     * Retrieves the name of this assessment.
     * @return The name of this assessment.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the name of this assessment
     * @param name The desired name of this assessment.
     */
    public void setName(String name) {
        this.name = name;
    }

}
