public class CpuAlert implements Alert{
    private double usage;

    public CpuAlert(double usage){
        this.usage = usage;
    }

    public boolean check() {
        return usage > 85.0;
    }

    public void report(){
        System.out.println("CPU at " + usage + "% usage");
    }
}
