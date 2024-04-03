package us.codecraft.webmagic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class SiteTest {

    @Test
    public void test() {
        Site site = Site.me().setDefaultCharset(StandardCharsets.UTF_8.name());
        assertEquals(StandardCharsets.UTF_8.name(), site.getDefaultCharset());
    }

    @Test
    public void addCookieTest(){
        Site site=Site.me().setDefaultCharset(StandardCharsets.UTF_8.name());
        site.addCookie("cookieDefault","cookie-webmagicDefault");
        String firstDomain="example.com";
        String secondDomain="exampleCopy.com";
        site.addCookie(firstDomain, "cookie", "cookie-webmagic");
        site.addCookie(firstDomain, "cookieCopy", "cookie-webmagicCopy");
        site.addCookie(secondDomain, "cookie", "cookie-webmagic");
        Map<String, Map<String, String>> allCookies = site.getAllCookies();
        List<String> domains=new ArrayList<>();
        for(String key : allCookies.keySet()){
            domains.add(key);
        }
        assertEquals("cookie-webmagic", allCookies.get(firstDomain).get("cookie"));
        assertEquals("cookie-webmagicCopy", allCookies.get(firstDomain).get("cookieCopy"));
        assertEquals("cookie-webmagic", allCookies.get(secondDomain).get("cookie"));
        assertEquals(2, domains.size());
    }
    
    @Test
    public void shouldAddCustomHeaderCorrectly() {
        Site site = Site.me();
        String headerName = "HeaderName";
        String headerValue = "SomeValue";

        site.addHeader(headerName, headerValue);

        Map<String, String> headers = site.getHeaders();
        assertEquals(headerValue, headers.get(headerName));
    }
    
    @Test
    public void shouldSetUserAgentCorrectly() {
        Site site = Site.me();
        String userAgentValue = "Mozilla/5.0";

        site.setUserAgent(userAgentValue);

        assertEquals("Le User-Agent doit être configuré correctement", userAgentValue, site.getUserAgent());
    }
    
    @Test
    public void shouldSetTimeoutCorrectly() {
        Site site = Site.me();
        int timeoutValue = 20000; // 20 secondes

        site.setTimeOut(timeoutValue);

        assertEquals("Le timeout doit être configuré correctement", timeoutValue, site.getTimeOut());
    }
    
    @Test
    public void shouldSetAcceptStatusCodeCorrectly() {
        Site site = Site.me();
        Set<Integer> acceptStatusCodes = new HashSet<>(Arrays.asList(200, 404));

        site.setAcceptStatCode(acceptStatusCodes);

        assertTrue("Le site doit être configuré pour accepter les codes de statut spécifiés", site.getAcceptStatCode().containsAll(acceptStatusCodes));
    }
}
