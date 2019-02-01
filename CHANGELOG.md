# Learn PrivateEye
**(learn-private-eye)**

0.11.20190131
- Work towards resolving issues relating to inclusion of stats database.

0.11.20190130
- Included tracking information from the stats database.
  - Some issues need to be fixed:
    - User may be shown twice (once for prod, once for stats).
    - Sorting tracking information by date is overridden by session ID sorting.
    - Counts are currently not accurate.

0.10.20181224
- Continuing developing content-based tracking.
- Start of developing compound event tracking.
  - Compound event is a single event containing multiple entries in the activity
  accumulator, corresponded by session ID and timestamp.

0.10.20181222
- Start of developing content-based tracking.

0.10.20181221
- Start of work to resolve errors when viewing course session counts in 'large'
courses, whereby the application loads a blank page.
- Changed the web.xml error page to a system page, instead of an included page.
- Increased application stability.

0.10.20181220
- Continued work with user context bread crumb navigation controls.
  - Resolved user (non-course and login attempts) bread crumb navigation.
- Changed the session collections so they are no longer singletons.

0.10.20181219
- Provided sorting mechanisms for (session) activity events.
- Continued work with bread crumb navigation controls.
  - Resolved course bread crumb navigation controls.

0.10.20181218
- Minor refactoring.
- Start of creating user login attempt queries.
- Start of creating bread crumb navigation controls.

0.10.20181217
- Slight reformatting of code.
- Created facade classes for queries.
- Continued work towards system (non-course) queries for tracking users.

0.10.20181216
- More work towards system (non-course-based) queries for tracking users.

0.10.20181214
- Work towards system (non-course-based) queries for tracking users.

0.10.20181212
- Worked towards getting the user tracking count to run successfully.
- Start of system (non-course-based) tracking for users.

0.10.20181207
- Changed versioning scheme (increments previous deployed implementation).
  - Major, minor, patch (dated).
- Various refactorings to resolve some errors.
- Minor refactorings for updating typos and the like.

0.0.9
- Refactored simple types to allow passing regular types in to constructors.
- Start of creating user context queries.
- Additional course context query functionality.

0.0.8
- Refactored a few classes.
- Restructured project to conform to Maven build standards.
- Created a few JSP views:
  - Course users sessions counter.
  - Course user sessions list.

0.0.7
- Renamed SingleCourseUserSession and SingCourseUserSessionsCollection.

0.0.6
- Start of fleshing out index.jsp with course context and persistence manager
instances.

0.0.5
- Minor refactoring.
- Start of creating query classes for retrieving tracking information.

0.0.4
- Developed more contextual aspects.
- Minor refactoring to provide better consistency across code base.
- Start of fleshing out activity accumulator aspects.

0.0.3
- Created some simple value objects.
- Start of developing a context (based on course or user).
- Added some building block configuration files (such as the bb-manifest).

0.0.2
- Start of building out some types that may be useful.

 0.0.1
- Initial commit and start of project.
