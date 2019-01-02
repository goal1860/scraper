package dataloader;

import entities.Chdg4ArtifactgameCard;
import entities.Chdg4K2Items;
import org.apache.commons.lang3.StringUtils;
import playartifact.migration.CardV2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class DbLoader {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static Connection conn = null;
    //  Database credentials
//    static final String USER = "artifae7_artifac";
//    static final String PASS = "h56&(,zN4t@o";
//    private static final String DB_URL =
//                    "jdbc:mysql://artifact.game/artifae7_artifact?useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

        static final String USER = "root";
        static final String PASS = "root";
        private static final String DB_URL =
                        "jdbc:mysql://localhost:9001/artifact?useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode" +
                                        "=false&serverTimezone=UTC";

    public static List<String> loadKeywordLinks() {
        Statement stmt = null;
        List<String> linkList = new ArrayList<>();
        try {
            //STEP 2: Register JDBC driver
            //            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT link FROM chdg4_sl_keywordlinking_keywords";
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name

                String keyword = rs.getString("link");
                linkList.add(keyword);


                //                System.out.println(keyword);

            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
        }//end try
        //        System.out.println("Goodbye!");
        return linkList;
    }

    public static Map<String, String> loadKeywordsMap() {
        Statement stmt = null;
        Map<String, String> keywordMap = new HashMap<>();
        try {
            //STEP 2: Register JDBC driver
            //            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM chdg4_sl_keywordlinking_keywords";
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name

                String keyword = rs.getString("keyword");
                String tooltip = rs.getString("tooltip");
                keywordMap.put(keyword, tooltip);


                //                System.out.println(keyword);

            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }// nothing we can do
        }//end try
        //        System.out.println("Goodbye!");
        return keywordMap;
    }

    public static List<String> loadKeywords() {
        Statement stmt = null;
        List<String> keywordList = new ArrayList<>();
        try {
            //STEP 2: Register JDBC driver
            //            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT keyword FROM chdg4_sl_keywordlinking_keywords";
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name

                String keyword = rs.getString("keyword");
                keywordList.add(keyword);


                //                System.out.println(keyword);

            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }// nothing we can do
        }//end try
        //        System.out.println("Goodbye!");
        return keywordList;
    }//end main

    public static Map<String, Integer> getCategoryMap() {
        Map<String, Integer> catMap = new HashMap<>();

        Statement stmt = null;
        List<String> keywordList = new ArrayList<>();
        try {
            //STEP 2: Register JDBC driver
            //            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT id, alias FROM chdg4_k2_categories";
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name

                Integer id = rs.getInt("id");
                String alias = rs.getString("alias");
                catMap.put(alias, id);

            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }// nothing we can do
        }
        return catMap;
    }

    public static Map<String, Integer> getEmptyIntroRecords() {
        Map<String, Integer> recMap = new HashMap<>();

        Statement stmt = null;
        List<String> keywordList = new ArrayList<>();
        try {
            //STEP 2: Register JDBC driver
            //            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");


            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT id, title FROM chdg4_k2_items where introtext=''";
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name

                Integer id = rs.getInt("id");
                String alias = rs.getString("title");
                recMap.put(alias, id);

            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
        }
        return recMap;
    }

    public static void main(String[] args) {
        loadKeywords();
    }


    public static void updateExtraFields(int id, String fields) throws SQLException {
        updateK2Items(id, fields, "extra_fields");
    }

    public static void updateIntro(int id, String intro) throws SQLException {
        updateK2Items(id, intro, "introtext");
    }

    private static void updateK2Items(int id, String value, String field) throws SQLException {
        PreparedStatement updateStmt = null;


        String updateString = "update chdg4_k2_items set " + field + " = ? where id = ?";

        try {
            
            conn.setAutoCommit(false);
            updateStmt = conn.prepareStatement(updateString);
            updateStmt.setString(1, value);
            updateStmt.setInt(2, id);
            updateStmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    conn.rollback();
                } catch (SQLException excep) {
                    excep.printStackTrace();
                }
            }
        } finally {
            if (updateStmt != null) {
                updateStmt.close();
            }
            conn.setAutoCommit(true);
        }
    }

    public static Map<String, Integer> loadItemsByCategory(Integer catId) {
        Map<String, Integer> recMap = new HashMap<>();
        
        Statement stmt = null;
        List<String> keywordList = new ArrayList<>();
        try {
            //STEP 2: Register JDBC driver
            //            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT id, title FROM chdg4_k2_items where catid=" + catId;
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name

                Integer id = rs.getInt("id");
                String alias = rs.getString("title");
                recMap.put(alias, id);

            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }// nothing we can do

        }
        return recMap;
    }

    public static Map<String, Integer> getFieldMap(String type) {
        Map<String, Integer> recMap = new HashMap<>();
        
        Statement stmt = null;
        List<String> keywordList = new ArrayList<>();
        try {
            //STEP 2: Register JDBC driver
            //            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            
            int groupId = getFieldGroupid(type, conn);
            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT id, name FROM chdg4_k2_extra_fields where `group`=" + groupId;
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name

                Integer id = rs.getInt("id");
                String name = rs.getString("name");
                recMap.put(name, id);

            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do

        }
        return recMap;


    }

    private static int getFieldGroupid(String type, Connection conn) {
        Statement stmt = null;
        int groupId = -1;
        try {


            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT id FROM chdg4_k2_extra_fields_groups where `name`='" + type + "'";
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name

                Integer id = rs.getInt("id");

                groupId = id;
                break;

            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do

        }
        return groupId;
    }

    public static void cleanKeywords() {
        
        Statement stmt = null;

        try {
            //STEP 2: Register JDBC driver
            //            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT id, link FROM chdg4_sl_keywordlinking_keywords";
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name

                String link = rs.getString("link");
                int id = rs.getInt("id");
                String newlink = clean(link);
                System.out.println(newlink);
                if (!newlink.equals(link)) {
                    stmt = conn.createStatement();
                    sql = "UPDATE chdg4_sl_keywordlinking_keywords SET link = '" + link + "' WHERE id=" + id;
                    System.out.println(sql);
                    stmt.executeUpdate(sql);
                }

                //                System.out.println(keyword);

            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }// nothing we can do

        }//end try
        //        System.out.println("Goodbye!");

    }

    private static String clean(String link) {
        link = link.replaceAll("[0-9]+\\-", "");
        link = link.replace("https://artifact.game", "");
        link = link.replace("resources/cards/items/items", "items");
        link = link.replace("resources/cards/heroes/hero-cards", "heroes");
        link = link.replace("resources/cards/spells/spell-cards", "spells");
        link = link.replace("cards/heroes/hero-cards", "heroes");
        link = link.replace("cards/spells", "spells");
        link = link.replace("all-heroes/hero-cards", "heroes");
        link = link.replace("spell-cards/", "");
        link = link.replace("hero-skills/hero-skills", "hero-skills");

        link = link.replace("mechanics/mechanics", "mechanics");
        link = link.replace("items/items", "items");
        return link;

    }

    public static List<Chdg4ArtifactgameCard> getCardList() {
        return getCardList(null);
    }

    public static List<Chdg4ArtifactgameCard> getCardList(String where) {
        
        Statement stmt = null;
        List<Chdg4ArtifactgameCard> cardList = new ArrayList<>();

        try {
            //STEP 2: Register JDBC driver
            //            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;

            sql = "SELECT id, jid, name, type, subtype,  mana, gold, rarity, color, sigCard, sigId, sigMana, sigCardId, sigOf, attack, armor, health, skills, effect, effectType FROM chdg4_artifactgame_card";
            if(where != null){
                sql = sql + " where " + where;
            }
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                Chdg4ArtifactgameCard card = new Chdg4ArtifactgameCard();
                card.setSubtype(rs.getString("subtype"));
                card.setName(rs.getString("name"));
                card.setSigId(rs.getInt("sigId"));
                card.setJid(rs.getInt("jid"));
                card.setId(rs.getInt("id"));
                card.setSigMana(rs.getInt("sigMana"));
                card.setSigCard(rs.getString("sigCard"));
                card.setMana(rs.getInt("mana"));
                card.setGold(rs.getInt("gold"));
                card.setType(rs.getString("type"));
                card.setRarity(rs.getString("rarity"));
                card.setColor(rs.getString("color"));
                card.setSigOf(rs.getString("sigOf"));
                card.setSigCardId(rs.getInt("sigCardId"));
                card.setAttack(rs.getInt("attack"));
                card.setArmor(rs.getInt("armor"));
                card.setHealth(rs.getInt("health"));
                card.setEffect(rs.getString("effect"));
                card.setEffectType(rs.getString("effectType"));
                card.setSkillString(String.join(", ", rs.getString("skills").split("\\|")));
                cardList.add(card);

                //                System.out.println(keyword);

            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }// nothing we can do

        }//end try
        return cardList;
    }

    public static List<Chdg4K2Items> getK2ItemList() {
        return getK2ItemList(null);
    }

    public static List<Chdg4K2Items> getK2ItemList(String where) {
        
        Statement stmt = null;
        List<Chdg4K2Items> itemList = new ArrayList<>();

        try {
            //STEP 2: Register JDBC driver
            //            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT id, title, alias FROM chdg4_k2_items";
            if(where != null){
                sql = sql + " where " + where;
            }
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                Chdg4K2Items item = new Chdg4K2Items();
                item.setTitle(rs.getString("title"));
                item.setId(rs.getInt("id"));
                item.setAlias(rs.getString("alias"));
                itemList.add(item);

                //                System.out.println(keyword);

            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }// nothing we can do

        }//end try
        return itemList;
    }

    public static void saveCard(Chdg4ArtifactgameCard card) {
        

        try {

            System.out.println("Connecting to database...");
            

            // create a sql date object so we can use it in our INSERT statement
            Calendar calendar = Calendar.getInstance();
            java.sql.Date updateDate = new java.sql.Date(calendar.getTime().getTime());

            // the mysql insert statement
            String query =
                            " insert into chdg4_artifactgame_card (jid, name, type, subtype, color, revealed, attack, armor, health, mana, gold, skills, effect, effectType, sigCard, sigOf, update_time)" +
                                            " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, card.getJid());
            preparedStmt.setString(2, card.getName());
            preparedStmt.setString(3, card.getType());
            preparedStmt.setString(4, card.getSubtype());
            preparedStmt.setString(5, card.getColor());
            preparedStmt.setBoolean(6, card.getRevealed());
            preparedStmt.setInt(7, card.getAttack());
            preparedStmt.setInt(8, card.getArmor());
            preparedStmt.setInt(9, card.getHealth());
            preparedStmt.setInt(10, card.getMana());
            preparedStmt.setInt(11, card.getGold());
            preparedStmt.setString(12, card.getSkills());
            preparedStmt.setString(13, card.getEffect());
            preparedStmt.setString(14, card.getEffectType());
            preparedStmt.setString(15, card.getSigCard());
            preparedStmt.setString(16, card.getSigOf());
            preparedStmt.setDate(17, updateDate);


            // execute the preparedstatement
            preparedStmt.execute();

            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static int getMaxKeywordOrdering() {
        
        Statement stmt = null;

        int maxOrder = -1;
        try {
            //STEP 2: Register JDBC driver
            //            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT max(ordering) as max_order from chdg4_sl_keywordlinking_keywords;";
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                Chdg4K2Items item = new Chdg4K2Items();
                maxOrder = rs.getInt("max_order");
                break;

                //                System.out.println(keyword);

            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }// nothing we can do

        }//end try
        return maxOrder;
    }

    public static void saveKeyword(String title, String link, int catid, String tooltip, int maxOrder) {
        
        Statement stmt = null;
        try {

            System.out.println("Connecting to database...");
            

            // create a sql date object so we can use it in our INSERT statement
            Calendar calendar = Calendar.getInstance();
            java.sql.Date updateDate = new java.sql.Date(calendar.getTime().getTime());

            // the mysql insert statement
            String query =
                            " insert into chdg4_sl_keywordlinking_keywords (catid, keyword, keywords, language, link, target, tooltip, state, ordering, access, checked_out)" +
                                            " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, catid);
            preparedStmt.setString(2, title);
            preparedStmt.setString(3, "");
            preparedStmt.setString(4, "*");
            preparedStmt.setString(5, link);
            preparedStmt.setInt(6, -1);
            preparedStmt.setString(7, tooltip);
            preparedStmt.setInt(8, 1);
            preparedStmt.setInt(9, maxOrder);
            preparedStmt.setInt(10, 1);
            preparedStmt.setInt(11, 0);


            // execute the preparedstatement
            preparedStmt.execute();

            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void updateSigInfo(int id, Integer jid, Integer mana) {
        
        Statement stmt = null;

        try {
            //STEP 2: Register JDBC driver
            //            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            



            //Retrieve by column name


            stmt = conn.createStatement();
            String sql = "UPDATE chdg4_artifactgame_card SET sigId='" + jid + "', sigMana='" + mana + "' WHERE id=" +
                            id;
            System.out.println(sql);
            stmt.executeUpdate(sql);


            //                System.out.println(keyword);


            //STEP 6: Clean-up environment

            stmt.close();
            
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }// nothing we can do
        }
    }

    public static Map<Integer, String> getDeckCardList() {
        
        Statement stmt = null;
        Map<Integer, String> itemMap = new HashMap<>();

        try {
            //STEP 2: Register JDBC driver
            //            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql =
                            "SELECT dc.id id, title FROM chdg4_artifactgame_deck_card dc join chdg4_listofitems_items li on dc.card_id=li.id;";
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            while (rs.next()) {

                Integer id = rs.getInt("id");
                String title = rs.getString("title");

                itemMap.put(id, title);

                //                System.out.println(keyword);

            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }// nothing we can do

        }//end try
        return itemMap;
    }

    public static void updateDeckCardMap(int deckCardId, int newId) {
        
        Statement stmt = null;

        try {
            //STEP 2: Register JDBC driver
            //            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            



            //Retrieve by column name


            stmt = conn.createStatement();
            String sql = "UPDATE chdg4_artifactgame_deck_card SET card_id='" + newId + "' WHERE id=" + deckCardId;
            System.out.println(sql);
            stmt.executeUpdate(sql);


            //                System.out.println(keyword);


            //STEP 6: Clean-up environment

            stmt.close();
            
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }// nothing we can do

        }
    }

    public static Map<String, Integer> getEmptyRarityRecords() {
        Map<String, Integer> recMap = new HashMap<>();
        
        Statement stmt = null;

        try {
            //STEP 2: Register JDBC driver
            //            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT id, name FROM chdg4_artifactgame_card where rarity is NULL";
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name

                Integer id = rs.getInt("id");
                String name = rs.getString("name");
                recMap.put(name, id);

            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }// nothing we can do

        }
        return recMap;
    }

    public static void updateRarity(int id, String rarity) {
        
        Statement stmt = null;

        try {
            //STEP 2: Register JDBC driver
            //            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            



            //Retrieve by column name


            stmt = conn.createStatement();
            String sql = "UPDATE chdg4_artifactgame_card SET rarity='" + rarity + "' WHERE id=" + id;
            System.out.println(sql);
            stmt.executeUpdate(sql);


            //                System.out.println(keyword);


            //STEP 6: Clean-up environment

            stmt.close();
            
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }// nothing we can do

        }
    }

    public static void updateSigCardId(int id, int sigCardId) {
        
        Statement stmt = null;

        try {
            //STEP 2: Register JDBC driver
            //            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            



            //Retrieve by column name


            stmt = conn.createStatement();
            String sql = "UPDATE chdg4_artifactgame_card SET sigCardId='" + sigCardId + "' WHERE id=" + id;
            System.out.println(sql);
            stmt.executeUpdate(sql);


            //                System.out.println(keyword);


            //STEP 6: Clean-up environment

            stmt.close();
            
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }// nothing we can do

        }
    }

    public static Map<String, Integer> getK2Categories() {
        Map<String, Integer> recMap = new HashMap<>();
        
        Statement stmt = null;

        try {
            //STEP 2: Register JDBC driver
            //            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT name, id FROM chdg4_k2_categories";
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name

                Integer id = rs.getInt("id");
                String name = rs.getString("name");
                recMap.put(name, id);

            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }// nothing we can do

        }
        return recMap;
    }

    public static void openConnection() {
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeConnection() {
        try {
            if (conn != null) conn.close();
                
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateMetadata(int jid, String description, String keywordsString) throws SQLException {
        PreparedStatement updateStmt = null;


        String updateString = "update chdg4_k2_items set metadesc=?, metakey=? where id = ?";

        try {
            
            conn.setAutoCommit(false);
            updateStmt = conn.prepareStatement(updateString);
            updateStmt.setString(1, description);
            updateStmt.setString(2, keywordsString);
            updateStmt.setInt(3, jid);
            updateStmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    conn.rollback();
                } catch (SQLException excep) {
                    excep.printStackTrace();
                }
            }
        } finally {
            if (updateStmt != null) {
                updateStmt.close();
            }
            conn.setAutoCommit(true);
        }
    }

    public static Set<Integer> getHeroSkillSet(){
        Set<Integer> skillSet = new HashSet<>();

        Statement stmt = null;

        try {
            //STEP 2: Register JDBC driver
            //            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");


            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT id, name FROM chdg4_artifactgame_heroskill";
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name

                Integer id = rs.getInt("id");
                skillSet.add(id);

            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }// nothing we can do

        }
        return skillSet;
    }

    public static void saveHeroSkill(int id, String type, String name, String text) {
        try {

            System.out.println("Connecting to database...");




            // the mysql insert statement
            String query =
                    " insert into chdg4_artifactgame_heroskill (id, type, name, text) values (?, ?, ?, ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, id);
            preparedStmt.setString(2, type);
            preparedStmt.setString(3, name);
            preparedStmt.setString(4, text);


            // execute the preparedstatement
            preparedStmt.execute();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void clearCardV2() {
        try {

            System.out.println("Connecting to database...");




            // the mysql insert statement
            String query =
                    "truncate chdg4_artifactgame_cardv2";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            
            // execute the preparedstatement
            preparedStmt.execute();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, String> loadHeroSkillMap() {
        Map<String, String> heroSkillMap = new HashMap<>();

        Statement stmt = null;

        try {
            //STEP 2: Register JDBC driver
            //            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");


            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM chdg4_artifactgame_heroskill";
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name

                String name = rs.getString("name");
                String text = rs.getString("text");
                heroSkillMap.put(name, text);

            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }// nothing we can do

        }
        return heroSkillMap;
    }

    public static void saveCardV2(int id, String name, String type, String subtype, String color, String rarity, String information, String miniImgUrl, String link) {
        try {

            System.out.println("Connecting to database...");

            // the mysql insert statement
            String query =
                    " insert into chdg4_artifactgame_cardv2 (id, name, type, subtype, color, rarity, information, miniImgUrl, link) " +
                            "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, id);
            preparedStmt.setString(2, name);
            preparedStmt.setString(3, type);
            preparedStmt.setString(4, subtype);
            preparedStmt.setString(5, color);
            preparedStmt.setString(6, rarity);
            preparedStmt.setString(7, information);
            preparedStmt.setString(8, miniImgUrl);
            preparedStmt.setString(9, link);

            // execute the preparedstatement
            preparedStmt.execute();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void saveCardV2(CardV2 card){
        saveCardV2(card.getId(), card.getName(), card.getType(), card.getSubtype(), card.getColor(), card.getRarity(), card.getInformation(), card.getMiniImgUrl(), card.getLink());
    }
}//end FirstExample
