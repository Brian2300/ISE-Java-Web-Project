package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.Transaction;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

/**
 * Servlet implementation class jsonDV
 */
@WebServlet("/jsonDV")
public class jsonDV extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public jsonDV() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	//writer to write the results
        PrintWriter writer = response.getWriter();

        //create gson printer to do pretty printing formatting of json object
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        //JSON array for error messages
        JsonArray errorlist = new JsonArray();

        //to store things to display
        JsonObject json = new JsonObject();


            json.addProperty("status", "error");  //status
            json.add("message", errorlist); //error messages
            writer.println(gson.toJson(json));
            return;
        }
    
    public static void printJson() {
    	//writer to write the results

        //create gson printer to do pretty printing formatting of json object
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        //JSON array for error messages
        JsonArray errorlist = new JsonArray();

        //to store things to display
        JsonObject json = new JsonObject();


            json.addProperty("status", "error");  //status
            json.add("message", errorlist); //error messages
            System.out.println(gson.toJson(json));
            return;
    }
	

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
