package game;

import com.ethanzeigler.jgamegui.JGameGUI;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.event.*;

/**
 * Created by gregorygiovannini on 5/19/16.
 */
public class InventoryGUI
{
    private JPanel inventoryInterface;
    private JTextArea playerStats;
    private JButton useButton;
    private JButton backButton;
    private JTable playerItems;
    private JTextArea playerItemsList;
    private static JFrame frame;

    private static Item selectedItem;
    DefaultTableModel model;

    public InventoryGUI()
    {
        useButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    System.out.println(selectedItem.itemStats());
                    useItem();
                }
                catch (NullPointerException exception)
                {
                    System.out.println("No item selected");
                }

            }
        });


        backButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                frame.dispose();
                GameGUI.startGame();
            }
        });

        playerStats.setText(((Player)GameGUI.player).detailedPlayerStats());

        playerItems.addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent e)
            {
                super.componentResized(e);
            }

            @Override
            public void componentMoved(ComponentEvent e)
            {
                super.componentMoved(e);
            }

            @Override
            public void componentShown(ComponentEvent e)
            {
                super.componentShown(e);
            }

            @Override
            public void componentHidden(ComponentEvent e)
            {
                super.componentHidden(e);
            }
        });

        /*playerItemsList.setText("Items: \n");
        if(GameGUI.player.getInventory() != null)
        {
            if(GameGUI.player.getInventory().getItems() != null)
            {
                for(Item item: GameGUI.player.getInventory().getItems())
                {
                    playerItemsList.append(item.itemStats() + "\n");
                }
            }
        }*/
    }


    public static void openInventory()
    {
        frame = new JFrame("InventoryGUI");
        frame.setContentPane(new InventoryGUI().inventoryInterface);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents()
    {
        // TODO: place custom component creation code here

        updateTable();
        playerItems = new JTable(model);
        //playerItems = new JTable(model, rowData, columnTitles);

        playerItems.setCellSelectionEnabled(true);
        ListSelectionModel cellSelectionModel = playerItems.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectionModel.addListSelectionListener(new ListSelectionListener()
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
                setSelectedItem(itemNumber);
                System.out.println(selectedItem.itemStats());
                //String itemName = extractItemNameFromData(selectedData);
                //System.out.println(itemName);
            }

        });

        for(int row = playerItems.getRowCount() -1; row >= 0; row--)
        {
            playerItems.setRowHeight(row, 30);
        }

        playerItems.setShowGrid(true);
        model = (DefaultTableModel) playerItems.getModel();

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

    public void setSelectedItem(String itemNumber) // Sets the selected item based on its number in the table
    {
        for(Item i: GameGUI.player.getInventory().getItems())
        {
            if(i.makeItemNumberString().equals(itemNumber))
            {
                selectedItem = i;
                break;
            }
        }
    }

    public void useItem()
    {
        if(selectedItem instanceof Armor) // Item is some type of armor - check which type
        {
            if(selectedItem instanceof Boots) // Item is boots
            {
                ((Boots) selectedItem).equipArmor();
            }
            else if(selectedItem instanceof Gauntlets) // Item is gauntlets
            {
                ((Gauntlets) selectedItem).equipArmor();
            }
            else if(selectedItem instanceof Leggings) // Item is leggings
            {
                ((Leggings) selectedItem).equipArmor();
            }
            else if(selectedItem instanceof Chestplate) // Item is chestplate
            {
                ((Chestplate) selectedItem).equipArmor();
            }
            else if(selectedItem instanceof Helmet) // Item is helmet
            {
                ((Helmet) selectedItem).equipArmor();
            }
            System.out.println(selectedItem.getName() + " equipped");
        }

        else if(selectedItem instanceof Weapon) // Item is a weapon
        {
            ((Weapon) selectedItem).equipWeapon();
            System.out.println(selectedItem.getName() + " equipped");
        }

        else if(selectedItem instanceof Potion) // Item is a potion
        {
            ((Potion) selectedItem).drinkPotion();
            System.out.println(selectedItem.getName() + " drank");
            GameGUI.player.getInventory().sortInventoryAlphabetically(); // Resort inventory
            //model.removeRow(selectedItem.getItemNumber() -1);
            //model.removeRow(GameGUI.player.getInventory().getItems().size() -1);
        }
        frame.dispose();
        openInventory();
        selectedItem = null;
    }

    public void updateTable()
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

        model = new DefaultTableModel(rowData, columnTitles) {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
        GameGUI.player.getInventory().sortInventoryAlphabetically(); // Resort inventory

    }


    /*public String extractItemNameFromData(String selectedData) // Gets the name out of the data from the table
    {
        String[] words = selectedData.split("         ");
        return words[0];
    }*/

    /*public static void main(String[] args)
    {
        JFrame frame = new JFrame("InventoryGUI");
        frame.setContentPane(new InventoryGUI().inventoryInterface);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }*/
}
