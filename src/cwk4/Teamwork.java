package cwk4;

//
/**
 * Details of your team
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Teamwork
{
    private String[] details = new String[12];

    public Teamwork()
    {   // in each line replace the contents of the String 
        // with the details of your team member
        // Please list the member details alphabetically by surname 
        // i.e. the surname of member1 should come alphabetically 
        // before the surname of member 2...etc
        details[0] = "CS04";

        details[1] = "Jakubiec";
        details[2] = "Milosz";
        details[3] = "21079214";

        details[4] = "Ilie";
        details[5] = "Stefan";
        details[6] = "21033168";

        details[7] = "Martin";
        details[8] = "Joe";
        details[9] = "21041561";


        details[10] = "Dyer";
        details[11] = "Ewan";
        details[12] = "22003870";

    }

    public String[] getTeamDetails()
    {
        return details;
    }

    public void displayDetails()
    {
        for(String temp:details)
        {
            System.out.println(temp.toString());
        }
    }
}

        
