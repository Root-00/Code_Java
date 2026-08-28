public class DiskAlter implements Alert{
    private double freePercent;

    public DiskAlter(double freePercent){
        this.freePercent = freePercent;
    }

    @Override
    public boolean check() {
        return freePercent < 10.0;
    }

    @Override
    public void report() {
        System.out.println("Disk free space low: " + freePercent + "% free");
    }
}

