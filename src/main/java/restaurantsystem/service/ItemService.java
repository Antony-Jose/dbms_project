
package restaurantsystem.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import restaurantsystem.component.item.UpdateItem;
import restaurantsystem.model.Item;
import restaurantsystem.model.DatabaseManager;

public class ItemService {

    public ItemService() {
    }

    // public List<Item> getAll() {
    // List<Item> items = new ArrayList<>();
    // try (Scanner scanner = new Scanner(new FileInputStream("storage/item.txt")))
    // {
    // while (scanner.hasNextLine()) {
    // String itemLine = scanner.nextLine();

    // String itemInfo[] = itemLine.split(",");

    // Item item = new Item(itemInfo[0], Double.parseDouble(itemInfo[1]),
    // Integer.parseInt(itemInfo[2]));

    // items.add(item);
    // }
    // } catch (FileNotFoundException ex) {
    // Logger.getLogger(ItemService.class.getName()).log(Level.SEVERE, null, ex);
    // }
    // return items;
    // }

    public List<Item> getAll() {
        List<Item> items = new ArrayList<>();
        String query = "SELECT * FROM item";

        try (Connection conn = DatabaseManager.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Item item = new Item();
                // item.setId(rs.getInt("id"));
                item.setName(rs.getString("name"));
                item.setPrice(rs.getDouble("price"));
                item.setQuantity(rs.getInt("quantity"));
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    // public void create(Item item) {
    // try (PrintWriter pw = new PrintWriter(new
    // FileOutputStream("storage/item.txt", true))) {
    // pw.println(item.getName() + "," + item.getPrice() + "," +
    // item.getQuantity());
    // } catch (FileNotFoundException ex) {
    // Logger.getLogger(ItemService.class.getName()).log(Level.SEVERE, null, ex);
    // }
    // }

    public static void create(Item item) {
        String query = "INSERT INTO item (name, price, quantity) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, item.getName());
            pstmt.setDouble(2, item.getPrice());
            pstmt.setInt(3, item.getQuantity());
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // public synchronized boolean delete(String name) {

    // List<Item> itemList = getAll();

    // int indexToBeDeleted = -1;
    // // find the item to be deleted
    // for (int i = 0; i < itemList.size(); i++) {
    // Item item = itemList.get(i);

    // if (item.getName().equalsIgnoreCase(name)) {
    // indexToBeDeleted = i;
    // }
    // }

    // if (indexToBeDeleted == -1) {
    // return false;
    // }
    // itemList.remove(indexToBeDeleted);

    // try {
    // // Delete the entire file
    // Files.delete(Paths.get("storage/item.txt"));
    // } catch (IOException ex) {
    // Logger.getLogger(ItemService.class.getName()).log(Level.SEVERE, null, ex);
    // }

    // // Create a new file and write new data into the file
    // try (PrintWriter pw = new PrintWriter(new
    // FileOutputStream("storage/item.txt"))) {
    // itemList.forEach(item -> {
    // pw.println(item.getName() + "," + item.getPrice() + "," +
    // item.getQuantity());
    // });
    // } catch (FileNotFoundException ex) {
    // Logger.getLogger(ItemService.class.getName()).log(Level.SEVERE, null, ex);
    // }

    // return true;
    // }

    public static boolean delete(String name) {
        String query = "DELETE FROM item WHERE name = ?";

        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // public synchronized boolean update(String srcName, Item updatedItem) {
    // List<Item> itemList = new ArrayList<>();

    // // Read all the items
    // try (Scanner scanner = new Scanner(new FileInputStream("storage/item.txt")))
    // {
    // while (scanner.hasNextLine()) {
    // String itemLine = scanner.nextLine();

    // String itemInfo[] = itemLine.split(",");

    // Item item = new Item(itemInfo[0], Double.parseDouble(itemInfo[1]),
    // Integer.parseInt(itemInfo[2]));
    // itemList.add(item);
    // }
    // } catch (FileNotFoundException ex) {
    // Logger.getLogger(UpdateItem.class.getName()).log(Level.SEVERE, null, ex);
    // }

    // int itemIndexToUpdate = -1;

    // for (int i = 0; i < itemList.size(); i++) {
    // Item item = itemList.get(i);

    // if (item.getName().equalsIgnoreCase(srcName)) {
    // itemIndexToUpdate = i;
    // }
    // }

    // if (itemIndexToUpdate == -1) {
    // return false;
    // }

    // itemList.set(itemIndexToUpdate, updatedItem);

    // try {
    // Files.delete(Paths.get("storage/item.txt"));
    // } catch (IOException ex) {
    // Logger.getLogger(UpdateItem.class.getName()).log(Level.SEVERE, null, ex);
    // }

    // try (PrintWriter pw = new PrintWriter(new
    // FileOutputStream("storage/item.txt"))) {
    // itemList.forEach(item -> {
    // pw.println(item.getName() + "," + item.getPrice() + "," +
    // item.getQuantity());
    // });
    // } catch (FileNotFoundException ex) {
    // Logger.getLogger(UpdateItem.class.getName()).log(Level.SEVERE, null, ex);
    // }

    // return true;
    // }

    public static boolean update(String srcName, Item updatedItem) {
        String query = "UPDATE item SET name = ?, price = ?, quantity = ? WHERE name = ?";

        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, updatedItem.getName());
            pstmt.setDouble(2, updatedItem.getPrice());
            pstmt.setInt(3, updatedItem.getQuantity());
            pstmt.setString(4, srcName);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // public Item getItemByIndecx(int index) {
    //     List<Item> listOfItem = getAll();

    //     if (listOfItem.size() >= index) {
    //         return listOfItem.get(index - 1);
    //     }

    //     return null;
    // }

    public Item getItemByIndex(int index) {
        Item item=null;

        String query = "SELECT * FROM item WHERE idItem=?";

        try (Connection conn = DatabaseManager.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, index);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                item = new Item();
                item.setId(rs.getInt("idItem"));
                item.setName(rs.getString("name"));
                item.setPrice(rs.getDouble("price"));
                item.setQuantity(rs.getInt("quantity"));
                return item;

            }
        } catch (SQLException e) {
            e.printStackTrace();
            // return null;
        }
        return item;
    }


    // public synchronized void reduceItemQuantityByItemName(String itemName, int reduceNumber) {
    //     List<Item> itemList = getAll();

    //     for (int i = 0; i < itemList.size(); i++) {

    //         Item item = itemList.get(i);

    //         if (item.getName().equalsIgnoreCase(itemName)) {
    //             item.setQuantity(Math.max(0, item.getQuantity() - reduceNumber));
    //             itemList.set(i, item);
    //         }
    //     }

    //     try {
    //         Files.delete(Paths.get("storage/item.txt"));
    //     } catch (IOException ex) {
    //         Logger.getLogger(ItemService.class.getName()).log(Level.SEVERE, null, ex);
    //     }

    //     try (PrintWriter pw = new PrintWriter(new FileOutputStream("storage/item.txt"))) {
    //         itemList.forEach(item -> {
    //             pw.println(item.getName() + "," + item.getPrice() + "," + item.getQuantity());
    //         });
    //     } catch (FileNotFoundException ex) {
    //         Logger.getLogger(ItemService.class.getName()).log(Level.SEVERE, null, ex);
    //     }
    // }

    // public synchronized void reduceItemQuantityByItemName(String itemName, int reduceNumber) {
    //     List<Item> itemList = getAll();

    //     for (int i = 0; i < itemList.size(); i++) {

    //         Item item = itemList.get(i);

    //         if (item.getName().equalsIgnoreCase(itemName)) {
    //             item.setQuantity(Math.max(0, item.getQuantity() - reduceNumber));
    //             itemList.set(i, item);
    //         }
    //     }

    //     try {
    //         Files.delete(Paths.get("storage/item.txt"));
    //     } catch (IOException ex) {
    //         Logger.getLogger(ItemService.class.getName()).log(Level.SEVERE, null, ex);
    //     }

    //     try (PrintWriter pw = new PrintWriter(new FileOutputStream("storage/item.txt"))) {
    //         itemList.forEach(item -> {
    //             pw.println(item.getName() + "," + item.getPrice() + "," + item.getQuantity());
    //         });
    //     } catch (FileNotFoundException ex) {
    //         Logger.getLogger(ItemService.class.getName()).log(Level.SEVERE, null, ex);
    //     }
    // }

    public synchronized void update(Item updatedItem) {
        String query = "UPDATE item SET quantity = ? WHERE name = ?";

        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            
            pstmt.setInt(1, updatedItem.getQuantity());
            pstmt.setString(2, updatedItem.getName());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
 
    }

    public void reduceItemQuantityByItemName(String name, int quantity) {
        Item item = DatabaseManager.getItemByName(name); // Implement this method to get item by name
        if (item != null && item.getQuantity() >= quantity) {
            item.setQuantity(item.getQuantity() - quantity);
            update(item);
        }
    }


}
