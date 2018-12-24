package content;

import blackboard.data.content.Content;

import java.util.Date;

/**
 * The [SimpleContent] class...
 */
public class SimpleContent {
  private final String pk1;

  private final String coursePk1;

  private final String parentPk1;

  private final String title;

  private final String description;

  private final String contentHandler;

  private final Date created;

  private final Date modified;

  private final boolean isFolder;

  /**
   * The [SimpleContent] constructor...
   */
  SimpleContent (
    String pk1, String coursePk1, String parentPk1, String title,
    String description, String contentHandler, Date created,
    Date modified, boolean isFolder
  ) {
    this.pk1 = pk1;
    this.coursePk1 = coursePk1;
    this.parentPk1 = parentPk1;
    this.title = title;
    this.description = description;
    this.contentHandler = contentHandler;
    this.created = created;
    this.modified = modified;
    this.isFolder = isFolder;
  }

  /**
   * The [SimpleContent] constructor...
   */
  SimpleContent (Content content) {
    this.pk1 = content.getId().getExternalString();
    this.coursePk1 = content.getCourseId().getExternalString();
    this.parentPk1 = content.getParentId().getExternalString();
    this.title = content.getTitle();
    this.description = content.getShortDescription();
    this.contentHandler = content.getContentHandler();
    this.created = content.getCreatedDate().getTime();
    this.modified = content.getModifiedDate().getTime();
    this.isFolder = content.getIsFolder();
  }
}
