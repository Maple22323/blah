package net.runelite.client.plugins.bankvalue;

import net.runelite.api.Client;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;

import javax.inject.Inject;
import java.awt.*;
import java.awt.geom.Area;

import static java.lang.Math.max;

class BankOverlay extends Overlay {


    private final Client client;
    private final BankValuePlugin plugin;
    private final BankValueConfig config;


    @Inject
    private BankOverlay(Client client, BankValuePlugin plugin, BankValueConfig config)
    {
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
        this.client = client;
        this.plugin = plugin;
        this.config = config;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if(config.showHV()) {
            Widget BankContainer = client.getWidget(WidgetInfo.BANK_ITEM_CONTAINER);

            if (BankContainer == null || BankContainer.isHidden()) {
                return null;
            }
            for (Widget w : BankContainer.getDynamicChildren()) {
                if (Listcontains(plugin.bankCalculation.PairList, w)) {
                    highlightItem(graphics, BankContainer, w);
                }
            }

        }
        return null;
    }
    private boolean Listcontains(BankCalculation.Pair[] pairs, Widget w){
        for(BankCalculation.Pair p: pairs){
            if(p!=null) {
                if (p.getKey().getId() == w.getItemId()) {
                    return true;
                }
            }
        }
        return false;
    }
    private void highlightItem(Graphics2D graphics, Widget bankContainer, Widget w) {

        net.runelite.api.Point canvasLocation = w.getCanvasLocation();

        if (canvasLocation == null)
        {
            return;
        }

        // Don't draw outside the bank window
        net.runelite.api.Point windowLocation = bankContainer.getCanvasLocation();

        if (canvasLocation.getY() > windowLocation.getY() + bankContainer.getHeight()||
                canvasLocation.getY() + w.getHeight() < windowLocation.getY())
        {
            return;
        }

        Area widgetArea = new Area(
                new Rectangle(
                        canvasLocation.getX() - 1,
                        max(canvasLocation.getY() - 1,windowLocation.getY()) - 1,
                        w.getWidth() + 1,
                        getWidgetHeight(windowLocation.getY() - 1, bankContainer.getHeight() + 2, canvasLocation.getY() - 1, w.getHeight() + 2)));

        OverlayUtil.renderHoverableArea(graphics, widgetArea, client.getMouseCanvasPosition(),
                new Color(config.HighValueColour().getRed(), config.HighValueColour().getGreen(), config.HighValueColour().getBlue(), 20),config.HighValueColour() ,config.HighValueColour() );

    }

    private int getWidgetHeight(int y, int height, int y1, int height1) {
        //outside of window
        if((y1 >= (y + height) )||((y1 + height1 )<= y)){
            return 0;
        }
        //intersect with top
        else if (y1 < y && (y1 + height1)> y) {
            return height1 - (y - y1);
        }
        //intersect with bottom
        else if(y1 < (y + height) &&( y1 + height1 )>( y + height)){
            return (y + height) - y1;
        }
        return height1;
    }

}
