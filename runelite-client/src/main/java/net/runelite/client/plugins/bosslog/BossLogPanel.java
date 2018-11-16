package net.runelite.client.plugins.bosslog;

import net.runelite.client.game.AsyncBufferedImage;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.components.materialtabs.MaterialTab;
import net.runelite.client.ui.components.materialtabs.MaterialTabGroup;

import javax.annotation.Nullable;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

public class BossLogPanel extends PluginPanel {

    private final ItemManager itemManager;
    private final BossLogPlugin plugin;

    private final JPanel display = new JPanel();
    private final MaterialTabGroup tabGroup = new MaterialTabGroup(display);
    private final Map<Tab, MaterialTab> uiTabs = new HashMap<>();

    private final JLabel overallIcon = new JLabel();

    @Nullable
    private TabContentPanel activeTabPanel = null;

    BossLogPanel(final BossLogPlugin plugin, final ItemManager itemManager)
    {
        this.itemManager = itemManager;
        this.plugin = plugin;

        setLayout(new BorderLayout());
        setBackground(ColorScheme.DARK_GRAY_COLOR);

        display.setBorder(new EmptyBorder(3, 2, 3, 2));

        tabGroup.setLayout(new GridLayout(0, 6, 7, 7));
        tabGroup.setBorder(new EmptyBorder(0, 0, 0, 0));

        add(tabGroup, BorderLayout.NORTH);
        add(display, BorderLayout.CENTER);
        addTab(Tab.OVERVIEW, new BossLogDropPanel(itemManager, plugin.zulrah));
        addTab(Tab.ZULRAH, new BossLogDropPanel(itemManager, plugin.zulrah));
    }

    private void addTab(Tab tab, TabContentPanel tabContentPanel)
    {
        JPanel wrapped = new JPanel(new BorderLayout());
        wrapped.add(tabContentPanel, BorderLayout.NORTH);
        wrapped.setBackground(ColorScheme.DARK_GRAY_COLOR);

        JScrollPane scroller = new JScrollPane(wrapped);
        scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroller.getVerticalScrollBar().setPreferredSize(new Dimension(16, 0));
        scroller.getVerticalScrollBar().setBorder(new EmptyBorder(0, 9, 0, 0));
        scroller.setBackground(ColorScheme.DARK_GRAY_COLOR);

        // Use a placeholder icon until the async image gets loaded
        MaterialTab materialTab = new MaterialTab(new ImageIcon(), tabGroup, scroller);
        materialTab.setPreferredSize(new Dimension(30, 27));
        materialTab.setName(tab.getName());
        materialTab.setToolTipText(tab.getName());

        AsyncBufferedImage icon = itemManager.getImage(tab.getItemID());
        Runnable resize = () ->
        {
            BufferedImage subIcon = icon.getSubimage(0, 0, 32, 32);
            materialTab.setIcon(new ImageIcon(subIcon.getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
        };
        icon.onChanged(resize);
        resize.run();

        materialTab.setOnSelectEvent(() ->
        {
            activeTabPanel = tabContentPanel;
            tabContentPanel.update();
            return true;
        });

        uiTabs.put(tab, materialTab);
        tabGroup.addTab(materialTab);
    }

    void update() {
        plugin.zulrah.update(itemManager);
        activeTabPanel.update();
    }

    void loadHeaderIcon(BufferedImage img)
    {
        overallIcon.setIcon(new ImageIcon(img));
    }
}
