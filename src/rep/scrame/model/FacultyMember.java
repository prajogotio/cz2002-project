package rep.scrame.model;


/**
 *	The faculty member class. holds the information regarding a faculty member.
 */
public class FacultyMember extends Person {
	/**
	 * Enum class that holds the faculty member's title. Includes Professor, Assoc. Prof,
	 * Asst. Prof, and so on.
	 */
    public static enum STATUS {
        PROFESSOR ("Professor"),
        ASSOCIATE_PROFESSOR ("Associate Professor"),
        ASSISTANT_PROFESSOR ("Assistant Professor"),
        TEACHING_ASSISTANT ("Teaching Assistant"),
        STAFF ("Staff"),
        LECTURER ("Lecturer"),
        SENIOR_LECTURER ("Senior Lecturer");

        /**
         * Name of the title.
         */
        private final String name;

        /**
         * Private constructor of the status.
         * @param name	Name of the title.
         */
        private STATUS(String name) {
            this.name = name;
        }

        /**
         * Converts the title into string.
         * @return The string format of the title.
         */
        public String toString() {
            return name;
        }

    };

    /**
     * The faculty in which the member belongs to.
     */
    private Faculty faculty;
    
    /**
     * The title of the member.
     */
    private STATUS status;

    /**
     * Constructor of FacultyMember.
     * @param particulars	The particulars regarding the member.
     * @param faculty		The faculty in which he belongs to.
     * @param status		His title.
     */
    public FacultyMember(Particulars particulars, Faculty faculty, STATUS status) {
        super(particulars);
        this.faculty = faculty;
        this.status = status;
    }

    /**
     * Retrieves the faculty for which he is registered in.
     * @return	The faculty for which the faculty member is registered in.
     */
    public Faculty getFaculty() {
        return faculty;
    }

    /**
     * Sets the faculty of the member.
     * @param faculty	The faculty to be assigned.
     */
    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    /**
     * Retrieves the title of the member.
     * @return	The title of the member.
     */
    public String getStatus() {
        return status.toString();
    }

    /**
     * Gets the index of a particular title in the FaculyMember.STATUS.
     * @return	The index of the status.
     */
    public int getStatusOrdinal() { return status.ordinal(); }

    /**
     * Set the title of the faculty member.
     * @param status	The status to be assigned.
     */
    public void setStatus(STATUS status) {
        this.status = status;
    }
}
