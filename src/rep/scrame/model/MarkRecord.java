package rep.scrame.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Data structure to hold the mark records of a student in particular course.
 * Makes use of HashMap data structure for an O(1) retrieval on average, amortized cost.
 */
public class MarkRecord {
	/**
	 * The data structure of the assessment marks of a student.
	 */
    private Map<Assessment, Double> assessmentMark;

    /**
     * The constructor of the mark record.
     */
    public MarkRecord() {
        assessmentMark = new HashMap<Assessment, Double>();
    }

    /**
     * Sets an assessment mark of a student.
     * @param assessment	The assessment to be updated.
     * @param mark			The desired mark.
     */
    public void setAssessmentMark(Assessment assessment, double mark) {
        if (mark < 0) mark = 0;
        assessmentMark.put(assessment, mark);
    }

    /**
     * Retrieves the assessment mark of a student.
     * @param assessment	The assessment which mark is to be retrieved.
     * @return				The mark allocated to the student.
     */
    public double getAssessmentMark(Assessment assessment) {
        assessmentMark.putIfAbsent(assessment, 0.0);
        return assessmentMark.get(assessment);
    }
}
