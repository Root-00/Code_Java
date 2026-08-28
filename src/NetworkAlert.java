public class NetworkAlert implements Alert {
    private double usage;

    public NetworkAlert(double usage) {
        this.usage = usage;
    }

    @Override
    public boolean check() {
        return usage > 70.0;
    }

    @Override
    public void report() {
        System.out.println("Network at " + usage + "% usage");
    }
}
