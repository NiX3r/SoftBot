package Instances;

import java.util.ArrayList;

public class ShopUtilInstance {

    private ArrayList<ShopInstance> shops;

    public ShopUtilInstance() {
        this.shops = new ArrayList<ShopInstance>();
    }

    public ArrayList<ShopInstance> getShops() {
        return shops;
    }

    public int calculateShopPages(){

        return (shops.size() / 10) + 1;

    }

    public ShopInstance getShopById(int id){
        for(ShopInstance shop : this.shops){
            if(shop.getId() == id)
                return shop;
        }
        return null;
    }

    public ArrayList<ShopInstance> getGameArrayByPageIndex(int index){

        ArrayList<ShopInstance> output = new ArrayList<ShopInstance>();

        for(int i = 10 * (index - 1); i < (9 + (10 * (index - 1))); i++){

            if(i == shops.size())
                break;

            output.add(shops.get(i));

        }

        return output;

    }

}
