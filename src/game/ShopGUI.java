package game;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GregG on 6/14/16.
 */
public class ShopGUI {
    private JPanel shopInterface;
    private JTable shopItems;
    private JTextArea coins;
    private JButton buyButton;
    private JButton backButton;
    private JTable playerItems;
    private JButton sellButton;
    private static JFrame frame;

    private static Item selectedItemShop;
    private static Item selectedItemInventory;
    private static Inventory shopInventory;
    DefaultTableModel shopItemsModel;
    DefaultTableModel playerItemsModel;

    private NPC shopKeeper;

    public ShopGUI()
    {
        //initiateShopInventory();
        backButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                frame.dispose();
                GameGUI.startGame();
            }
        });

        sellButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    System.out.println(selectedItemInventory.itemStats());
                    sellItem();
                }
                catch (NullPointerException exception)
                {
                    System.out.println("No item selected");
                }

            }
        });

        buyButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    System.out.println(selectedItemShop.itemStats());
                    buyItem();
                }
                catch (NullPointerException exception)
                {
                    System.out.println("No item selected");
                }

            }
        });

        coins.setText("COINS: " + GameGUI.player.getCoins());
    }

    public static void openShop()
    {
        frame = new JFrame("ShopGUI");
        frame.setContentPane(new ShopGUI().shopInterface);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents()
    {
        // TODO: place custom component creation code here

        // ******************* INVENTORY TABLE *******************
        updateInventoryTable();
        playerItems = new JTable(playerItemsModel);

        playerItems.setCellSelectionEnabled(true);
        ListSelectionModel cellSelectionModelInventory = playerItems.getSelectionModel();
        cellSelectionModelInventory.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectionModelInventory.addListSelectionListener(new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent e)
            {
                String selectedData = null;

                int[] selectedRow = playerItems.getSelectedRows();
                int[] selectedColumns = playerItems.getSelectedColumns();

                for (int i = 0; i < selectedRow.length; i++)
                {
                    for (int j = 0; j < selectedColumns.length; j++)
                    {
                        selectedData = (String) playerItems.getValueAt(selectedRow[i], selectedColumns[j]);
                    }
                }
                System.out.println("Selected: " + selectedData);

                String itemNumber = extractItemNumberFromData(selectedData);
                System.out.println(itemNumber);
                setSelectedPlayerItem(itemNumber);
                System.out.println(selectedItemInventory.itemStats());
            }

        });

        for(int row = playerItems.getRowCount() -1; row >= 0; row--)
        {
            playerItems.setRowHeight(row, 30);
        }


        playerItems.setShowGrid(true);
        playerItemsModel = (DefaultTableModel) playerItems.getModel();


        // ******************* SHOP TABLE *******************
        updateShopTable();
        shopItems = new JTable(shopItemsModel);

        shopItems.setCellSelectionEnabled(true);
        ListSelectionModel cellSelectionModelShop = shopItems.getSelectionModel();
        cellSelectionModelShop.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectionModelShop.addListSelectionListener(new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent e)
            {
                String selectedData = null;

                int[] selectedRow = shopItems.getSelectedRows();
                int[] selectedColumns = shopItems.getSelectedColumns();

                for (int i = 0; i < selectedRow.length; i++)
                {
                    for (int j = 0; j < selectedColumns.length; j++)
                    {
                        selectedData = (String) shopItems.getValueAt(selectedRow[i], selectedColumns[j]);
                    }
                }
                System.out.println("Selected: " + selectedData);

                String itemNumber = extractItemNumberFromData(selectedData);
                System.out.println(itemNumber);
                setSelectedShopItem(itemNumber);
                System.out.println(selectedItemShop.itemStats());
            }
        });

        for(int row = shopItems.getRowCount() -1; row >= 0; row--)
        {
            shopItems.setRowHeight(row, 30);
        }


        shopItems.setShowGrid(true);
        shopItemsModel = (DefaultTableModel) shopItems.getModel();
    }

    public String extractItemNumberFromData(String selectedData) // Takes the number in String form from the data String that appears in the table
    {
        String itemNumber;
        if(selectedData.contains("*"))
        {
            int indexOfAsterik = selectedData.indexOf("*");
            itemNumber = selectedData.substring(0, indexOfAsterik);
        }
        else
        {
            String[] pieces = selectedData.split("   ");
            itemNumber = pieces[0];
        }

        return itemNumber;
    }

    public void setSelectedPlayerItem(String itemNumber) // Sets the selected item in inventory table based on its number in the table
    {
        for(Item i: GameGUI.player.getInventory().getItems())
        {
            if(i.makeItemNumberString().equals(itemNumber))
            {
                selectedItemInventory = i;
                break;
            }
        }
    }

    public void setSelectedShopItem(String itemNumber) // Sets the selected item in shop table based on its number in the table
    {
        for(Item i: shopKeeper.getInventory().getItems())
        {
            if(i.makeItemNumberString().equals(itemNumber))
            {
                selectedItemShop = i;
                break;
            }
        }
    }

    public void sellItem()
    {
        if(selectedItemInventory instanceof Armor) // Item is some type of armor - check which type
        {
            if(selectedItemInventory instanceof Boots) // Item is boots
            {
                ((Boots) selectedItemInventory).unequipArmor();
            }
            else if(selectedItemInventory instanceof Gauntlets) // Item is gauntlets
            {
                ((Gauntlets) selectedItemInventory).unequipArmor();
            }
            else if(selectedItemInventory instanceof Leggings) // Item is leggings
            {
                ((Leggings) selectedItemInventory).unequipArmor();
            }
            else if(selectedItemInventory instanceof Chestplate) // Item is chestplate
            {
                ((Chestplate) selectedItemInventory).unequipArmor();
            }
            else if(selectedItemInventory instanceof Helmet) // Item is helmet
            {
                ((Helmet) selectedItemInventory).unequipArmor();
            }
            //System.out.println(selectedItemInventory.getName() + " unequipped");
        }

        else if(selectedItemInventory instanceof Weapon) // Item is a weapon
        {
            ((Weapon) selectedItemInventory).unequipWeapon();
            //System.out.println(selectedItemInventory.getName() + " unequipped");
        }

        else if(selectedItemInventory instanceof Potion) // Item is a potion
        {
            System.out.println(selectedItemInventory.getName() + " potion found");
        }

        GameGUI.player.getInventory().getItems().remove(selectedItemInventory);
        GameGUI.player.setCoins(GameGUI.player.getCoins() + selectedItemInventory.getValue());

        GameGUI.player.getInventory().sortInventoryAlphabetically(); // Resort inventory
        frame.dispose();
        openShop();
        selectedItemInventory = null;
    }

    public void buyItem()
    {
        if(GameGUI.player.getCoins() >= selectedItemShop.getValue() && GameGUI.player.getInventory().getItems().size() < 15) // Player can afford item and inventory is not full
        {
            GameGUI.player.getInventory().addItemToInventory(selectedItemShop);
            GameGUI.player.setCoins(GameGUI.player.getCoins() - selectedItemShop.getValue());
            frame.dispose();
            openShop();
            selectedItemShop = null;
        }
        else System.out.println("You cannot afford that OR inventory is full.");

    }

    public void updateInventoryTable()
    {
        String[] columnTitles = { "ITEM"};//, "TYPE", "VALUE", "DESCRIPTION" };
        String[] itemInformation = new String[MainMenuGUI.player.getInventory().getItems().size()];

        int numberOfItems = 0;
        for(Item i: MainMenuGUI.player.getInventory().getItems())
        {
            itemInformation[numberOfItems] = i.itemStatsOnOneLine();
            numberOfItems++;
        }
        Object[][] rowData = new Object[numberOfItems][1];
        while(numberOfItems > 0)
        {
            rowData[numberOfItems-1][0] = itemInformation[numberOfItems-1];
            numberOfItems--;
        }

        //= { { "11", "12", "13", "14" }, { "21", "22", "23", "24" },
        //{ "31", "32", "33", "34" }, { "41", "42", "44", "44" } };

        playerItemsModel = new DefaultTableModel(rowData, columnTitles) {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
        GameGUI.player.getInventory().sortInventoryAlphabetically(); // Resort inventory
    }

    public void updateShopTable()
    {
        initiateShopInventory();
        String[] columnTitles = { "ITEM"};//, "TYPE", "VALUE", "DESCRIPTION" };
        String[] itemInformation = new String[shopKeeper.getInventory().getItems().size()];

        int numberOfItems = 0;
        for(Item i: shopKeeper.getInventory().getItems())
        {
            itemInformation[numberOfItems] = i.itemStatsOnOneLine();
            numberOfItems++;
        }
        Object[][] rowData = new Object[numberOfItems][1];
        while(numberOfItems > 0)
        {
            rowData[numberOfItems-1][0] = itemInformation[numberOfItems-1];
            numberOfItems--;
        }

        shopItemsModel = new DefaultTableModel(rowData, columnTitles) {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
        shopKeeper.getInventory().sortInventoryAlphabetically();

    }

    public void initiateShopInventory()
    {
        shopKeeper = new NPC("Shopkeeper");

        List<Item> items = new ArrayList<>();

        items.add(new Weapon(shopKeeper));
        items.add(new Helmet(shopKeeper));
        items.add(new Chestplate(shopKeeper));
        items.add(new Gauntlets(shopKeeper));
        items.add(new Leggings(shopKeeper));
        items.add(new Boots(shopKeeper));
        items.add(new Potion(shopKeeper));

        Inventory inventory = new Inventory(items, shopKeeper);

        inventory.sortInventoryAlphabetically();
        shopKeeper.setInventory(inventory);


    }
}
