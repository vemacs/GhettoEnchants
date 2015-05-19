## GhettoEnchants
Being dissatisfied with the current state of public APIs for custom enchantments (EnchantmentAPI comes to mind), I decided to write my own for my own server. This API allows developers to easily implement event-based enchants for tools and armor, and "ambient" enchants for armor. It supports dynamic enchant registration, uses lore for storage, provides sample enchant implementations, and provides a command to deliver items with vanilla and custom enchants to players. I would like to enable support for hooking into enchantment tables in the future.

### API usage
Put it in your build path. I'm currently looking into getting this into a public Maven repo. See sample implementations and main class for enchant registration.

### Compilation:
mvn clean install, if you're compiling with an IDE, it would be wise to install the Lombok plugin for your IDE.

### Sample implementations:
* An armor enchant that gives players the jump potion effect (varying on level) when the armor is worn: https://github.com/vemacs/GhettoEnchants/blob/master/src/main/java/me/vemacs/ghettoenchants/enchants/armor/JumpPotionEnchant.java
* A simple autosmelt implementation for pickaxes: https://github.com/vemacs/GhettoEnchants/blob/master/src/main/java/me/vemacs/ghettoenchants/enchants/tools/pickaxe/AutosmeltEnchant.java
* A sword enchant that has a chance of applying venom on damage: https://github.com/vemacs/GhettoEnchants/blob/master/src/main/java/me/vemacs/ghettoenchants/enchants/tools/sword/VenomEnchant.java

### For server owners:
Unless you want to use the provided samples, you need to get a plugin that hooks into this one. If you wish to disable the samples, set the option to use samples to false in config.yml

### Commands/permissions examples:
/giveenchanteditem|gei
* Permission (default op): ghettoenchants.admin
* Usage example: /gei vemacs diamond_pickaxe autosmelt:1,eff:3

### Source code:
https://github.com/vemacs/GhettoEnchants