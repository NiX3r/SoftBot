package Instances;

import Database.BazaarUtils;
import Enums.BazaarTypeEnum;

import java.util.ArrayList;

public class BazaarUtilInstance {

    private ArrayList<BazaarInstance> bazaar;

    public BazaarUtilInstance(){

        this.bazaar = new ArrayList<BazaarInstance>();

    }

    public ArrayList<BazaarInstance> getBazaar() {
        return bazaar;
    }

    public int calculateOfferPages(){
        return (getOffers().size() / 10) + 1;
    }

    public ArrayList<BazaarInstance> getOfferArrayByPageIndex(int index){

        ArrayList<BazaarInstance> output = new ArrayList<BazaarInstance>();
        ArrayList<BazaarInstance> offers = getOffers();

        for(int i = 10 * (index - 1); i < (9 + (10 * (index - 1))); i++){

            if(i == offers.size())
                break;

            output.add(offers.get(i));

        }

        return output;

    }

    public ArrayList<BazaarInstance> getOffers(){
        ArrayList<BazaarInstance> output = new ArrayList<BazaarInstance>();
        for(BazaarInstance bazaar : this.bazaar){
            if(bazaar.getType() == BazaarTypeEnum.OFFER)
                output.add(bazaar);
        }
        return output;
    }

    public BazaarInstance getOfferById(int id){
        for(BazaarInstance bazaar : this.bazaar){
            if(bazaar.getType() == BazaarTypeEnum.OFFER &&
                bazaar.getId() == id)
                return bazaar;
        }
        return null;
    }

    public int calculateInquiryPages(){
        return (getBazaar().size() / 10) + 1;
    }

    public ArrayList<BazaarInstance> getInquiryArrayByPageIndex(int index){

        ArrayList<BazaarInstance> output = new ArrayList<BazaarInstance>();
        ArrayList<BazaarInstance> inquiries = getInquiries();

        for(int i = 10 * (index - 1); i < (9 + (10 * (index - 1))); i++){

            if(i == inquiries.size())
                break;

            output.add(inquiries.get(i));

        }

        return output;

    }

    public ArrayList<BazaarInstance> getInquiries(){
        ArrayList<BazaarInstance> output = new ArrayList<BazaarInstance>();
        for(BazaarInstance bazaar : this.bazaar){
            if(bazaar.getType() == BazaarTypeEnum.INQUIRY)
                output.add(bazaar);
        }
        return output;
    }

    public BazaarInstance getInquiryById(int id){
        for(BazaarInstance bazaar : this.bazaar){
            if(bazaar.getType() == BazaarTypeEnum.INQUIRY &&
                    bazaar.getId() == id)
                return bazaar;
        }
        return null;
    }

}
