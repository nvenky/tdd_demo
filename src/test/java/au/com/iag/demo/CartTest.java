package au.com.iag.demo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartTest {

    //    @InjectMocks
    private Cart cart;

    @Mock
    private PromotionService mockPromotionService;

    @Before
    public void setup() {
        cart = new Cart();
    }

    @Test
    public void shouldAddItemsToCart() {
        cart.add(new Item(10.0));
        cart.add(new Item(20.0));
        assertThat(cart.size(), is(2));
    }

    @Test
    public void shouldReturnTotalAmount() {
        cart.add(new Item(10.0));
        cart.add(new Item(20.0));
        assertThat(cart.totalAmount(), is(30.0));
    }

    @Test
    public void shouldCalculateTotalWithSalesTax() {
        cart.add(new Item(20.0));
        cart.add(new Item(20.0));
        assertThat(cart.totalAmountWithSalesTax(), is(44.0));
    }

    @Test
    public void shouldApplyPromotionCode() {
        String promotionCode = "SPRING20";
        cart.setPromotionService(mockPromotionService);
        Promotion promotion = new Promotion(promotionCode, 20.0);
        when(mockPromotionService.getPromotion(promotionCode)).thenReturn(promotion);
        cart.applyPromotionCode(promotionCode);
        assertThat(cart.getPromotion(), is(promotion));
    }

    @Test(expected = InvalidPromoCodeException.class)
    public void shouldRaiseErrorWhenPromotionCodeIsInvalid() {
        String promotionCode = "INVALID";
        cart.setPromotionService(mockPromotionService);
        when(mockPromotionService.getPromotion(promotionCode)).thenThrow(new InvalidPromoCodeException("Invalid promo code"));
        cart.applyPromotionCode(promotionCode);
        fail("Expected exception not raised");
    }
}