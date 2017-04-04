package ru.stqa.training.selenium.client;

import org.junit.Test;
import ru.stqa.training.selenium.MultiBrowserBaseTest;
import ru.stqa.training.selenium.SeleniumBrowser;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlContains;

/**
 * @author Victoria Kadubina
 */
public class ProductParametersOnMainAndProductPagesTest extends MultiBrowserBaseTest {

    private static final Price.Color RED = new Price.Color(204, 0, 0);
    private static final Price.Color GREY_ON_MAIN_PAGE = new Price.Color(119, 119, 119);
    private static final Price.Color GREY_ON_PROD_PAGE = new Price.Color(102, 102, 102);

    private static final String TITLE_ON_MAIN_PAGE = "div.name";
    private static final String TITLE_ON_PROD_PAGE = "[itemprop=name]";


    public ProductParametersOnMainAndProductPagesTest(SeleniumBrowser browser) {
        super(browser);
    }

    @Test
    public void compareItemParametersOnMainAndProductPages() {
        clientApp.getMainPage().open();
        assertCampaignsContainsProducts();

        CampaignProduct productOnMainPage = new CampaignProduct(
                clientApp.getMainPage().getCampaignProducts().get(0), TITLE_ON_MAIN_PAGE);

        assertPriceParameters(productOnMainPage, GREY_ON_MAIN_PAGE);

        productOnMainPage.getLink().click();
        wait.until(urlContains("rubber-ducks"));

        CampaignProduct productOnSelfPage = new CampaignProduct(clientApp.getProductPage().getProduct(), TITLE_ON_PROD_PAGE);

        assertPriceParameters(productOnSelfPage, GREY_ON_PROD_PAGE);

        assertEquals("product titles expected to be the same",
                productOnMainPage.getTitle(), productOnSelfPage.getTitle());
        assertEquals("regular prices expected to be the same",
                productOnMainPage.getRegularPrice().getValue(), productOnSelfPage.getRegularPrice().getValue());
        assertEquals("regular prices expected to be the same",
                productOnMainPage.getCampaignPrice().getValue(), productOnSelfPage.getCampaignPrice().getValue());

    }

    private void assertPriceParameters(CampaignProduct product, Price.Color grey) {
        assertRegularPriceSizeLessThanCampaign(product);
        assertTrue("regular price expected to be struck out", product.getRegularPrice().isStruckOut());
        assertTrue("campaign price expected to be strong", product.getCampaignPrice().isStrong());
        assertEquals("regular price expected to be grey", grey, product.getRegularPrice().getColor());
        assertEquals("campaign price expected to be red", RED, product.getCampaignPrice().getColor());
    }

    private void assertRegularPriceSizeLessThanCampaign(CampaignProduct product) {
        double regularPriceSize = product.getRegularPrice().getFontSize();
        double campaignPriceSize = product.getCampaignPrice().getFontSize();
        assertTrue("regular price size expected to be less than campaign",
                regularPriceSize < campaignPriceSize);
    }

    private void assertCampaignsContainsProducts() {
        int campaignsProductsCount = clientApp.getMainPage().getCampaignProducts().size();
        assertTrue("Campaigns section expected to have products", campaignsProductsCount > 0);
    }

}