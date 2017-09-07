/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Checks whether the File passed in is a zip file
 * if not, return to fileUpload.jsp with error message
 * else unzip the zip file and get all the paths of the csv files and 
 * pass the csvList to the BootstrapController
 *  
 */
@WebServlet(name = "UploadController", urlPatterns = {"/UploadController"})
public class UploadController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        ArrayList<String> csvList = new ArrayList<String>();
        String DATA_DIRECTORY = "data";
        //int MAX_MEMORY_SIZE = 1024 * 1024 * 2; // 2MB
        //int MAX_REQUEST_SIZE = 1024 * 1024; // 1MB
        
        // Check that we have a file upload request
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        if (!isMultipart) {
            return;
        }

        // Create a factory for disk-based file items
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // Sets the size threshold beyond which files are written directly to
        // disk.
        // factory.setSizeThreshold(MAX_MEMORY_SIZE);
        // Sets the directory used to temporarily store files that are larger
        // than the configured size threshold. We use temporary directory for
        // java
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        // constructs the folder where uploaded file will be stored
        String uploadFolder = getServletContext().getRealPath("")
                + File.separator + DATA_DIRECTORY;
       // String uploadFolder = getServletContext().getRealPath("")
         //       + DATA_DIRECTORY;
        //System.out.println("Reached Upload Controller 2 "+uploadFolder);
        // if upload folder does not exist, create the folder
        if (!(new File(uploadFolder).exists())) {
            new File(uploadFolder).mkdir();
        } else {
            emptyFolder(new File(uploadFolder));
        }

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
        // Set overall request size constraint
//        upload.setSizeMax(MAX_REQUEST_SIZE);

        //ZipInputStream & FileOutputStream
        ZipInputStream in = null;
        FileOutputStream os = null;
        try {
            // Parse the request into a list of file items
        	List<FileItem> items = upload.parseRequest(request);
            // Store all the file items into an iterator
            Iterator<FileItem> iter = items.iterator();
 
            while (iter.hasNext()) {

                // Retrieves the current file item from the iterator object
                FileItem item = (FileItem) iter.next();

                // Check if the current item is NOT a form field (E.g. textfield, buttons, etc)
                if (!item.isFormField()) {

                    // Gets the name of the current file item
                    String fileName = new File(item.getName()).getName();

                    //if not zip file redirect back
                    if (!fileName.contains(".zip")) {
                        RequestDispatcher view = request.getRequestDispatcher("fileUpload.jsp");
                        request.setAttribute("fileUploadError", "Please only select zip files");
                        view.forward(request, response);
                        return;
                    }

                    // Set the file path for the current file to be stored at
                    String filePath = uploadFolder + File.separator + fileName;

                    // Create a file object with the file path as reference
                    //File uploadedFile = new File(filePath);
                    //System.out.println(filePath);
                    // saves the file to upload directory
                    //item.write(uploadedFile); removed so that no saving of the zip files

                    // --- UNZIPPING STARTS HERE ---
                    // Create a zip input stream object to read zipped folders
                    //in = new ZipInputStream(new FileInputStream(filePath));
                    in = new ZipInputStream(item.getInputStream());

                    // Create a zip entry object to be able to store each entries within the zipped folder
                    ZipEntry entry;

                    // Loop through the zipped folder to retrieve individual files in it
                    while ((entry = in.getNextEntry()) != null) {

                        // Get the name of the current entry
                        String entryName = entry.getName();

                        // Checks if the current entry is a directory (folder)
                        if (!entry.isDirectory()) {

                            String[] parts = entryName.split("/");

                            //ignoring the folder name when coming up with the path
                            String path = uploadFolder + File.separator + parts[parts.length - 1];

                            // Creates a File Output Stream object to prepare the system to writing files to our desired folder
                            os = new FileOutputStream(path);

                            // Transfer bytes from the ZIP file to the output file
                            byte[] buf = new byte[1024];

                            int len;

                            // Transferring in progress...
                            while ((len = in.read(buf)) > 0) {
                                os.write(buf, 0, len);
                            }
                            if (path.contains(".csv")) {
                                csvList.add(path);
                            }

                        }
                    }
                }
            }

        } catch (FileUploadException ex) {
            throw new ServletException(ex);

        } catch (Exception ex) {
        	 System.out.println("It throws a exception");
            throw new ServletException(ex);
        } finally {
        	System.out.println("It throws a exception reached finally");
        	if (in != null) {
                in.closeEntry();
            }
            

            if (os != null) {
                os.close();
            }
        }
        
        
        /*
        
            Redirect to bootstrap servlet for valdidation
        
         */
        RequestDispatcher view = request.getRequestDispatcher("/BootstrapController");
        request.setAttribute("csvList", csvList);
        view.forward(request, response);

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
/**
 * Remove all the empty files in the given parameter, file
 * @param file the file to be processed
 */
    public static void emptyFolder(File file) {
        //to end the recursive loop
        if (!file.exists()) {
            return;
        }

        //if directory, go inside and call recursively
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                //call recursively
                emptyFolder(f);
            }
        } else {
            //call delete to delete files and empty directory
            file.delete();
        }

    }

}
