public class SimpleStartup {
    int[] locationCells;
    int numOfHits = 0;
    boolean[] alreadyHit;

    public void setLocationCells(int[] locs) {
        locationCells = locs;
        alreadyHit = new boolean[locs.length];
    }

    public String checkYourself(int guess) {
        String result = "miss";

        for (int i = 0; i < locationCells.length; i++) {
            if (guess == locationCells[i]) {
                if(!alreadyHit[i]) {
                    alreadyHit[i] = true;
                    numOfHits++;
                    result = "hit";
                    break;

                }
            }
        }

            if (numOfHits == locationCells.length) {
                result = "kill";
            }

            System.out.println(result);
            return result;
        }
    }

