package course;

/**
 * The [SimpleCourse] class...
 */
public class SimpleCourse {
  private final String pk1;

  private final String courseId;

  private final String batchUid;

  private final String name;

  private final boolean availableInd;

  /**
   * The [SimpleCourse] constructor...
   */
  public SimpleCourse (
    String pk1, String courseId, String batchUid, String name, boolean availableInd
  ) {
    this.pk1 = pk1;
    this.courseId = courseId;
    this.batchUid = batchUid;
    this.name = name;
    this.availableInd = availableInd;
  }

  public String getPk1() {
    return pk1;
  }

  public String getCourseId() {
    return courseId;
  }

  public String getBatchUid() {
    return batchUid;
  }

  public String getName() {
    return name;
  }

  public boolean isAvailableInd() {
    return availableInd;
  }
}
