import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Main {
    public static Connection connection;
    public static Statement statement;
    private static PreparedStatement preparedStatement;

    public static void start(String res){

        try {
            connect();
            String sql = String.format("SELECT * FROM main \n" +
                    "WHERE seria <%s", res);

        ResultSet rs =statement.executeQuery(sql);
while (rs.next()){
    System.out.println(rs.getInt(1));
}

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                disconnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }


    public static void main(String[] args) {
        try {
            connect();
            statement.executeUpdate("DELETE FROM main");
            connection.setAutoCommit(false);
            long t = System.currentTimeMillis();
            preparedStatement = connection.prepareStatement("INSERT INTO main(seria,title,cost)\n" +
                    "VALUES (?,?,?)");
            for (int i = 0; i <1000 ; i++) {
            preparedStatement.setInt(1, i);
            preparedStatement.setString(2,"car1" + i);
            preparedStatement.setInt(3, i+i+i+i+i+i+i+i+i+i);
            preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            System.out.println(System.currentTimeMillis() - t);
            connection.setAutoCommit(true);
            //           ResultSet rs = statement.executeQuery("SELECT * FROM main");



//           statement.executeUpdate("\n" +
//                   "INSERT INTO main(prodid,title,cost)\n" +
//                   "VALUES (15,'Maus', 100)");                    //добавляет значения в базу данных



//            ResultSetMetaData rsmd = rs.getMetaData();
//            for (int i = 1; i <=rsmd.getColumnCount() ; i++) {
//                System.out.println(rsmd.getColumnName(i));
//
//            }
//            while (rs.next()){
//                System.out.println(rs.getInt(1 ) + " " + rs.getString("title"));  //выводит название столбцов и id + title
//            }
//            statement.executeUpdate("DELETE FROM main");
//            connection.setAutoCommit(false);
//            long t = System.currentTimeMillis();
//
//
//            for (int i = 0; i <10000 ; i++) {
//                statement.executeUpdate("INSERT INTO main(seria,title,cost)\n" +
//                        "VALUES (" + i + ", 'car', 100)");
//
//            }
//            System.out.println(System.currentTimeMillis() - t);
//            connection.setAutoCommit(true);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                disconnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }



    public static void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:Shop.db");
        statement = connection.createStatement();
    }
    public static void disconnection() throws SQLException {
        connection.close();
    }
}

class W extends JFrame{
    public W(){
        setTitle("ps");
        JPanel panel = new JPanel(new GridLayout(1,2));
        setBounds(400,400,400,400);
        final JTextField jtf = new JTextField();
        JButton jbt = new JButton("send");

        panel.add(jtf);
        panel.add(jbt);

        jbt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                final String res = jtf.getText();
                Main.start(res);
            }
        });
        add(panel, BorderLayout.NORTH);
        setVisible(true);
    }
}
class Start {
    public static void main(String[] args) {
        new W();
    }
}
