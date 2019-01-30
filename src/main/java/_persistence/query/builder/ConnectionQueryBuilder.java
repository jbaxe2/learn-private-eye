package _persistence.query.builder;

import java.sql.Connection;

/**
 * The [ConnectionQueryBuilder] abstract class...
 */
abstract class ConnectionQueryBuilder implements QueryBuilder {
  final Connection connection;

  final Connection statsConnection;

  /**
   * The [ConnectionQueryBuilder] constructor...
   */
  ConnectionQueryBuilder (Connection connection, Connection statsConnection) {
    this.connection = connection;
    this.statsConnection = statsConnection;
  }

  /**
   * The [whichConnection] method...
   */
  Connection whichConnection (boolean forStats) {
    if (forStats) {
      return statsConnection;
    }

    return connection;
  }
}
