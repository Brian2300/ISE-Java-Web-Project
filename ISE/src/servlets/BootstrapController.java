package servlets;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dao.BootstrapDAO;
import dao.RegisterStatusDAO;
import utility.BootstrapValidator;

import com.opencsv.CSVReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * Reads the csv files that are being uploaded into the BIOS System,
 * wipes all the data in the database, 
 * processes the data and then returns error messages of the rows that are invalid
 * and places the valid rows into the database.
 */
@WebServlet(name = "BootstrapController", urlPatterns = {"/BootstrapController"})
public class BootstrapController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @SuppressWarnings("empty-statement")
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        System.out.println("Reached BootstrapController");
        //arraylist of the csv files in folder
        ArrayList<String> csvList = (ArrayList<String>) request.getAttribute("csvList");

        //instantiates a hashmap to store key and value
        //key being the name of the file, value being the path
        HashMap<String, String> csvHash = new HashMap<String, String>();

        //goes through every csv in the arraylist, and store them into the hashmap
        //using substring, we get the name of the file to set as key
        //the value will be the path itself
        //this way we can retrieve the filepath when we want them
        //if there had been additional csv files uploaded by user, it does not matter
        //because we will only be calling the 6 that we need from the hashmap
        //for example <Key:"student.csv" Value:"......build/web/data/student.csv"> 
       TreeMap<Integer, ArrayList<String>> invalidStudents = new TreeMap<>(); //stores invalid rows to be displayed
       // ArrayList<String> invalidStudents = new ArrayList<String>();
        for (String path : csvList) {
        	System.out.println("csv list: " +path);
            String fileName = path.substring(path.lastIndexOf(File.separator) + 1);
	        String uploaded_section = fileName;
        /*
	        ==========================================================
	            READING CSV
	        ==========================================================
	         */
     
	        CSVReader studentReader = new CSVReader(new FileReader(path)); //using CSVReader to read csv
	        ArrayList<String[]> studentlist = new ArrayList<>(); //stores valid rows to be passed to DAO
	        
	        String[] student;
	        String[] header;
	        header = studentReader.readNext(); //skips the header row
	        header = studentReader.readNext(); //skips the header row
	        header = studentReader.readNext(); //skips the header row
	        //
	        //BootstrapValidator.validateHeader(header)
	        int studentRowNumber = 1;
	
	        String section="";
	        while ((student = studentReader.readNext()) != null) {
	        	
	        	ArrayList<String> errors = BootstrapValidator.validateStudent(student);
	            studentRowNumber++;
	        	
	        	if (errors == null) {
	        		studentlist.add(student);
	            } else {
	                invalidStudents.put((Integer)studentRowNumber, errors);
	            }
	        	
	        	section =student[5];
	        }
	        //String section = student[5];
	        /*
	        ==========================================================
	            WIPING ALL THE TABLES
	        ==========================================================
	         */
	       System.out.println("Wipe the table of "+section);
	       BootstrapDAO.wipeTables(section);
	        
	
	        
	
	        //Converting ArrayList<String[]> to String[][]
	        String[][] validStudentArray = studentlist.toArray(new String[0][0]);
	
	
	
	//System.out.println("valid student array"+validStudentArray[0][0]);
	//System.out.println("valid student array"+validStudentArray[2][3]);
	        //Calling BootstrapDAO to batch process all valid students into database
	        int numOfRecords = BootstrapDAO.batchProcessClassList(validStudentArray);
	        request.setAttribute("numOfRecords", numOfRecords);
	        studentReader.close();
    }
        /*
       
        ==========================================================
            DELETING ALL UPLOADED FILES
        ==========================================================
         */
        String path = getServletContext().getRealPath("")
                + File.separator + "data";
        File uploadDirectory = new File(path);
        UploadController.emptyFolder(uploadDirectory);
        boolean error = false;
        // if no error
       
    	request.setAttribute("invalidStudents", invalidStudents);
    	
    	String[][] student_status = RegisterStatusDAO.retrieveRegisterStatus();
    	request.setAttribute("studentsRegistration", student_status);
    	RequestDispatcher view = request.getRequestDispatcher("bootstrapResult.jsp");
        view.forward(request, response);
        
        
        
       

  
        
        
        //BootstrapDAO.startBiddingRound();
        
        /*
        ==================================================================================
            RESULTS OF BOOTSTRAP TO BE PASSED ON TO BE DISPLAYED ON "bootstrapResult.jsp"
        ==================================================================================
         */
       /* HttpSession session = request.getSession();

        session.setAttribute("invalidStudents", invalidStudents);
        session.setAttribute("invalidCourses", invalidCourses);
        session.setAttribute("invalidSections", invalidSections);
        session.setAttribute("invalidPrerequisite", invalidPrereq);
        session.setAttribute("invalidCourseCompleted", invalidCourseCompleted);
        session.setAttribute("invalidBids", invalidBids);

        session.setAttribute("validStudents", studentBSResults);
        session.setAttribute("validCourses", courseBSResults);
        session.setAttribute("validSections", sectionBSResults);
        session.setAttribute("validPrerequisite", prereqBSResults);
        session.setAttribute("validCourseCompleted", ccBSResults);
        session.setAttribute("validBids", (bidRowNumber-invalidBids.size()-1));

        response.sendRedirect("bootstrapResults.jsp");*/

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
}
