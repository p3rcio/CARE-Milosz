package cwk4;
//
import java.util.ArrayList;
import java.util.Objects;

public class Vizier {
    private String vizierName;
    private boolean defeated;
    private int vizierGulden;
    public ArrayList<Champion> vizierTeam; // array of champions in player's rooster
    private ArrayList<Champion> disqualifiedChamps; // array of disqualified vizier champs

    /**
     * this is the vizer constructor for creating new vizier object
     * @param name - name of vizier
     */
    public Vizier(String name){
        vizierName = name;
        defeated = false;
        vizierGulden = 1000;
        vizierTeam = new ArrayList<Champion>();
        disqualifiedChamps = new ArrayList<Champion>();

    }

    public int getGulden(){
        return vizierGulden;
    }
    public void addChampion(Champion champion) {
        vizierTeam.add(champion);
    }
    public void removeChampion(Champion champion){
        vizierTeam.remove(champion);
    }
    public void addDisqualifiedChamp(Champion champion){
        disqualifiedChamps.add(champion);
    }

    public void setDefeated() {
        defeated = true;
    }

    public String getDefeated(){
        if (defeated){
            return "Yes";
        } else {
            return "No";
        }
    }

    public ArrayList<Champion> getVizierChampArray(){
        return vizierTeam;
    }

    public String getVizierTeam(){
        String champions = "";
        for (Champion champion : vizierTeam){
            champions += "\n" + champion;
        }
        if (champions.isEmpty()){
            return " EMPTY";
        }
        return champions;
    }

    public String getVizierDisqualifiedTeam(){
        String champions = "";
        for (Champion champion : disqualifiedChamps){
            champions += champion + "\n";
        }
        return champions;
    }

    public String getVizierName(){
        return vizierName;
    }

    public void deductGulden(int gulden){
        vizierGulden -= gulden;
    }

    public void addGulden(int gulden){
        vizierGulden += gulden;
    }
    public boolean isChampInTeam(String name){
        for (Champion champion : vizierTeam){
            if (Objects.equals(champion.getChampionName(), name)){
                return true;
            }
        }
        return false;
    }

    public String getChampDetails(String name){
        for (Champion champion : vizierTeam){
            if (Objects.equals(champion.getChampionName(), name)){
                return champion.toString() + "Champion State: " + champion.getChampionState();
            }
        }
        return "\nNo such champion";
    }

}
