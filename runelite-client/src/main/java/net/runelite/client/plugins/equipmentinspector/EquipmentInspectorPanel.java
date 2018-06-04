package net.runelite.client.plugins.equipmentinspector;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ItemComposition;
import net.runelite.api.kit.KitType;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;

import javax.inject.Singleton;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Singleton
public class EquipmentInspectorPanel extends PluginPanel
{
	private GridBagConstraints c;
	private JPanel equipmentPanels;
	private GroupLayout layout;
	private JPanel header;

	//@Inject
	//private HashMap<KitType, ItemComposition> playerEquipment;

	public EquipmentInspectorPanel()
	{
//		layout = new GroupLayout(this);
//		setLayout(layout);
//		setBorder(new EmptyBorder(10, 10, 10, 10));
//		setBackground(ColorScheme.DARK_GRAY_COLOR);
//
//		equipmentPanels = new JPanel(new GridBagLayout());
//		c = new GridBagConstraints();
//		c.fill = GridBagConstraints.HORIZONTAL;
//		c.weightx = 1;
//		c.gridx = 0;
//		c.gridy = 0;
//
//		JPanel header = new JPanel();
//		header.setLayout(new BorderLayout());
//		header.setBorder(new CompoundBorder(
//				BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(58, 58, 58)),
//				BorderFactory.createEmptyBorder(0, 0, 10, 0)));
//
//		JLabel title = new JLabel("Equipment Inspector");
//		title.setForeground(Color.WHITE);
//
//		header.add(title, c);
//		c.gridy++;
//
//		equipmentPanels = new JPanel(new GridBagLayout());
		layout = new GroupLayout(this);
		setLayout(layout);
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setBackground(ColorScheme.DARK_GRAY_COLOR);

		equipmentPanels = new JPanel(new GridBagLayout());
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.gridx = 0;
		c.gridy = 0;

		header = new JPanel();
		header.setLayout(new BorderLayout());
		header.setBorder(new CompoundBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(58, 58, 58)),
				BorderFactory.createEmptyBorder(0, 0, 10, 0)));

		JLabel pluginName = new JLabel("Equipment Inspector Plugin");
		pluginName.setForeground(Color.WHITE);

		header.add(pluginName, BorderLayout.CENTER);

		layout.setHorizontalGroup(layout.createParallelGroup()
				.addComponent(equipmentPanels)
				.addComponent(header)
		);
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(header)
				.addGap(10)
				.addComponent(equipmentPanels)
		);

		update(new HashMap<>());
	}

	public void update(Map<KitType, ItemComposition> playerEquipment)
	{
		SwingUtilities.invokeLater(() ->
				{
					equipmentPanels.removeAll();
					playerEquipment.forEach((kitType, itemComposition) ->
					{
						equipmentPanels.add(new ItemPanel(itemComposition, kitType), c);
						c.gridy++;

					});
					header.revalidate();
					header.repaint();
				}
		);
	}
}
