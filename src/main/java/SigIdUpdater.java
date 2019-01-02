import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class SigIdUpdater {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        // Setup the connection with the DB
        Connection connect = DriverManager
                        .getConnection("jdbc:mysql://localhost:9001/artifact?"
                                        + "user=root&password=password");
        Statement statement = connect.createStatement();
        ResultSet resultSet = statement
                        .executeQuery("select * from artifact.chdg4_listofitems_items where catid='99'");
        Map<String, String[]> spellDic = new HashMap<>();
        while (resultSet.next()) {
            // It is possible to get the columns via name
            // also possible to get the columns via the column number
            // which starts at 1
            // e.g. resultSet.getSTring(2);
            int id = resultSet.getInt("id");
            String cost = resultSet.getString("feature_9");
            String name = resultSet.getString("title");
            spellDic.put(name, new String[]{"" + id, cost});
        }
        statement = connect.createStatement();
        resultSet = statement
                        .executeQuery("select * from artifact.chdg4_listofitems_items where catid='97'");
        while (resultSet.next()) {
            // It is possible to get the columns via name
            // also possible to get the columns via the column number
            // which starts at 1
            // e.g. resultSet.getSTring(2);
            int id = resultSet.getInt("id");
            String sigcard = resultSet.getString("feature_7");
            String sigid = resultSet.getString("feature_12");
            if(spellDic.containsKey(sigcard)){
                System.out.println("Updating sig id from " + sigid + " to: " + spellDic.get(sigcard));
                PreparedStatement preparedStatement =
                                connect.prepareStatement("update artifact.chdg4_listofitems_items set feature_12 = ?, feature_13=? where id=?");
                preparedStatement.setString(1, (spellDic.get(sigcard))[0]);
                preparedStatement.setString(2, (spellDic.get(sigcard))[1]);
                preparedStatement.setInt(3, id);
                preparedStatement.executeUpdate();
            }else{
                System.out.println("Sig card is not found: " + sigcard);
            }
        }
//        writeResultSet(resultSet);


    }

    private static void writeResultSet(ResultSet resultSet) throws SQLException {
        // ResultSet is initially before the first data set
        while (resultSet.next()) {
            // It is possible to get the columns via name
            // also possible to get the columns via the column number
            // which starts at 1
            // e.g. resultSet.getSTring(2);
            String title = resultSet.getString("title");
            System.out.println(title);
        }
    }
}
