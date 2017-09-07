package utility;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class BootstrapValidator {
	 /**
     * HashMap of Students's identification details of the BootstrapValidator
     * where the userid as key and name as value
     */
    private static HashMap<String, String> studentUsernameMap = new HashMap<>();
    
    public static ArrayList<String> validateStudent(String[] student) {

        ArrayList<String> errors = new ArrayList<String>();

        String number = student[0].trim();
        String name = student[1].trim();
        String user_id = student[2].trim();
        String school = student[3].trim();
        String email = student[4].trim();
        String section = student[5].trim();
        String bidding = student[6].trim();
        String lab_category = student[7].trim();
        String project_groups = student[8].trim();

        
        if (number.length() == 0) {
            errors.add("blank number");
        }

        
        if (name.length() == 0) {
            errors.add("blank name");
        }

    
        if (user_id.length() == 0) {
            errors.add("blank user_id");
        }

        //check if school is blank
        if (school.length() == 0) {
            errors.add("blank school");
        }

       
        if (email.length() == 0) {
            errors.add("blank email");
        }

        if (section.length() == 0) {
            errors.add("blank section");
        }

    
        if (bidding.length() == 0) {
            errors.add("blank bidding");
        }

        //check if school is blank
        if (lab_category.length() == 0) {
            errors.add("blank lab_category");
        }

       
        if (project_groups.length() == 0) {
            errors.add("blank project_groups");
        }

        //if there are any errors as of now, return the arraylist
        //no need to continue with file specific validations
        // if (!errors.isEmpty()) {
        //   return errors;
        // }

        if (errors.size() > 0) {
            return errors;
        } else {
            return null;
        }
    }
}
