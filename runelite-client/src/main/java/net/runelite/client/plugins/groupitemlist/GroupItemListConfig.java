package net.runelite.client.plugins.groupitemlist;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("groupitemlist")
public interface GroupItemListConfig extends Config {

    enum positionOption{
        PREPEND,
        APPEND
    }

    @ConfigItem(
            keyName = "positionConfig",
            name = "Quantity position",
            description = "Configures where to place the item's quantity",
            position = 0
    )

    default positionOption getPositionConfig(){
        return positionOption.APPEND;
    }

    enum styleOption{
        SQUARE,
        PARENTHESIS,
        X_NUM
    }

    @ConfigItem(
            keyName = "styleConfig",
            name = "Quantity style",
            description = "Configures how the quantity will be styled",
            position = 1
    )

    default styleOption getStyleConfig(){
        return styleOption.SQUARE;
    }
}
