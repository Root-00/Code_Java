public class AlterMonitor {
    public void monitor(Alert alert){
        if(alert.check()) {
            alert.report();
        }else {
            System.out.println("All good");
        }
    }
}
