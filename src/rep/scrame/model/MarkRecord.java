package rep.scrame.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MarkRecord {
    private Map<Assessment, Double> assessmentMark;

    public MarkRecord() {
        assessmentMark = new HashMap<Assessment, Double>();
    }

    public void setAssessmentMark(Assessment assessment, double mark) {
        if (mark < 0) mark = 0;
        assessmentMark.put(assessment, mark);
    }

    public double getAssessmentMark(Assessment assessment) {
        assessmentMark.putIfAbsent(assessment, 0.0);
        return assessmentMark.get(assessment);
    }
}
