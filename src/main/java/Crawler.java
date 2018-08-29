public class Crawler {
    public static void main(String[] args) throws InterruptedException {

        CrawlerLeg cl = new CrawlerLeg();
        cl.setDriver();
        cl.loadPage("https://classic.flysas.com/");
        cl.setFlightDirections("ARN", "LHR");
        cl.setTripDates();
        cl.startBooking();
        cl.testSearchButton();
    }
}
