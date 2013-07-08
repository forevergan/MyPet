/*
 * This file is part of MyPet
 *
 * Copyright (C) 2011-2013 Keyle
 * MyPet is licensed under the GNU Lesser General Public License.
 *
 * MyPet is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MyPet is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package de.Keyle.MyPet.entity.types.mooshroom;

import de.Keyle.MyPet.entity.EntitySize;
import de.Keyle.MyPet.entity.types.EntityMyPet;
import de.Keyle.MyPet.entity.types.MyPet;
import net.minecraft.server.v1_6_R1.EntityHuman;
import net.minecraft.server.v1_6_R1.ItemStack;
import net.minecraft.server.v1_6_R1.World;
import org.bukkit.Material;

@EntitySize(width = 0.9F, height = 1.3F)
public class EntityMyMooshroom extends EntityMyPet
{
    public static int GROW_UP_ITEM = Material.POTION.getId();

    public EntityMyMooshroom(World world, MyPet myPet)
    {
        super(world, myPet);
    }

    public void setMyPet(MyPet myPet)
    {
        if (myPet != null)
        {
            super.setMyPet(myPet);

            this.setBaby(((MyMooshroom) myPet).isBaby());
        }
    }

    public boolean isBaby()
    {
        return ((MyMooshroom) myPet).isBaby;
    }

    public void setBaby(boolean flag)
    {
        if (flag)
        {
            this.datawatcher.watch(12, new Integer(Integer.MIN_VALUE));
        }
        else
        {
            this.datawatcher.watch(12, new Integer(0));
        }
        ((MyMooshroom) myPet).isBaby = flag;
    }

    // Obfuscated Methods -------------------------------------------------------------------------------------------

    protected void a()
    {
        super.a();
        this.datawatcher.a(12, new Integer(0)); // age
    }

    public boolean a(EntityHuman entityhuman)
    {
        try
        {
            if (super.a(entityhuman))
            {
                return true;
            }

            ItemStack itemStack = entityhuman.inventory.getItemInHand();

            if (getOwner().equals(entityhuman) && itemStack != null && canUseItem())
            {
                if (itemStack.id == GROW_UP_ITEM && getOwner().getPlayer().isSneaking())
                {
                    if (isBaby())
                    {
                        if (!entityhuman.abilities.canInstantlyBuild)
                        {
                            if (--itemStack.count <= 0)
                            {
                                entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, null);
                            }
                        }
                        this.setBaby(false);
                        return true;
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    protected void a(int i, int j, int k, int l)
    {
        makeSound("mob.cow.step", 0.15F, 1.0F);
    }

    /**
     * Returns the sound that is played when the MyPet get hurt
     */
    @Override
    protected String aK()
    {
        return "mob.cow.hurt";
    }

    /**
     * Returns the sound that is played when the MyPet dies
     */
    @Override
    protected String aL()
    {
        return "mob.cow.hurt";
    }

    /**
     * Returns the default sound of the MyPet
     */
    protected String r()
    {
        return !playIdleSound() ? "" : "mob.cow.say";
    }
}