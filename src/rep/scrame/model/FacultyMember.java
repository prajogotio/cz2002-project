package rep.scrame.model;


public class FacultyMember extends Person {
    public static enum STATUS {
        PROFESSOR ("Professor"),
        ASSOCIATE_PROFESSOR ("Associate Professor"),
        ASSISTANT_PROFESSOR ("Assistant Professor"),
        TEACHING_ASSISTANT ("Teaching Assistant"),
        STAFF ("Staff"),
        LECTURER ("Lecturer"),
        SENIOR_LECTURER ("Senior Lecturer");

        private final String name;

        private STATUS(String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }

    };

    private Faculty faculty;
    private STATUS status;

    public FacultyMember(Particulars particulars, Faculty faculty, STATUS status) {
        super(particulars);
        this.faculty = faculty;
        this.status = status;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public String getStatus() {
        return status.toString();
    }

    public int getStatusOrdinal() { return status.ordinal(); }

    public void setStatus(STATUS status) {
        this.status = status;
    }
}
