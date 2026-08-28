public class AlterTest {
    public static void main(String[] args) {
        AlterMonitor monitor = new AlterMonitor();

        CpuAlert cpu = new CpuAlert(92.0);
        monitor.monitor(cpu);

        CpuAlert cpuFine = new CpuAlert(30.0);
        monitor.monitor(cpuFine);

        MemoryAlert mem = new MemoryAlert(95.0);
        monitor.monitor(mem);

        MemoryAlert memFine = new MemoryAlert(40.0);
        monitor.monitor(memFine);

        DiskAlter disk = new DiskAlter(8.0);
        monitor.monitor(disk);

        DiskAlter diskFine = new DiskAlter(50.0);
        monitor.monitor(diskFine);

        NetworkAlert net = new NetworkAlert(80.0);
        monitor.monitor(net);

        NetworkAlert netFine = new NetworkAlert(20.0);
        monitor.monitor(netFine);
    }
}
