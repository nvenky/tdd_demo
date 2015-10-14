package au.com.iag.demo;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private static final double DEFAULT_TAX_RATE = 10;

    private List<Item> items = new ArrayList<Item>();
    private PromotionService promotionService;
    private Promotion promotion;

    public void add(Item item) {
      items.add(item);
    }

    public int size() {
       return items.size();
    }

    public double totalAmount() {
        double total=0;
        for (Item item : items) {
            total += item.getPrice();
        }
        return total;
    }

    public double totalAmountWithSalesTax() {
        return totalAmount() * (1.0 + DEFAULT_TAX_RATE / 100);
    }

    public void applyPromotionCode(String promotionCode) {
       this.promotion = promotionService.getPromotion(promotionCode);
    }

    public void setPromotionService(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    public Promotion getPromotion() {
        return promotion;
    }
}
