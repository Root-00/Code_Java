public class MemoryAlert implements Alert{
    private double usgae;

    public MemoryAlert(double usgae){
        this.usgae = usgae;
    }

    @Override
    public boolean check() {
        return usgae > 90.0;
    }

    @Override
    public void report() {
        System.out.println("Memory at " + usgae + "% used");
    }


}
