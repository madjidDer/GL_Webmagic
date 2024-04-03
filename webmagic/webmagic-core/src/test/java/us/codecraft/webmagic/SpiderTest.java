package us.codecraft.webmagic;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.processor.SimplePageProcessor;
import us.codecraft.webmagic.scheduler.Scheduler;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Tests for Spider.
 */
public class SpiderTest {

    private Spider spider;

    @Before
    public void setUp() {
        spider = Spider.create(new SimplePageProcessor("http://www.oschina.net/*"));
    }
    @Ignore("long time")
    @Test
    public void testStartAndStop() throws InterruptedException {
        spider.addPipeline(new Pipeline() {
            @Override
            public void process(ResultItems resultItems, Task task) {
                assertTrue("Pipeline process should be executed", true);
            }
        }).thread(1).addUrl("http://www.oschina.net/");
        spider.start();
        spider.stop();
        spider.start();
    }

    @Ignore("long time")
    @Test
    public void testWaitAndNotify() {
        spider.setDownloader(new Downloader() {
            @Override
            public Page download(Request request, Task task) {
                return new Page().setRawText("");
            }
            @Override
            public void setThread(int threadNum) {
            }
        }).setScheduler(new Scheduler() {
            private AtomicInteger count = new AtomicInteger();
            private Random random = new Random();

            @Override
            public void push(Request request, Task task) {
            }
            @Override
            public synchronized Request poll(Task task) {
                if (count.incrementAndGet() > 1000) {
                    return null;
                }
                if (random.nextInt(100) > 90) {
                    return null;
                }
                return new Request("test");
            }
        }).thread(10);
        spider.run();
    }
}
