package connectionsAndDaos;
import exeptions.ConnectionPoolException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class ConnectionPool {
    public static ConnectionPool instance=new ConnectionPool();
    private String url="jdbc:mysql://localhost:3306/couponsystem";
    private String password="1234";
    private String user="root";
    private Set<Connection>connections=new HashSet<>();

    /**
     * the constructor of the connection pool.
     * create a list of 5 connections.
     */
    public ConnectionPool() throws ConnectionPoolException {
            try {
                for (int i = 0; i <5 ; i++) {
                    connections.add(DriverManager.getConnection(url,user,password));
                }
            } catch (SQLException e) {
                throw new ConnectionPoolException("Can't create a connections",e);
            }
    }

    /**
     * @return the instance of connection pool
     */
    public static ConnectionPool getInstance() {
        return instance;
    }

    /**
     * get connection from connection pool
     * @return connection
     */
    public synchronized Connection getConnection() throws ConnectionPoolException {
        try {
            while (true) {
                for (Connection connection : connections) {
                    if (!connection.isClosed()){
                        connections.remove(connection);
                        return connection;
                    }
                }
                wait();
            }
        } catch (InterruptedException | SQLException e){
            throw new ConnectionPoolException("get connecting filed", e);
        }
    }

    /**
     * restore connection to connection pool
     * @param connection the connection to restore
     */
    public synchronized void restoreConnection(Connection connection){
            connections.add(connection);
            notify();
    }

    /**
     * close all connections in connection pool
     */
    public void closeAllConnections() throws ConnectionPoolException {
        Iterator iterator=connections.iterator();
        while (iterator.hasNext()) {
            Connection connection=(Connection) iterator.next();
            try {
                connection.close();
            } catch (SQLException e) {
                throw new ConnectionPoolException("can't close the connections",e);
            }
        }
    }
}
